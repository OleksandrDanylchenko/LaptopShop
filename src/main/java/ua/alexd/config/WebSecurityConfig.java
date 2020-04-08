package ua.alexd.config;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.alexd.security.ShopUserDetails;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ShopUserDetails shopUserDetails;

    public WebSecurityConfig(ShopUserDetails shopUserDetails) {
        this.shopUserDetails = shopUserDetails;
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
                "/basket",
                "/registration/activate/*"
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(@NotNull AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(shopUserDetails)
                .passwordEncoder(passwordEncoder());
    }
}