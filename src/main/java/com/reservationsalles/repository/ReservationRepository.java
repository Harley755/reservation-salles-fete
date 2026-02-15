package com.reservationsalles.repository;

import com.reservationsalles.model.Reservation;
import com.reservationsalles.model.Salle;
import com.reservationsalles.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repository pour l'entité Reservation
 * Contient des requêtes critiques pour la détection de conflits de réservation
 
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Recherche les réservations d'un utilisateur
     * 
     * @param utilisateur Utilisateur concerné
     * @return Liste des réservations de cet utilisateur
     */
    List<Reservation> findByUtilisateur(Utilisateur utilisateur);

    /**
     * Recherche les réservations d'une salle
     * 
     * @param salle Salle concernée
     * @return Liste des réservations de cette salle
     */
    List<Reservation> findBySalle(Salle salle);

    /**
     * Recherche les réservations à une date donnée
     * 
     * @param dateReservation Date à rechercher
     * @return Liste des réservations à cette date
     */
    List<Reservation> findByDateReservation(LocalDate dateReservation);

    /**
     * Recherche les réservations d'une salle à une date donnée
     * 
     * @param salle Salle concernée
     * @param dateReservation Date à rechercher
     * @return Liste des réservations de cette salle à cette date
     */
    List<Reservation> findBySalleAndDateReservation(Salle salle, LocalDate dateReservation);

    /**
     * REQUÊTE CRITIQUE : Détecte les conflits de réservation pour une salle
     * 
     * Un conflit existe si une réservation existante chevauche le créneau demandé :
     * - Même salle
     * - Même date
     * - Chevauchement horaire : (heureDebut < heureFin_existante) ET (heureFin > heureDebut_existante)
     * - Exclut la réservation en cours d'édition (si reservationId est fourni)
     * 
     * @param salleId ID de la salle
     * @param dateReservation Date de réservation
     * @param heureDebut Heure de début du créneau
     * @param heureFin Heure de fin du créneau
     * @param reservationId ID de la réservation en cours d'édition (null pour création)
     * @return Liste des réservations en conflit
     */
    @Query("SELECT r FROM Reservation r WHERE r.salle.id = :salleId " +
           "AND r.dateReservation = :dateReservation " +
           "AND r.heureDebut < :heureFin " +
           "AND r.heureFin > :heureDebut " +
           "AND (:reservationId IS NULL OR r.id <> :reservationId)")
    List<Reservation> findConflictingReservations(
            @Param("salleId") Long salleId,
            @Param("dateReservation") LocalDate dateReservation,
            @Param("heureDebut") LocalTime heureDebut,
            @Param("heureFin") LocalTime heureFin,
            @Param("reservationId") Long reservationId
    );

    /**
     * Recherche les réservations futures d'un utilisateur (triées par date)
     * 
     * @param utilisateur Utilisateur concerné
     * @param dateActuelle Date actuelle
     * @return Liste des réservations futures
     */
    @Query("SELECT r FROM Reservation r WHERE r.utilisateur = :utilisateur " +
           "AND r.dateReservation >= :dateActuelle " +
           "ORDER BY r.dateReservation ASC, r.heureDebut ASC")
    List<Reservation> findFutureReservationsByUtilisateur(
            @Param("utilisateur") Utilisateur utilisateur,
            @Param("dateActuelle") LocalDate dateActuelle
    );

    /**
     * Recherche les réservations futures d'une salle (triées par date)
     * 
     * @param salle Salle concernée
     * @param dateActuelle Date actuelle
     * @return Liste des réservations futures
     */
    @Query("SELECT r FROM Reservation r WHERE r.salle = :salle " +
           "AND r.dateReservation >= :dateActuelle " +
           "ORDER BY r.dateReservation ASC, r.heureDebut ASC")
    List<Reservation> findFutureReservationsBySalle(
            @Param("salle") Salle salle,
            @Param("dateActuelle") LocalDate dateActuelle
    );

    /**
     * Compte le nombre de réservations d'un utilisateur
     * 
     * @param utilisateur Utilisateur concerné
     * @return Nombre de réservations
     */
    long countByUtilisateur(Utilisateur utilisateur);

    /**
     * Compte le nombre de réservations d'une salle
     * 
     * @param salle Salle concernée
     * @return Nombre de réservations
     */
    long countBySalle(Salle salle);
}
