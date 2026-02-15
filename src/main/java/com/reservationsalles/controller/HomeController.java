package com.reservationsalles.controller;

import com.reservationsalles.service.ReservationService;
import com.reservationsalles.service.SalleService;
import com.reservationsalles.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller pour la page d'accueil
 * 
 */
@Controller
public class HomeController {

    private final UtilisateurService utilisateurService;
    private final SalleService salleService;
    private final ReservationService reservationService;

    @Autowired
    public HomeController(UtilisateurService utilisateurService,
                          SalleService salleService,
                          ReservationService reservationService) {
        this.utilisateurService = utilisateurService;
        this.salleService = salleService;
        this.reservationService = reservationService;
    }

    /**
     * Page d'accueil avec statistiques
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalUtilisateurs", utilisateurService.count());
        model.addAttribute("totalSalles", salleService.count());
        model.addAttribute("totalReservations", reservationService.count());
        model.addAttribute("sallesDisponibles", salleService.findSallesDisponibles().size());
        
        return "index";
    }
}
