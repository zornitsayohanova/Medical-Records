package com.example.MedicalRecords.config;

import com.example.MedicalRecords.services.implementations.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userServiceImpl;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userServiceImpl)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/", "/register**", "/login**").anonymous()
                .antMatchers("/profile/patient").hasAuthority("PATIENT")
                .antMatchers("/medical-record").hasAuthority("PATIENT")
                .antMatchers("/profile/doctor").hasAuthority("DOCTOR")
                .antMatchers("/patients").hasAnyAuthority("DOCTOR", "ADMIN")
                .antMatchers("/patients/patient").hasAuthority("DOCTOR")
                .antMatchers("/diagnose").hasAuthority("DOCTOR")
                .antMatchers("/diagnose/statistics").hasAuthority("DOCTOR")
                .antMatchers("/patients/patient/add-record").hasAuthority("DOCTOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile", true)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }
}