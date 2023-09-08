package com.api.rest.library.apirestlibrarybooksbidirectional.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.rest.library.apirestlibrarybooksbidirectional.models.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
