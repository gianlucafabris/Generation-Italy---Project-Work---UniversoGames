package com.universogames.cmsnewsrecensioni.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.universogames.cmsnewsrecensioni.auth.Utente;
import com.universogames.cmsnewsrecensioni.security.Roles;

@Service
public class AuthService implements UserDetailsService {

	private UtenteRepository dao;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public AuthService(UtenteRepository dao, PasswordEncoder passwordEncoder) {
		this.dao = dao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<? extends UserDetails> user = dao.findByUsername(username);

		if (user.isPresent())
			return user.get();

		throw new UsernameNotFoundException("Nessun utente col username: " + username);
	}

	public boolean signup(String nome, String cognome, String username, String password) {
		if (nome != null && cognome != null && username != null && password != null) {
			Utente newUtente = new Utente();
			newUtente.setNome(nome);
			newUtente.setCognome(cognome);
			newUtente.setUsername(username);
			newUtente.setPassword(passwordEncoder.encode(password));
			newUtente.setRuolo(Roles.USER);
			try {			
				dao.save(newUtente);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
		
	}
	
}
