package com.reservationsalles.repository;

import com.reservationsalles.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Salle
 * Hérite de JpaRepository qui fournit les méthodes CRUD de base
 
 */
@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

    /**
     * Recherche des salles disponibles
     * 
     * @param disponible Statut de disponibilité
     * @return Liste des salles disponibles
     */
    List<Salle> findByDisponible(boolean disponible);

    /**
     * Recherche des salles par localisation
     * 
     * @param localisation Localisation à rechercher
     * @return Liste des salles dans cette localisation
     */
    List<Salle> findByLocalisationContainingIgnoreCase(String localisation);

    /**
     * Recherche des salles ayant une capacité minimale
     * 
     * @param capacite Capacité minimale
     * @return Liste des salles ayant au moins cette capacité
     */
    List<Salle> findByCapaciteGreaterThanEqual(int capacite);

    /**
     * Recherche des salles disponibles avec capacité minimale
     * Requête personnalisée JPQL
     * 
     * @param disponible Statut de disponibilité
     * @param capacite Capacité minimale
     * @return Liste des salles répondant aux critères
     */
    @Query("SELECT s FROM Salle s WHERE s.disponible = ?1 AND s.capacite >= ?2")
    List<Salle> findSallesDisponiblesAvecCapaciteMin(boolean disponible, int capacite);

    /**
     * Recherche des salles par nom (insensible à la casse)
     * 
     * @param nom Partie du nom à rechercher
     * @return Liste des salles correspondantes
     */
    List<Salle> findByNomContainingIgnoreCase(String nom);
}
