package com.ieum.ict.ieum.auth.security;

import com.ieum.ict.ieum.auth.api.AuthResponse;
import com.ieum.ict.ieum.auth.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final AuthService authService;

    @Value("${app.oauth2.success-redirect-uri:http://localhost:3000/oauth/callback}")
    private String successRedirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        AuthResponse tokens = authService.loginWithGoogle(email, name == null ? email : name);

        String redirectUri = successRedirectUri + "#accessToken=" + encode(tokens.accessToken())
                + "&refreshToken=" + encode(tokens.refreshToken());
        response.sendRedirect(redirectUri);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
