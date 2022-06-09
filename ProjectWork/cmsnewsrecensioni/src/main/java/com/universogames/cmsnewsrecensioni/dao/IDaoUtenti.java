package com.universogames.cmsnewsrecensioni.dao;

import java.util.ArrayList;

import com.universogames.cmsnewsrecensioni.entities.Utente;

public interface IDaoUtenti {

	ArrayList<Utente> utenti();

	Utente utente(int id);
	
	boolean utenteUsername(String nome);
	
	boolean aggiungi(Utente user);
	
	boolean elimina(int id);
	
	boolean modifica(Utente utente);
	
}
