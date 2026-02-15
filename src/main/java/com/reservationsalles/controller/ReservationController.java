package com.reservationsalles.controller;

import com.reservationsalles.exception.ReservationConflictException;
import com.reservationsalles.exception.ResourceNotFoundException;
import com.reservationsalles.model.Reservation;
import com.reservationsalles.service.ReservationService;
import com.reservationsalles.service.SalleService;
import com.reservationsalles.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller MVC pour la gestion des Réservations
 * Opérations CRUD complètes avec gestion des conflits
 */
@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UtilisateurService utilisateurService;
    private final SalleService salleService;

    @Autowired
    public ReservationController(ReservationService reservationService,
                                 UtilisateurService utilisateurService,
                                 SalleService salleService) {
        this.reservationService = reservationService;
        this.utilisateurService = utilisateurService;
        this.salleService = salleService;
    }

    /**
     * Liste toutes les réservations
     * GET /reservations
     */
    @GetMapping
    public String listReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAll());
        return "reservations/list";
    }

    /**
     * Affiche le formulaire de création
     * GET /reservations/create
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("utilisateurs", utilisateurService.findAll());
        model.addAttribute("salles", salleService.findSallesDisponibles());
        return "reservations/create";
    }

    /**
     * Traite la création d'une réservation
     * POST /reservations
     */
    @PostMapping
    public String createReservation(@Valid @ModelAttribute("reservation") Reservation reservation,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (result.hasErrors()) {
            model.addAttribute("utilisateurs", utilisateurService.findAll());
            model.addAttribute("salles", salleService.findSallesDisponibles());
            return "reservations/create";
        }

        try {
            reservationService.save(reservation);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Réservation créée avec succès !");
            return "redirect:/reservations";
        } catch (ReservationConflictException e) {
            // Conflit de réservation détecté
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("utilisateurs", utilisateurService.findAll());
            model.addAttribute("salles", salleService.findSallesDisponibles());
            return "reservations/create";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("utilisateurs", utilisateurService.findAll());
            model.addAttribute("salles", salleService.findSallesDisponibles());
            return "reservations/create";
        }
    }

    /**
     * Affiche les détails d'une réservation
     * GET /reservations/{id}
     */
    @GetMapping("/{id}")
    public String viewReservation(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Reservation reservation = reservationService.findById(id);
            model.addAttribute("reservation", reservation);
            return "reservations/view";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reservations";
        }
    }

    /**
     * Affiche le formulaire de modification
     * GET /reservations/{id}/edit
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Reservation reservation = reservationService.findById(id);
            model.addAttribute("reservation", reservation);
            model.addAttribute("utilisateurs", utilisateurService.findAll());
            model.addAttribute("salles", salleService.findAll());
            return "reservations/edit";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reservations";
        }
    }

    /**
     * Traite la modification d'une réservation
     * POST /reservations/{id}/edit
     */
    @PostMapping("/{id}/edit")
    public String updateReservation(@PathVariable Long id,
                                   @Valid @ModelAttribute("reservation") Reservation reservation,
                                   BindingResult result,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        // Vérification des erreurs de validation
        if (result.hasErrors()) {
            reservation.setId(id); // Conserver l'ID pour l'affichage
            model.addAttribute("utilisateurs", utilisateurService.findAll());
            model.addAttribute("salles", salleService.findAll());
            return "reservations/edit";
        }

        try {
            reservationService.update(id, reservation);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Réservation modifiée avec succès !");
            return "redirect:/reservations/" + id;
        } catch (ReservationConflictException e) {
            // Conflit de réservation détecté
            model.addAttribute("errorMessage", e.getMessage());
            reservation.setId(id);
            model.addAttribute("utilisateurs", utilisateurService.findAll());
            model.addAttribute("salles", salleService.findAll());
            return "reservations/edit";
        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reservations/" + id + "/edit";
        }
    }

    /**
     * Supprime une réservation
     * POST /reservations/{id}/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Réservation supprimée avec succès !");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/reservations";
    }

    /**
     * Affiche les réservations d'un utilisateur
     * GET /reservations/utilisateur/{utilisateurId}
     */
    @GetMapping("/utilisateur/{utilisateurId}")
    public String reservationsByUtilisateur(@PathVariable Long utilisateurId, 
                                           Model model, 
                                           RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("reservations", reservationService.findByUtilisateur(utilisateurId));
            model.addAttribute("utilisateur", utilisateurService.findById(utilisateurId));
            return "reservations/list";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reservations";
        }
    }

    /**
     * Affiche les réservations d'une salle
     * GET /reservations/salle/{salleId}
     */
    @GetMapping("/salle/{salleId}")
    public String reservationsBySalle(@PathVariable Long salleId, 
                                     Model model, 
                                     RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("reservations", reservationService.findBySalle(salleId));
            model.addAttribute("salle", salleService.findById(salleId));
            return "reservations/list";
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/reservations";
        }
    }
}
