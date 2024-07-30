package com.pedrohugo.alfredos_api.exception;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiError(
        LocalDateTime timestamp,
        Integer code,
        String status,
        List<String> errors
) {
}
