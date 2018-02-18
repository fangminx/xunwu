package com.fangminx.service;

import com.fangminx.web.dto.HouseDTO;
import com.fangminx.web.form.DatatableSearch;
import com.fangminx.web.form.HouseForm;

public interface IHouseService {

    /**
     * 新增
     * @param houseForm
     * @return
     */
    ServiceResult<HouseDTO> save(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody);
}
