package com.universogames.cmsnewsrecensioni.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universogames.cmsnewsrecensioni.dao.IDaoUtenti;
import com.universogames.cmsnewsrecensioni.entities.Utente;

@RestController
@RequestMapping("/utenti")
public class ControllerUtente {
	
	@Autowired
	private IDaoUtenti dao;
	
	@GetMapping
	public ArrayList<Utente> getAll() {
		return dao.utenti();
	}
	
	@GetMapping("/{id}")
	public Utente getOne(@PathVariable int id) {
		return dao.utente(id);
	}
	
}
