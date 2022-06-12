package com.universogames.cmsnewsrecensioni.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.universogames.cmsnewsrecensioni.entities.Recensione;
import com.universogames.cmsnewsrecensioni.entities.Utente;

@Repository
public class DaoRecensioni implements IDaoRecensioni{

	@Autowired
	private IDaoUtenti daoUtenti;
	
	private String dbAddress;
	private String username;
	private String password;
	
	public DaoRecensioni(@Value("${db.address}") String dbAddress, @Value("${db.user}") String username, @Value("${db.psw}") String password) {
		this.dbAddress = dbAddress;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public ArrayList<Recensione> recensioni() {
		ArrayList<Recensione> ris = new ArrayList<Recensione>();
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from recensioni");
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				ris.add(new Recensione(rs.getInt("id"), rs.getString("titolovideogioco"), rs.getDate("datapublicazione"), rs.getString("recensione"), rs.getInt("punteggio"), daoUtenti.utente(rs.getInt("idutente"))));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	@Override
	public Recensione recensione(int id) {	
		Recensione ris = null;
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from recensioni where id = ?");
			
			stm.setInt(1, id);
			
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				ris = new Recensione(rs.getInt("id"), rs.getString("titolovideogioco"), rs.getDate("datapublicazione"), rs.getString("recensione"), rs.getInt("punteggio"), daoUtenti.utente(rs.getInt("idutente")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}

	@Override
	public ArrayList<Recensione> recensioni(Utente utente) {
		ArrayList<Recensione> ris = new ArrayList<Recensione>();
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from recensioni where idutente = ?");
			
			stm.setInt(1, utente.getId());
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				ris.add(new Recensione(rs.getInt("id"), rs.getString("titolovideogioco"), rs.getDate("datapublicazione"), rs.getString("recensione"), rs.getInt("punteggio"), daoUtenti.utente(rs.getInt("idutente"))));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	@Override
	public boolean aggiungi(Recensione recensione) {
		if (recensione != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("insert into recensioni (titolovideogioco, datapublicazione, recensione, punteggio, idutente) values (?, ?, ?, ?, ?)");
				
				stm.setString(1, recensione.getTitoloVideogioco());
//				stm.setDate(2, recensione.getData());
				stm.setDate(2, new Date(recensione.getData().getTime()));
				stm.setString(3, recensione.getRecensione());
				stm.setInt(4, recensione.getPunteggio());
				stm.setInt(5, recensione.getAutore().getId());
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean elimina(int id) {
		Recensione r = recensione(id);
		
		if (r != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("delete from recensioni where id = ?");
				
				stm.setInt(1, id);
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean modifica(Recensione recensione) {
		Recensione r = recensione(recensione.getId());
		
		if (r != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("update recensioni set titolovideogioco = ?, datapublicazione = ?, recensione = ?, punteggio = ?, idutente = ? where id = ?");

				stm.setString(1, recensione.getTitoloVideogioco());
//				stm.setDate(2, recensione.getData());
				stm.setDate(2, new Date(recensione.getData().getTime()));
				stm.setString(3, recensione.getRecensione());
				stm.setInt(4, recensione.getPunteggio());
				stm.setInt(5, recensione.getAutore().getId());
				stm.setInt(6, recensione.getId());
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
}
