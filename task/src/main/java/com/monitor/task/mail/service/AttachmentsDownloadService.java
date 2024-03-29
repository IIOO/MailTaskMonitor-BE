package com.monitor.task.mail.service;

import com.monitor.task.config.MailDownloadProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentsDownloadService {
    private final MailDownloadProperties mailDownloadProperties;

    public boolean downloadAttachments(Message message) {
        // store attachment file name, separated by comma
        StringBuilder attachFiles = new StringBuilder();
        try {
            String contentType = message.getContentType();

            if (contentType.contains("multipart")) {
                // content may contain attachments
                Multipart multiPart = (Multipart) message.getContent();
                for (int partCount = 0; partCount < multiPart.getCount(); partCount++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                        // this part is attachment
                        String fileName = part.getFileName();
                        attachFiles.append(fileName).append(", ");
                        part.saveFile(mailDownloadProperties.getPath() + File.separator + fileName);
                    }
                }
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return attachFiles.length() != 0;
    }
}
