package com.flukebsk.quartzschedulerdatabase.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "qrtz_simprop_triggers")
public class QrtzSimpropTriggers implements Serializable {
    @Id
    @Column(name = "sched_name", nullable = false)
    private String schedName;

    @Id
    @Column(name = "trigger_name", nullable = false)
    private String triggerName;

    @Id
    @Column(name = "trigger_group", nullable = false)
    private String triggerGroup;

    @Column(name = "str_prop_1", nullable = true)
    private String strProp1;

    @Column(name = "str_prop_2", nullable = true)
    private String strProp2;

    @Column(name = "str_prop_3", nullable = true)
    private String strProp3;

    @Column(name = "int_prop_1", nullable = true)
    private Long intProp1;

    @Column(name = "int_prop_2", nullable = true)
    private Long intProp2;

    @Column(name = "long_prop_1", nullable = true)
    private Long longProp1;

    @Column(name = "long_prop_2", nullable = true)
    private Long longProp2;

    @Column(name = "dec_prop_1", precision = 13, scale = 4, nullable = true)
    private BigDecimal decProp1;

    @Column(name = "dec_prop_2", precision = 13, scale = 4, nullable = true)
    private BigDecimal decProp2;

    @Column(name = "bool_prop_1", nullable = true)
    private Boolean boolProp1;

    @Column(name = "bool_prop_2", nullable = true)
    private Boolean boolProp2;

}
