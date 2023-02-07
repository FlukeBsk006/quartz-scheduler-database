package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Blob;

@Data
@Entity
@Table(name = "qrtz_triggers")
public class QrtzTriggers implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Id
    @Column(name = "trigger_group", nullable = false)
    private String triggerGroup;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "job_group", nullable = false)
    private String jobGroup;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "next_fire_time", nullable = true)
    private Long nextFireTime;

    @Column(name = "prev_fire_time", nullable = true)
    private Long prevFireTime;

    @Column(name = "priority", nullable = true)
    private Long priority;

    @Column(name = "trigger_state", nullable = false)
    private String triggerState;

    @Column(name = "trigger_type", nullable = false)
    private String triggerType;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time", nullable = true)
    private Long endTime;

    @Column(name = "calendar_name", nullable = true)
    private String calendarName;

    @Column(name = "misfire_instr", nullable = true)
    private Integer misfireInstr;

    @Column(name = "job_data", nullable = true)
    private Blob jobData;

}
