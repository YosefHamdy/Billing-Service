package com.mop.billing.exception;

public class BillNotFoundException extends RuntimeException {
    public BillNotFoundException(Long billNumber) {
        super("Bill not found: " + billNumber);
    }
}
