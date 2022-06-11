package com.autumnia.batch.demo.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DemoJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.debug("");
		log.debug("----------------------------------------------------");
		log.debug("Before Starting the job");
		log.debug("jobname: {}", jobExecution.getJobInstance().getJobName());
		jobExecution.getExecutionContext().put("batch name", "my batch");
		log.debug("job context: {}", jobExecution.getExecutionContext().toString() );		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.debug("job context: {}", jobExecution.getExecutionContext().toString() );
		log.debug("After Ending the job");
		log.debug("----------------------------------------------------");
		log.debug("");
	}
	
}
