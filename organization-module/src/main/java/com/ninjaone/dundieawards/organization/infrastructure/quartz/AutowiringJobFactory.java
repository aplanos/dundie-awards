package com.ninjaone.dundieawards.organization.infrastructure.quartz;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AutowiringJobFactory implements JobFactory {

    private final ApplicationContext applicationContext;

    public AutowiringJobFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
        return applicationContext.getBean(bundle.getJobDetail().getJobClass());
    }
}
