package com.ntnt.dut.crawlerapi.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ta_score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private long id;

    @Column(name = "t_semester", length = 12)
    private String semester;

    @Column(name = "t_code")
    private String code;

    @Column(name = "t_subject")
    private String subject;

    @Column(name = "i_credit")
    private float credit;

    @Column(name = "f_score_1")
    private float score1;

    @Column(name = "f_score_2")
    private float score2;

    @Column(name = "f_score_3")
    private float score3;

    @Column(name = "f_score_4")
    private float score4;

    @Column(name = "f_score_5")
    private float score5;

    @Column(name = "f_score_6")
    private float score6;

    @Column(name = "f_score_7")
    private float score7;

    @Column(name = "f_score_8")
    private float score8;

    @Column(name = "t_score_9", length = 3)
    private String score9;
}
