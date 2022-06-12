package com.universogames.cmsnewsrecensioni.dao;

import java.util.ArrayList;

import com.universogames.cmsnewsrecensioni.entities.Immagine;

public interface IDaoImmagini {
	
	public ArrayList<Immagine> immaginiCarosello(int id);

	public Immagine immagineCopertina(int id);

	public Immagine immagine(int id);
	
	public boolean aggiungi(Immagine immagine, int idNotizia);
	
	public boolean elimina(int id);
	
	public boolean eliminaNotizia(int id);
	
	public boolean modifica(Immagine immagine, int idNotizia);
	
}
