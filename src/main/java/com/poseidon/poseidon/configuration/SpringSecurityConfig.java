package com.poseidon.poseidon.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LogManager.getLogger(SpringSecurityConfig.class);

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("HTTP security");
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home", "/user/list", "/css/**").permitAll()
                .antMatchers("/user/add", "/user/validate").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().successForwardUrl("/bidList/list").permitAll()
                .and()
                .oauth2Login().defaultSuccessUrl("/bidList/list").permitAll()
                .and()
                .logout().logoutUrl("/app-logout").logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.debug("Authentication Manager");
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Encode password");
        return new BCryptPasswordEncoder();
    }

}
