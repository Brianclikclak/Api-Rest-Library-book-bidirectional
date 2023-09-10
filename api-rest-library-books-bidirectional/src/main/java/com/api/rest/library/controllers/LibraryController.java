package com.api.rest.library.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<Library> savedLibrary(@Valid @RequestBody Library library) {
        Library savedLibrary = libraryRepository.save(library);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLibrary.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedLibrary);
    }

}
