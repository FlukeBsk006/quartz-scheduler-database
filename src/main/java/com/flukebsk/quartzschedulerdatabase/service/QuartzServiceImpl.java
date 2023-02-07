package com.flukebsk.quartzschedulerdatabase.service;

import com.flukebsk.quartzschedulerdatabase.entity.HistoryJobEntity;
import com.flukebsk.quartzschedulerdatabase.model.JobDescriptor;
import com.flukebsk.quartzschedulerdatabase.model.JobUpdate;
import com.flukebsk.quartzschedulerdatabase.model.TriggerDescriptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.quartz.JobKey.jobKey;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuartzServiceImpl implements QuartzService {

    private final Scheduler scheduler;

    private final HistoryJobService historyJobService;

//    public JobDescriptor createJob(String group, JobDescriptor descriptor) {
//        descriptor.setGroup(group);
//        JobDetail jobDetail = descriptor.buildJobDetail();
//        Set<Trigger> triggersForJob = descriptor.buildTriggers();
//        log.info("About to save job with key - {}", jobDetail.getKey());
//        try {
//            scheduler.scheduleJob(jobDetail, triggersForJob, false);
//            log.info("Job with key - {} saved successfully", jobDetail.getKey());
//        } catch (SchedulerException e) {
//            log.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(), e.getLocalizedMessage());
//            throw new IllegalArgumentException(e.getLocalizedMessage());
//        }
//        return descriptor;
//    }

    public JobDescriptor createJob(String group, JobDescriptor descriptor) throws ClassNotFoundException {
        Class<?> className = Class.forName(descriptor.getJobClass());
        descriptor.setGroup(group);
        JobDetail jobDetail = descriptor.buildJobDetail(className);
        Set<Trigger> triggersForJob = descriptor.buildTriggers();
        log.info("About to save job with key - {}", jobDetail.getKey());
        try {
            scheduler.scheduleJob(jobDetail, triggersForJob, false);

            for (TriggerDescriptor triggerDescriptor : descriptor.getTriggerDescriptors()) {

                HistoryJobEntity historyJobEntity = new HistoryJobEntity();
                historyJobEntity.setHisJobClass(descriptor.getJobClass());
                historyJobEntity.setHisGroup(descriptor.getGroup());
                historyJobEntity.setHisName(descriptor.getName());
                historyJobEntity.setHisCronName(triggerDescriptor.getName());
                historyJobEntity.setHisNewCron(triggerDescriptor.getCron());
                historyJobEntity.setHisOldCron(triggerDescriptor.getCron());
                historyJobEntity.setHisDateUpdate(new Date());
                historyJobEntity.setHisType("created");

                historyJobService.createHistory(historyJobEntity);
            }

            log.info("Job with key - {} saved successfully", jobDetail.getKey());
        } catch (SchedulerException e) {
            log.error("Could not save job with key - {} due to error - {}", jobDetail.getKey(), e.getLocalizedMessage());
            throw new IllegalArgumentException(e.getLocalizedMessage());
        }
        return descriptor;
    }

    public List<JobDescriptor> findAllJobs() {
        List<JobDescriptor> jobList = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    String name = jobKey.getName();
                    String group = jobKey.getGroup();
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
                    List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
                    jobList.add(JobDescriptor.buildDescriptor(jobDetail, triggers, scheduler));
                }
            }
        } catch (SchedulerException e) {
            log.error("Could not find all jobs due to error - {}", e.getLocalizedMessage());
        }
        return jobList;
    }

    @Transactional(readOnly = true)
    public Optional<JobDescriptor> findJob(String group, String name) {
        try {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey(name, group));
            if (Objects.nonNull(jobDetail)) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
                return Optional.of(
                        JobDescriptor.buildDescriptor(jobDetail, triggers, scheduler));
            }
        } catch (SchedulerException e) {
            log.error("Could not find job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
        log.warn("Could not find job with key - {}.{}", group, name);
        return Optional.empty();
    }

    public Optional<JobDetail> updateJob(String group, String name, JobUpdate jobUpdate) {
        try {
            JobDetail oldJobDetail = scheduler.getJobDetail(jobKey(name, group));
            if (Objects.nonNull(oldJobDetail)) {
                String replaceJobClass = String.valueOf(oldJobDetail.getJobClass()).replace("class ", "");

                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(oldJobDetail.getKey());

                List<TriggerDescriptor> triggerDescriptorsOld = JobDescriptor.buildDescriptor(oldJobDetail, triggers, scheduler).getTriggerDescriptors();
                List<String> triggerNameOldList = triggerDescriptorsOld.stream()
                        .map(TriggerDescriptor::getName)
                        .collect(Collectors.toList());

                List<String> triggerNameNewList = jobUpdate.getTriggerDescriptors().stream()
                        .map(TriggerDescriptor::getName)
                        .collect(Collectors.toList());

                List<String> triggerCronOldList = triggerDescriptorsOld.stream()
                        .map(TriggerDescriptor::getCron)
                        .collect(Collectors.toList());

                List<TriggerDescriptor> triggerDescriptorsNew = jobUpdate.getTriggerDescriptors();

                scheduler.deleteJob(jobKey(name, group));

                for (TriggerDescriptor trigger : triggerDescriptorsNew) {
                    HistoryJobEntity historyJobEntity = new HistoryJobEntity();
                    historyJobEntity.setHisJobClass(replaceJobClass);
                    historyJobEntity.setHisGroup(group);
                    historyJobEntity.setHisName(name);
                    historyJobEntity.setHisCronName(trigger.getName());
                    historyJobEntity.setHisNewCron(trigger.getCron());
                    historyJobEntity.setHisDateUpdate(new Date());
//
                    if(triggerNameOldList.contains(trigger.getName())){
                        historyJobEntity.setHisOldCron(triggerCronOldList.get(triggerNameOldList.indexOf(trigger.getName())));
                        historyJobEntity.setHisType("updated compare");
                    }else{
                        historyJobEntity.setHisType("updated add");
                    }

                    historyJobService.createHistory(historyJobEntity);
                }

                triggerNameOldList.removeAll( triggerNameNewList );

                for (String cronName : triggerNameOldList) {
                    HistoryJobEntity historyJobEntity = new HistoryJobEntity();
                    historyJobEntity.setHisJobClass(replaceJobClass);
                    historyJobEntity.setHisGroup(group);
                    historyJobEntity.setHisName(name);
                    historyJobEntity.setHisCronName(cronName);
                    historyJobEntity.setHisDateUpdate(new Date());
                    historyJobEntity.setHisType("updated delete");
                    historyJobService.createHistory(historyJobEntity);
                }

                Set<Trigger> triggersForJob = jobUpdate.buildTriggers();
                scheduler.scheduleJob(oldJobDetail, triggersForJob, false);

                JobBuilder jb = oldJobDetail.getJobBuilder();
                JobDetail newJobDetail = jb
                        .storeDurably()
                        .build();

                log.info("Updated job with key - {}", newJobDetail.getKey());

                return Optional.of(newJobDetail);
            }
            log.warn("Could not find job with key - {}.{} to update", group, name);
        } catch (SchedulerException e) {
            log.error("Could not find job with key - {}.{} to update due to error - {}", group, name, e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public void deleteJob(String group, String name) {
        try {
            JobDetail oldJobDetail = scheduler.getJobDetail(jobKey(name, group));

            String replaceJobClass = String.valueOf(oldJobDetail.getJobClass()).replace("class ", "");

            HistoryJobEntity historyJobEntity = new HistoryJobEntity();
            historyJobEntity.setHisJobClass(replaceJobClass);
            historyJobEntity.setHisGroup(group);
            historyJobEntity.setHisName(name);
            historyJobEntity.setHisDateUpdate(new Date());
            historyJobEntity.setHisType("deleted");

            historyJobService.createHistory(historyJobEntity);

            scheduler.deleteJob(jobKey(name, group));

            log.info("Deleted job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            log.error("Could not delete job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    public void pauseJob(String group, String name) {
        try {
            scheduler.pauseJob(jobKey(name, group));
            log.info("Paused job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            log.error("Could not pause job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

    public void resumeJob(String group, String name) {
        try {
            scheduler.resumeJob(jobKey(name, group));
            log.info("Resumed job with key - {}.{}", group, name);
        } catch (SchedulerException e) {
            log.error("Could not resume job with key - {}.{} due to error - {}", group, name, e.getLocalizedMessage());
        }
    }

}
