package com.example.springsecurity.test.springsecuritytest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Create a class and extends WebSecurityConfigurerAdapter
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Hide the super.configure(auth) below?
        // super.configure(auth);

        // For example, if I want to do memory authentication, you can configure it like this.
        // method chaining: You can call a method behind a method and so on.
        auth.inMemoryAuthentication().withUser("blah").password("blah").roles("USER").and().withUser("foo").password("foo").roles("USER");
        // There's Method level security (Not taught)
    }

    // You can create this bean anywhere. And Spring Security will auto using yours as default PasswordEncoder
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // This NoOpPasswordEncoder object is deprecated is not because it's expired, but it's the Spring Security team
        // don't want to recommend you to use this type of password encoder in your application.
        return NoOpPasswordEncoder.getInstance();
    }

    // Override the configure() method with HttpSecurity object inside
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Remove super call
        // super.configure(http);
        // Ant matcher is an important concept, like regex
        // In antPatterns, a few that you need to know:
        // If you put / , it will read any API that has /
        // If you put /** , it will read any API that has / but accepting anything that behinds it, no matter how many slashes behind it. For example:
        // /foo , /example/adaw/rghrog
        //
        http.authorizeRequests()
                .antMatchers("/**")
                .hasAnyRole("USER", "ADMIN")
        // .hasRole("USER")
        .and()
        .formLogin();

        // formLogin() is the popular choice. Use a form to login.
    }
}
