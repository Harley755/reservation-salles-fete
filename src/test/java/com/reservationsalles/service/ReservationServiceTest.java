package com.reservationsalles.service;

import com.reservationsalles.exception.ReservationConflictException;
import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Reservation;
import com.reservationsalles.model.Salle;
import com.reservationsalles.model.Utilisateur;
import com.reservationsalles.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires pour ReservationService
 * Focus sur la détection de conflits de réservation
 * 
 * @author Projet Master 1
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UtilisateurService utilisateurService;

    @Mock
    private SalleService salleService;

    @InjectMocks
    private ReservationService reservationService;

    private Utilisateur utilisateur;
    private Salle salle;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // Données de test
        utilisateur = new Utilisateur("John Doe", "john@example.com", "CLIENT");
        utilisateur.setId(1L);

        salle = new Salle("Salle des Fêtes", 100, "Paris", true);
        salle.setId(1L);

        reservation = new Reservation(
                LocalDate.now().plusDays(7),
                LocalTime.of(14, 0),
                LocalTime.of(18, 0),
                utilisateur,
                salle
        );
    }

    @Test
    void testSaveReservation_Success() {
        // Given
        when(utilisateurService.findById(1L)).thenReturn(utilisateur);
        when(salleService.findById(1L)).thenReturn(salle);
        when(reservationRepository.findConflictingReservations(anyLong(), any(), any(), any(), any()))
                .thenReturn(new ArrayList<>());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // When
        Reservation saved = reservationService.save(reservation);

        // Then
        assertNotNull(saved);
        verify(reservationRepository, times(1)).save(reservation);
        verify(reservationRepository, times(1)).findConflictingReservations(
                eq(salle.getId()),
                eq(reservation.getDateReservation()),
                eq(reservation.getHeureDebut()),
                eq(reservation.getHeureFin()),
                isNull()
        );
    }

    @Test
    void testSaveReservation_WithConflict_ThrowsException() {
        // Given
        Reservation existingReservation = new Reservation(
                LocalDate.now().plusDays(7),
                LocalTime.of(15, 0), // Chevauche 14h-18h
                LocalTime.of(17, 0),
                utilisateur,
                salle
        );

        when(utilisateurService.findById(1L)).thenReturn(utilisateur);
        when(salleService.findById(1L)).thenReturn(salle);
        when(reservationRepository.findConflictingReservations(anyLong(), any(), any(), any(), any()))
                .thenReturn(List.of(existingReservation));

        // When & Then
        assertThrows(ReservationConflictException.class, () -> {
            reservationService.save(reservation);
        });

        verify(reservationRepository, never()).save(any());
    }

    @Test
    void testSaveReservation_SalleNonDisponible_ThrowsException() {
        // Given
        salle.setDisponible(false);
        when(utilisateurService.findById(1L)).thenReturn(utilisateur);
        when(salleService.findById(1L)).thenReturn(salle);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.save(reservation);
        });

        assertTrue(exception.getMessage().contains("n'est pas disponible"));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void testSaveReservation_DatePassee_ThrowsException() {
        // Given
        reservation.setDateReservation(LocalDate.now().minusDays(1));
        when(utilisateurService.findById(1L)).thenReturn(utilisateur);
        when(salleService.findById(1L)).thenReturn(salle);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.save(reservation);
        });

        assertTrue(exception.getMessage().contains("ne peut pas être dans le passé"));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void testSaveReservation_HeureFinAvantHeureDebut_ThrowsException() {
        // Given
        reservation.setHeureDebut(LocalTime.of(18, 0));
        reservation.setHeureFin(LocalTime.of(14, 0)); // Fin avant début !

        when(utilisateurService.findById(1L)).thenReturn(utilisateur);
        when(salleService.findById(1L)).thenReturn(salle);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.save(reservation);
        });

        assertTrue(exception.getMessage().contains("doit être après l'heure de début"));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void testFindById_ExistingReservation() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // When
        Reservation found = reservationService.findById(1L);

        // Then
        assertNotNull(found);
        assertEquals(reservation.getId(), found.getId());
    }

    @Test
    void testFindById_NonExistingReservation_ThrowsException() {
        // Given
        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            reservationService.findById(999L);
        });
    }

    @Test
    void testDelete_ExistingReservation() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        doNothing().when(reservationRepository).delete(reservation);

        // When
        reservationService.delete(1L);

        // Then
        verify(reservationRepository, times(1)).delete(reservation);
    }

    @Test
    void testUpdate_Success() {
        // Given
        reservation.setId(1L);
        Reservation updatedData = new Reservation(
                LocalDate.now().plusDays(8),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                utilisateur,
                salle
        );

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(utilisateurService.findById(1L)).thenReturn(utilisateur);
        when(salleService.findById(1L)).thenReturn(salle);
        when(reservationRepository.findConflictingReservations(anyLong(), any(), any(), any(), any()))
                .thenReturn(new ArrayList<>());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // When
        Reservation result = reservationService.update(1L, updatedData);

        // Then
        assertNotNull(result);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testReservationChevauche_True() {
        // Given
        Reservation r1 = new Reservation(
                LocalDate.now(),
                LocalTime.of(14, 0),
                LocalTime.of(18, 0),
                utilisateur,
                salle
        );

        Reservation r2 = new Reservation(
                LocalDate.now(),
                LocalTime.of(16, 0), // Commence avant la fin de r1
                LocalTime.of(20, 0),
                utilisateur,
                salle
        );

        // When
        boolean chevauche = r1.chevauche(r2);

        // Then
        assertTrue(chevauche, "Les créneaux devraient se chevaucher");
    }

    @Test
    void testReservationChevauche_False() {
        // Given
        Reservation r1 = new Reservation(
                LocalDate.now(),
                LocalTime.of(14, 0),
                LocalTime.of(18, 0),
                utilisateur,
                salle
        );

        Reservation r2 = new Reservation(
                LocalDate.now(),
                LocalTime.of(19, 0), // Commence après la fin de r1
                LocalTime.of(22, 0),
                utilisateur,
                salle
        );

        // When
        boolean chevauche = r1.chevauche(r2);

        // Then
        assertFalse(chevauche, "Les créneaux ne devraient pas se chevaucher");
    }
}
