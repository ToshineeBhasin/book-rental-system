package org.bookrental.controller;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;
import org.bookrental.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
