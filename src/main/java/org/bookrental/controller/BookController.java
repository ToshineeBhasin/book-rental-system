
package org.bookrental.controller;

import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.BookRequest;
import org.bookrental.dto.response.BookResponse;
import org.bookrental.dto.response.RentalResponse;
import org.bookrental.entity.Book;
import org.bookrental.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ApiResponse<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest){
        return bookService.addBook(bookRequest);
    }

    @GetMapping
    public ApiResponse<List<BookResponse>> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(bookService.updateBook(id,bookRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchBook(@RequestParam String title){
        return ResponseEntity.ok(bookService.searchBookByTitle(title));
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getBooksWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                bookService.getBooksWithPAgination(
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getAvailableBooks(){

        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    @GetMapping("/category/available")
    public ResponseEntity<ApiResponse<Boolean>> isCategoryAvailable(@RequestParam String category) {

        return ResponseEntity.ok(
                bookService.isCategoryAvailable(category)
        );
    }

    public ResponseEntity<ApiResponse<BookResponse>> getFirstAvailableBookByCategory(@RequestParam String category)
    {
        return ResponseEntity.ok(bookService.getFirstAvailableBookByCategory(category));
    }

    @GetMapping("/sort/available-copies")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksSortedByAvailableCopies() {

        return ResponseEntity.ok(bookService.getBooksSortedByAvailableCopies()
        );
    }

    @GetMapping("/category/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getBookCountByCategory() {

        return ResponseEntity.ok(
                bookService.getBookCountByCategory()
        );
    }


}
