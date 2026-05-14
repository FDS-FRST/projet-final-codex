package org.foodshare.api.service;

import org.foodshare.api.dto.OffreDTO;
import org.foodshare.api.entity.Offer;
import org.foodshare.api.entity.Role;
import org.foodshare.api.entity.User;
import org.foodshare.api.repository.OfferRepository;
import org.foodshare.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OfferService offerService;

    private User offreur;
    private OffreDTO request;
    private Offer offer;

    @BeforeEach
    void setUp() {
        offreur = new User("offreur@test.com", "password", "Resto", Role.OFFREUR);
        offreur.setId(1L);

        request = new OffreDTO();
        request.setTitre("Test offre");
        request.setDescription("Description");
        request.setQuantity(3);
        request.setQuantityRemaining(3);
        request.setPrice(2.5);
        request.setStartRetrieval(LocalDateTime.now().plusDays(1));
        request.setEndRetrieval(LocalDateTime.now().plusDays(1).plusHours(2));
        request.setLocation("Cafétéria");

        offer = new Offer();
        offer.setId(1L);
        offer.setTitle(request.getTitre());
        offer.setDescription(request.getDescription());
        offer.setQuantity(request.getQuantity());
        offer.setQuantityRemaining(request.getQuantity());
        offer.setPrice(request.getPrice());
        offer.setStartRetrieval(request.getStartRetrieval());
        offer.setEndRetrieval(request.getEndRetrieval());
        offer.setLocation(request.getLocation());
        offer.setOfferer(offreur);
    }

    @Test
    void createOffer_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(offreur));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        OffreDTO result = offerService.createOffer(request, 1L);

        assertNotNull(result);
        assertEquals("Test offre", result.getTitre());
        assertEquals(3, result.getQuantityRemaining());
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    void createOffer_OffererNotFound_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> offerService.createOffer(request, 99L));
        verify(offerRepository, never()).save(any());
    }

    @Test
    void getOfferById_NotFound_ThrowsException() {
        when(offerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> offerService.getOfferById(99L));
    }
}