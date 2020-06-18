package com.infy.scheduler.service;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
	
	@Autowired
	private Scheduler scheduler;
	
	public Boolean isJobPresent(String jobName, String group) throws SchedulerException {
		JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName, group));
		if(jobDetail == null)
			return false;
		else 
			return true;
	}
}
