package com.universogames.cmsnewsrecensioni.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universogames.cmsnewsrecensioni.auth.Utente;

@RestController
@RequestMapping("/logged")
public class LoggedController {

    @GetMapping
    public Utente detail(@AuthenticationPrincipal Utente utente) {
        return utente;
    }
    
}
