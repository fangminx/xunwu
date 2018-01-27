package com.fangminx.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //同时兼容h2
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String name;
}
