package com.medilabo.solutions.risk.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medilabo.solutions.risk.enums.RiskConstant;
import com.medilabo.solutions.risk.services.RiskService;

@RestController
@RequestMapping("/risk")
public class RiskController {
    
    @Autowired
    private RiskService riskService;

    @GetMapping("/{id}")
    public String getRisk(@PathVariable int id) throws IllegalArgumentException, Exception {
        RiskConstant risk = riskService.evaluateRisk(id).orElseThrow(() -> new IllegalArgumentException("Patient with id " + id + " not found"));
        return risk.name();
    }
}
