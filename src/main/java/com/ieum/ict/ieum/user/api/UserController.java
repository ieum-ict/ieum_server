package com.ieum.ict.ieum.user.api;

import com.ieum.ict.ieum.common.api.CommonResponse;
import com.ieum.ict.ieum.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public CommonResponse<UserResponse> getMyInfo(Authentication authentication) {
        return CommonResponse.ok(userService.getMyInfo(authentication.getName()));
    }
}
