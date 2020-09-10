package com.ntnt.dut.crawlerapi.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ta_user")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private Long id;

    @Column(name = "t_username")
    private String username;

    @Column(name = "t_password")
    private String password;

    @Column(name = "t_code")
    private String code;

    @Column(name = "t_crawled_web_cookie")
    private String crawledWebCookie;
}
