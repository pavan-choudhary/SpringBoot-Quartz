package com.infy.scheduler.job;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteJob {
	@Autowired
	private Scheduler scheduler;
		
	public Boolean deleteJob(String jobName, String group) throws SchedulerException {
		return scheduler.deleteJob(new JobKey(jobName, group));
	}
}
