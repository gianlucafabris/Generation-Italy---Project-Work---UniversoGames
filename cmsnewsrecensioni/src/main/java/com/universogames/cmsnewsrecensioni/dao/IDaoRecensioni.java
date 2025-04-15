package com.universogames.cmsnewsrecensioni.dao;

import java.util.ArrayList;

import com.universogames.cmsnewsrecensioni.entities.Recensione;
import com.universogames.cmsnewsrecensioni.entities.Utente;

public interface IDaoRecensioni {
	
	ArrayList<Recensione> recensioni();
	
	Recensione recensione(int id);
	
	ArrayList<Recensione> recensioni(Utente utente);
	
	boolean aggiungi(Recensione recensione);
	
	boolean elimina(int id);
	
	boolean modifica(Recensione recensione);
	
}
