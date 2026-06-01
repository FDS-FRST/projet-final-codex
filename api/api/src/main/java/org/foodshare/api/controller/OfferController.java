package org.foodshare.api.controller;

import jakarta.validation.Valid;
import org.foodshare.api.dto.OffreDTO;
import org.foodshare.api.dto.ReservationDTO;
import org.foodshare.api.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.foodshare.api.service.OfferService;
import org.foodshare.api.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "*")
public class OfferController {

    private final OfferService offerService;
    private final ReservationService reservationService;

    public OfferController(OfferService offerService, ReservationService reservationService) {
        this.offerService = offerService;
        this.reservationService = reservationService;
    }

    // GET /api/offers -> liste toutes les offres
    @GetMapping
    public List<OffreDTO> getOffers() {
        return offerService.getAllOffers();
    }

    // GET /api/offers/me -> offres de l'offreur authentifié
    @GetMapping("/me")
    public List<OffreDTO> getMyOffers() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long userId = ((User) principal).getId();
            return offerService.getOffersByOfferer(userId);
        }
        return List.of();
    }

    // GET /api/offers/{id} -> détail d'une offre
    @GetMapping("/{id}")
    public OffreDTO getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id);
    }

    // POST /api/offers -> créer une offre (l'offreur est l'utilisateur authentifié)
    @PostMapping
    public ResponseEntity<OffreDTO> createOffer(@Valid @RequestBody OffreDTO offer) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long offererId = null;
        if (principal instanceof User) {
            offererId = ((User) principal).getId();
        }
        OffreDTO saved = offerService.createOffer(offer, offererId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public OffreDTO updateOffer(@PathVariable Long id, @Valid @RequestBody OffreDTO offer) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long offererId = ((User) principal).getId();
            return offerService.updateOffer(id, offer, offererId);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long offererId = ((User) principal).getId();
            offerService.deleteOffer(id, offererId);
            return ResponseEntity.noContent().build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié");
    }

    @GetMapping("/{id}/reservations")
    public List<ReservationDTO> getReservationsByOffer(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            Long offererId = ((User) principal).getId();
            OffreDTO offer = offerService.getOfferById(id);
            if (!offererId.equals(offer.getOffererId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous ne pouvez consulter que les réservations de vos offres");
            }
            return reservationService.getReservationsByOffer(id);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié");
    }
}