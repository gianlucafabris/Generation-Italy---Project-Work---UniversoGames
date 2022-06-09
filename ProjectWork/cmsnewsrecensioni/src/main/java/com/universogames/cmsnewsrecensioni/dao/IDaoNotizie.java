package com.universogames.cmsnewsrecensioni.dao;

import java.util.ArrayList;

import com.universogames.cmsnewsrecensioni.entities.Notizia;
import com.universogames.cmsnewsrecensioni.entities.Utente;

public interface IDaoNotizie {
	
	ArrayList<Notizia> notizie();
	
	Notizia notizia(int id);
	
	ArrayList<Notizia> notizie(Utente utente);
	
	boolean aggiungi(Notizia notizia);
	
	boolean elimina(int id);
	
	boolean modifica(Notizia notizia);
	
}
