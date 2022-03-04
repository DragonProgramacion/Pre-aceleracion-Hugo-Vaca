package com.alkemy.disney.controllers;

import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieService movieService ;

    public MovieController(MovieService movieService) {this.movieService = movieService; }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getDetailsById(@PathVariable Long id) {
        MovieDTO movie = this.movieService.getDetailsById(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping
    public ResponseEntity<List<MovieBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<Long> genres,
            @RequestParam(required = false, defaultValue = "DESC") String order
    ) {
        List<MovieBasicDTO> movies = this.movieService.getByFilters(title, genres,order);
        return ResponseEntity.ok(movies);
    }

    @PostMapping()
    public ResponseEntity<MovieDTO> save(@RequestBody MovieDTO movie) {
        MovieDTO movieCreated = movieService.save(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody MovieDTO movieDTO){
        MovieDTO result = this.movieService.update(id,movieDTO);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.movieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("{id}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> addCharacter(@PathVariable Long id, @PathVariable Long idCharacter) {
        this.movieService.addCharacter(id, idCharacter);
        return ResponseEntity.ok().body(movieService.getDetailsById(id));
    }

    @DeleteMapping("{id}/character/{idCharacter}")
    public ResponseEntity<MovieDTO> removeCharacter(@PathVariable Long id, @PathVariable Long idCharacter) {
        this.movieService.removeCharacter(id, idCharacter);
        return ResponseEntity.ok().body(movieService.getDetailsById(id));
    }

}
