package com.example.chasa.services;

import com.example.chasa.entities.LicensesEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class LicensesService {

    /**
     * Method to find all licences
     * @param em
     * @return List of all licenses
     */
    public List<LicensesEntity> findAll(EntityManager em){
        return em.createNamedQuery("License.SelectAll",LicensesEntity.class)
                .getResultList();
    }

    public LicensesEntity findById(int id, EntityManager em){
        return em.createNamedQuery("License.SelectById", LicensesEntity.class)
                .setParameter("idLicense",id)
                .getSingleResult();
    };

    public List<LicensesEntity> findLicenseNotOwnByUser(int idUser,EntityManager em){
        return em.createNamedQuery("License.SelectAllByUser", LicensesEntity.class)
                .setParameter("idUser",idUser)
                .getResultList();
    }
}
