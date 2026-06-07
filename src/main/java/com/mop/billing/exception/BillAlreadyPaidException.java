package com.mop.billing.exception;

public class BillAlreadyPaidException extends RuntimeException {
    public BillAlreadyPaidException(Long billNumber) {
        super("Bill is already paid and cannot be cancelled: " + billNumber);
    }
}
