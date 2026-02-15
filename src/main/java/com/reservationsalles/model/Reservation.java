package com.reservationsalles.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Entité JPA représentant une Réservation
 * Relations: 
 * - ManyToOne vers Utilisateur
 * - ManyToOne vers Salle
 * 
 * @author Projet Master 1
 * @version 1.0
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La date de réservation est obligatoire")
    @Column(nullable = false)
    private LocalDate dateReservation;

    @NotNull(message = "L'heure de début est obligatoire")
    @Column(nullable = false)
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    @Column(nullable = false)
    private LocalTime heureFin;

    /**
     * Relation ManyToOne vers Utilisateur
     * fetch = FetchType.LAZY pour optimisation (chargement à la demande)
     * optional = false signifie que la relation est obligatoire (non-nullable)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @NotNull(message = "L'utilisateur est obligatoire")
    private Utilisateur utilisateur;

    /**
     * Relation ManyToOne vers Salle
     * fetch = FetchType.LAZY pour optimisation (chargement à la demande)
     * optional = false signifie que la relation est obligatoire (non-nullable)
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "salle_id", nullable = false)
    @NotNull(message = "La salle est obligatoire")
    private Salle salle;

    // ===============================
    // CONSTRUCTEURS
    // ===============================

    public Reservation() {
    }

    public Reservation(LocalDate dateReservation, LocalTime heureDebut, LocalTime heureFin, 
                       Utilisateur utilisateur, Salle salle) {
        this.dateReservation = dateReservation;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.utilisateur = utilisateur;
        this.salle = salle;
    }

    // ===============================
    // MÉTHODES DE VALIDATION MÉTIER
    // ===============================

    /**
     * Vérifie si l'heure de fin est après l'heure de début
     * Utilisé pour la validation côté métier
     */
    public boolean isHeureFinApresHeureDebut() {
        if (heureDebut == null || heureFin == null) {
            return false;
        }
        return heureFin.isAfter(heureDebut);
    }

    /**
     * Vérifie si cette réservation chevauche une autre réservation
     * 
     * @param autre L'autre réservation à comparer
     * @return true si les créneaux se chevauchent, false sinon
     */
    public boolean chevauche(Reservation autre) {
        if (autre == null || !this.dateReservation.equals(autre.dateReservation)) {
            return false;
        }
        
        // Chevauchement si :
        // - heureDebut de this est avant heureFin de autre ET
        // - heureFin de this est après heureDebut de autre
        return this.heureDebut.isBefore(autre.heureFin) && 
               this.heureFin.isAfter(autre.heureDebut);
    }

    // ===============================
    // GETTERS ET SETTERS
    // ===============================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    // ===============================
    // EQUALS, HASHCODE, TOSTRING
    // ===============================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", dateReservation=" + dateReservation +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", utilisateur=" + (utilisateur != null ? utilisateur.getNom() : "null") +
                ", salle=" + (salle != null ? salle.getNom() : "null") +
                '}';
    }
}
