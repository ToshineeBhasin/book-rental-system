package org.bookrental.controller;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;
import org.bookrental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<ApiResponse<RentalResponse>> rentBook(@Valid @RequestBody RentalRequest rentalRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rentalService.rentBook(rentalRequest));
    }

    @PutMapping("/{rentalId}/return")
    public ResponseEntity<ApiResponse<RentalResponse>> returnBook(@PathVariable Long rentalId) {

        return ResponseEntity.ok(
                rentalService.returnBook(rentalId)
        );
    }

    @GetMapping("/{rentalId:\\d+}")  // \\d+ means only numeric values are accepted.
    public ResponseEntity<ApiResponse<RentalResponse>> getRentalById(
            @PathVariable Long rentalId) {

        return ResponseEntity.ok(
                rentalService.getRentalById(rentalId)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<RentalResponse>>> getRentalsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                rentalService.getRentalsByUserId(userId)
        );
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<List<RentalResponse>>> getRentalsByBookId(
            @PathVariable Long bookId) {

        return ResponseEntity.ok(
                rentalService.getRentalsByBookId(bookId)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<RentalResponse>>> getActiveRentals() {

        return ResponseEntity.ok(
                rentalService.getActiveRentals()
        );
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<RentalResponse>>> getOverdueRentals() {

        return ResponseEntity.ok(
                rentalService.getOverdueRentals()
        );
    }


}
