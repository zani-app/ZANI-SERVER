package com.zani.zani.security.config;

import com.zani.zani.core.constant.Constants;
import com.zani.zani.core.utility.JsonWebTokenUtil;
import com.zani.zani.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.zani.zani.security.application.usecase.AuthenticateOauthUseCase;
import com.zani.zani.security.filter.ExceptionFilter;
import com.zani.zani.security.filter.GlobalLoggerFilter;
import com.zani.zani.security.filter.JsonWebTokenAuthenticationFilter;
import com.zani.zani.security.filter.OauthAuthenticationFilter;
import com.zani.zani.security.handler.common.DefaultAccessDeniedHandler;
import com.zani.zani.security.handler.common.DefaultAuthenticationEntryPoint;
import com.zani.zani.security.handler.login.Oauth2SuccessHandler;
import com.zani.zani.security.handler.logout.DefaultLogoutProcessHandler;
import com.zani.zani.security.handler.logout.DefaultLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultLogoutProcessHandler defaultLogoutProcessHandler;
    private final DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    private final Oauth2SuccessHandler oauth2SuccessHandler;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;
    private final AuthenticateOauthUseCase authenticateOauthUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                        .anyRequest().authenticated()
                )

                .logout(configurer -> configurer
                        .logoutUrl("/v1/auth/logout")
                        .addLogoutHandler(defaultLogoutProcessHandler)
                        .logoutSuccessHandler(defaultLogoutSuccessHandler)
                )

                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                )

                .addFilterBefore(
                        new OauthAuthenticationFilter(
                                "/api/v1/auth/login",
                                oauth2SuccessHandler,
                                authenticateOauthUseCase
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )

                .addFilterBefore(
                        new JsonWebTokenAuthenticationFilter(
                                authenticateJsonWebTokenUseCase,
                                jsonWebTokenUtil
                        ),
                        LogoutFilter.class
                )

                .addFilterBefore(
                        new ExceptionFilter(),
                        JsonWebTokenAuthenticationFilter.class
                )

                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        ExceptionFilter.class
                )

                .getOrBuild();
    }
}

