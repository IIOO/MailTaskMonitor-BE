package com.monitor.task.service;

import com.monitor.task.StoreConnectionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.*;
import javax.mail.search.*;
import java.util.*;

@Service
@Slf4j
public class MailService {
    private StoreConnectionProperties storeConnectionProperties;
    private Store store;


    @Autowired
    public MailService(StoreConnectionProperties storeConnectionProperties) {
        this.storeConnectionProperties = storeConnectionProperties;
    }

    public Optional<List<Message>> getMails() {

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
            } );

            List<Message> messageList = Arrays.asList(messages);
            return Optional.of(messageList);
        } catch (MessagingException e) {
            log.error("Error during reading mail messages");
            e.printStackTrace();
        }
        return null;
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
        Session session = Session.getDefaultInstance(props);
        try {
            this.store = session.getStore("imaps");
            store.connect(storeConnectionProperties.getHost(), 993, storeConnectionProperties.getUsername(), storeConnectionProperties.getPassword());
        } catch (MessagingException e) {
            log.error("Couldn't connect to the store");
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void disconnect() throws MessagingException {
        this.store.close();
    }
}
