package com.universogames.cmsnewsrecensioni.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.universogames.cmsnewsrecensioni.entities.Immagine;

@Repository
public class DaoImmagini implements IDaoImmagini {

	private String dbAddress;
	private String username;
	private String password;

	public DaoImmagini(@Value("${db.address}") String dbAddress, @Value("${db.user}") String username, @Value("${db.psw}") String password) {
		this.dbAddress = dbAddress;
		this.username = username;
		this.password = password;
	}

	@Override
	public ArrayList<Immagine> immaginiCarosello(int id) {
		ArrayList<Immagine> ris = new ArrayList<Immagine>();
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from immagini where idnotizia = ? and ruolo = 'carosello'");

			stm.setInt(1, id);
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				ris.add(new Immagine(rs.getInt("id"), rs.getString("nomeimmagine"), rs.getString("ruolo")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}

	@Override
	public Immagine immagineCopertina(int id) {	
		Immagine ris = null;
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from immagini where idnotizia = ? and ruolo = 'copertina'");
			
			stm.setInt(1, id);
			
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				ris = new Immagine(rs.getInt("id"), rs.getString("nomeimmagine"), rs.getString("ruolo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	@Override
	public Immagine immagine(int id) {	
		Immagine ris = null;
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from immagini where id = ?");
			
			stm.setInt(1, id);
			
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				ris = new Immagine(rs.getInt("id"), rs.getString("nomeimmagine"), rs.getString("ruolo"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}

	@Override
	public boolean aggiungi(Immagine immagine, int idNotizia) {
		if (immagine != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("insert into immagini (nomeimmagine, ruolo, idnotizia) values (?, ?, ?)");
				
				stm.setString(1, immagine.getNome());
				if (immagine.getRuolo().equalsIgnoreCase("copertina") || immagine.getRuolo().equalsIgnoreCase("carosello")) {
					stm.setString(2, immagine.getRuolo());
					stm.setInt(3, idNotizia);
				} else {
					return false;
				}
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}

	@Override
	public boolean elimina(int id) {
		Immagine i = immagine(id);
		
		if (i != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("delete from immagini where id = ?");
				
				stm.setInt(1, id);
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}

	@Override
	public boolean eliminaNotizia(int id) {
		Immagine i = immagine(id);
		
		if (i != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("delete from immagini where idnotizia = ? and ruolo = 'carosello'");
				
				stm.setInt(1, id);
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean modifica(Immagine immagine, int idNotizia) {
		Immagine i = immagine(immagine.getId());
		
		if (i != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("update immagini set nomeimmagine = ?, ruolo = ?, idnotizia = ? where id = ?");

				stm.setString(1, immagine.getNome());
				if (immagine.getRuolo().equalsIgnoreCase("copertina") || immagine.getRuolo().equalsIgnoreCase("carosello")) {
					stm.setString(2, immagine.getRuolo());
					stm.setInt(3, idNotizia);
				} else {
					return false;
				}
				stm.setInt(4, immagine.getId());
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
}
