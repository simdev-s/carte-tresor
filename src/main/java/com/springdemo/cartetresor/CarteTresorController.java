package com.springdemo.cartetresor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarteTresorController {

    @RequestMapping
    public String carteTresor(){
        return "Bienvenue dans le projet de la carte au trésor, Simon !";
    }
}
