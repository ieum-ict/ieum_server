package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.transfer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @GetMapping
    public CommonResponse<List<TransferResponse>> findAll(Authentication authentication) {
        return CommonResponse.ok(transferService.findAll(authentication.getName()));
    }

    @GetMapping("/{transferId}")
    public CommonResponse<TransferResponse> findById(@PathVariable Long transferId,
                                                      Authentication authentication) {
        return CommonResponse.ok(transferService.findById(authentication.getName(), transferId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<TransferResponse> create(@Valid @RequestBody TransferRequest.Create request,
                                                    Authentication authentication) {
        return CommonResponse.ok(transferService.create(authentication.getName(), request));
    }
}
