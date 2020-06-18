package com.infy.scheduler.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infy.scheduler.dto.CronJobDetail;
import com.infy.scheduler.service.JobService;
import com.infy.scheduler.service.NewJobDetail;

@Component
public class CronJobMaker {
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private NewJobDetail newJobdetail;
	
	public String createJob(CronJobDetail params) throws SchedulerException {
		if(jobService.isJobPresent(params.getJobName(), params.getGroup())) {
			return "exists";
		}
		else {
			JobDetail jobDetail = newJobdetail.newJobDetail(params.getRequest(),params.getJobName(),params.getGroup(),params.getDescription());
			scheduler.scheduleJob(jobDetail, cronTrigger(jobDetail, params.getCronExpression()));			
		}
		return "Created";
	}
	
	private CronTrigger cronTrigger(JobDetail jobDetail, String cronExpression) {
		return TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
				.build();
	}
}
