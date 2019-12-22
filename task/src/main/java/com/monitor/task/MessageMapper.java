package com.monitor.task;

import com.monitor.task.dto.TaskPreviewDto;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

public class MessageMapper {
    public static TaskPreviewDto mapMessageToTaskDto(Message message) {
        return TaskPreviewDto.builder()
            .id(message.getMessageNumber())
            .from(addressesToString(message))
            .subject(readSubject(message))
            .build();
    }

    private static String addressesToString(Message message) {
        StringBuilder fromString = new StringBuilder();
        try {
            for ( Address adr : message.getFrom()) {
                fromString.append(adr.toString());
                fromString.append(", ");
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

        return fromString.toString();
    }

    private static String readSubject(Message message) {
        String subject = "";
        try {
            subject = message.getSubject();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return subject;
    }
}
