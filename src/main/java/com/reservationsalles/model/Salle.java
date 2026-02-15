package com.reservationsalles.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entité JPA représentant une Salle de fête
 * Relations: 1 Salle possède 0..* Reservations
 * 
 * @author Projet Master 1
 * @version 1.0
 */
@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la salle est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotNull(message = "La capacité est obligatoire")
    @Min(value = 1, message = "La capacité doit être au moins de 1 personne")
    @Column(nullable = false)
    private int capacite;

    @NotBlank(message = "La localisation est obligatoire")
    @Size(min = 3, max = 200, message = "La localisation doit contenir entre 3 et 200 caractères")
    @Column(nullable = false, length = 200)
    private String localisation;

    @NotNull(message = "Le statut de disponibilité est obligatoire")
    @Column(nullable = false)
    private boolean disponible;

    /**
     * Relation bidirectionnelle One-to-Many avec Reservation
     * mappedBy = "salle" indique que Reservation est le propriétaire de la relation
     * cascade = CascadeType.ALL permet de propager les opérations
     * orphanRemoval = true supprime les réservations orphelines
     */
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    // ===============================
    // CONSTRUCTEURS
    // ===============================

    public Salle() {
    }

    public Salle(String nom, int capacite, String localisation, boolean disponible) {
        this.nom = nom;
        this.capacite = capacite;
        this.localisation = localisation;
        this.disponible = disponible;
    }

    // ===============================
    // MÉTHODES UTILITAIRES
    // ===============================

    /**
     * Ajoute une réservation à la salle (gestion bidirectionnelle)
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setSalle(this);
    }

    /**
     * Retire une réservation de la salle (gestion bidirectionnelle)
     */
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setSalle(null);
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // ===============================
    // EQUALS, HASHCODE, TOSTRING
    // ===============================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salle salle = (Salle) o;
        return Objects.equals(id, salle.id) && Objects.equals(nom, salle.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", capacite=" + capacite +
                ", localisation='" + localisation + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
