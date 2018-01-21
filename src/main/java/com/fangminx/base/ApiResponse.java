package com.fangminx.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
    private boolean more;

    //自定义构造器
    public ApiResponse(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    //默认无参构造器，返回成功
    public ApiResponse(){
        this.code = Status.SUCCESS.getCode();
        this.message = Status.SUCCESS.getStandardMessage();
    }

    //只传入code和message
    public static ApiResponse ofMessage(int code,String message){
        return new ApiResponse(code,message,null);
    }

    //传入成功状态的data信息
    public static ApiResponse ofSuccess(Object data){
        return new ApiResponse(Status.SUCCESS.getCode(),Status.SUCCESS.getStandardMessage(),data);
    }

    //只传入状态信息
    public static ApiResponse ofStatus(Status status){
        return new ApiResponse(status.getCode(),status.getStandardMessage(),null);
    }

    @Getter
    @AllArgsConstructor
    public enum Status{
        SUCCESS(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        NOT_VALID_PARAM(40005, "Not valid Params"),
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        NOT_LOGIN(50000, "Not Login"),

        ;

        private int code;
        private String standardMessage;

    }
}
