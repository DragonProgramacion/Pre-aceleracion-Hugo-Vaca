package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.PeliculaBasicDTO;
import com.alkemy.disney.dto.PeliculaDTO;
import com.alkemy.disney.services.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService peliculaService ;

    public PeliculaController(PeliculaService peliculaService) {this.peliculaService = peliculaService; }

    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDTO> getDetailsById(@PathVariable Long id) {
        PeliculaDTO pelicula = this.peliculaService.getDetailsById(id);
        return ResponseEntity.ok(pelicula);
    }

    @GetMapping
    public ResponseEntity<List<PeliculaBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Set<Long> generos,
            @RequestParam(required = false, defaultValue = "DESC") String order
    ) {
        List<PeliculaBasicDTO> peliculas = this.peliculaService.getByFilters(titulo,generos,order);
        return ResponseEntity.ok(peliculas);
    }

    @PostMapping()
    public ResponseEntity<PeliculaDTO> save(@RequestBody PeliculaDTO pelicula) {
        PeliculaDTO peliculaCreada = peliculaService.save(pelicula);
        return ResponseEntity.status(HttpStatus.CREATED).body(peliculaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDTO> update(@PathVariable Long id, @RequestBody PeliculaDTO peliculaDTO){
        PeliculaDTO result = this.peliculaService.update(id,peliculaDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.peliculaService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PostMapping("{id}/personaje/{idPersonaje}")
    public ResponseEntity<PeliculaDTO> addPersonaje(@PathVariable Long id, @PathVariable Long idPersonaje) {
        this.peliculaService.addPersonaje(id,idPersonaje);
        return ResponseEntity.ok().body(peliculaService.getDetailsById(id));
    }

    @DeleteMapping("{id}/personaje/{idPersonaje}")
    public ResponseEntity<PeliculaDTO> removePersonaje(@PathVariable Long id, @PathVariable Long idPersonaje) {
        this.peliculaService.removePersonaje(id,idPersonaje);
        return ResponseEntity.ok().body(peliculaService.getDetailsById(id));
    }

}
