package com.universogames.cmsnewsrecensioni.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.universogames.cmsnewsrecensioni.entities.Immagine;

public class DaoImmagini implements IDaoImmagini {

	@Autowired
	private IDaoUtenti daoUtenti;

	private String dbAddress;
	private String username;
	private String password;

	public DaoImmagini(@Value("${db.address}") String dbAddress, @Value("${db.user}") String username, @Value("${db.psw}") String password) {
		this.dbAddress = dbAddress;
		this.username = username;
		this.password = password;
	}

	@Override
	public Immagine getImg(int id, String path) {

		Immagine img = null;

		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM immagini WHERE id = ?");
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			img = new Immagine(rs.getString(path));

		} catch(SQLException e) {
			System.out.println("mi spiace");
			e.printStackTrace();
		}

		return img;
	}

	@Override
	public boolean createImage (Immagine foto) {

		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO immagini (NOMEIMMAGINE, IDNOTIZIA, IDRECENSIONE) values (?,?,?)"); 
			ps.setString(1,foto.getNome());
			ps.setInt(2, foto.getIdNotizia());
			ps.setInt(3, foto.getIdRecensione());
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delImage(int id) {

		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {

			PreparedStatement ps = conn.prepareStatement(
					"DELETE FROM immagini WHERE id = ?"); 
			ps.setInt(1,id);

			int conferma = ps.executeUpdate();
			return (conferma > 0);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editImg(int id, Immagine foto) {

		try (Connection conn = DriverManager.getConnection(dbAddress, username, password)) {
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE immagini SET nomeimmagine = ?, idnotizia = ?, idrecensione = ? WHERE id = ?");
			
			ps.setString(1,foto.getNome());
			ps.setInt(2, foto.getIdNotizia());
			ps.setInt(3, foto.getIdRecensione());
			ps.setInt(4, id);
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
