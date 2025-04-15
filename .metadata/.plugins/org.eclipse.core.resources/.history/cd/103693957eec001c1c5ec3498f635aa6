package com.universogames.cmsnewsrecensioni.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.universogames.cmsnewsrecensioni.entities.Utente;

@Repository
public class DaoUtenti implements IDaoUtenti {
	
	private String dbAddress;
	private String username;
	private String password;
	
	public DaoUtenti(@Value("${db.address}") String dbAddress, @Value("${db.user}") String username, @Value("${db.psw}") String password) {
		this.dbAddress = dbAddress;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public ArrayList<Utente> utenti() {
		ArrayList<Utente> ris = new ArrayList<Utente>();
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement ps =  conn.prepareStatement("select * from utenti");
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				ris.add(new Utente(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("username"), rs.getString("password")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ris;
	}
	
	@Override
	public Utente utente(int id) {
		Utente ris = null;
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement ps =  conn.prepareStatement("select * from utenti where id = ?");
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				ris = new Utente(rs.getInt("id"), rs.getString("nome"), rs.getString("cognome"), rs.getString("username"), rs.getString("password"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}

	@Override
	public boolean utenteUsername(String usern) {
		boolean ris = false;
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from utenti where username = ?");
			
			stm.setString(1, usern);
			
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				ris = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	@Override
	public boolean aggiungi(Utente utente) {
		if (utente != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement ps =  conn.prepareStatement("insert into utenti (nome, cognome, username, password) values (?,?,?,?)");
				
				ps.setString(1, utente.getNome());
				ps.setString(2, utente.getCognome());
				ps.setString(3, utente.getUsername());
				ps.setString(4, utente.getPassword());
				
				return ps.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean elimina(int id) {
		Utente u = utente(id);
		
		if (u != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement ps =  conn.prepareStatement("delete from utenti where id = ?");
				
				ps.setInt(1, id);
				
				return ps.executeUpdate() > 0;
			} catch (SQLException e) {
				System.out.println("utente terminato.");
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean modifica(Utente utente) {
		Utente u = utente(utente.getId());
		
		if (u != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				
				PreparedStatement ps =  conn.prepareStatement("update utenti set nome = ?, cognome = ?, username = ?, password = ? where id = ?");
				
				ps.setString(1, utente.getNome());
				ps.setString(2, utente.getCognome());
				ps.setString(3, utente.getUsername());
				ps.setString(4, utente.getPassword());
				ps.setInt(5, utente.getId());
				
				int conferma = ps.executeUpdate();
				if (conferma > 0) {
					return true;
				} else {
					return false;
				}
			} catch (SQLException e) {
				System.out.println("utente terminato.");
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
}
