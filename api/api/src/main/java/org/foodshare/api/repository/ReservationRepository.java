package org.foodshare.api.repository;

import org.foodshare.api.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStudentId(Long studentId);
    List<Reservation> findByOfferId(Long offerId);
}