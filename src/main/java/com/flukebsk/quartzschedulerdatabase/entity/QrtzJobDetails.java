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
@Table(name = "qrtz_job_details")
public class QrtzJobDetails implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Id
    @Column(name = "job_group", nullable = false)
    private String jobGroup;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "job_class_name", nullable = false)
    private String jobClassName;

    @Column(name = "is_durable", nullable = false)
    private Boolean isDurable;

    @Column(name = "is_nonconcurrent", nullable = false)
    private Boolean isNonconcurrent;

    @Column(name = "is_update_data", nullable = false)
    private Boolean isUpdateData;

    @Column(name = "requests_recovery", nullable = false)
    private Boolean requestsRecovery;

    @Column(name = "job_data", nullable = true)
    private Blob jobData;

}
