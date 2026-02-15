package com.reservationsalles.service;

import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Salle;
import com.reservationsalles.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service métier pour la gestion des Salles
 * Contient la logique métier et les validations
 
 */
@Service
@Transactional
public class SalleService {

    private final SalleRepository salleRepository;

    @Autowired
    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }

    /**
     * Récupère toutes les salles
     * 
     * @return Liste de toutes les salles
     */
    @Transactional(readOnly = true)
    public List<Salle> findAll() {
        return salleRepository.findAll();
    }

    /**
     * Récupère une salle par son ID
     * 
     * @param id ID de la salle
     * @return La salle trouvée
     * @throws ResourceNotFoundException si la salle n'existe pas
     */
    @Transactional(readOnly = true)
    public Salle findById(Long id) {
        return salleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle", "id", id));
    }

    /**
     * Récupère les salles disponibles
     * 
     * @return Liste des salles disponibles
     */
    @Transactional(readOnly = true)
    public List<Salle> findSallesDisponibles() {
        return salleRepository.findByDisponible(true);
    }

    /**
     * Recherche des salles par localisation (partiel, insensible à la casse)
     * 
     * @param localisation Localisation à rechercher
     * @return Liste des salles correspondantes
     */
    @Transactional(readOnly = true)
    public List<Salle> searchByLocalisation(String localisation) {
        return salleRepository.findByLocalisationContainingIgnoreCase(localisation);
    }

    /**
     * Recherche des salles par nom (partiel, insensible à la casse)
     * 
     * @param nom Nom à rechercher
     * @return Liste des salles correspondantes
     */
    @Transactional(readOnly = true)
    public List<Salle> searchByNom(String nom) {
        return salleRepository.findByNomContainingIgnoreCase(nom);
    }

    /**
     * Recherche des salles avec capacité minimale
     * 
     * @param capacite Capacité minimale
     * @return Liste des salles ayant au moins cette capacité
     */
    @Transactional(readOnly = true)
    public List<Salle> findByCapaciteMin(int capacite) {
        return salleRepository.findByCapaciteGreaterThanEqual(capacite);
    }

    /**
     * Recherche des salles disponibles avec capacité minimale
     * 
     * @param capacite Capacité minimale
     * @return Liste des salles disponibles ayant au moins cette capacité
     */
    @Transactional(readOnly = true)
    public List<Salle> findSallesDisponiblesAvecCapaciteMin(int capacite) {
        return salleRepository.findSallesDisponiblesAvecCapaciteMin(true, capacite);
    }

    /**
     * Crée une nouvelle salle
     * 
     * @param salle Salle à créer
     * @return La salle créée
     */
    public Salle save(Salle salle) {
        // Validation métier : la capacité doit être positive
        if (salle.getCapacite() <= 0) {
            throw new IllegalArgumentException("La capacité doit être supérieure à 0");
        }
        
        return salleRepository.save(salle);
    }

    /**
     * Met à jour une salle existante
     * 
     * @param id ID de la salle à modifier
     * @param salleDetails Nouvelles données
     * @return La salle mise à jour
     * @throws ResourceNotFoundException si la salle n'existe pas
     */
    public Salle update(Long id, Salle salleDetails) {
        Salle salle = findById(id);

        // Validation métier : la capacité doit être positive
        if (salleDetails.getCapacite() <= 0) {
            throw new IllegalArgumentException("La capacité doit être supérieure à 0");
        }

        // Mise à jour des champs
        salle.setNom(salleDetails.getNom());
        salle.setCapacite(salleDetails.getCapacite());
        salle.setLocalisation(salleDetails.getLocalisation());
        salle.setDisponible(salleDetails.isDisponible());

        return salleRepository.save(salle);
    }

    /**
     * Supprime une salle
     * Note : Les réservations liées seront supprimées en cascade (orphanRemoval = true)
     * 
     * @param id ID de la salle à supprimer
     * @throws ResourceNotFoundException si la salle n'existe pas
     */
    public void delete(Long id) {
        Salle salle = findById(id);
        salleRepository.delete(salle);
    }

    /**
     * Change le statut de disponibilité d'une salle
     * 
     * @param id ID de la salle
     * @param disponible Nouveau statut
     * @return La salle mise à jour
     * @throws ResourceNotFoundException si la salle n'existe pas
     */
    public Salle toggleDisponibilite(Long id, boolean disponible) {
        Salle salle = findById(id);
        salle.setDisponible(disponible);
        return salleRepository.save(salle);
    }

    /**
     * Compte le nombre total de salles
     * 
     * @return Nombre de salles
     */
    @Transactional(readOnly = true)
    public long count() {
        return salleRepository.count();
    }
}
