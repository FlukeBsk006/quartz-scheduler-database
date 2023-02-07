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
@Table(name = "qrtz_blob_triggers")
public class QrtzBlobTriggers implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Id
    @Column(name = "trigger_group", nullable = false)
    private String triggerGroup;

    @Column(name = "blob_data", nullable = true)
    private Blob blobData;

}
