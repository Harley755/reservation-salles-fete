package com.reservationsalles.service;

import com.reservationsalles.exception.ReservationConflictException;
import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Reservation;
import com.reservationsalles.model.Salle;
import com.reservationsalles.model.Utilisateur;
import com.reservationsalles.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service métier pour la gestion des Réservations
 * Contient la logique critique de détection de conflits de réservation
 
 */
@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UtilisateurService utilisateurService;
    private final SalleService salleService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              UtilisateurService utilisateurService,
                              SalleService salleService) {
        this.reservationRepository = reservationRepository;
        this.utilisateurService = utilisateurService;
        this.salleService = salleService;
    }

    /**
     * Récupère toutes les réservations
     * 
     * @return Liste de toutes les réservations
     */
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    /**
     * Récupère une réservation par son ID
     * 
     * @param id ID de la réservation
     * @return La réservation trouvée
     * @throws ResourceNotFoundException si la réservation n'existe pas
     */
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", "id", id));
    }

    /**
     * Récupère les réservations d'un utilisateur
     * 
     * @param utilisateurId ID de l'utilisateur
     * @return Liste des réservations de cet utilisateur
     */
    @Transactional(readOnly = true)
    public List<Reservation> findByUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        return reservationRepository.findByUtilisateur(utilisateur);
    }

    /**
     * Récupère les réservations d'une salle
     * 
     * @param salleId ID de la salle
     * @return Liste des réservations de cette salle
     */
    @Transactional(readOnly = true)
    public List<Reservation> findBySalle(Long salleId) {
        Salle salle = salleService.findById(salleId);
        return reservationRepository.findBySalle(salle);
    }

    /**
     * Récupère les réservations futures d'un utilisateur
     * 
     * @param utilisateurId ID de l'utilisateur
     * @return Liste des réservations futures
     */
    @Transactional(readOnly = true)
    public List<Reservation> findFutureReservationsByUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        return reservationRepository.findFutureReservationsByUtilisateur(utilisateur, LocalDate.now());
    }

    /**
     * Récupère les réservations futures d'une salle
     * 
     * @param salleId ID de la salle
     * @return Liste des réservations futures
     */
    @Transactional(readOnly = true)
    public List<Reservation> findFutureReservationsBySalle(Long salleId) {
        Salle salle = salleService.findById(salleId);
        return reservationRepository.findFutureReservationsBySalle(salle, LocalDate.now());
    }

    /**
     * Crée une nouvelle réservation après validation des règles métier
     * 
     * @param reservation Réservation à créer
     * @return La réservation créée
     * @throws ReservationConflictException si un conflit est détecté
     * @throws IllegalArgumentException si les données sont invalides
     */
    public Reservation save(Reservation reservation) {
        // Validation 1 : Vérifier que l'utilisateur et la salle existent
        if (reservation.getUtilisateur() == null || reservation.getUtilisateur().getId() == null) {
            throw new IllegalArgumentException("L'utilisateur est obligatoire");
        }
        if (reservation.getSalle() == null || reservation.getSalle().getId() == null) {
            throw new IllegalArgumentException("La salle est obligatoire");
        }

        // Charger les entités complètes
        Utilisateur utilisateur = utilisateurService.findById(reservation.getUtilisateur().getId());
        Salle salle = salleService.findById(reservation.getSalle().getId());
        reservation.setUtilisateur(utilisateur);
        reservation.setSalle(salle);

        // Validation 2 : Vérifier que la salle est disponible
        if (!salle.isDisponible()) {
            throw new IllegalArgumentException("La salle '" + salle.getNom() + "' n'est pas disponible");
        }

        // Validation 3 : Vérifier que la date n'est pas dans le passé
        if (reservation.getDateReservation().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de réservation ne peut pas être dans le passé");
        }

        // Validation 4 : Vérifier que l'heure de fin est après l'heure de début
        if (!reservation.isHeureFinApresHeureDebut()) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début");
        }

        // Validation 5 : CRITIQUE - Vérifier qu'il n'y a pas de conflit
        checkForConflicts(reservation);

        return reservationRepository.save(reservation);
    }

    /**
     * Met à jour une réservation existante après validation
     * 
     * @param id ID de la réservation à modifier
     * @param reservationDetails Nouvelles données
     * @return La réservation mise à jour
     * @throws ResourceNotFoundException si la réservation n'existe pas
     * @throws ReservationConflictException si un conflit est détecté
     */
    public Reservation update(Long id, Reservation reservationDetails) {
        Reservation reservation = findById(id);

        // Charger les entités complètes
        Utilisateur utilisateur = utilisateurService.findById(reservationDetails.getUtilisateur().getId());
        Salle salle = salleService.findById(reservationDetails.getSalle().getId());

        // Validation : Vérifier que la salle est disponible
        if (!salle.isDisponible()) {
            throw new IllegalArgumentException("La salle '" + salle.getNom() + "' n'est pas disponible");
        }

        // Validation : Vérifier que la date n'est pas dans le passé
        if (reservationDetails.getDateReservation().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de réservation ne peut pas être dans le passé");
        }

        // Validation : Vérifier que l'heure de fin est après l'heure de début
        if (!reservationDetails.isHeureFinApresHeureDebut()) {
            throw new IllegalArgumentException("L'heure de fin doit être après l'heure de début");
        }

        // Mise à jour des champs
        reservation.setDateReservation(reservationDetails.getDateReservation());
        reservation.setHeureDebut(reservationDetails.getHeureDebut());
        reservation.setHeureFin(reservationDetails.getHeureFin());
        reservation.setUtilisateur(utilisateur);
        reservation.setSalle(salle);

        // Validation : CRITIQUE - Vérifier qu'il n'y a pas de conflit
        checkForConflicts(reservation);

        return reservationRepository.save(reservation);
    }

    /**
     * Supprime une réservation
     * 
     * @param id ID de la réservation à supprimer
     * @throws ResourceNotFoundException si la réservation n'existe pas
     */
    public void delete(Long id) {
        Reservation reservation = findById(id);
        reservationRepository.delete(reservation);
    }

    /**
     * MÉTHODE CRITIQUE : Vérifie les conflits de réservation
     * 
     * Algorithme :
     * 1. Récupère toutes les réservations de la même salle à la même date
     * 2. Exclut la réservation en cours d'édition (si applicable)
     * 3. Vérifie si les créneaux horaires se chevauchent
     * 
     * Condition de chevauchement :
     * - heureDebut_nouvelle < heureFin_existante ET
     * - heureFin_nouvelle > heureDebut_existante
     * 
     * @param reservation Réservation à vérifier
     * @throws ReservationConflictException si un conflit est détecté
     */
    private void checkForConflicts(Reservation reservation) {
        List<Reservation> conflits = reservationRepository.findConflictingReservations(
                reservation.getSalle().getId(),
                reservation.getDateReservation(),
                reservation.getHeureDebut(),
                reservation.getHeureFin(),
                reservation.getId()  // null lors de la création, ID lors de la modification
        );

        if (!conflits.isEmpty()) {
            Reservation premierConflit = conflits.get(0);
            throw new ReservationConflictException(
                    reservation.getSalle().getNom(),
                    reservation.getDateReservation().toString(),
                    premierConflit.getHeureDebut().toString(),
                    premierConflit.getHeureFin().toString()
            );
        }
    }

    /**
     * Compte le nombre total de réservations
     * 
     * @return Nombre de réservations
     */
    @Transactional(readOnly = true)
    public long count() {
        return reservationRepository.count();
    }

    /**
     * Compte le nombre de réservations d'un utilisateur
     * 
     * @param utilisateurId ID de l'utilisateur
     * @return Nombre de réservations
     */
    @Transactional(readOnly = true)
    public long countByUtilisateur(Long utilisateurId) {
        Utilisateur utilisateur = utilisateurService.findById(utilisateurId);
        return reservationRepository.countByUtilisateur(utilisateur);
    }

    /**
     * Compte le nombre de réservations d'une salle
     * 
     * @param salleId ID de la salle
     * @return Nombre de réservations
     */
    @Transactional(readOnly = true)
    public long countBySalle(Long salleId) {
        Salle salle = salleService.findById(salleId);
        return reservationRepository.countBySalle(salle);
    }
}
