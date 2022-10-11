package com.autumnia.junitprj.bookshop.common.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseException {
    private Date timestamp;
    private String message;
    private String details;
}
