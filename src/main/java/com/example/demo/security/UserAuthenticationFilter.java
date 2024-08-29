package com.example.demo.security;

import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private JwtDecoder jwtDecoder;

    public UserAuthenticationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader != null && authHeader.startsWith(BEARER_PREFIX)){
            String rawToken = authHeader.substring(BEARER_PREFIX.length());
            Jwt token = jwtDecoder.decode(rawToken);

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                    token.getTokenValue(),
                    token.getIssuedAt(),
                    token.getExpiresAt());

            String name = token.getClaimAsString("preferred_username");
            List<GrantedAuthority> roles = token.getClaimAsStringList("spring-sec-roles").stream()
                    .filter(role -> role.startsWith("ROLE_"))
                    //.map(role -> role.substring(5))
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();

            Map<String, Object> claims = token.getClaims();

            DefaultOAuth2AuthenticatedPrincipal authenticatedPrincipal = new DefaultOAuth2AuthenticatedPrincipal(name,
                    claims, roles);

            BearerTokenAuthentication bearerTokenAuthentication = new BearerTokenAuthentication(authenticatedPrincipal,
                    accessToken,
                    roles);

            SecurityContextHolder.getContext().setAuthentication(bearerTokenAuthentication);
            /*context.setAuthentication(bearerTokenAuthentication);
            SecurityContextHolder.setContext(context);*/
        }

        filterChain.doFilter(request, response);
    }
}
