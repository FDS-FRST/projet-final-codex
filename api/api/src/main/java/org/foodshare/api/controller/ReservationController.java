package org.foodshare.api.controller;

import org.foodshare.api.entity.Reservation;
import org.foodshare.api.entity.ReservationStatus;
import org.foodshare.api.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Réserver une offre (étudiant)
    @PostMapping("/{offerId}/student/{studentId}")
    public ResponseEntity<Reservation> createReservation(@PathVariable Long offerId,
                                                         @PathVariable Long studentId) {
        Reservation saved = reservationService.createReservation(offerId, studentId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Lister les réservations d'un étudiant
    @GetMapping("/student/{studentId}")
    public List<Reservation> getReservationsByStudent(@PathVariable Long studentId) {
        return reservationService.getReservationsByStudent(studentId);
    }

    // Lister les réservations d'une offre (pour l'offreur)
    @GetMapping("/offer/{offerId}")
    public List<Reservation> getReservationsByOffer(@PathVariable Long offerId) {
        return reservationService.getReservationsByOffer(offerId);
    }

    // Changer le statut d'une réservation (retirée/non retirée)
    @PatchMapping("/{reservationId}/status")
    public Reservation updateStatus(@PathVariable Long reservationId,
                                    @RequestParam ReservationStatus status) {
        return reservationService.updateReservationStatus(reservationId, status);
    }
}