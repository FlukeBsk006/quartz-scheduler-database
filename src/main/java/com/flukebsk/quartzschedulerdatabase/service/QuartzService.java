package com.flukebsk.quartzschedulerdatabase.service;

import com.flukebsk.quartzschedulerdatabase.model.JobDescriptor;
import org.quartz.JobDetail;

import java.util.List;
import java.util.Optional;

public interface QuartzService {

//    JobDescriptor createJob(String group, JobDescriptor descriptor);

    JobDescriptor createJob(String group, JobDescriptor descriptor) throws ClassNotFoundException;

    List<JobDescriptor> findAllJobs();

    Optional<JobDescriptor> findJob(String group, String name);

    Optional<JobDetail> updateJob(String group, String name, JobDescriptor descriptor);

    void deleteJob(String group, String name);

    void pauseJob(String group, String name);

    void resumeJob(String group, String name);
}
