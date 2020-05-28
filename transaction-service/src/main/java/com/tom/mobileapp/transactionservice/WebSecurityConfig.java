package com.tom.mobileapp.transactionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.tom.mobileapp.transactionservice.filters.JwtRequestFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	 @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
	
		 
		 httpSecurity.csrf()
			.disable().authorizeRequests()
			    .antMatchers("/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/authenticate").permitAll().anyRequest()
					.authenticated().and().exceptionHandling().and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	    
	 }
	 
	
		
		  @Bean
		  public RestTemplate restTemplate() { 
			  return new RestTemplate(); 
		  }
		 

}
