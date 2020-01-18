package com.monitor.task.mail;

import com.monitor.task.mail.dto.MailDto;
import com.monitor.task.mail.service.MailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.time.ZoneId;

public class MessageMapper {

    public static MailDto mapMessageToMailDto(Message message) {
        MailDto mail = null;
        try {
            String subject = readSubject(message);
            mail = MailDto.builder()
                    .uid(MailService.getInbox().getUID(message))
                    .orderNo(SubjectSearchUtil.findOrderNumber(subject))
                    .subject(subject)
                    .from(addressesToString(message))
                    .content(getTextFromMessage(message))
                    .numberOfAttachments(attachmentsCount(message))
                    .receivedDate(message.getReceivedDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime())
                    .build();
        } catch (MessagingException | IOException ex) {
            ex.printStackTrace();
        }
        return mail;
    }
//
//    public static MailTaskEntity mapMailDtoToMailTaskEntity (MailDto dto) {
//        return MailTaskEntity.builder()
//                .uid(dto.getUid())
//                .orderNo(dto.getOrderNo())
//                .subject(dto.getSubject())
//                .from(dto.getFrom())
//                .content(dto.getContent())
//                .numberOfAttachments(dto.getNumberOfAttachments())
//                .receivedDate(dto.getReceivedDate())
//                .build();
//    }

    private static String addressesToString(Message message) throws MessagingException {
        Address[] froms = message.getFrom();
        return froms == null ? null : ((InternetAddress) froms[0]).getAddress();
    }

    public static String readSubject(Message message) {
        try {
            return  message.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                result = (String) bodyPart.getContent();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }

    private static int attachmentsCount(Message message) {
        int counter = 0;
        try {
            String contentType = message.getContentType();
            if (contentType.contains("multipart")) {
                Multipart multiPart = (Multipart) message.getContent();
                for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                        counter++;
                    }
                }
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return counter;
    }
}
