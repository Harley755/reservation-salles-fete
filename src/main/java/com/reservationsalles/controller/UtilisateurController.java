package com.reservationsalles.controller;

import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Utilisateur;
import com.reservationsalles.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller MVC pour la gestion des Utilisateurs
 * Opérations CRUD complètes
 * 
 * @author Projet Master 1
 * @version 1.0
 */
@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Liste tous les utilisateurs
     * GET /utilisateurs
     */
    @GetMapping
    public String listUtilisateurs(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        return "utilisateurs/list";
    }

    /**
     * Affiche le formulaire de création
     * GET /utilisateurs/create
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "utilisateurs/create";
    }

    /**
     * Traite la création d'un utilisateur
     * POST /utilisateurs
     */
    @PostMapping
    public String createUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (result.hasErrors()) {
            return "utilisateurs/create";
        }

        try {
            utilisateurService.save(utilisateur);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Utilisateur créé avec succès !");
            return "redirect:/utilisateurs";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/utilisateurs/create";
        }
    }

    /**
     * Affiche les détails d'un utilisateur
     * GET /utilisateurs/{id}
     */
    @GetMapping("/{id}")
    public String viewUtilisateur(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Utilisateur utilisateur = utilisateurService.findById(id);
            model.addAttribute("utilisateur", utilisateur);
            return "utilisateurs/view";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/utilisateurs";
        }
    }

    /**
     * Affiche le formulaire de modification
     * GET /utilisateurs/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Utilisateur utilisateur = utilisateurService.findById(id);
            model.addAttribute("utilisateur", utilisateur);
            return "utilisateurs/edit";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/utilisateurs";
        }
    }

    /**
     * Traite la modification d'un utilisateur
     * POST /utilisateurs/{id}/edit
     */
    @PostMapping("/{id}/edit")
    public String updateUtilisateur(@PathVariable Long id,
                                    @Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (result.hasErrors()) {
            utilisateur.setId(id); // Conserver l'ID pour l'affichage
            return "utilisateurs/edit";
        }

        try {
            utilisateurService.update(id, utilisateur);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Utilisateur modifié avec succès !");
            return "redirect:/utilisateurs/" + id;
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/utilisateurs/" + id + "/edit";
        }
    }

    /**
     * Supprime un utilisateur
     * POST /utilisateurs/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteUtilisateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            utilisateurService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Utilisateur supprimé avec succès !");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/utilisateurs";
    }

    /**
     * Recherche des utilisateurs par nom
     * GET /utilisateurs/search?nom=...
     */
    @GetMapping("/search")
    public String searchUtilisateurs(@RequestParam(required = false) String nom, Model model) {
        if (nom != null && !nom.trim().isEmpty()) {
            model.addAttribute("utilisateurs", utilisateurService.searchByNom(nom));
            model.addAttribute("searchQuery", nom);
        } else {
            model.addAttribute("utilisateurs", utilisateurService.findAll());
        }
        return "utilisateurs/list";
    }
}
