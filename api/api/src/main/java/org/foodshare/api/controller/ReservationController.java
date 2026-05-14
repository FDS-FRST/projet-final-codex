package org.foodshare.api.controller;

import org.foodshare.api.dto.ReservationDTO;
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

    @PostMapping("/{offerId}/student/{studentId}")
    public ResponseEntity<ReservationDTO> createReservation(@PathVariable Long offerId,
                                                            @PathVariable Long studentId) {
        ReservationDTO saved = reservationService.createReservation(offerId, studentId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public List<ReservationDTO> getReservationsByStudent(@PathVariable Long studentId) {
        return reservationService.getReservationsByStudent(studentId);
    }

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