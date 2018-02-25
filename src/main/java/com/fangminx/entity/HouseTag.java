package com.fangminx.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "house_tag")
@Data
@NoArgsConstructor
public class HouseTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_id")
    private Long houseId;

    private String name;

    public HouseTag(Long houseId, String name) {
        this.houseId = houseId;
        this.name = name;
    }
}
