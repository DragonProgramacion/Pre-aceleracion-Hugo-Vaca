package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.PersonajeBasicDTO;
import com.alkemy.disney.dto.PersonajeDTO;
import com.alkemy.disney.services.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("personajes")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    public  PersonajeController(PersonajeService personajeService) { this.personajeService = personajeService; }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDTO> getDetailsById(@PathVariable Long id){
        PersonajeDTO personaje = this.personajeService.getDetailsById(id);
        return ResponseEntity.ok(personaje);
    }

    @GetMapping
    public ResponseEntity<List<PersonajeBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer edad,
            @RequestParam(required = false) Set<Long> peliculas,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<PersonajeBasicDTO> personajes = this.personajeService.getByFilters(nombre,edad, peliculas,order);
        return ResponseEntity.ok(personajes);
    }

    @PostMapping()
    public ResponseEntity<PersonajeDTO> save(@RequestBody PersonajeDTO personaje) {
        PersonajeDTO personajeCreada = personajeService.save(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(personajeCreada);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<PersonajeDTO> update(@PathVariable Long id, @RequestBody PersonajeDTO personaje) {
        PersonajeDTO result = this.personajeService.update(id,personaje);
        return ResponseEntity.ok().body(result);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.personajeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/pelicula/{idPelicula}")
    public ResponseEntity<Void> addPelicula(@PathVariable Long id, @PathVariable Long idPelicula){
        this.personajeService.addPelicula(id,idPelicula);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}/pelicula/{idPelicula}")
    public ResponseEntity<Void> removePelicula(@PathVariable Long id, @PathVariable Long idPelicula) {
        this.personajeService.removePelicula(id,idPelicula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
