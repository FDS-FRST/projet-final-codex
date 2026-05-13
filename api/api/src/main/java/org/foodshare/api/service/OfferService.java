package org.foodshare.api.service;

import org.foodshare.api.entity.Offer;
import org.foodshare.api.entity.User;
import org.foodshare.api.repository.OfferRepository;
import org.foodshare.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    // Créer une offre
    @Transactional
    public Offer createOffer(Offer offer, Long offererId) {
        User offerer = userRepository.findById(offererId)
                .orElseThrow(() -> new RuntimeException("Utilisateur offreur non trouvé"));
        offer.setOfferer(offerer);
        // La quantité restante est initialisée à la quantité totale
        offer.setQuantityRemaining(offer.getQuantity());
        return offerRepository.save(offer);
    }

    // Lister toutes les offres (pour les étudiants)
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    // Lister les offres d'un offreur
    public List<Offer> getOffersByOfferer(Long offererId) {
        return offerRepository.findByOffererId(offererId);
    }

    // Récupérer une offre par son id
    public Offer getOfferById(Long id) {
        return offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
    }
}
