package com.ieum.ict.ieum.common.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/api/health")
    public CommonResponse<String> health() {
        return CommonResponse.ok("ok");
    }
}
