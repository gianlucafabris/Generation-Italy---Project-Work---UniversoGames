package com.universogames.cmsnewsrecensioni.security;

import javax.sql.DataSource;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import com.universogames.cmsnewsrecensioni.auth.AuthService;
import com.universogames.cmsnewsrecensioni.auth.Utente;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter { 
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthService authService;

	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, AuthService authService) {
		this.passwordEncoder = passwordEncoder;
		this.authService = authService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests().antMatchers("/", "index.html", "/recensioni.html", "/images/**", "/script/**", "/style/**", "/utente.html", "/signin", "/login").permitAll()
				.antMatchers("/gestione.html").hasAnyRole(Roles.USER)
				.and()
				.exceptionHandling()
				.accessDeniedPage("/utente.html")
				.and()
				.formLogin()
				.loginPage("/utente.html")
				.loginProcessingUrl("/login")
				.permitAll()
				.defaultSuccessUrl("/gestione.html", true)
				.failureUrl("/utente.html")
				.and()
				.logout().logoutUrl("/logout")
				.clearAuthentication(true).logoutSuccessUrl("/index.html");
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(authService);
		return provider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
}
