package com.alkemy.disney.controllers;


import com.alkemy.disney.dto.GeneroDTO;
import com.alkemy.disney.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService ;

    @GetMapping()
    public ResponseEntity<List<GeneroDTO>> getAll() {
        List<GeneroDTO> generos = generoService.getAll();
        return ResponseEntity.ok().body(generos);
    }

    @PostMapping()
    public ResponseEntity<GeneroDTO> save(@RequestBody GeneroDTO genero) {
        GeneroDTO generoCreada = generoService.save(genero);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoCreada);

    }

}
