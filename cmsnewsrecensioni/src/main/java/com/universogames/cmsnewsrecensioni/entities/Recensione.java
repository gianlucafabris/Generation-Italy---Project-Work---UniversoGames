package com.universogames.cmsnewsrecensioni.entities;

import java.sql.Date;

public class Recensione {
	
	private int id;
	private String titoloVideogioco;
	private Date data;
	private String recensione;
	private int punteggio;
	private Utente autore;
	
	public Recensione(int id, String titoloVideogioco, Date data, String recensione, int punteggio, Utente autore) {
		this.id = id;
		this.titoloVideogioco = titoloVideogioco;
		this.data = data;
		this.recensione = recensione;
		this.punteggio = punteggio;
		this.autore = autore;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitoloVideogioco() {
		return titoloVideogioco;
	}

	public void setTitoloVideogioco(String titoloVideogioco) {
		this.titoloVideogioco = titoloVideogioco;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRecensione() {
		return recensione;
	}

	public void setRecensione(String recensione) {
		this.recensione = recensione;
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public Utente getAutore() {
		return autore;
	}

	public void setAutore(Utente autore) {
		this.autore = autore;
	}
	
}
