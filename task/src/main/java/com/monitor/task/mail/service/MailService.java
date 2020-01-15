package com.monitor.task.mail.service;

import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.service.MailTaskService;
import com.monitor.task.config.StoreConnectionProperties;
import com.monitor.task.mail.MessageMapper;
import com.monitor.task.mail.SubjectSearchUtil;
import com.sun.mail.imap.IMAPFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private static final String FOLDER_NAME = "INBOX";
    private static IMAPFolder inbox;

    private final Message[] NO_MESSAGES = {};
    private final StoreConnectionProperties storeConnectionProperties;
    private final MailTaskService mailTaskService;

    private Store store;

    public List<Message> getMailsWithMatchingSubject() {
        return Arrays.asList(getMessages(SubjectSearchUtil.getSubjectSearchTerm()));
    }

    public List<Message> getMails() {
        return Arrays.asList(getMessages(null));
    }

    public Message getMailByNumber(int messageNumber) {
        Message[] messages = getMessages(SubjectSearchUtil.getMsgNumberSearchTerm(messageNumber));
        for (Message message : messages) {
            if (message.getMessageNumber() == messageNumber) {
                log.info("Email with " + messageNumber + " found");
                return message;
            }
        }
        return null;
    }

    private Message[] getMessages(javax.mail.search.SearchTerm searchTerm) {
        try {
            if (Objects.nonNull(searchTerm)) {
                return inbox.search(searchTerm);
            } else {
                return inbox.getMessages();
            }
        } catch (MessagingException e) {
            log.error("Error during reading mail messages");
            e.printStackTrace();
        }
        return NO_MESSAGES;
    }

    @PostConstruct
    private void connect() {
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.ssl.trust", "imap.gmail.com");
        // getDefaultInstance in order to use one session (otherwise to consider new props - use getInstance)
        Session session = Session.getDefaultInstance(props);
        try {
            this.store = session.getStore("imaps");
            store.connect(storeConnectionProperties.getHost(), 993, storeConnectionProperties.getUsername(), storeConnectionProperties.getPassword());
            log.info("Connected to mail server");
            inbox = (IMAPFolder) store.getFolder(FOLDER_NAME);
            log.info("UIDValidity for folder " + FOLDER_NAME + ": " + inbox.getUIDValidity());
            setMessageCountListener();

        } catch (MessagingException e) {
            log.error("Couldn't connect to the store");
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void disconnect() throws MessagingException {
        this.store.close();
        log.info("Disconnected form mail server");
    }

    private void setMessageCountListener() throws MessagingException{
        inbox.open(Folder.READ_WRITE);
        inbox.addMessageCountListener(new MessageCountAdapter() {
            @Override
                public void messagesAdded(MessageCountEvent ev) {
                    log.info("RECEIVED MAIL (Before subject & from check)");
                    Arrays.stream(ev.getMessages())
                            .filter(msg -> SubjectSearchUtil.stringMatchSubjectPattern(MessageMapper.readSubject(msg)))
                            .map(MessageMapper::mapMessageToTaskDto)
                            .forEach(task -> {
                                MailTaskEntity saved = mailTaskService.saveMappedMailTaskToDb(task);
                                log.info("NEW MAIL SAVED TO DB: " + saved.getUid());
                            });
            }
        });
        new Thread(() -> {
            while (inbox.isOpen()) {
                // poke server with a inbox.getMessageCount() to keep the connection active/open
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                final Runnable pokeInbox = () -> {
                    try {
                        inbox.getMessageCount();
                    } catch (MessagingException ex) {
                        ex.printStackTrace();
                    }
                };
                scheduler.schedule(pokeInbox, 20, TimeUnit.MINUTES);
                try {
                    inbox.idle();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static IMAPFolder getInbox() {
        if (inbox.isOpen()) {
            return inbox;
        } else {
            log.error("Folder " + FOLDER_NAME + " is closed!");
        }
        return inbox;
    }
}
