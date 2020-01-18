package com.monitor.task.business;

import com.monitor.task.business.dto.TaskGroupDto;
import com.monitor.task.business.persistance.CompanyEntity;
import com.monitor.task.business.persistance.MailTaskGroupEntity;

import java.util.Optional;

public class MailTaskGroupMapper {
    public static TaskGroupDto mapMailTaskGroupEntityToTaskGroupDto(MailTaskGroupEntity entity) {
        return TaskGroupDto.builder()
                .orderNo(entity.getOrderNo())
                .companyName(Optional.ofNullable(entity.getCompany()).map(CompanyEntity::getName).orElse(null))
                .build();
    }
}
