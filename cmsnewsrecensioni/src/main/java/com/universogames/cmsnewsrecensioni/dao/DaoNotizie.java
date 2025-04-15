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

import com.universogames.cmsnewsrecensioni.entities.Immagine;
import com.universogames.cmsnewsrecensioni.entities.Notizia;
import com.universogames.cmsnewsrecensioni.entities.Utente;

@Repository
public class DaoNotizie implements IDaoNotizie {

	@Autowired
	private IDaoUtenti daoUtenti;

	@Autowired
	private IDaoImmagini daoImmagini;
	
	private String dbAddress;
	private String username;
	private String password;
	
	public DaoNotizie(@Value("${db.address}") String dbAddress, @Value("${db.user}") String username, @Value("${db.psw}") String password) {
		this.dbAddress = dbAddress;
		this.username = username;
		this.password = password;
	}
	
	@Override
	public ArrayList<Notizia> notizie() {
		ArrayList<Notizia> ris = new ArrayList<Notizia>();
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from notizie");
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				ris.add(new Notizia(rs.getInt("id"), rs.getString("titolo"), rs.getString("categoria"), rs.getString("contenuto"), rs.getDate("datapublicazione"), daoUtenti.utente(rs.getInt("idutente")), daoImmagini.immagineCopertina(rs.getInt("id")), daoImmagini.immaginiCarosello(rs.getInt("id"))));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	@Override
	public Notizia notizia(int id) {	
		Notizia ris = null;
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from notizie where id = ?");
			
			stm.setInt(1, id);
			
			ResultSet rs = stm.executeQuery();
			
			if (rs.next()) {
				ris = new Notizia(rs.getInt("id"), rs.getString("titolo"), rs.getString("categoria"), rs.getString("contenuto"), rs.getDate("datapublicazione"), daoUtenti.utente(rs.getInt("idutente")), daoImmagini.immagineCopertina(rs.getInt("id")), daoImmagini.immaginiCarosello(rs.getInt("id")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}

	@Override
	public ArrayList<Notizia> notizie(Utente utente) {
		ArrayList<Notizia> ris = new ArrayList<Notizia>();
		
		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement stm = conn.prepareStatement("select * from notizie where idutente = ?");
			
			stm.setInt(1, utente.getId());
			
			ResultSet rs = stm.executeQuery();
			
			while (rs.next()) {
				ris.add(new Notizia(rs.getInt("id"), rs.getString("titolo"), rs.getString("categoria"), rs.getString("contenuto"), rs.getDate("datapublicazione"), daoUtenti.utente(rs.getInt("idutente")), daoImmagini.immagineCopertina(rs.getInt("id")), daoImmagini.immaginiCarosello(rs.getInt("id"))));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ris;
	}
	
	@Override
	public boolean aggiungi(Notizia notizia) {
		if (notizia != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("insert into notizie (titolo, categoria, contenuto, datapublicazione, idutente) values (?, ?, ?, ?, ?)");
				
				stm.setString(1, notizia.getTitolo());
				stm.setString(2, notizia.getCategoria());
				stm.setString(3, notizia.getContenuto());
//				stm.setDate(4, notizia.getData());
				stm.setDate(4, new Date(notizia.getData().getTime()));
				stm.setInt(5, notizia.getAutore().getId());
				
				if(stm.executeUpdate() == 0) {
					return false;
				} else {
					ArrayList<Notizia> notizie = notizie(notizia.getAutore());
					int idnotizia = notizie.get(notizie.size() - 1).getId();
					notizia.setId(idnotizia);
					if (!daoImmagini.aggiungi(notizia.getCopertina(), notizia.getId())) {
						return false;
					}
					for (Immagine immagine : notizia.getCarosello()) {
						if (!daoImmagini.aggiungi(immagine, notizia.getId())) {
							return false;
						}
					}
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean elimina(int id) {
		Notizia n = notizia(id);
		
		if (n != null) {
//			if (!daoImmagini.elimina(n.getCopertina().getId())) {
//				return false;
//			}
//			for (Immagine immagine : n.getCarosello()) {
//				if (!daoImmagini.elimina(immagine.getId())) {
//					return false;
//				}
//			}
			if (!daoImmagini.elimina(daoImmagini.immagineCopertina(id).getId())) {
				return false;
			}
			if (!daoImmagini.eliminaNotizia(n.getId())) {
				return false;
			}
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("delete from notizie where id = ?");
				
				stm.setInt(1, id);
				
				return stm.executeUpdate() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	@Override
	public boolean modifica(Notizia notizia) {
		Notizia n = notizia(notizia.getId());
		
		if (n != null) {
			try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
				PreparedStatement stm = conn.prepareStatement("update notizie set titolo = ?, categoria = ?, contenuto = ?, datapublicazione = ?, idutente = ? where id = ?");

				stm.setString(1, notizia.getTitolo());
				stm.setString(2, notizia.getCategoria());
				stm.setString(3, notizia.getContenuto());
//				stm.setDate(4, notizia.getData());
				stm.setDate(4, new Date(notizia.getData().getTime()));
				stm.setInt(5, notizia.getAutore().getId());
				stm.setInt(6, notizia.getId());
				if(stm.executeUpdate() == 0) {
					return false;
				} else {
//					if (!daoImmagini.modifica(notizia.getCopertina(), notizia.getId())) {
//						System.out.println(1);
//						return false;
//					}
					if (!daoImmagini.elimina(daoImmagini.immagineCopertina(notizia.getId()).getId())) {
						return false;
					}
					if (!daoImmagini.aggiungi(notizia.getCopertina(), notizia.getId())) {
						return false;
					}
					if (!daoImmagini.eliminaNotizia(notizia.getId())) {
						return false;
					}
					for (Immagine immagine : notizia.getCarosello()) {
//						if (!daoImmagini.modifica(immagine, notizia.getId())) {
//							return false;
//						}
						if (!daoImmagini.aggiungi(immagine, notizia.getId())) {
							return false;
						}
					}
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
}
