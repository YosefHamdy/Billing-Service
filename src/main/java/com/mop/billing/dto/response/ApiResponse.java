package com.mop.billing.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Standard envelope for all REST responses.
 * {@code success} false responses include an {@code error} message.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String  message;
    private T       data;
    private String  error;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder().success(true).data(data).build();
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponse<T> fail(String error) {
        return ApiResponse.<T>builder().success(false).error(error).build();
    }
}
