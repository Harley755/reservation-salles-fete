package com.reservationsalles.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entité JPA représentant un Utilisateur du système
 * Relations: 1 Utilisateur possède 0..* Reservations
 * 
 * @author Projet Master 1
 * @version 1.0
 */
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Le rôle est obligatoire")
    @Size(min = 3, max = 50, message = "Le rôle doit contenir entre 3 et 50 caractères")
    @Column(nullable = false, length = 50)
    private String role;

    /**
     * Relation bidirectionnelle One-to-Many avec Reservation
     * mappedBy = "utilisateur" indique que Reservation est le propriétaire de la relation
     * cascade = CascadeType.ALL permet de propager les opérations
     * orphanRemoval = true supprime les réservations orphelines
     */
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    // ===============================
    // CONSTRUCTEURS
    // ===============================

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email, String role) {
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    // ===============================
    // MÉTHODES UTILITAIRES
    // ===============================

    /**
     * Ajoute une réservation à l'utilisateur (gestion bidirectionnelle)
     */
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setUtilisateur(this);
    }

    /**
     * Retire une réservation de l'utilisateur (gestion bidirectionnelle)
     */
    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setUtilisateur(null);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
