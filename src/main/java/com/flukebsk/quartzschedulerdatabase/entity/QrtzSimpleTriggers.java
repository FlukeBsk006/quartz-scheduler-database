package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "qrtz_simple_triggers")
public class QrtzSimpleTriggers implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Id
    @Column(name = "trigger_group", nullable = false)
    private String triggerGroup;

    @Column(name = "repeat_count", nullable = false)
    private Long repeatCount;

    @Column(name = "repeat_interval", nullable = false)
    private Long repeatInterval;

    @Column(name = "times_triggered", nullable = false)
    private Long timesTriggered;

}
