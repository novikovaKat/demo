package com.example.demo.config;

import com.example.demo.models.enums.Role;
import com.example.demo.repository.AccountRepository;
import com.example.demo.security.UserAuthenticationFilter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${keycloak.admin-client-id}")
    private String adminClientId;
    @Value("${keycloak.admin-client-secret}")
    private String adminClientSecret;
    @Value("${keycloak.server-uri}")
    private String keycloakServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    private static final String JWT_ISSUER_URI = "http://localhost:8080/realms/demo/protocol/openid-connect/certs";
    private AccountRepository accountRepository;

    public SecurityConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

      //  http.oauth2Login(Customizer.withDefaults());
        return http
                .addFilterBefore(new UserAuthenticationFilter(jwtDecoder()), AnonymousAuthenticationFilter.class)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        new AntPathRequestMatcher("/account/all"),
                                        new AntPathRequestMatcher("/appointment/all"),
                                        new AntPathRequestMatcher("/doctor/{doctorId}/details")
                                )
                                .hasRole(Role.ADMIN.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/doctor/{accountId}")
                                )
                                .hasRole(Role.DOCTOR.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/account/{uuid}", HttpMethod.GET.name()),
                                        new AntPathRequestMatcher("/account/{uuid}", HttpMethod.PUT.name())
                                )
                                .hasRole(Role.BASIC_USER.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/appointment/cancel")
                                )
                                .hasAnyRole(Role.BASIC_USER.name(), Role.ADMIN.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/appointment/create")
                                )
                                .authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/account/signup", HttpMethod.POST.name()))
                                .anonymous()
                                .anyRequest()
                                .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(JWT_ISSUER_URI)
                .jwsAlgorithm(SignatureAlgorithm.RS256)
                .build();
    }

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientId(adminClientId)
                .clientSecret(adminClientSecret)
                .grantType("client_credentials")
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .build();
    }

}
