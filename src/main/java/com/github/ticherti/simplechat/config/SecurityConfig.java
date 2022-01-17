package com.github.ticherti.simplechat.config;

import com.github.ticherti.simplechat.entity.Permission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)      //This one is for @Preauthorize above controller methods to work
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/rest/users").anonymous()
                .antMatchers("/rest/auth/login").permitAll()
                .antMatchers(HttpMethod.PATCH, "rest/admin/users/{id}/ban").hasAuthority(Permission.BAN_USER.name())
                .antMatchers(HttpMethod.PATCH, "rest/admin/users/{id}/moderator").hasAuthority(Permission.MAKE_MODERATOR.name())
                .antMatchers(HttpMethod.DELETE, "rest/{roomId}/messages").hasAuthority(Permission.DELETE_MESSAGE.name())
                .antMatchers(HttpMethod.PUT, "/rest/{roomId}/messages").denyAll()
                .antMatchers("/rest/**").authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
