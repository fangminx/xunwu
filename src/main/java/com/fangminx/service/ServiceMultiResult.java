package com.fangminx.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 通用多结果Service返回结构
 * @param <T>
 */
@AllArgsConstructor
@Getter
@Setter
public class ServiceMultiResult<T> {
    private long total;
    private List<T> result;

    public int getResultSize(){
        if(this.result == null){
            return 0;
        }
        return this.result.size();
    }
}
