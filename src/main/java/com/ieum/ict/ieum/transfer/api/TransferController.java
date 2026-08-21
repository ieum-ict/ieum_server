package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.transfer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<TransferResponse> create(@Valid @RequestBody TransferRequest.Create request,
                                                    Authentication authentication) {
        return CommonResponse.ok(transferService.create(authentication.getName(), request));
    }
}
