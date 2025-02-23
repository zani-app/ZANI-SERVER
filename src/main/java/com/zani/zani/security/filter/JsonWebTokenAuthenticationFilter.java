package com.zani.zani.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zani.zani.core.constant.Constants;
import com.zani.zani.core.exception.error.ErrorCode;
import com.zani.zani.core.exception.type.CommonException;
import com.zani.zani.core.utility.HeaderUtil;
import com.zani.zani.core.utility.JsonWebTokenUtil;
import com.zani.zani.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.zani.zani.security.domain.type.ESecurityRole;
import com.zani.zani.security.info.CustomUserPrincipal;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class JsonWebTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    static final String AUTH_BRIEFS_URL = "/v1/auth/briefs";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        Optional<String> tokenOptional = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX);

        if (AUTH_BRIEFS_URL.equals(requestURI) && tokenOptional.isEmpty()) {
            writeGuestResponse(response);
            return;
        }

        String token = tokenOptional.orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        Claims claims = jsonWebTokenUtil.validateToken(token);

        UUID accountId = UUID.fromString(claims.get(Constants.ACCOUNT_ID_CLAIM_NAME, String.class));
        ESecurityRole role = ESecurityRole.fromString(claims.get(Constants.ACCOUNT_ROLE_CLAIM_NAME, String.class));

        CustomUserPrincipal principal = authenticateJsonWebTokenUseCase.execute(accountId);

        if (!role.equals(principal.getRole())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }

        // AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );

        // SecurityContext에 AuthenticationToken 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 헤더가 없을 경우, 게스트 응답(JSON)을 작성하여 반환
     */
    private void writeGuestResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());

        Map<String, Object> guestData = new HashMap<>();
        guestData.put("account_type", ESecurityRole.GUEST);
        guestData.put("name", null);

        Map<String, Object> guestResponse = new HashMap<>();
        guestResponse.put("success", true);
        guestResponse.put("data", guestData);
        guestResponse.put("error", null);

        objectMapper.writeValue(response.getWriter(), guestResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // 인증이 필요 없는 URL 목록에 포함되는지 확인
        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(excludePattern -> requestURI.matches(excludePattern.replace("**", ".*")));
    }
}

