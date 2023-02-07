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
@Table(name = "qrtz_calendars")
public class QrtzCalendars implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "calendar_name", nullable = false)
    private String calendarName;

    @Column(name = "calendar", nullable = false)
    private Blob calendar;

}
