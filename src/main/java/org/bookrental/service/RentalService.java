package org.bookrental.service;

import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;

public interface RentalService {

    ApiResponse<RentalResponse> rentBook(RentalRequest rentalRequest);
}
