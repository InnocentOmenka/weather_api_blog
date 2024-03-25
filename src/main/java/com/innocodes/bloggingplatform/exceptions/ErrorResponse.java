package com.innocodes.bloggingplatform.exceptions;

import lombok.Data;

@Data
public class ErrorResponse<T> {
    private String status;
    private String statusCode;
    private T message;
}
