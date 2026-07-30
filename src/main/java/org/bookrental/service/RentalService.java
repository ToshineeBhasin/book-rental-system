package org.bookrental.service;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;

import java.util.List;

public interface RentalService {
    ApiResponse<RentalResponse> rentBook(RentalRequest rentalRequest);
    ApiResponse<RentalResponse> returnBook(Long rentalId);
    ApiResponse<RentalResponse> getRentalById(Long rentalId);
    ApiResponse<List<RentalResponse>> getRentalsByUserId(Long userId);
    ApiResponse<List<RentalResponse>> getRentalsByBookId(Long bookId);
    ApiResponse<List<RentalResponse>> getActiveRentals();
    ApiResponse<List<RentalResponse>> getOverdueRentals();
}
