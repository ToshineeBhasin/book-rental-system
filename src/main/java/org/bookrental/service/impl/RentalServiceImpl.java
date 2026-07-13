package org.bookrental.service.impl;

import org.bookrental.common.enums.RentalStatus;
import org.bookrental.common.enums.ResponseStatus;
import org.bookrental.common.enums.Role;
import org.bookrental.dto.common.ApiResponse;
import org.bookrental.dto.request.RentalRequest;
import org.bookrental.dto.response.RentalResponse;
import org.bookrental.entity.Book;
import org.bookrental.entity.Rental;
import org.bookrental.entity.User;
import org.bookrental.exception.*;
import org.bookrental.repository.BookRepository;
import org.bookrental.repository.RentalRepository;
import org.bookrental.repository.UserRepository;
import org.bookrental.service.RentalService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public RentalServiceImpl(
            RentalRepository rentalRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public ApiResponse<RentalResponse> rentBook(RentalRequest rentalRequest) {

        User user = userRepository.findById(rentalRequest.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with id: " +
                                        rentalRequest.getUserId()
                        )
                );

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException(
                    "Only customers are allowed to rent books"
            );
        }

        Book book = bookRepository.findById(rentalRequest.getBookId())
                .orElseThrow(() ->
                        new BookNotFoundException(
                                "Book not found with id: " +
                                        rentalRequest.getBookId()
                        )
                );

        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException(
                    "Book is currently not available"
            );
        }

        boolean alreadyRented =
                rentalRepository.existsByUserIdAndBookIdAndStatus(
                        user.getId(),
                        book.getId(),
                        RentalStatus.RENTED
                );

        if (alreadyRented) {
            throw new BookAlreadyRentedException(
                    "User has already rented this book"
            );
        }

        long activeRentalCount =
                rentalRepository.countByUserIdAndStatus(
                        user.getId(),
                        RentalStatus.RENTED
                );

        if (activeRentalCount >= 3) {
            throw new RentalLimitExceededException(
                    "A customer can rent maximum 3 books"
            );
        }

        LocalDate rentalDate = LocalDate.now();
        LocalDate dueDate = rentalDate.plusDays(14);

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setReturnDate(null);
        rental.setStatus(RentalStatus.RENTED);
        rental.setFineAmount(0.0);

        book.setAvailableCopies(
                book.getAvailableCopies() - 1
        );

        bookRepository.save(book);

        Rental savedRental = rentalRepository.save(rental);

        RentalResponse rentalResponse = new RentalResponse(
                savedRental.getId(),
                savedRental.getUser().getId(),
                savedRental.getUser().getName(),
                savedRental.getBook().getId(),
                savedRental.getBook().getTitle(),
                savedRental.getRentalDate(),
                savedRental.getDueDate(),
                savedRental.getReturnDate(),
                savedRental.getStatus(),
                savedRental.getFineAmount()
        );

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "Book rented successfully",
                rentalResponse
        );
    }

    @Override
    @Transactional
    public ApiResponse<RentalResponse> returnBook(Long rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() ->
                        new RentalNotFoundException(
                                "Rental not found with id: " + rentalId
                        )
                );

        if (rental.getStatus() == RentalStatus.RETURNED) {
            throw new BookAlreadyReturnedException(
                    "Book has already been returned"
            );
        }

        LocalDate returnDate = LocalDate.now();

        double fineAmount = 0.0;

        if (returnDate.isAfter(rental.getDueDate())) {

            long lateDays = ChronoUnit.DAYS.between(
                    rental.getDueDate(),
                    returnDate
            );

            fineAmount = lateDays * 10.0;
        }

        rental.setReturnDate(returnDate);
        rental.setStatus(RentalStatus.RETURNED);
        rental.setFineAmount(fineAmount);

        Book book = rental.getBook();

        book.setAvailableCopies(
                book.getAvailableCopies() + 1
        );

        bookRepository.save(book);

        Rental updatedRental = rentalRepository.save(rental);

        RentalResponse rentalResponse = new RentalResponse(
                updatedRental.getId(),
                updatedRental.getUser().getId(),
                updatedRental.getUser().getName(),
                updatedRental.getBook().getId(),
                updatedRental.getBook().getTitle(),
                updatedRental.getRentalDate(),
                updatedRental.getDueDate(),
                updatedRental.getReturnDate(),
                updatedRental.getStatus(),
                updatedRental.getFineAmount()
        );

        return new ApiResponse<>(
                ResponseStatus.SUCCESS,
                "Book returned successfully",
                rentalResponse
        );
    }
}
