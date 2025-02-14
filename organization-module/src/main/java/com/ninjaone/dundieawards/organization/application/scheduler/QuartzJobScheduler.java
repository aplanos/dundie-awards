package com.ninjaone.dundieawards.organization.application.scheduler;

import com.ninjaone.dundieawards.organization.domain.entity.QuartzJob;
import com.ninjaone.dundieawards.organization.domain.enums.JobStatus;
import com.ninjaone.dundieawards.organization.infrastructure.messaging.broker.config.MessageBrokerProperties;
import com.ninjaone.dundieawards.organization.infrastructure.repository.QuartzJobRepository;
import com.ninjaone.dundieawards.organization.infrastructure.quartz.MessageBrokerSubscriptionJob;
import jakarta.annotation.PostConstruct;
import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "messaging.strategy", havingValue = "message_broker")
public class QuartzJobScheduler {

    private final Scheduler scheduler;
    private final QuartzJobRepository jobRepository;
    private final MessageBrokerProperties messageBrokerProperties;

    public QuartzJobScheduler(Scheduler scheduler,
                              QuartzJobRepository jobRepository,
                              MessageBrokerProperties messageBrokerProperties) {
        this.scheduler = scheduler;
        this.jobRepository = jobRepository;
        this.messageBrokerProperties = messageBrokerProperties;
    }

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler.start();
        scheduleJob("MESSAGE_BROKER_LISTENER", messageBrokerProperties.getPollingInterval());
    }

    public void scheduleJob(String jobName, String cronExpression) throws SchedulerException {

        final var jobDetail = JobBuilder.newJob(MessageBrokerSubscriptionJob.class)
                .withIdentity(jobName)
                .storeDurably()
                .build();

        final var trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "_TRIGGER")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        final var job = jobRepository.findByJobName(jobName)
                .orElse(new QuartzJob(jobName, cronExpression));

        job.setJobStatus(JobStatus.RUNNING);
        job.setCronExpression(cronExpression);

        jobRepository.save(job);
    }

    public void updateJobStatus(String jobName, JobStatus status) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobName));

        final var job = jobRepository.findByJobName(jobName)
                .orElseThrow(() -> new SchedulerException("Job not found"));

        job.setJobStatus(status);
        jobRepository.save(job);
    }
}
