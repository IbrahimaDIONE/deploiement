package com.acl.ecom.service;

import com.acl.ecom.domain.Produit;
import com.acl.ecom.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService{
    private final ProduitRepository ProduitRepository;
    @Override
    public Produit create(Produit p) {
        return ProduitRepository.save(p);
    }

    @Override
    public Produit findById(long id) {
        return null;
    }

    @Override
    public Produit findByID(long id) {
        return ProduitRepository.findById(id).get();
    }

}
