package com.cash.machine.api.models.response;

import org.springframework.http.ResponseEntity;

public class HttpResponse {
    private boolean status;

    private int code;

    private String message;

    private Object response;

    private HttpResponse() { }

    public static HttpResponse build() {
        return new HttpResponse();
    }

    public Object getResponse() {
        return response;
    }

    public HttpResponse setResponse(Object response) {
        this.response = response;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HttpResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public HttpResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isStatus() {
        return status;
    }

    public HttpResponse setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public ResponseEntity<HttpResponse> responseGenerator() {
        return ResponseEntity.status(this.code).body(this);
    }

}
