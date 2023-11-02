package com.nighthawk.spring_portfolio;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.StaticHeadersWriter;


/*
* To enable HTTP Security in Spring
*/
@Configuration
@EnableWebSecurity  // Beans to enable basic Web security
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // @Bean  // Sets up password encoding style
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	@Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
	
    // Provide security configuration
		@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http
				.csrf(csrf -> csrf
					.disable()
				)
				// list the requests/endpoints need to be authenticated
				.authorizeHttpRequests(auth -> auth
					.requestMatchers("/authenticate").permitAll()
					.requestMatchers("/mvc/person/update/**", "/mvc/person/delete/**").authenticated()
					.requestMatchers("/api/person/**").authenticated()
					.requestMatchers("/**").permitAll()
				)
				// support cors
				.cors(Customizer.withDefaults())
				.headers(headers -> headers
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-ExposedHeaders", "*", "Authorization"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type", "Authorization", "x-csrf-token"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-MaxAge", "600"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "POST", "GET", "OPTIONS", "HEAD"))
					.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
				);
			return http.build();
	}
}
