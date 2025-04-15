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

import com.universogames.cmsnewsrecensioni.dao.IDaoRecensioni;
import com.universogames.cmsnewsrecensioni.dao.IDaoUtenti;
import com.universogames.cmsnewsrecensioni.entities.Recensione;

@RestController
@RequestMapping("/recensioni")
public class ControllerRecensione {

	@Autowired
	private IDaoRecensioni dao;
	
	@Autowired
	private IDaoUtenti daoUtenti;

	@GetMapping
	public ArrayList<Recensione> getAll() {
		return dao.recensioni();
	}

	@GetMapping("/utente/{idUtente}")
	public ArrayList<Recensione> getAllUser(@PathVariable int idUtente) {
		return dao.recensioni(daoUtenti.utente(idUtente));
	}
	
	@GetMapping("/{id}")
	public Recensione getOne(@PathVariable int id) {
		return dao.recensione(id);
	}
	
	@PostMapping
	public ResponseEntity<String> post(@RequestBody Recensione recensione) {
		String ris = "{ \"msg\": \"[MSG]\" }";
		if (dao.aggiungi(recensione)) {
			return new ResponseEntity<String>(ris.replace("[MSG]", "OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ris.replace("[MSG]", "Ops... Something went wrong"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		String ris = "{ \"msg\": \"[MSG]\" }";
		if (dao.elimina(id)) {
			return new ResponseEntity<String>(ris.replace("[MSG]", "OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ris.replace("[MSG]", "Ops... Something went wrong"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Recensione recensione) {
		String ris = "{ \"msg\": \"[MSG]\" }";
		if (dao.modifica(recensione)) {
			return new ResponseEntity<String>(ris.replace("[MSG]", "OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ris.replace("[MSG]", "Ops... Something went wrong"), HttpStatus.BAD_REQUEST);
		}
	}
	
}
