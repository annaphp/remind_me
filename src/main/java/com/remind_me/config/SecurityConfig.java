package com.remind_me.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userService;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**");
       
    }
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
		        .authorizeRequests()// reverse order
		        	.antMatchers("/user/**").hasRole("OWNER")
		            .antMatchers("/","/register").permitAll()
		           	.and()
	            .formLogin()
	                .loginPage("/login")
	                .permitAll()
	                .successHandler((request, response, authentication) -> response.sendRedirect("/user/home/"))
	                .failureHandler((request, response, authentication) -> response.sendRedirect("/login") )
	                .and()
	           .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
	                .invalidateHttpSession(true) 	            
	                .and()
	           .csrf();    
	    }

	 @Bean
	 public SpringSecurityDialect securityDialect() {
	   return new SpringSecurityDialect();
	 }
}
