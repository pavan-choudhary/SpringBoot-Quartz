package com.infy.scheduler.service;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.springframework.stereotype.Service;

import com.infy.scheduler.dto.RequestDetail;
import com.infy.scheduler.job.action.JobAction;

@Service
public class NewJobDetail {
	public JobDetail newJobDetail(RequestDetail request, String jobName, String group, String description) {
		JobDataMap jobDetail= new JobDataMap();
		jobDetail.put("url", request.getRequestUrl());
		jobDetail.put("method", request.getHttpMethod());
		jobDetail.put("body", request.getBody());
		jobDetail.put("headers", request.getHeaders());
		return JobBuilder.newJob()
				.ofType(JobAction.class)
				.storeDurably()
				.usingJobData(jobDetail)
				.withIdentity(new JobKey(jobName, group))
				.withDescription(description)
				.build();
	}
}
