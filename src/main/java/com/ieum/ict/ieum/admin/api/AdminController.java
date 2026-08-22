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

    @GetMapping("/stats")
    public CommonResponse<AdminStatsResponse> stats() { return CommonResponse.ok(adminService.stats()); }
}
