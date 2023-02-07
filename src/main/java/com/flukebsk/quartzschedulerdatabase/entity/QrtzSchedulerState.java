package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "qrtz_scheduler_state")
public class QrtzSchedulerState implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "instance_name", nullable = false)
    private String instanceName;

    @Column(name = "last_checkin_time", nullable = false)
    private Long lastCheckinTime;

    @Column(name = "checkin_interval", nullable = false)
    private Long checkinInterval;

}
