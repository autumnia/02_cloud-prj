package com.autumnia.batch.demo.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DemoStepExcecutionListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.debug("");
		log.debug("----------------------------------------------------");
		log.debug("Before Starting the step");
		log.debug("step context: {}", stepExecution.getJobExecution().getExecutionContext() );	
		log.debug("job parameter : {}", stepExecution.getJobExecution().getJobParameters() );
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.debug("step context: {}", stepExecution.getJobExecution().getExecutionContext() );
		log.debug("After Ending the step");
		log.debug("----------------------------------------------------");
		log.debug("");
		return null;
	}
  
}
