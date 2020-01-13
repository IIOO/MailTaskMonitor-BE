package com.monitor.task.mail.service;

import com.monitor.task.config.StoreConnectionProperties;
import com.monitor.task.mail.MessageMapper;
import com.monitor.task.mail.SearchTermBuilder;
import com.sun.mail.imap.IMAPFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.search.SearchTerm;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final Message[] NO_MESSAGES = {};
    private final StoreConnectionProperties storeConnectionProperties;

    private Store store;
    private IMAPFolder inbox;

    public List<Message> getMailsWithMatchingSubject() {
        Message[] messages = getMessages(SearchTermBuilder.getSubjectSearchTerm());
        return Arrays.asList(messages);
    }

    public List<Message> getMails() {
        Message[] messages = getMessages(null);
        return Arrays.asList(messages);
    }

    public Message getMailByNumber(int messageNumber) {
        Message[] messages = getMessages(SearchTermBuilder.getMsgNumberSearchTerm(messageNumber));
        for (Message message : messages) {
            if (message.getMessageNumber() == messageNumber) {
                log.info("Email with " + messageNumber + " found");
                return message;
            }
        }
        return null;
    }

    private Message[] getMessages(SearchTerm searchTerm) {
        try {
//            Folder inbox = store.getFolder( "INBOX" );
//            inbox.open( Folder.READ_ONLY );
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
            this.inbox = (IMAPFolder) store.getFolder("INBOX");
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

    private Message[] sortMessages(Message[] messages) {
        // Sort messages from recent to oldest
        Arrays.sort( messages, (m1, m2 ) -> {
            try {
                return m2.getSentDate().compareTo( m1.getSentDate() );
            } catch ( MessagingException e ) {
                throw new RuntimeException( e );
            }
        });
        return messages;
    }

    private void setMessageCountListener() throws MessagingException{
        inbox.open(Folder.READ_WRITE);
        inbox.addMessageCountListener(new MessageCountAdapter() {
            @Override
                public void messagesAdded(MessageCountEvent ev) {
                    log.info("RECIEVED MAIL");
                    //TODO save to DB
                    Arrays.stream(ev.getMessages())
                            .filter(msg -> SearchTermBuilder.stringMatchSubjectPattern(MessageMapper.readSubject(msg)))
                            .collect(Collectors.toList());
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
}
