package com.universogames.cmsnewsrecensioni.entities;

import java.sql.Date;
import java.util.ArrayList;

public class Notizia {
	
	private int id;
	private String titolo;
	private String categoria;
	private String contenuto;
	private Date data;
	private Utente autore;
	private Immagine copertina;
	private ArrayList<Immagine> carosello;
	
	public Notizia(int id, String titolo, String categoria, String contenuto, Date data, Utente autore, Immagine copertina, ArrayList<Immagine> carosello) {
		this.id = id;
		this.titolo = titolo;
		this.categoria = categoria;
		this.contenuto = contenuto;
		this.data = data;
		this.autore = autore;
		this.copertina = copertina;
		this.carosello = carosello;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}

	public Immagine getCopertina() {
		return copertina;
	}

	public void setCopertina(Immagine copertina) {
		this.copertina = copertina;
	}

	public ArrayList<Immagine> getCarosello() {
		return carosello;
	}

	public void setCarosello(ArrayList<Immagine> carosello) {
		this.carosello = carosello;
	}
	
}
