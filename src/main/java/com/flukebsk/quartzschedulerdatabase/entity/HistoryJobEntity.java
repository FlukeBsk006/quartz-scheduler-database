package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "history_job")
public class HistoryJobEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "his_id", nullable = false)
    private Long hisId;

    @Column(name = "his_group", nullable = false)
    private String hisGroup;

    @Column(name = "his_name", nullable = false)
    private String hisName;

    @Column(name = "his_cron_name", nullable = true)
    private String hisCronName;

    @Column(name = "his_type", nullable = false)
    private String hisType;

    @Column(name = "his_job_class", nullable = false)
    private String hisJobClass;

    @Column(name = "his_old_cron", nullable = true)
    private String hisOldCron;

    @Column(name = "his_new_cron", nullable = true)
    private String hisNewCron;

    @Column(name = "his_date_update", nullable = false)
    private Date hisDateUpdate;

}
