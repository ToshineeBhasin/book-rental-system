package org.bookrental.service.impl;


import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.BookRequest;
import org.bookrental.dto.response.BookResponse;
import org.bookrental.entity.Book;
import org.bookrental.exception.BookAlreadyExistsException;
import org.bookrental.exception.BookNotFoundException;
import org.bookrental.repository.BookRepository;
import org.bookrental.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public ApiResponse<BookResponse> getBookById(Long id){

        Book book = bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException("Book not found with id : " + id)
        );
        //orElseThrow() is used to retrieve the value from an Optional when present, otherwise it throws the supplied exception.
        //It avoids manual null checking and makes the failure case explicit.
        BookResponse bookResponse = new BookResponse(
                book.getId(),
                book.getAuthor(),
                book.getCategory(),
                book.getTitle(),
                book.getIsbn(),
                book.getAvailableCopies(),
                book.getTotalCopies()
        );
        return new ApiResponse<>(
                ResponseStatus.SUCCESS, "Book Fetched Sucessfully" , bookResponse
                );
    }

    public ApiResponse<BookResponse> updateBook(Long id, BookRequest bookRequest){
        Book existingBook = bookRepository.findById(id).orElseThrow(
                ()-> new BookNotFoundException("Book not found with id : " + id)
        );
                existingBook.setTitle(bookRequest.getTitle());
                existingBook.setAuthor(bookRequest.getAuthor());
                existingBook.setCategory(bookRequest.getCategory());
                existingBook.setIsbn(bookRequest.getIsbn());
                existingBook.setTotalCopies(bookRequest.getTotalCopies());
                existingBook.setAvailableCopies(bookRequest.getTotalCopies());

                Book updatedBook = bookRepository.save(existingBook);

                BookResponse bookResponse = new BookResponse(
                        updatedBook.getId(),
                        updatedBook.getAuthor(),
                        updatedBook.getCategory(),
                        updatedBook.getTitle(),
                        updatedBook.getIsbn(),
                        updatedBook.getAvailableCopies(),
                        updatedBook.getTotalCopies()
                );

        return new ApiResponse<>(ResponseStatus.SUCCESS, "Book updated successfully.", bookResponse);
    }

    public ApiResponse<Void> deleteBook(Long id){
        Book deletBook = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(
                "Book not found with id : " + id)
                        );

        bookRepository.delete(deletBook);

        return new ApiResponse<>(
                ResponseStatus.SUCCESS, "Book deleted successfully.", null
        );
    }

    public ApiResponse<List<BookResponse>> searchBookByTitle(String title){
        List<Book> books = bookRepository.findBookByTitleContainingIgnoreCase(title);

        List<BookResponse> bookResponses = new ArrayList<>();
        for(Book book : books){
            BookResponse bookResponse = new BookResponse(
                    book.getId(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getAvailableCopies(),
                    book.getTotalCopies()
            );
            bookResponses.add(bookResponse);

        }

        if(bookResponses.isEmpty())
        {
            return new ApiResponse<>(
                    ResponseStatus.FAILED, "Books not found.", bookResponses
            );
        }
        else
        {
            return new ApiResponse<>(
                    ResponseStatus.SUCCESS,"Book Searched successfully", bookResponses
            );
        }

    }

    public ApiResponse<Page<BookResponse>> getBooksWithPAgination(int page,
                                                           int size,
                                                           String sortBy,
                                                           String sortDir){
        Sort sort;
        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Book> bookPage = bookRepository.findAll(pageable);

        Page<BookResponse> bookResponsePage =
                bookPage.map(book -> new BookResponse(
                        book.getId(),
                        book.getAuthor(),
                        book.getCategory(),
                        book.getTitle(),
                        book.getIsbn(),
                        book.getAvailableCopies(),
                        book.getTotalCopies()
                ));

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "Books fetched successfully",
                bookResponsePage
        );

    }
}
