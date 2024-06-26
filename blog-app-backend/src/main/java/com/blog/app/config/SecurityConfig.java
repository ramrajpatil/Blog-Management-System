package com.blog.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.app.security.CustomUserDetailService;
import com.blog.app.security.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enables Spring Security's web security support
@EnableMethodSecurity(prePostEnabled = true) // Enables Spring Security's method security support
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig {

	public static final String[] PUBLIC_URLS = { 
			"/api/auth/login", 
			"/api/auth/register", 
			"/v3/api-docs",
			"/v2/api-docs", 
			"/swagger-resources/**", 
			"/swagger-ui.html", // Add the Swagger UI endpoint
			"/webjars/**" 
			};

	@Autowired
	private CustomUserDetailService customUserDetailService;

//	@Autowired
//	private JWTAuthenticationEntryPoint entryPoint;

	@Autowired
	private JWTAuthenticationFilter filter;

	// Configures the security filter chain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auths -> auths // For docs
				.requestMatchers(PUBLIC_URLS).permitAll() // Permit access to public URLs without authentication
				.requestMatchers(HttpMethod.GET).permitAll()
				.anyRequest().authenticated() // All other requests require authentication
		).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless
																										// session
																										// management
		).authenticationProvider(daoAuthenticationProvider()) // Configure authentication provider
				.addFilterBefore(this.filter, UsernamePasswordAuthenticationFilter.class) // Add custom filter before
																							// UsernamePasswordAuthenticationFilter
				.build();
	}

	// Configures the authentication provider
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		// Return your implementation of AuthenticationProvider here
		// For example:
		// return new MyAuthenticationProvider();

		// Configures a DAO-based authentication provider with custom user details
		// service and password encoder
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.customUserDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	// Configures the password encoder bean
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Uses BCryptPasswordEncoder for password encoding
	}

	// Configures the authentication manager bean
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager(); // Retrieves the authentication manager from the
													// AuthenticationConfiguration
	}

//	@Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOriginPattern("*");
//        
//        corsConfiguration.addAllowedHeader("Authorization");
//        corsConfiguration.addAllowedHeader("Content-Type");
//        corsConfiguration.addAllowedHeader("Accept");
//        corsConfiguration.addAllowedMethod("POST");
//        corsConfiguration.addAllowedMethod("GET");
//        corsConfiguration.addAllowedMethod("DELETE");
//        corsConfiguration.addAllowedMethod("PUT");
//        corsConfiguration.addAllowedMethod("OPTIONS");
//        corsConfiguration.setMaxAge(3600L);
//        
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        
//        CorsFilter corsFilter = new CorsFilter(source);
//
//        FilterRegistrationBean<CorsFilter> bean =
//                new FilterRegistrationBean<>(corsFilter);
//
//        bean.setOrder(0);
//
//        return bean;
//    }
	

}
