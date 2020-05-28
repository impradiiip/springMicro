package com.tom.mobileapp.transactionservice.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tom.mobileapp.transactionservice.MyUserDetails;
import com.tom.mobileapp.transactionservice.User;
import com.tom.mobileapp.transactionservice.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		
		System.out.println("request ================== "+request);
		System.out.println("authorizationHeader =========== "+authorizationHeader);

		String username = null;
		String jwt = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			String tempJwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(tempJwt);
		}

		System.out.println("tran = "+username);
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			try {

				HttpHeaders headers = new HttpHeaders();
				headers.add("Authorization", authorizationHeader);
				headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

				HttpEntity<String> entity = new HttpEntity<String>("", headers);

				
				  ResponseEntity<String> responseEntity =
				  restTemplate.exchange("http://localhost:8765/security-auth-server/current",
				  HttpMethod.POST, entity, String.class);
				 
				
				/*
				 * ResponseEntity<CurrencyConversionBean> responseEntity = new
				 * RestTemplate().getForEntity(
				 * "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
				 * CurrencyConversionBean.class, mapValues);
				 */
				
				/*
				 * ResponseEntity<UserDetails> responseEntity = restTemplate.postForEntity(
				 * "http://localhost:8765/security-auth-server/current", entity,
				 * UserDetails.class);
				 */

				
				
				
				String jsonUserDetails = responseEntity.getBody();
				
				//UserDetails UserDetails = responseEntity.getBody();
				//System.out.println("user name =========== "+UserDetails.getUsername());
				
				System.err.println("jsonUserDetails === "+jsonUserDetails);
				
				
				  UserDetails userDetails = prepareUserDetails(jsonUserDetails);
				  
				  if (userDetails != null) {
					  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities()); 
				 	  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				      SecurityContextHolder.getContext().setAuthentication(authentication); 
				  }
				 
				 

			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		}

		filterChain.doFilter(request, response);

	}
	
private UserDetails prepareUserDetails(String jsonUserDetails) throws JsonProcessingException, IOException{
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	JsonNode root = objectMapper.readTree(jsonUserDetails);
    	
    	//String userId = root.get("dbUser").get("id").asText();
    	String username = root.get("username").asText();
    	String password = root.get("password").asText();
    	boolean isEnabled =  root.get("enabled").asBoolean();
    	
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	
    	Iterator<JsonNode> authoritiesIterator = root.get("authorities").elements();
    	while(authoritiesIterator.hasNext()){
    		JsonNode authorityNode = authoritiesIterator.next();
    		authorities.add(new SimpleGrantedAuthority(authorityNode.get("authority").asText()));
    	}
    	
    	return new MyUserDetails(username, password, isEnabled, authorities);
    }

}
