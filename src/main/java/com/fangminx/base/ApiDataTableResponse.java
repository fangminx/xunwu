package com.fangminx.base;

import lombok.Getter;
import lombok.Setter;

/**
 * DataTables响应结构
 */
@Getter
@Setter
public class ApiDataTableResponse extends ApiResponse {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;

    public ApiDataTableResponse(ApiResponse.Status status){
        this(status.getCode(),status.getStandardMessage(),null);
    }

    public ApiDataTableResponse(int code,String message,Object data){
        super(code,message,data);
    }
}
