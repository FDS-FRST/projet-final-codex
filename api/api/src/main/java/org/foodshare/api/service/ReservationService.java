package org.foodshare.api.service;

import org.foodshare.api.entity.Offer;
import org.foodshare.api.entity.Reservation;
import org.foodshare.api.entity.ReservationStatus;
import org.foodshare.api.entity.User;
import org.foodshare.api.dto.ReservationDTO;
import org.foodshare.api.repository.OfferRepository;
import org.foodshare.api.repository.ReservationRepository;
import org.foodshare.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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
    public ReservationDTO createReservation(Long offerId, Long studentId) {
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
        Reservation saved = reservationRepository.save(reservation);
        return convertToDTO(saved);
    }

    // Lister toutes les réservations sur les offres d'un offreur spécifique
    public List<ReservationDTO> getReservationsByOfferer(Long offererId) {
        // 1. Récupérer toutes les offres de cet offreur
        List<Offer> offers = offerRepository.findByOffererId(offererId);

        // 2. Pour chaque offre, récupérer ses réservations
        return offers.stream()
                .flatMap(offer -> reservationRepository.findByOfferId(offer.getId()).stream())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lister les réservations d'un étudiant
    public List<ReservationDTO> getReservationsByStudent(Long studentId) {
        return reservationRepository.findByStudentId(studentId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lister les réservations pour une offre (utile pour l'offreur)
    public List<ReservationDTO> getReservationsByOffer(Long offerId) {
        return reservationRepository.findByOfferId(offerId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour le statut d'une réservation (retirée / non retirée)
    @Transactional
    public ReservationDTO updateReservationStatus(Long reservationId, ReservationStatus newStatus) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation non trouvée"));
        reservation.setStatus(newStatus);
        Reservation updated = reservationRepository.save(reservation);
        return convertToDTO(updated);
    }

    // Conversion entité -> DTO
    private ReservationDTO convertToDTO(Reservation reservation) {
        return new ReservationDTO(
                reservation.getId(),
                reservation.getOffer().getId(),
                reservation.getOffer().getTitle(),
                reservation.getStudent().getId(),
                reservation.getReservationDate(),
                reservation.getStatus().toString()
        );
    }
}