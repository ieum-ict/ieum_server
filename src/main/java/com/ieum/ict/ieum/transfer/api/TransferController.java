package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.transfer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ieum.ict.ieum.transfer.domain.TransferStatus;

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

    @PatchMapping("/{transferId}")
    public CommonResponse<TransferResponse> update(@PathVariable Long transferId,
                                                    @Valid @RequestBody TransferRequest.Update request,
                                                    Authentication authentication) {
        return CommonResponse.ok(transferService.update(authentication.getName(), transferId, request));
    }

    @DeleteMapping("/{transferId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResponse<Void> cancel(@PathVariable Long transferId, Authentication authentication) {
        transferService.cancel(authentication.getName(), transferId);
        return CommonResponse.ok(null);
    }

    @GetMapping("/{transferId}/status")
    public CommonResponse<TransferStatus> findStatus(@PathVariable Long transferId,
                                                       Authentication authentication) {
        return CommonResponse.ok(transferService.findStatus(authentication.getName(), transferId));
    }

    @PatchMapping("/{transferId}/status")
    public CommonResponse<TransferResponse> updateStatus(@PathVariable Long transferId,
                                                          @Valid @RequestBody TransferRequest.StatusUpdate request,
                                                          Authentication authentication) {
        return CommonResponse.ok(transferService.updateStatus(authentication.getName(), transferId, request.status()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<TransferResponse> create(@Valid @RequestBody TransferRequest.Create request,
                                                    Authentication authentication) {
        return CommonResponse.ok(transferService.create(authentication.getName(), request));
    }
}
