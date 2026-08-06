package org.bookrental.controller;

import org.bookrental.common.enums.RentalStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;
import org.bookrental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/active/count")
    public ResponseEntity<ApiResponse<Long>> getActiveRentalCount() {

        return ResponseEntity.ok(
                rentalService.getActiveRentalCount()
        );
    }

    @GetMapping("/status/count")
    public ResponseEntity<ApiResponse<Map<RentalStatus, Long>>> getRentalCountByStatus() {

        return ResponseEntity.ok(
                rentalService.getRentalCountByStatus()
        );
    }

    @GetMapping("/user/count")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getRentalCountByUser() {

        return ResponseEntity.ok(
                rentalService.getRentalCountByUser()
        );
    }

    @GetMapping("/overdue/partition")
    public ResponseEntity<ApiResponse<Map<Boolean, List<RentalResponse>>>> partitionRentalsByOverdueStatus() {

        return ResponseEntity.ok(
                rentalService.partitionRentalsByOverdueStatus()
        );
    }

    @GetMapping("/highest-fine")
    public ResponseEntity<ApiResponse<RentalResponse>> getHighestFineRental() {

        return ResponseEntity.ok(
                rentalService.getHighestFineRental()
        );
    }

    @GetMapping("/fine/total")
    public ResponseEntity<ApiResponse<Double>> getTotalFineCollected() {

        return ResponseEntity.ok(
                rentalService.getTotalFineCollected()
        );
    }

}
