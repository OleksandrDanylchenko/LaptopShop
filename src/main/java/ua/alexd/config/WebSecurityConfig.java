package ua.alexd.config;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import ua.alexd.security.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(@NotNull HttpSecurity http) throws Exception {
        var allowedPages = new String[]{
                "/registration",
                "/",
                "/shop",
                "/laptop",
                "/type",
                "/hardware",
                "/display",
                "/ssd",
                "/hdd",
                "/ram",
                "/gpu",
                "/cpu",
                "/basket"
        };

        var staticResources = new String[]{
                "/favicon.ico",
                "/navbarLogo.png",
                "/images/**",
                "/styles/**",
                "/scripts/**"
        };

        var allowedUrls = ArrayUtils.addAll(staticResources, allowedPages);

        http.authorizeRequests()
                .antMatchers(allowedUrls).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(@NotNull AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}