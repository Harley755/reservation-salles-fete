package com.reservationsalles.repository;

import com.reservationsalles.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Utilisateur
 * Hérite de JpaRepository qui fournit les méthodes CRUD de base
 
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    /**
     * Recherche un utilisateur par email (unique)
     * 
     * @param email Email de l'utilisateur
     * @return Optional contenant l'utilisateur si trouvé
     */
    Optional<Utilisateur> findByEmail(String email);

    /**
     * Recherche des utilisateurs par rôle
     * 
     * @param role Rôle à rechercher
     * @return Liste des utilisateurs ayant ce rôle
     */
    List<Utilisateur> findByRole(String role);

    /**
     * Recherche des utilisateurs dont le nom contient la chaîne (insensible à la casse)
     * 
     * @param nom Partie du nom à rechercher
     * @return Liste des utilisateurs correspondants
     */
    List<Utilisateur> findByNomContainingIgnoreCase(String nom);

    /**
     * Vérifie si un email existe déjà dans la base
     * 
     * @param email Email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean existsByEmail(String email);
}
