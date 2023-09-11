package com.api.rest.library.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rest.library.apirestlibrarybooksbidirectional.models.Library;
import com.api.rest.library.apirestlibrarybooksbidirectional.repositories.LibraryRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping
    public ResponseEntity<Page<Library>> listLibrarys(Pageable pageable) {
        return ResponseEntity.ok(libraryRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> findLibraryById(@PathVariable int id) {
        Optional<Library> OptionalLibrary = libraryRepository.findById(id);

        if (!OptionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(OptionalLibrary.get());

    }

    @PostMapping
    public ResponseEntity<Library> savedLibrary(@Valid @RequestBody Library library) {
        Library savedLibrary = libraryRepository.save(library);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLibrary.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedLibrary);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> editLibrary(@PathVariable int id, @Valid @RequestBody Library library) {
        Optional<Library> OptionalLibrary = libraryRepository.findById(id);

        if (!OptionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        library.setId(OptionalLibrary.get().getId());
        libraryRepository.save(library);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Library> deleteLibrary(@PathVariable int id) {
        Optional<Library> OptionalLibrary = libraryRepository.findById(id);

        if (!OptionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        libraryRepository.delete(OptionalLibrary.get());
        return ResponseEntity.noContent().build();
    }

}
