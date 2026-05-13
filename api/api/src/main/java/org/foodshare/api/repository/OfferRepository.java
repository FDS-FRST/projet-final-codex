package org.foodshare.api.repository;

import org.foodshare.api.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByOffererId(Long offererId);
}