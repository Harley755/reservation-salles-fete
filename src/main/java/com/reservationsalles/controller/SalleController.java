package com.reservationsalles.controller;

import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Salle;
import com.reservationsalles.service.SalleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller MVC pour la gestion des Salles
 * Opérations CRUD complètes
 
 */
@Controller
@RequestMapping("/salles")
public class SalleController {

    private final SalleService salleService;

    @Autowired
    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }

    /**
     * Liste toutes les salles
     * GET /salles
     */
    @GetMapping
    public String listSalles(Model model) {
        model.addAttribute("salles", salleService.findAll());
        return "salles/list";
    }

    /**
     * Affiche le formulaire de création
     * GET /salles/create
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("salle", new Salle());
        return "salles/create";
    }

    /**
     * Traite la création d'une salle
     * POST /salles
     */
    @PostMapping
    public String createSalle(@Valid @ModelAttribute("salle") Salle salle,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (result.hasErrors()) {
            return "salles/create";
        }

        try {
            salleService.save(salle);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Salle créée avec succès !");
            return "redirect:/salles";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/salles/create";
        }
    }

    /**
     * Affiche les détails d'une salle
     * GET /salles/{id}
     */
    @GetMapping("/{id}")
    public String viewSalle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Salle salle = salleService.findById(id);
            model.addAttribute("salle", salle);
            return "salles/view";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/salles";
        }
    }

    /**
     * Affiche le formulaire de modification
     * GET /salles/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Salle salle = salleService.findById(id);
            model.addAttribute("salle", salle);
            return "salles/edit";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/salles";
        }
    }

    /**
     * Traite la modification d'une salle
     * POST /salles/{id}/edit
     */
    @PostMapping("/{id}/edit")
    public String updateSalle(@PathVariable Long id,
                             @Valid @ModelAttribute("salle") Salle salle,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (result.hasErrors()) {
            salle.setId(id); // Conserver l'ID pour l'affichage
            return "salles/edit";
        }

        try {
            salleService.update(id, salle);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Salle modifiée avec succès !");
            return "redirect:/salles/" + id;
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/salles/" + id + "/edit";
        }
    }

    /**
     * Supprime une salle
     * POST /salles/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteSalle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            salleService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Salle supprimée avec succès !");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/salles";
    }

    /**
     * Change le statut de disponibilité
     * POST /salles/{id}/toggle-disponibilite
     */
    @PostMapping("/{id}/toggle-disponibilite")
    public String toggleDisponibilite(@PathVariable Long id, 
                                      @RequestParam boolean disponible,
                                      RedirectAttributes redirectAttributes) {
        try {
            salleService.toggleDisponibilite(id, disponible);
            String message = disponible ? "Salle marquée comme disponible" : "Salle marquée comme non disponible";
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/salles/" + id;
    }

    /**
     * Recherche de salles
     * GET /salles/search?nom=...&localisation=...&capacite=...
     */
    @GetMapping("/search")
    public String searchSalles(@RequestParam(required = false) String nom,
                               @RequestParam(required = false) String localisation,
                               @RequestParam(required = false) Integer capacite,
                               Model model) {
        if (nom != null && !nom.trim().isEmpty()) {
            model.addAttribute("salles", salleService.searchByNom(nom));
            model.addAttribute("searchQuery", nom);
        } else if (localisation != null && !localisation.trim().isEmpty()) {
            model.addAttribute("salles", salleService.searchByLocalisation(localisation));
            model.addAttribute("searchQuery", localisation);
        } else if (capacite != null && capacite > 0) {
            model.addAttribute("salles", salleService.findByCapaciteMin(capacite));
            model.addAttribute("searchQuery", "Capacité >= " + capacite);
        } else {
            model.addAttribute("salles", salleService.findAll());
        }
        return "salles/list";
    }
}
