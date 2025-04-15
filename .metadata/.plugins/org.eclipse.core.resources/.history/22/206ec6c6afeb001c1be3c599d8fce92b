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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.universogames.cmsnewsrecensioni.dao.IDaoImmagini;
import com.universogames.cmsnewsrecensioni.entities.Immagine;
import com.universogames.cmsnewsrecensioni.service.FileStorageService;

@RestController
@RequestMapping("/immagini")
public class ControllerImmagini {
	
	@Autowired
	private IDaoImmagini dao;
	
	@GetMapping("/immaginiCarosello/{id}")
	public ArrayList<Immagine> getAll(@PathVariable int id) {
		return dao.immaginiCarosello(id);
	}
	
	@GetMapping("/immagineCopertina/{id}")
	public Immagine getOne(@PathVariable int id) {
		return dao.immagineCopertina(id);
	}
	
	@Autowired
	private FileStorageService fss;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
    	String ris = "{ \"msg\": \"[MSG]\" }";
		if (fss.salvaFile(file)) {
			return new ResponseEntity<String>(ris.replace("[MSG]", "OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ris.replace("[MSG]", "Ops... Something went wrong"), HttpStatus.BAD_REQUEST);
		}
    }
    
}
