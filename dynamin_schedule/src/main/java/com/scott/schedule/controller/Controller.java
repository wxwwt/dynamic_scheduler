package com.scott.schedule.controller;

import com.scott.schedule.model.TaskVO;
import com.scott.schedule.service.ScheduleTaskJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static org.quartz.CronScheduleBuilder.cronSchedule;


/**
 * @author scott
 * @date 2019/5/2 15:05
 */
@Slf4j
@RestController
public class Controller {

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    @PostMapping(value = "/api/executeTask")
    public String executeTask(TaskVO taskVO) {
        // job类
        JobDetail jobDetail = JobBuilder.newJob(ScheduleTaskJob.class)
               .withIdentity(taskVO.getJobName(), taskVO.getJobGroupName())
                .build();
        // 触发器类
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(taskVO.getTriggerName(), taskVO.getTriggerGroupName())
                .startNow()
                .withSchedule(cronSchedule(taskVO.getCronExpression()))
                .build();
        try {
            // 执行任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("任务执行异常", e);
        }
        return "success";
    }
}