package org.bookrental.service;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.BookRequest;
import org.bookrental.dto.response.BookResponse;
import org.bookrental.entity.Book;

import java.util.List;

public interface BookService {

    //Book addBook(Book book);
    //List<Book> getAllBooks();
    ApiResponse<BookResponse>  addBook(BookRequest bookRequest);
    ApiResponse<List<BookResponse>> getAllBooks();  //response wrapper ke andar list of books rahegi
    ApiResponse<BookResponse> getBookById(Long id);
}
