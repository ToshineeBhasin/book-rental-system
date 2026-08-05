package org.bookrental.config;

import org.bookrental.security.CustomAuthenticationEntryPoint;
import org.bookrental.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.PasswordAuthentication;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint authenticationEntryPoint) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf().disable()

                /*
                 * JWT authentication is stateless.
                 * Spring will not create a login session.
                 */
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeRequests()

                /*
                 * Public APIs:
                 * Login and registration need no token.
                 */
                .antMatchers(
                        "/api/auth/login",
                        "/api/users/register"
                ).permitAll()

                /*
                 * Only ADMIN can create, update or delete books.
                 */
                .antMatchers(
                        HttpMethod.POST,
                        "/api/books/**"
                ).hasRole("ADMIN")

                .antMatchers(
                        HttpMethod.PUT,
                        "/api/books/**"
                ).hasRole("ADMIN")

                .antMatchers(
                        HttpMethod.DELETE,
                        "/api/books/**"
                ).hasRole("ADMIN")

                /*
                 * ADMIN and CUSTOMER can view books.
                 */
                .antMatchers(
                        HttpMethod.GET,
                        "/api/books/**"
                ).hasAnyRole("ADMIN", "CUSTOMER")

                /*
                 * CUSTOMER can rent and return books.
                 */
                .antMatchers(
                        HttpMethod.POST,
                        "/api/rentals/rent"
                ).hasRole("CUSTOMER")

                .antMatchers(
                        HttpMethod.PUT,
                        "/api/rentals/*/return"
                ).hasRole("CUSTOMER")

                /*
                 * For now both roles may view rental information.
                 * Later we can restrict customers to their own records.
                 */
                .antMatchers(
                        HttpMethod.GET,
                        "/api/rentals/**"
                ).hasAnyRole("ADMIN", "CUSTOMER")

                /*
                 * User-management APIs are ADMIN-only.
                 */
                .antMatchers(
                        "/api/users/**"
                ).hasRole("ADMIN")

                /*
                 * Any endpoint not covered above still requires login.
                 */
                .anyRequest().authenticated();

        /*
         * Execute our JWT filter before Spring's standard
         * username/password authentication filter.
         */
        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

}