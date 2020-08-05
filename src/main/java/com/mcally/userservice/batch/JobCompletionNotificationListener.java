package com.mcally.userservice.batch;

import com.mcally.userservice.repository.PersonRepository;
import com.mcally.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private final UserRepository userRepository;

  private final PersonRepository personRepository;

  @Autowired
  public JobCompletionNotificationListener(UserRepository userRepository, PersonRepository personRepository) {
    this.userRepository = userRepository;
    this.personRepository = personRepository;
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
      log.info("----------------------------------------------");
      userRepository.findAll()
              .forEach(user -> log.info(user.toString()));
      log.info("----------------------------------------------");
      personRepository.findAll()
              .forEach(person -> log.info(person.toString()));
      log.info("----------------------------------------------");
      log.info("----------------------------------------------");


    }
  }
}