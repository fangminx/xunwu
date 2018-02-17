package com.fangminx.service;

import com.fangminx.web.controller.house.SupportAddressDTO;

/**
 * 地址服务接口
 */
public interface IAddressService {
    ServiceMultiResult<SupportAddressDTO> findAllCities();
}
