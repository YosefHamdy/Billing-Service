package com.mop.billing.exception;

public class TahseelException extends RuntimeException {
    private final String statusCode;

    public TahseelException(String billNo, String statusCode) {
        super("Tahseel dispatch failed for bill=" + billNo + " statusCode=" + statusCode);
        this.statusCode = statusCode;
    }

    public String getStatusCode() { return statusCode; }
}
