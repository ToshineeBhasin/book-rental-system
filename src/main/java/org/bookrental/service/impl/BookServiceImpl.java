package org.bookrental.service.impl;


import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.BookRequest;
import org.bookrental.dto.response.BookResponse;
import org.bookrental.entity.Book;
import org.bookrental.exception.BookAlreadyExistsException;
import org.bookrental.repository.BookRepository;
import org.bookrental.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


//BookRequest → Book Entity → DB → BookResponse → ApiResponse


@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public ApiResponse<BookResponse> addBook(BookRequest bookRequest){

        boolean bookExists = bookRepository.existsByIsbn(bookRequest.getIsbn());
        if(bookExists)
        {
            throw new BookAlreadyExistsException("Book already exists with ISBN :" + bookRequest.getIsbn());
        }

        Book book = new Book();
        book.setAuthor(bookRequest.getAuthor());
        book.setAvailableCopies(bookRequest.getTotalCopies());
        book.setCategory(bookRequest.getCategory());
        book.setIsbn(bookRequest.getIsbn());
        book.setTitle(bookRequest.getTitle());
        book.setTotalCopies(bookRequest.getTotalCopies());

        Book savedBook = bookRepository.save(book);

        BookResponse bookResponse = new BookResponse(
                savedBook.getId(),
                savedBook.getAuthor(),
                savedBook.getCategory(),
                savedBook.getTitle(),
                savedBook.getIsbn(),
                savedBook.getAvailableCopies(),
                savedBook.getTotalCopies()
        );

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,"Book added successfully", bookResponse
        );

    }

    @Override
    public ApiResponse<List<BookResponse>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> bookresponse = new ArrayList<>();

        for(Book book : books){
            BookResponse response = new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getIsbn(),
                    book.getTotalCopies(),
                    book.getAvailableCopies()
            );

            bookresponse.add(response);

        }
        return new ApiResponse<>(
                ResponseStatus.SUCCESS, "Books fetched successfully " ,bookresponse
        );
    }
}
