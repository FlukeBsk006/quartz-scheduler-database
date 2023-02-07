package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "qrtz_cron_triggers")
public class QrtzCronTriggers implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Id
    @Column(name = "trigger_group", nullable = false)
    private String triggerGroup;

    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    @Column(name = "time_zone_id")
    private String timeZoneId;

}
