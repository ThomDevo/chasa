package com.example.chasa.services;

import com.example.chasa.entities.DiveSitesEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class DiveSitesService {

    public DiveSitesEntity findById(int idDiveSite, EntityManager em) {
        return em.createNamedQuery("DiveSites.findById",DiveSitesEntity.class)
                .setParameter("idDiveSite", idDiveSite)
                .getSingleResult();
    }

    public List<DiveSitesEntity> findAllByIdDiveSites(int idDiveSite, EntityManager em){
        return em.createNamedQuery("DiveSites.findAllById",DiveSitesEntity.class)
                .setParameter("idDiveSite", idDiveSite)
                .getResultList();
    }

    public List<DiveSitesEntity> findAll(EntityManager em){
        return em.createNamedQuery("DiveSites.findAll",DiveSitesEntity.class)
                .getResultList();
    }

    public List<DiveSitesEntity> findAllByLabel(String researchDiveSite, EntityManager em){
        return em.createNamedQuery("DiveSites.findByLabel",DiveSitesEntity.class)
                .setParameter("researchDiveSite", researchDiveSite)
                .getResultList();
    }
}
