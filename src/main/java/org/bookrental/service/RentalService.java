package org.bookrental.service;

import org.bookrental.common.enums.RentalStatus;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;

import java.util.List;
import java.util.Map;

public interface RentalService {
    ApiResponse<RentalResponse> rentBook(RentalRequest rentalRequest);
    ApiResponse<RentalResponse> returnBook(Long rentalId);
    ApiResponse<RentalResponse> getRentalById(Long rentalId);
    ApiResponse<List<RentalResponse>> getRentalsByUserId(Long userId);
    ApiResponse<List<RentalResponse>> getRentalsByBookId(Long bookId);
    ApiResponse<List<RentalResponse>> getActiveRentals();
    ApiResponse<List<RentalResponse>> getOverdueRentals();
    ApiResponse<Long> getActiveRentalCount();
    ApiResponse<Map<RentalStatus, Long>> getRentalCountByStatus();
    ApiResponse<Map<String, Long>> getRentalCountByUser();
    ApiResponse<Map<Boolean, List<RentalResponse>>> partitionRentalsByOverdueStatus();
    ApiResponse<RentalResponse> getHighestFineRental();
    ApiResponse<Double> getTotalFineCollected();

}
