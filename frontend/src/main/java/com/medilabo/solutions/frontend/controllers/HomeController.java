package com.medilabo.solutions.frontend.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String index(Model model) {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        // Si l'utilisateur est authentifié
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal(); // Récupère l'utilisateur de type 'User'
            System.out.println("user: " + user);
            // Utiliser l'utilisateur pour afficher des informations
            model.addAttribute("username", user.getUsername());
            model.addAttribute("message", "Bonjour, " + user.getUsername() + " ! \n Vous pouvez accéder aux fonctionnalités de l'application.");
        } else {
            model.addAttribute("username", null);
            model.addAttribute("message", "Vous devez vous connecter pour accéder aux fonctionnalités de l'application.");
        }
        model.addAttribute("accueil", "Bienvenue sur Medilabo Solutions !");

        return "index";
    }
}
