package com.fangminx.service;

import com.fangminx.entity.SupportAddress;
import com.fangminx.web.controller.house.SupportAddressDTO;

import java.util.Map;

/**
 * 地址服务接口
 */
public interface IAddressService {
    /**
     * 获取所有支持的城市列表
     * @return
     */
    ServiceMultiResult<SupportAddressDTO> findAllCities();

    /**
     * 根据英文简写获取具体区域的信息
     * @param cityEnName
     * @param regionEnName
     * @return
     */
    Map<SupportAddress.Level, SupportAddressDTO> findCityAndRegion(String cityEnName, String regionEnName);
}
