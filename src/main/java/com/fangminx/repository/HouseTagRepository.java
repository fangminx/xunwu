package com.fangminx.repository;

import com.fangminx.entity.HouseTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {
    HouseTag findByNameAndHouseId(String name, Long houseId);

    List<HouseTag> findAllByHouseId(Long id);

    List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);
}