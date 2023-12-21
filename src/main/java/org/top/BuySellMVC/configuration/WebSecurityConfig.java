package org.top.BuySellMVC.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.top.BuySellMVC.rdb.repository.UserRepository;
import org.top.BuySellMVC.rdb.security.RdbUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public final UserRepository userRepository;
    private final DataSource dataSource;

    public WebSecurityConfig(UserRepository userRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/announcement/all").permitAll()
                        .requestMatchers("/", "/user/register", "webjars/**").permitAll()
                        .requestMatchers(
                                "/profile/add",
                                "/profile/update",
                                "/profile/update/*")
                        .hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll().successForwardUrl("/"))
                .logout(customizer -> customizer.logoutSuccessUrl("/login"));
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new RdbUserDetailsService(userRepository);
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }
}
