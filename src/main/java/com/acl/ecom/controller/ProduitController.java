package com.acl.ecom.controller;

import com.acl.ecom.domain.Produit;
import com.acl.ecom.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProduitController {
    private final ProduitService produitService;
    @PostMapping()
    public Produit save(@RequestBody Produit p){
        return produitService.create(p);
    }
    @GetMapping("/{id}")
    public Produit findById(@PathVariable long id){
        return produitService.findById(id);
    }
}
