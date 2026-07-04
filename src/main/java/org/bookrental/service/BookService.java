package org.bookrental.service;

import org.bookrental.entity.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);
    List<Book> getAllBooks();

}
