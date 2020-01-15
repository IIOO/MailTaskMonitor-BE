package com.monitor.task;

import com.monitor.task.business.persistance.MailAddressEntity;
import com.monitor.task.business.persistance.MailTaskEntity;
import com.monitor.task.business.service.MailTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class MailTaskServiceTests extends TaskApplicationTests {
    @Autowired
    MailTaskService mailTaskService;

    @Test
    void canSaveMailTaskWithCorrectFrom() {
        MailTaskEntity task = MailTaskEntity.builder()
                .uid(50L)
                .orderNo(1L)
                .from(new MailAddressEntity("random@gmail.com"))
                .subject("Test subject")
                .content("Text content")
                .numberOfAttachments(0)
                .build();
        mailTaskService.save(task);
        assertThat(task.getFrom().getAddress()).isEqualTo("random@gmail.com");
    }

    @Test
    void findAllMailsByCompanyName() {
        List<MailTaskEntity> mails = mailTaskService.findTasksByCompanyName("TestPOL");
        log.info("Found " + mails.size() + " mail tasks");
        assertThat(mails.size()).isNotEqualTo(0);
    }

    @Test
    void findAllMailsByFromAddress() {
        List<MailTaskEntity> mails = mailTaskService.findTasksByFromAddress("random@gmail.com");
        log.info("Found " + mails.size() + " mail tasks");
        assertThat(mails.size()).isNotEqualTo(0);
    }
}
