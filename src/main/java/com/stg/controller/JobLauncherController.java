package com.stg.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Enumeration;

/**
 * Job Launcher Controller.
 *
 * ex. http://localhost:8080/jobLauncher?job=processEodJob
 *
 * Created by dqromney on 3/28/17.
 */
public class JobLauncherController {
    private static final String JOB_PARAM = "job";

    private JobLauncher jobLauncher;

    private JobRegistry jobRegistry;

    public JobLauncherController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        super();
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @RequestMapping(value="jobLauncher", method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void launch(@RequestParam String job, HttpServletRequest request) throws Exception {
        JobParametersBuilder builder = extractParameters(request);
        jobLauncher.run(jobRegistry.getJob(request.getParameter(JOB_PARAM)), builder.toJobParameters());
    }

    private JobParametersBuilder extractParameters(HttpServletRequest request) {
        JobParametersBuilder builder = new JobParametersBuilder();
        Enumeration<String> paramNames = request.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if(!JOB_PARAM.equals(paramName)) {
                builder.addString(paramName, request.getParameter(paramName));
            }
        }
        return builder;
    }
}
