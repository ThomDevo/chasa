package com.example.chasa.services;

import com.example.chasa.entities.DiveSiteCharacteristicsEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class DiveSiteCharacteristicService {

    public List<DiveSiteCharacteristicsEntity> findallDiveSiteCharacteristicsByIdDiveSite(int idDiveSite, EntityManager em) {
        return em.createNamedQuery("DiveSiteCharacteristics.findByIdDiveSite", DiveSiteCharacteristicsEntity.class)
                .setParameter("diveSitesByIdDiveSite", idDiveSite)
                .getResultList();
    }
}
