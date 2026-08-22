package com.ieum.ict.ieum.hospital.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.hospital.service.HospitalService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospitals")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    public CommonResponse<List<HospitalResponse>> findAll() {
        return CommonResponse.ok(hospitalService.findAll());
    }

    @GetMapping("/{hospitalId}")
    public CommonResponse<HospitalResponse> findById(@PathVariable Long hospitalId) {
        return CommonResponse.ok(hospitalService.findById(hospitalId));
    }

    @PostMapping("/search")
    public CommonResponse<List<HospitalResponse>> search(@Valid @RequestBody HospitalRequest.Search request) {
        return CommonResponse.ok(hospitalService.search(request));
    }

    @GetMapping("/{hospitalId}/resources")
    public CommonResponse<String> resources(@PathVariable Long hospitalId) {
        return CommonResponse.ok(hospitalService.findResources(hospitalId));
    }

    @PatchMapping("/{hospitalId}/resources")
    public CommonResponse<HospitalResponse> updateResources(@PathVariable Long hospitalId,
                                                             @Valid @RequestBody HospitalRequest.ResourceUpdate request,
                                                             Authentication authentication) {
        return CommonResponse.ok(hospitalService.updateResources(hospitalId, request));
    }

    @GetMapping("/{hospitalId}/resources/history")
    public CommonResponse<List<HospitalResourceHistoryResponse>> resourceHistory(@PathVariable Long hospitalId) {
        return CommonResponse.ok(hospitalService.findResourceHistory(hospitalId));
    }
}
