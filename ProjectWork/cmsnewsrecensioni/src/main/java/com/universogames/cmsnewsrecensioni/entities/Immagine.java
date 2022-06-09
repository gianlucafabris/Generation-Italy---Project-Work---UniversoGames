package com.universogames.cmsnewsrecensioni.entities;

public class Immagine {
	
	private String nome;
	private int idNotizia;
	private int idRecensione;

	public Immagine(String nome, int idNotizia, int idRecensione) {
		this.nome = nome;
		this.idNotizia = idNotizia;
		this.idRecensione = idRecensione;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdNotizia() {
		return idNotizia;
	}

	public void setIdNotizia(int idNotizia) {
		this.idNotizia = idNotizia;
	}

	public int getIdRecensione() {
		return idRecensione;
	}

	public void setIdRecensione(int idRecensione) {
		this.idRecensione = idRecensione;
	}

	
	
}
