package com.ieum.ict.ieum.admin.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.admin.service.AdminService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/users")
    public CommonResponse<List<AdminUserResponse>> users() { return CommonResponse.ok(adminService.findUsers()); }

    @PatchMapping("/users/{userId}")
    public CommonResponse<AdminUserResponse> updateUser(@PathVariable Long userId,
                                                         @Valid @RequestBody AdminRequest.UserUpdate request) {
        return CommonResponse.ok(adminService.updateUser(userId, request));
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResponse<Void> deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return CommonResponse.ok(null);
    }

    @GetMapping("/transfers/history")
    public CommonResponse<List<com.ieum.ict.ieum.transfer.api.TransferResponse>> transferHistory() {
        return CommonResponse.ok(adminService.transferHistory().stream()
                .map(com.ieum.ict.ieum.transfer.api.TransferResponse::from).toList());
    }

    @GetMapping("/transfers")
    public CommonResponse<List<com.ieum.ict.ieum.transfer.api.TransferResponse>> transfers() {
        return CommonResponse.ok(adminService.findTransfers().stream()
                .map(com.ieum.ict.ieum.transfer.api.TransferResponse::from).toList());
    }

    @PatchMapping("/transfers/{transferId}/status")
    public CommonResponse<com.ieum.ict.ieum.transfer.api.TransferResponse> updateTransferStatus(
            @PathVariable Long transferId, @Valid @RequestBody AdminRequest.TransferStatusUpdate request) {
        return CommonResponse.ok(com.ieum.ict.ieum.transfer.api.TransferResponse.from(
                adminService.updateTransferStatus(transferId, request.status())));
    }

    @GetMapping("/acceptance-requests")
    public CommonResponse<List<com.ieum.ict.ieum.request.api.AcceptanceRequestResponse>> acceptanceRequests() {
        return CommonResponse.ok(adminService.findAcceptanceRequests());
    }

    @PatchMapping("/acceptance-requests/{requestId}")
    public CommonResponse<com.ieum.ict.ieum.request.api.AcceptanceRequestResponse> respondAcceptanceRequest(
            @PathVariable Long requestId, @Valid @RequestBody AdminRequest.AcceptanceResponse request) {
        return CommonResponse.ok(adminService.respondAcceptanceRequest(requestId, request));
    }

    @GetMapping("/hospitals")
    public CommonResponse<List<com.ieum.ict.ieum.hospital.api.HospitalResponse>> hospitals() {
        return CommonResponse.ok(adminService.findHospitals());
    }

    @PatchMapping("/hospitals/{hospitalId}")
    public CommonResponse<com.ieum.ict.ieum.hospital.api.HospitalResponse> updateHospital(
            @PathVariable Long hospitalId, @Valid @RequestBody AdminRequest.HospitalUpdate request) {
        return CommonResponse.ok(adminService.updateHospital(hospitalId, request));
    }

    @DeleteMapping("/hospitals/{hospitalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommonResponse<Void> deleteHospital(@PathVariable Long hospitalId) {
        adminService.deleteHospital(hospitalId);
        return CommonResponse.ok(null);
    }

    @GetMapping("/stats")
    public CommonResponse<AdminStatsResponse> stats() { return CommonResponse.ok(adminService.stats()); }
}
