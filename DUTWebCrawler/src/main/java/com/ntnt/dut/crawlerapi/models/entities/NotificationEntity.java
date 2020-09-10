package com.ntnt.dut.crawlerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ntnt.dut.crawlerapi.enums.NotiType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ta_notification")
@Getter
@Setter
@NoArgsConstructor
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private Long id;

    @Column(name = "d_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date date;

    @Column(name = "t_title", columnDefinition = "TINYTEXT")
    private String title;

    @Column(name = "t_content",  columnDefinition = "TEXT")
    private String content;

    @Column(name = "t_type", length = 10)
    @Enumerated(EnumType.STRING)
    private NotiType type;
}
