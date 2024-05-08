package com.example.demo.config;

import com.example.demo.models.enums.Role;
import com.example.demo.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;
import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

@Configuration
//@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
//                .addFilterBefore()
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize
//                                .requestMatchers(
//                                        new AntPathRequestMatcher( "/doctors/appointment", HttpMethod.POST.name()))
//                                .hasAuthority(Role.BASIC_USER.name())
//                                .hasAnyRole(Role.BASIC_USER.name())

//                                .requestMatchers(
//                                        new AntPathRequestMatcher( "/**", HttpMethod.OPTIONS.name()),
//                                        new AntPathRequestMatcher( "/", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher( "/doctors", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher( "/doctors/{doctorId}", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher( "/doctors/{doctorId}/appointments", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher( "/doctors/appointment", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher( "/services", HttpMethod.GET.name()),
//                                        new AntPathRequestMatcher( "/account/signup", HttpMethod.POST.name()),
//                                        new AntPathRequestMatcher( "/account/login", HttpMethod.POST.name()))
//                                .permitAll()

//                                .requestMatchers("/api/auth/**")
//                                .permitAll()
//                                .anyRequest().authenticated())
                                .anyRequest().permitAll())
                .cors(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(IF_REQUIRED))
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
