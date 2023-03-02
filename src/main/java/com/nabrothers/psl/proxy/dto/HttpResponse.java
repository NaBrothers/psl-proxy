package com.nabrothers.psl.proxy.dto;

import lombok.Data;

@Data
public class HttpResponse<T> {

    public HttpResponse() {
    }

    public HttpResponse(T data) {
        this.data = data;
    }

    private int code = 200;
    private String message;
    private T data;
}
