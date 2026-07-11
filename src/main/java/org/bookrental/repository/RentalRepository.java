package org.bookrental.repository;

import org.bookrental.common.enums.RentalStatus;
import org.bookrental.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    long countByUserIdAndStatus(Long userId, RentalStatus status);

    boolean existsByUserIdAndBookIdAndStatus( Long userId, Long bookId, RentalStatus status );
}
