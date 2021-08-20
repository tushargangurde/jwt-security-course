package com.tushar.securitycourse.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class ApiError {

    private Integer errorCode;
    private String errorMessage;

}
