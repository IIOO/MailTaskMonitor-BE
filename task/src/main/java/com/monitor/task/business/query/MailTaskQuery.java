package com.monitor.task.business.query;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MailTaskQuery {
    private Long groupId;
    private String orderNo;
}
