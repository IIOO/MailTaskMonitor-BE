package com.monitor.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.*;

@Service
public class MailService {
    @Autowired
    StoreConnectionProperties storeConnectionProperties;

    Optional<List<Message>> getMails() throws Exception{
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true");
        props.setProperty("mail.imaps.ssl.trust", "imap.gmail.com");
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("imaps");
        store.connect(storeConnectionProperties.getHost(), 993, storeConnectionProperties.getUsername(), storeConnectionProperties.getPassword());
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

        List<Message> messageList = new ArrayList<>();
        for ( Message message : messages ) {
            System.out.println(
                    "sendDate: " + message.getSentDate()
                            + " subject:" + message.getSubject() );
            messageList.add(message);
        }
        return Optional.of(messageList);
    }
}
