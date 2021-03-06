package com.fangminx.service;

/**
 * 检索接口
 */
public interface ISearchService {
    /**
     * 索引目标房源
     * @param houseId
     */
    boolean index(Long houseId);

    /**
     * 移除房源索引
     * @param houseId
     */
    void remove(Long houseId);
}
