package com.ieum.ict.ieum.request.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.request.service.AcceptanceRequestService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class AcceptanceRequestController {
    private final AcceptanceRequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<AcceptanceRequestResponse> create(@Valid @RequestBody AcceptanceRequestRequest.Create request,
                                                              Authentication authentication) {
        return CommonResponse.ok(requestService.create(authentication.getName(), request));
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<List<AcceptanceRequestResponse>> createBulk(@Valid @RequestBody AcceptanceRequestRequest.BulkCreate request,
                                                                       Authentication authentication) {
        return CommonResponse.ok(requestService.createBulk(authentication.getName(), request));
    }

    @GetMapping
    public CommonResponse<List<AcceptanceRequestResponse>> findAll(Authentication authentication) {
        return CommonResponse.ok(requestService.findAll(authentication.getName()));
    }

    @GetMapping("/{requestId}")
    public CommonResponse<AcceptanceRequestResponse> findById(@PathVariable Long requestId, Authentication authentication) {
        return CommonResponse.ok(requestService.findById(authentication.getName(), requestId));
    }

    @PostMapping("/{requestId}/response")
    public CommonResponse<AcceptanceRequestResponse> respond(@PathVariable Long requestId,
                                                              @Valid @RequestBody AcceptanceRequestRequest.Response request,
                                                              Authentication authentication) {
        return CommonResponse.ok(requestService.respond(authentication.getName(), requestId, request));
    }

    @PatchMapping("/{requestId}/response")
    public CommonResponse<AcceptanceRequestResponse> updateResponse(@PathVariable Long requestId,
                                                                     @Valid @RequestBody AcceptanceRequestRequest.Response request,
                                                                     Authentication authentication) {
        return CommonResponse.ok(requestService.respond(authentication.getName(), requestId, request));
    }

    @PostMapping("/{requestId}/retry")
    public CommonResponse<AcceptanceRequestResponse> retry(@PathVariable Long requestId, Authentication authentication) {
        return CommonResponse.ok(requestService.retry(authentication.getName(), requestId));
    }
}
