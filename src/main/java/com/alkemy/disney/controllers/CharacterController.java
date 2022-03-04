package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    public CharacterController(CharacterService characterService) { this.characterService = characterService; }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getDetailsById(@PathVariable Long id){
        CharacterDTO character = this.characterService.getDetailsById(id);
        return ResponseEntity.ok(character);
    }

    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Set<Long> movies,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<CharacterBasicDTO> characters = this.characterService.getByFilters(name, age, movies,order);
        return ResponseEntity.ok(characters);
    }

    @PostMapping()
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO character, @RequestParam Long idMovie) {
        CharacterDTO characterCreated = characterService.save(character, idMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterCreated);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable Long id, @RequestBody CharacterDTO character) {
        CharacterDTO result = this.characterService.update(id,character);
        return ResponseEntity.ok().body(result);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.characterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
