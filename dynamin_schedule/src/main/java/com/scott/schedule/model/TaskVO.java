package com.scott.schedule.model;

import lombok.Data;

/**
 * @author scott
 * @date 2019/5/12 11:17
 */
@Data
public class TaskVO {

    private String taskClassName;

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

    private String cronExpression;
}