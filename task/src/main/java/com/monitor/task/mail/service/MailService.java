package com.monitor.task.mail.service;

import com.monitor.task.config.StoreConnectionProperties;
import com.sun.mail.imap.IMAPFolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.*;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.search.FlagTerm;
import javax.mail.search.MessageNumberTerm;
import javax.mail.search.SearchTerm;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final StoreConnectionProperties storeConnectionProperties;
    private Store store;


    public Optional<List<Message>> getMails() {
        List<Message> messageList = null;
        try {
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );

            // Fetch unseen messages from inbox folder
            Message[] messages = inbox.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            // Sort messages from recent to oldest
            Arrays.sort( messages, (m1, m2 ) -> {
                try {
                    return m2.getSentDate().compareTo( m1.getSentDate() );
                } catch ( MessagingException e ) {
                    throw new RuntimeException( e );
                }
            });

            messageList = Arrays.asList(messages);
        } catch (MessagingException e) {
            log.error("Error during reading mail messages");
            e.printStackTrace();
        }
        return Optional.ofNullable(messageList);
    }

    public Message getMailByNumber(int messageNumber) {
        SearchTerm searchTerm = new MessageNumberTerm(messageNumber);

        try {
            Folder inbox = store.getFolder( "INBOX" );
            inbox.open( Folder.READ_ONLY );
            Message[] messages = inbox.search(searchTerm);
            for (Message message : messages) {
                if (message.getMessageNumber() == messageNumber) {
                    log.debug("Email with " + messageNumber + " found");
                    return message;
                }
            }
        } catch (MessagingException e) {
            log.error("Error during reading mail by number: " + messageNumber);
            e.printStackTrace();
        }
        return null;
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
        IMAPFolder inbox = (IMAPFolder) store.getFolder("Inbox");
        inbox.open(Folder.READ_WRITE);
        inbox.addMessageCountListener(new MessageCountAdapter() {
            @Override
                public void messagesAdded(MessageCountEvent ev) {
                    log.info("RECIEVED MAIL");
                    // save to DB
            }
        });
        new Thread(() -> {
            while (store.isConnected()) {
                try {
                    Thread.sleep(1000);
                    try {
                        inbox.idle();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    log.info("In loop");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
