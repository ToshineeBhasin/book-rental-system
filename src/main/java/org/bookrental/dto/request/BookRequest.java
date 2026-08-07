package org.bookrental.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BookRequest {
    @NotNull(message = "Title is mandatory.")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotNull(message = "Author is mandatory.")
    @Size(max = 100, message = "Author cannot exceed 100 characters")
    private String author;

    @NotNull(message = "ISBN is mandatory")
    @Size(max = 150, message = "ISBN cannot exceed 150 characters")
    private String isbn;

    @NotNull(message = "Category is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String category;

    @NotNull(message = "Total copies is mandatory")
    @Min(value = 1, message = "Total copies should be less than 1")
    @Max(value = 1000, message = "Total copies should not be greater than 1000")
    private Integer totalCopies;

    public BookRequest(String title, String author, String isbn, String category, Integer totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.totalCopies = totalCopies;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }


}
