package com.milkit.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.milkit.app.config.jwt.JwtTokenProvider;
import com.milkit.app.config.jwt.filter.JwtAuthenticationFilter;
import com.milkit.app.config.jwt.filter.JwtAuthenticationProvider;
import com.milkit.app.config.jwt.filter.JwtAuthorizationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
        		"/h2-console/**",
        		
//        		"/**",
        		
        		"/v2/api-docs", 
        		"/configuration/ui",
                "/swagger-resources/**", 
                "/swagger-ui.html", 
                "/webjars/**",
                "/swagger/**"
        	);
    }
	

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
//    	http.authorizeRequests().antMatchers("/**").permitAll();	//just for test

        http
        	.httpBasic().disable() 
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeRequests()
	        .antMatchers(HttpMethod.POST, "/login").permitAll()
	        .anyRequest().authenticated()
	        .and()
	        .addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class)
	        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
	        ;
       
    }
    
    @Bean
    public JwtTokenProvider jwtTokenProvider() throws Exception {
        return new JwtTokenProvider();
    }
    
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager());
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager());
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public JwtAuthenticationProvider authenticationProvider() {
        return new JwtAuthenticationProvider(passwordEncoder());
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }

}