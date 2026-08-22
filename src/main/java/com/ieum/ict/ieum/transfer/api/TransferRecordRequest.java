package com.ieum.ict.ieum.transfer.api;

import jakarta.validation.constraints.NotBlank;

public record TransferRecordRequest(@NotBlank String content) {}
