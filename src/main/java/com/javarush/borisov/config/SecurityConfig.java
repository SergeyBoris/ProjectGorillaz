package com.javarush.borisov.config;

import com.javarush.borisov.db.Repository.UserRepo;
import com.javarush.borisov.db.Service.newService.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepo userRepo;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login",  "/assets/**","/","/index",
                                "/assigned-requests","/equipment/active-equipment").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")                  // Ваша кастомная страница логина
                        .loginProcessingUrl("/authorise")     // URL, на который отправляется форма
                        .usernameParameter("email")           // Название поля email
                        .passwordParameter("password")        // Название поля password
                        .defaultSuccessUrl("/index", true)     // Куда перенаправить после входа
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ).userDetailsService(userDetailsService());


        return http.build();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        return new UserSecurityService(userRepo);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder();
    }
}