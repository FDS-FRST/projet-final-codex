package org.foodshare.api.controller;

import org.foodshare.api.dto.ReservationDTO;
import org.foodshare.api.entity.ReservationStatus;
import org.foodshare.api.entity.User;
import org.foodshare.api.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Créer réservation : l'étudiant est l'utilisateur authentifié
    @PostMapping("/{offerId}")
    public ResponseEntity<ReservationDTO> createReservation(@PathVariable Long offerId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long studentId = null;
        if (principal instanceof User) {
            studentId = ((User) principal).getId();
        }
        ReservationDTO saved = reservationService.createReservation(offerId, studentId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Lister les réservations de l'étudiant authentifié
    @GetMapping("/me")
    public List<ReservationDTO> getMyReservations() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long studentId = ((User) principal).getId();
            return reservationService.getReservationsByStudent(studentId);
        }
        return List.of();
    }

    // Lister les réservations d'une offre (pour l'offreur)
    @GetMapping("/offer/{offerId}")
    public List<ReservationDTO> getReservationsByOffer(@PathVariable Long offerId) {
        return reservationService.getReservationsByOffer(offerId);
    }

    @PatchMapping("/{reservationId}/status")
    public ReservationDTO updateStatus(@PathVariable Long reservationId,
                                       @RequestParam ReservationStatus status) {
        return reservationService.updateReservationStatus(reservationId, status);
    }
}
