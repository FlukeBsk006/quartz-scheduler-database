package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "qrtz_fired_triggers")
public class QrtzFiredTriggers implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "entry_id", nullable = false)
    private String entryId;

    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Column(name = "trigger_group", nullable = false)
    private String triggerGroup;

    @Column(name = "instance_name", nullable = false)
    private String instanceName;

    @Column(name = "fired_time", nullable = false)
    private Long firedTime;

    @Column(name = "sched_time", nullable = false)
    private Long schedTime;

    @Column(name = "priority", nullable = false)
    private Long priority;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "job_name", nullable = true)
    private String jobName;

    @Column(name = "job_group", nullable = true)
    private String jobGroup;

    @Column(name = "is_nonconcurrent", nullable = true)
    private Boolean isNonconcurrent;

    @Column(name = "requests_recovery", nullable = true)
    private Boolean requestsRecovery;

}
