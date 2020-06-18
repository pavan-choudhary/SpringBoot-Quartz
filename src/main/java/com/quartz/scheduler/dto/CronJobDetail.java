package com.infy.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CronJobDetail {

	@JsonProperty("job_name")
	private String jobName;
	private String group;
	private String description;

	@JsonProperty("cron_expression")
	private String cronExpression;

	private RequestDetail request;

	public String getJobName() {
		return jobName;
	}

	public String getGroup() {
		return group;
	}

	public String getDescription() {
		return description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public RequestDetail getRequest() {
		return request;
	}
	
}
