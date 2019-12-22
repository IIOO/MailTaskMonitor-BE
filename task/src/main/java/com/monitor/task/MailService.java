package com.monitor.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.search.*;
import java.util.*;

@Service
public class MailService {
    private Logger log = Logger.getLogger(MailService.class);

    @Autowired
    StoreConnectionProperties storeConnectionProperties;

    Optional<List<Message>> getMails() throws Exception{
        Store store = connect();
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
    }


    Message getMailByNumber(int messageNumber) throws Exception {
        SearchTerm searchTerm = new MessageNumberTerm(messageNumber);

        Store store = connect();
        Folder inbox = store.getFolder( "INBOX" );
        inbox.open( Folder.READ_ONLY );
        Message[] messages = inbox.search(searchTerm);
        for (Message message : messages) {
            if (message.getMessageNumber() == messageNumber) {
                log.debug("Email with " + messageNumber + " found");
                return message;
            }
        }
        return null;
    }

    private Store connect() throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.ssl.trust", "imap.gmail.com");
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("imaps");
        store.connect(storeConnectionProperties.getHost(), 993, storeConnectionProperties.getUsername(), storeConnectionProperties.getPassword());
        return store;
    }
}
