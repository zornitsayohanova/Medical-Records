package com.example.MedicalRecords.config;

import com.example.MedicalRecords.services.implementations.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
   // private final UserServiceImpl userServiceImpl;

   // private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      //  auth
       //         .userDetailsService(userServiceImpl)
               // .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //  http.csrf().disable();
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers( "/css/**").permitAll()
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





//<name>MedicalRecords</name>

              /*  .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login**").anonymous()
                .antMatchers("/css/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile", true)
                .failureUrl("/login?error")
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized")
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/login")
                .permitAll();*/
    }
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll().antMatchers("/profile")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/doctors")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/patients")
                .hasAnyRole("ADMIN")
                .anyRequest().authenticated().and().formLogin()
                .permitAll().and().logout().permitAll();

        http.csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication().withUser("employee").password("employee")
                .authorities("ROLE_USER").and().withUser("javainuse").password("javainuse")
                .authorities("ROLE_USER", "ROLE_ADMIN");
    }*/




    /*@Bean
    public BCryptPasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }*/

}