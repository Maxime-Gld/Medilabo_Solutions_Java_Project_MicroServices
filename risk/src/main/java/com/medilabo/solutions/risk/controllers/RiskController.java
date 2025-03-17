package com.medilabo.solutions.risk.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/risk")
public class RiskController {
    

    @GetMapping("/{id}")
    public String getRisk(@PathVariable int id) {
        return "Risk " + id;
    }
}
