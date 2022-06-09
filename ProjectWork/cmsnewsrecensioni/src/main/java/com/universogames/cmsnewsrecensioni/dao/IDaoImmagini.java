package com.universogames.cmsnewsrecensioni.dao;

import com.universogames.cmsnewsrecensioni.entities.Immagine;

public interface IDaoImmagini {

	Immagine getImg(int id, String path);
	
	boolean createImage(Immagine foto);
	
	boolean delImage(int id);
	
	boolean editImg(int id, Immagine foto);
	
}
