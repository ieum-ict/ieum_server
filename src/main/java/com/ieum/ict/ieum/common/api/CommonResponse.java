package com.ieum.ict.ieum.common.api;

public record CommonResponse<T>(boolean success, T data) {
    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(true, data);
    }
}
