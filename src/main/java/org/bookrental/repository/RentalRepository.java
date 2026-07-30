package org.bookrental.repository;

import org.bookrental.common.enums.RentalStatus;
import org.bookrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    long countByUserIdAndStatus(Long userId, RentalStatus status);

    boolean existsByUserIdAndBookIdAndStatus( Long userId, Long bookId, RentalStatus status );

    List<Rental> findByUserId(Long userId);

    List<Rental> findByBookId(Long bookId);

    List<Rental> findByStatus(RentalStatus status);

    List<Rental> findByStatusAndDueDateBefore(RentalStatus status, LocalDate date);
}
