package com.ntnt.dut.crawlerapi.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ta_schedule")
@Getter
@Setter
@AllArgsConstructor
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private long id;

    @Column(name = "t_code")
    private String code;

    @Column(name = "t_name")
    private String name;

    @Column(name = "f_credit")
    private float credit;

    @Column(name = "f_tuition")
    private float tuition;

    @Column(name = "b_payed")
    private boolean payed;

    @Column(name = "t_lecturer")
    private String lecturer;

    @Column(name = "t_schedule")
    private String schedule;

    @Column(name = "t_studying_week")
    private String studyingWeek;
}
