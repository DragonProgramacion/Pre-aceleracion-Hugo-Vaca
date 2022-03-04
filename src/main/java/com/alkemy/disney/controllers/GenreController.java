package com.alkemy.disney.controllers;


import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
public class GenreController {

    @Autowired
    private GenreService genreService ;

    @GetMapping()
    public ResponseEntity<List<GenreDTO>> getAll() {
        List<GenreDTO> genres = genreService.getAll();
        return ResponseEntity.ok().body(genres);
    }

    @PostMapping()
    public ResponseEntity<GenreDTO> save(@RequestBody GenreDTO genre) {
        GenreDTO genreCreated = genreService.save(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreCreated);

    }

}
