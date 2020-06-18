package com.infy.scheduler.job;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infy.scheduler.dto.TimedJobDetail;
import com.infy.scheduler.service.JobService;
import com.infy.scheduler.service.NewJobDetail;

@Component
public class TimedJobMaker {
	
	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private NewJobDetail newJobdetail;
	
	public String createJob(TimedJobDetail params) throws SchedulerException {
		if(jobService.isJobPresent(params.getJobName(), params.getGroup())) {
			return "exists";
		}
		else {
			JobDetail job = newJobdetail.newJobDetail(params.getRequest(),params.getJobName(),params.getGroup(),params.getDescription());
			scheduler.scheduleJob(job, trigger(job, params.getDateTime()));			
		}
		return "Created";
	}

	private SimpleTrigger trigger(JobDetail jobDetail, Date triggerStartTime) {
		return TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
				.startAt(triggerStartTime)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule())
				.build();
	}
}
