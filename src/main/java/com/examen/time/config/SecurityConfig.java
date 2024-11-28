package com.examen.time.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {
        @Autowired
        private AuthenticationConfiguration authenticationConfiguration;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                // Configuración de la seguridad personalizada
                return httpSecurity
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers("/api/v1/time/**") // Ignorar CSRF en rutas
                                                                                            // específicas
                                )
                                .httpBasic(Customizer.withDefaults())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(http -> {
                                        http
                                                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/v3/api-docs.yaml", "/v3/api-docs.json")
                                                        .permitAll()
                                                        .requestMatchers(HttpMethod.GET, "/api/v1/time/**")
                                                        .hasAnyAuthority("READ")
                                                        .requestMatchers(HttpMethod.POST, "/api/v1/time/**")
                                                        .hasAnyAuthority("CREATE")
                                                        .requestMatchers(HttpMethod.PUT, "/api/v1/time/**")
                                                        .hasAnyAuthority("UPDATE")
                                                        .requestMatchers(HttpMethod.DELETE, "/api/v1/time/**")
                                                        .hasAnyAuthority("DELETE")
                                                        .anyRequest().denyAll();
                                })
                                .build();
        }

        // Authentication Manager
        @Bean
        public AuthenticationManager authenticationManager() throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        // Autehntication Provider -> Dao -> Va a proporcionar la autenticación
        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
                daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
                daoAuthenticationProvider.setUserDetailsService(userDetailsService());
                return daoAuthenticationProvider;
        }

        // Password Encoder
        @Bean
        public PasswordEncoder passwordEncoder() {
                // return new BCryptPasswordEncoder();
                return NoOpPasswordEncoder.getInstance();
        }

        // UserDetailsService -> base de datos o memoria
        @Bean
        public UserDetailsService userDetailsService() {
                // Definir usuarios en memoria
                // No vamos a usar una base de datos
                UserDetails admin = User.withUsername("admin")
                                .password("admin")
                                .roles("ADMIN")
                                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                                .build();

                UserDetails user = User.withUsername("user")
                                .password("user")
                                .roles("User")
                                .authorities("READ")
                                .build();

                UserDetails moderator = User.withUsername("moderator")
                                .password("moderator")
                                .roles("MODERATOR")
                                .authorities("READ", "UPDATE")
                                .build();

                UserDetails editor = User.withUsername("editor")
                                .password("editor")
                                .roles("EDITOR")
                                .authorities("UPDATE")
                                .build();

                UserDetails developer = User.withUsername("developer")
                                .password("developer")
                                .roles("EDITOR")
                                .authorities("READ", "UPDATE", "CREATE", "DELETE", "WRITE")
                                .build();

                UserDetails analyst = User.withUsername("analyst")
                                .password("analyst")
                                .roles("EDITOR")
                                .authorities("READ", "DELETE")
                                .build();

                List<UserDetails> usersDetailList = new ArrayList<>();
                usersDetailList.add(admin);
                usersDetailList.add(user);
                usersDetailList.add(moderator);
                usersDetailList.add(editor);
                usersDetailList.add(developer);
                usersDetailList.add(analyst);
                return new InMemoryUserDetailsManager(usersDetailList);
        }
}
