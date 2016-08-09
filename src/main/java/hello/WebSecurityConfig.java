package hello;

import hello.filter.ImprovedUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home","/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .logout()
                .permitAll()
                .and()
             //Replace FORM_LOGIN_FILTER with your own custom implementation
             .addFilterAt(improvedUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
               .exceptionHandling()
               .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
               .and()
             .csrf()
             //Comment out to allow easy testing without csrf
             .disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    public UsernamePasswordAuthenticationFilter improvedUsernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authFilter = new ImprovedUsernamePasswordAuthenticationFilter();
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        authFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
        return authFilter;
    }

}
