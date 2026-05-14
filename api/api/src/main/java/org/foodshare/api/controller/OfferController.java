package org.foodshare.api.controller;

import jakarta.validation.Valid;
import org.foodshare.api.dto.OffreDTO;
import org.foodshare.api.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.foodshare.api.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "*")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
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

    // GET /api/offers/offerer?offererId=1 → offres d'un offreur spécifique
    @GetMapping("/offerer")
    public List<OffreDTO> getOffersByOfferer(@RequestParam Long offererId) {
        return offerService.getOffersByOfferer(offererId);
    }

    // POST /api/offers?offererId=1 -> créer une offre (en attendant l'authentification)
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
}