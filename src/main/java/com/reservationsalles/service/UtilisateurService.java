package com.reservationsalles.service;

import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Utilisateur;
import com.reservationsalles.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service métier pour la gestion des Utilisateurs
 * Contient la logique métier et les validations
 * 
 * @author Projet Master 1
 * @version 1.0
 */
@Service
@Transactional
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Récupère tous les utilisateurs
     * 
     * @return Liste de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    /**
     * Récupère un utilisateur par son ID
     * 
     * @param id ID de l'utilisateur
     * @return L'utilisateur trouvé
     * @throws ResourceNotFoundException si l'utilisateur n'existe pas
     */
    @Transactional(readOnly = true)
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "id", id));
    }

    /**
     * Récupère un utilisateur par son email
     * 
     * @param email Email de l'utilisateur
     * @return L'utilisateur trouvé
     * @throws ResourceNotFoundException si l'utilisateur n'existe pas
     */
    @Transactional(readOnly = true)
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur", "email", email));
    }

    /**
     * Recherche des utilisateurs par rôle
     * 
     * @param role Rôle à rechercher
     * @return Liste des utilisateurs ayant ce rôle
     */
    @Transactional(readOnly = true)
    public List<Utilisateur> findByRole(String role) {
        return utilisateurRepository.findByRole(role);
    }

    /**
     * Recherche des utilisateurs par nom (partiel, insensible à la casse)
     * 
     * @param nom Partie du nom à rechercher
     * @return Liste des utilisateurs correspondants
     */
    @Transactional(readOnly = true)
    public List<Utilisateur> searchByNom(String nom) {
        return utilisateurRepository.findByNomContainingIgnoreCase(nom);
    }

    /**
     * Crée un nouvel utilisateur
     * Vérifie que l'email n'existe pas déjà
     * 
     * @param utilisateur Utilisateur à créer
     * @return L'utilisateur créé
     * @throws IllegalArgumentException si l'email existe déjà
     */
    public Utilisateur save(Utilisateur utilisateur) {
        // Validation : email unique (lors de la création)
        if (utilisateur.getId() == null && utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà : " + utilisateur.getEmail());
        }
        
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Met à jour un utilisateur existant
     * Vérifie que l'email n'est pas déjà utilisé par un autre utilisateur
     * 
     * @param id ID de l'utilisateur à modifier
     * @param utilisateurDetails Nouvelles données
     * @return L'utilisateur mis à jour
     * @throws ResourceNotFoundException si l'utilisateur n'existe pas
     * @throws IllegalArgumentException si l'email est déjà utilisé
     */
    public Utilisateur update(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = findById(id);

        // Validation : email unique (ne doit pas appartenir à un autre utilisateur)
        utilisateurRepository.findByEmail(utilisateurDetails.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new IllegalArgumentException("Un autre utilisateur utilise déjà cet email : " 
                                + utilisateurDetails.getEmail());
                    }
                });

        // Mise à jour des champs
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setRole(utilisateurDetails.getRole());

        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Supprime un utilisateur
     * Note : Les réservations liées seront supprimées en cascade (orphanRemoval = true)
     * 
     * @param id ID de l'utilisateur à supprimer
     * @throws ResourceNotFoundException si l'utilisateur n'existe pas
     */
    public void delete(Long id) {
        Utilisateur utilisateur = findById(id);
        utilisateurRepository.delete(utilisateur);
    }

    /**
     * Compte le nombre total d'utilisateurs
     * 
     * @return Nombre d'utilisateurs
     */
    @Transactional(readOnly = true)
    public long count() {
        return utilisateurRepository.count();
    }

    /**
     * Vérifie si un email existe déjà
     * 
     * @param email Email à vérifier
     * @return true si l'email existe, false sinon
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return utilisateurRepository.existsByEmail(email);
    }
}
