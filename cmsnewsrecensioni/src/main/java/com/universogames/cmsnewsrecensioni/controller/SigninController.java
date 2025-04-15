package com.universogames.cmsnewsrecensioni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.universogames.cmsnewsrecensioni.auth.AuthService;

@RestController
@RequestMapping("/signin")
public class SigninController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping
    public ResponseEntity<String> signup(@RequestParam String nome, @RequestParam String cognome, @RequestParam String username, @RequestParam String password) {
    	String ris = "{ \"msg\": \"[MSG]\" }";
		if (authService.signup(nome, cognome, username, password)) {
			return new ResponseEntity<String>(ris.replace("[MSG]", "OK"), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(ris.replace("[MSG]", "Ops... Something went wrong"), HttpStatus.BAD_REQUEST);
		}
    }
	
}
