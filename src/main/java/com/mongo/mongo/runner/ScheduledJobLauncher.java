package com.mongo.mongo.runner;


import com.mongo.mongo.domain.enums.JobParametersKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author aek
 */
@Component
public class ScheduledJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledJobLauncher.class);

    @Value("${batch.csv-path-directory}")
    private String pathDirectory;
    @Autowired
    private  Job tripJob;


    private final JobLauncher jobLauncher;

    ScheduledJobLauncher(Job job, JobLauncher jobLauncher) {
        this.tripJob = job;
        this.jobLauncher = jobLauncher;
    }

    // run every 2 min
   // @Scheduled(fixedRate = 120000)
    @Scheduled(fixedRate = 2000)
    void launchFileToJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {
        LOGGER.info("Scheduled Starting job");

        JobParameters params = new JobParametersBuilder()
                .addLong(JobParametersKey.JOB_ID.getKey(), System.currentTimeMillis())
                .addDate(JobParametersKey.CURRENT_TIME.getKey(),new Date())
                .addString(JobParametersKey.PATH_DIRECTORY.getKey(), pathDirectory)
                .toJobParameters();

        jobLauncher.run(tripJob, params);

       // LOGGER.info("Scheduled Stopping job");
    }

}