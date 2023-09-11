package com.api.rest.library.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.rest.library.apirestlibrarybooksbidirectional.models.Book;
import com.api.rest.library.apirestlibrarybooksbidirectional.models.Library;
import com.api.rest.library.apirestlibrarybooksbidirectional.repositories.BookRepository;
import com.api.rest.library.apirestlibrarybooksbidirectional.repositories.LibraryRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @PostMapping
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
        Optional<Library> optionalLibrary = libraryRepository.findById(book.getLibrary().getId());

        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        book.setLibrary(optionalLibrary.get());
        Book savedBook = bookRepository.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBook.getId()).toUri();

        return ResponseEntity.created(location).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book, @PathVariable int id) {
        Optional<Library> optionalLibrary = libraryRepository.findById(book.getLibrary().getId());

        if (!optionalLibrary.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Book> optionalBook = bookRepository.findById(id);

        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        book.setLibrary(optionalLibrary.get());
        book.setId(optionalBook.get().getId());
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable int id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (!optionalBook.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        bookRepository.delete(optionalBook.get());
        return ResponseEntity.noContent().build();
    }
}
