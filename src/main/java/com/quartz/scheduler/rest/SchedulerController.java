package com.infy.scheduler.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infy.scheduler.dto.CronJobDetail;
import com.infy.scheduler.dto.IntervalJobDetail;
import com.infy.scheduler.dto.TimedJobDetail;
import com.infy.scheduler.job.CronJobMaker;
import com.infy.scheduler.job.DeleteJob;
import com.infy.scheduler.job.IntervalJobMaker;
import com.infy.scheduler.job.TimedJobMaker;

@RestController
@RequestMapping("/api")
public class SchedulerController {

	@Autowired
	IntervalJobMaker intervalJob = new IntervalJobMaker();

	@Autowired
	TimedJobMaker timedJob = new TimedJobMaker();
	
	@Autowired
	CronJobMaker cronJob = new CronJobMaker();

	@Autowired
	DeleteJob deleteJob;

	@Autowired
	private Scheduler scheduler;

	private final Logger LOG = Logger.getLogger(SchedulerController.class);

	@PostMapping("/newIntervalJob")
	public ResponseEntity<String> newIntervalJob(@RequestBody IntervalJobDetail params) throws SchedulerException {
		String status = intervalJob.createJob(params);
		if (status == "Created")
			return new ResponseEntity<String>("Job created", HttpStatus.CREATED);
		else if (status == "exists")
			return new ResponseEntity<String>("Job Already Exists", HttpStatus.CONFLICT);
		
		return new ResponseEntity<String>("Cannot create job", HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/newTimedJob")
	public ResponseEntity<String> newTimedJob(@RequestBody TimedJobDetail params) throws SchedulerException {
		String status = timedJob.createJob(params);
		if (status == "Created")
			return new ResponseEntity<String>("Job created", HttpStatus.CREATED);
		else if (status == "exists")
			return new ResponseEntity<String>("Job Already Exists", HttpStatus.CONFLICT);
		
		return new ResponseEntity<String>("Cannot create job", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/newCronJob")
	public ResponseEntity<String> newCronJob(@RequestBody CronJobDetail params) throws SchedulerException {
		String status = cronJob.createJob(params);
		if (status == "Created")
			return new ResponseEntity<String>("Job created", HttpStatus.CREATED);
		else if (status == "exists")
			return new ResponseEntity<String>("Job Already Exists", HttpStatus.CONFLICT);
		
		return new ResponseEntity<String>("Cannot create job", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/deleteJob")
	public ResponseEntity<String> deleteJob(@RequestParam String job_name, @RequestParam String group)
			throws SchedulerException {
		if (deleteJob.deleteJob(job_name, group)) {
			LOG.info("Job: " + job_name + ", Group: " + group + " deleted");
			return new ResponseEntity<String>("Job deleted", HttpStatus.OK);
		}
		LOG.info("Job: " + job_name + ", Group: " + group + " NOT FOUND");
		return new ResponseEntity<String>("Job not found", HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getJobs")
	public List<String> getAllJobs() throws SchedulerException {
		List<String> jobs = new ArrayList<String>();
		for (String groupName : scheduler.getJobGroupNames()) {
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
				jobs.add(scheduler.getJobDetail(jobKey).getJobDataMap().get("url").toString());
			}
		}
		return jobs;
	}

}
