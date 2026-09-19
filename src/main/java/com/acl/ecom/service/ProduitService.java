package com.acl.ecom.service;

import com.acl.ecom.domain.Produit;
import org.springframework.stereotype.Service;

@Service
public interface ProduitService {
    Produit create(Produit p);
    Produit findById(long id);

    Produit findByID(long id);
}
