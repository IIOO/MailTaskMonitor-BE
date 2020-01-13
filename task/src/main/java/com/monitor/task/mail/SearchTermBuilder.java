package com.monitor.task.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.MessageNumberTerm;
import javax.mail.search.SearchTerm;
import java.util.regex.Pattern;

public class SearchTermBuilder {
    private static final Pattern SUBJECT_PATTERN = Pattern.compile("([Zz]lecenie)[\\s](odprawy)[\\s](numer|nr)[\\s:\\.](\\d+)");

    public static SearchTerm getSubjectSearchTerm() {
        return new SearchTerm() {
            @Override
            public boolean match(Message message) {
                try {
                    return stringMatchSubjectPattern(message.getSubject());
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        };
    }

    public static SearchTerm getMsgNumberSearchTerm(int msgNumber) {
        return new MessageNumberTerm(msgNumber);
    }

    public static boolean stringMatchSubjectPattern(String str) {
        return SUBJECT_PATTERN.matcher(str).matches();
    }
}
