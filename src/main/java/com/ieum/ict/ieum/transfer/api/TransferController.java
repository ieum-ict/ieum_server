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

    @PostMapping("/{transferId}/start")
    public CommonResponse<TransferResponse> start(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.start(authentication.getName(), transferId));
    }

    @PostMapping("/{transferId}/arrival")
    public CommonResponse<TransferResponse> arrive(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.arrive(authentication.getName(), transferId));
    }

    @PostMapping("/{transferId}/handover")
    public CommonResponse<TransferResponse> handover(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.handover(authentication.getName(), transferId));
    }

    @GetMapping("/{transferId}/history")
    public CommonResponse<List<TransferHistoryResponse>> history(@PathVariable Long transferId,
                                                                  Authentication authentication) {
        return CommonResponse.ok(transferService.findHistory(authentication.getName(), transferId));
    }

    @PostMapping("/{transferId}/route")
    public CommonResponse<TransferRecordResponse> route(@PathVariable Long transferId,
                                                         @Valid @RequestBody TransferRecordRequest request,
                                                         Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "ROUTE", request.content()));
    }

    @PostMapping("/{transferId}/updates")
    public CommonResponse<TransferRecordResponse> addUpdate(@PathVariable Long transferId,
                                                              @Valid @RequestBody TransferRecordRequest request,
                                                              Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "UPDATE", request.content()));
    }

    @GetMapping("/{transferId}/updates")
    public CommonResponse<List<TransferRecordResponse>> updates(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.findRecords(authentication.getName(), transferId, "UPDATE"));
    }

    @GetMapping("/{transferId}/resources")
    public CommonResponse<List<TransferRecordResponse>> resources(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.findRecords(authentication.getName(), transferId, "RESOURCE"));
    }

    @PatchMapping("/{transferId}/resources")
    public CommonResponse<TransferRecordResponse> resource(@PathVariable Long transferId,
                                                            @Valid @RequestBody TransferRecordRequest request,
                                                            Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "RESOURCE", request.content()));
    }

    @GetMapping("/{transferId}/preparations")
    public CommonResponse<List<TransferRecordResponse>> preparations(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.findRecords(authentication.getName(), transferId, "PREPARATION"));
    }

    @PatchMapping("/{transferId}/preparations")
    public CommonResponse<TransferRecordResponse> preparation(@PathVariable Long transferId,
                                                               @Valid @RequestBody TransferRecordRequest request,
                                                               Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "PREPARATION", request.content()));
    }

    @PostMapping("/{transferId}/preparations/alerts")
    public CommonResponse<TransferRecordResponse> preparationAlert(@PathVariable Long transferId,
                                                                     @Valid @RequestBody TransferRecordRequest request,
                                                                     Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "PREPARATION_ALERT", request.content()));
    }

    @PatchMapping("/{transferId}/patient")
    public CommonResponse<TransferRecordResponse> patient(@PathVariable Long transferId,
                                                           @Valid @RequestBody TransferRecordRequest request,
                                                           Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "PATIENT", request.content()));
    }

    @PostMapping("/{transferId}/patient/apply")
    public CommonResponse<TransferRecordResponse> applyPatient(@PathVariable Long transferId,
                                                                @Valid @RequestBody TransferRecordRequest request,
                                                                Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "PATIENT_APPLIED", request.content()));
    }

    @PostMapping("/{transferId}/recommendations")
    public CommonResponse<TransferRecordResponse> recommendations(@PathVariable Long transferId,
                                                                   @Valid @RequestBody TransferRecordRequest request,
                                                                   Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "RECOMMENDATION", request.content()));
    }

    @PostMapping("/{transferId}/hospital")
    public CommonResponse<TransferRecordResponse> selectHospital(@PathVariable Long transferId,
                                                                  @Valid @RequestBody TransferRecordRequest request,
                                                                  Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "HOSPITAL", request.content()));
    }

    @GetMapping("/{transferId}/hospital")
    public CommonResponse<List<TransferRecordResponse>> hospital(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.findRecords(authentication.getName(), transferId, "HOSPITAL"));
    }

    @GetMapping("/{transferId}/responses")
    public CommonResponse<List<TransferRecordResponse>> responses(@PathVariable Long transferId, Authentication authentication) {
        return CommonResponse.ok(transferService.findRecords(authentication.getName(), transferId, "RESPONSE"));
    }

    @PostMapping("/{transferId}/sync")
    public CommonResponse<TransferRecordResponse> sync(@PathVariable Long transferId,
                                                       @Valid @RequestBody TransferRecordRequest request,
                                                       Authentication authentication) {
        return CommonResponse.ok(transferService.saveRecord(authentication.getName(), transferId, "SYNC", request.content()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<TransferResponse> create(@Valid @RequestBody TransferRequest.Create request,
                                                    Authentication authentication) {
        return CommonResponse.ok(transferService.create(authentication.getName(), request));
    }
}
