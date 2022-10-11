package com.autumnia.junitprj.bookshop.web.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class ResponseDto<T> {

    private Date timestamp;
    private Integer code ;
    private String msg;
    private T body;

    @Builder
    public ResponseDto(Date timestamp, Integer code, String msg, T body) {
        this.timestamp = timestamp;
        this.code = code;
        this.msg = msg;
        this.body = body;
    }
}
