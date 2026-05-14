package org.foodshare.api.service;

import org.foodshare.api.entity.Offer;
import org.foodshare.api.entity.Reservation;
import org.foodshare.api.entity.ReservationStatus;
import org.foodshare.api.entity.User;
import org.foodshare.api.repository.OfferRepository;
import org.foodshare.api.repository.ReservationRepository;
import org.foodshare.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              OfferRepository offerRepository,
                              UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    // Réserver une portion d'une offre
    @Transactional
    public Reservation createReservation(Long offerId, Long studentId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offre non trouvée"));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Étudiant non trouvé"));

        // Vérifier que l'utilisateur est bien un étudiant
        if (!student.getRole().equals(org.foodshare.api.entity.Role.ETUDIANT)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Seul un étudiant peut réserver");
        }

        // Vérifier qu'il reste des portions
        if (offer.getQuantityRemaining() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Plus de stock disponible pour cette offre");
        }

        // Décrémenter la quantité restante
        offer.setQuantityRemaining(offer.getQuantityRemaining() - 1);
        offerRepository.save(offer);

        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setOffer(offer);
        reservation.setStudent(student);
        reservation.setStatus(ReservationStatus.EN_ATTENTE);
        return reservationRepository.save(reservation);
    }

    // Lister les réservations d'un étudiant
    public List<Reservation> getReservationsByStudent(Long studentId) {
        return reservationRepository.findByStudentId(studentId);
    }

    // Lister les réservations pour une offre (utile pour l'offreur)
    public List<Reservation> getReservationsByOffer(Long offerId) {
        return reservationRepository.findByOfferId(offerId);
    }

    // Mettre à jour le statut d'une réservation (retirée / non retirée)
    @Transactional
    public Reservation updateReservationStatus(Long reservationId, ReservationStatus newStatus) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation non trouvée"));
        reservation.setStatus(newStatus);
        return reservationRepository.save(reservation);
    }
}