package org.foodshare.api.controller;

import org.foodshare.api.dto.OffreDTO;
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
    public List<OffreDTO> getOffers(@RequestParam(required = false) Long offererId) {
        if (offererId != null) {
            return offerService.getOffersByOfferer(offererId);
        }
        return offerService.getAllOffers();
    }

    // GET /api/offers/{id} -> détail d'une offre
    @GetMapping("/{id}")
    public OffreDTO getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id);
    }

    // POST /api/offers?offererId=1 -> créer une offre (en attendant l'authentification)
    @PostMapping
    public ResponseEntity<OffreDTO> createOffer(@RequestBody OffreDTO offer, @RequestParam Long offererId) {
        OffreDTO saved = offerService.createOffer(offer, offererId);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}