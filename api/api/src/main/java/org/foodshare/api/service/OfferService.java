package org.foodshare.api.service;

import org.foodshare.api.dto.OffreDTO;
import org.foodshare.api.entity.Offer;
import org.foodshare.api.entity.User;
import org.foodshare.api.repository.OfferRepository;
import org.foodshare.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public OfferService(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    public OffreDTO convertToDTO(Offer offer) {
        return new OffreDTO(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getQuantity(),
                offer.getQuantityRemaining(),
                offer.getPrice(),
                offer.getStartRetrieval(),
                offer.getEndRetrieval(),
                offer.getLocation(),
                offer.getOfferer().getId()
        );
    }

    // Créer une offre avec DTO
    @Transactional
    public OffreDTO createOffer(OffreDTO offreDTO, Long offererId) {
        // 1. Récupère l'utilisateur offreur
        User offerer = userRepository.findById(offererId)
                .orElseThrow(() -> new RuntimeException("Utilisateur offreur non trouvé"));

        // 2. Crée une nouvelle entité Offer (vide)
        Offer offer = new Offer();

        // 3. Remplis l'entité avec les données du DTO
        offer.setTitle(offreDTO.getTitre());
        offer.setDescription(offreDTO.getDescription());
        offer.setQuantity(offreDTO.getQuantity());
        offer.setPrice(offreDTO.getPrice());
        offer.setStartRetrieval(offreDTO.getStartRetrieval());
        offer.setEndRetrieval(offreDTO.getEndRetrieval());
        offer.setLocation(offreDTO.getLocation());
        offer.setOfferer(offerer);
        offer.setQuantityRemaining(offreDTO.getQuantity());

        // 4. Sauvegarde en base de données
        Offer saved = offerRepository.save(offer);

        // 5. Convertis le résultat en DTO et retourne
        return convertToDTO(saved);
    }

    // Lister toutes les offres (pour les étudiants)
    public List<OffreDTO> getAllOffers() {
        return offerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Lister les offres d'un offreur
    public List<OffreDTO> getOffersByOfferer(Long offererId) {
        return offerRepository.findByOffererId(offererId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer une offre par son id
    public OffreDTO getOfferById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre non trouvée"));
        return convertToDTO(offer);
    }
}
