package org.foodshare.api.service;

import org.foodshare.api.dto.ReservationDTO;
import org.foodshare.api.entity.*;
import org.foodshare.api.repository.OfferRepository;
import org.foodshare.api.repository.ReservationRepository;
import org.foodshare.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceSimpleTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testReserverAvecSuccesEtDiminutionStock() {
        // 1. Préparation : offre avec stock=2, étudiant
        Offer offre = new Offer();
        offre.setId(5L);
        offre.setTitle("Panier fruits");
        offre.setQuantity(2);
        offre.setQuantityRemaining(2); // stock initial

        User etudiant = new User("etu@test.com", "123", "Alice", Role.ETUDIANT);
        etudiant.setId(3L);

        Reservation reservationSauvegardee = new Reservation();
        reservationSauvegardee.setId(1L);
        reservationSauvegardee.setOffer(offre);
        reservationSauvegardee.setStudent(etudiant);
        reservationSauvegardee.setStatus(ReservationStatus.EN_ATTENTE);
        reservationSauvegardee.setReservationDate(LocalDateTime.now());

        // 2. Simulation
        when(offerRepository.findById(5L)).thenReturn(Optional.of(offre));
        when(userRepository.findById(3L)).thenReturn(Optional.of(etudiant));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservationSauvegardee);

        // 3. Exécution
        ReservationDTO resultat = reservationService.createReservation(5L, 3L);

        // 4. Vérifications
        assertNotNull(resultat);
        assertEquals(5L, resultat.getOfferId());
        assertEquals(3L, resultat.getStudentId());
        assertEquals("EN_ATTENTE", resultat.getStatus());

        // Vérifie que la quantité restante a diminué de 1
        assertEquals(1, offre.getQuantityRemaining());
        verify(offerRepository).save(offre); // on vérifie que l'offre a été mise à jour
    }
}