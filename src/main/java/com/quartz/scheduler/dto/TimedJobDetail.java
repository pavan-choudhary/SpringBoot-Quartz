package com.infy.scheduler.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TimedJobDetail {
	
	@JsonProperty("job_name")
	private String jobName;
	private String group;
	private String description;
	
	@JsonProperty("trigger_date_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss Z")
	private Date dateTime;
	
	private RequestDetail request;
	
	public RequestDetail getRequest() {
		return request;
	}

	public void setRequest(RequestDetail request) {
		this.request = request;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}	
	
}
