package com.universogames.cmsnewsrecensioni.entities;

public class Immagine {
	
	private int id;
	private String nome;
	private String ruolo;
	
	public Immagine(int id, String nome, String ruolo) {
		this.id = id;
		this.nome = nome;
		this.ruolo = ruolo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
}
