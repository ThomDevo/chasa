package com.example.chasa.services;

import com.example.chasa.entities.LicenseUsersEntity;
import com.example.chasa.utilities.ProcessUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class LicenseUserService {

    public List<LicenseUsersEntity> findAll(EntityManager em){
        return em.createNamedQuery("LicenseUser.SelectAll",LicenseUsersEntity.class)
                .getResultList();
    }

    public boolean isLicenseUserExists(int idUser, int idLicense, EntityManager em){
        Query query = em.createNamedQuery("LicenseUser.IsLicenseUserExists", LicenseUsersEntity.class)
                        .setParameter("idUser", idUser)
                        .setParameter("idLicense", idLicense);
        int count = ((Number)query.getSingleResult()).intValue();
        ProcessUtils.debug(""+ count);
        return count > 0;
    }

    public List<LicenseUsersEntity> findUserByFilter(String researchWord, EntityManager em){
        return em.createNamedQuery("LicenseUser.FindLicenseUserByCharacteristic", LicenseUsersEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    public LicenseUsersEntity findUserById(int idLicenseUser, EntityManager em) {
        return em.createNamedQuery("LicenseUser.SelectById", LicenseUsersEntity.class)
                .setParameter("idLicenseUser", idLicenseUser)
                .getSingleResult();
    }

    public List<LicenseUsersEntity> findAllByUser(int idUser,EntityManager em){
        return em.createNamedQuery("LicenseUser.findLicenseUserByUser",LicenseUsersEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    public List<LicenseUsersEntity> FindUserByCharacteristicMember(String researchWord, EntityManager em){
        return em.createNamedQuery("LicenseUser.FindLicenseUserByCharacteristicMember", LicenseUsersEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    public LicenseUsersEntity addLicenseUser(LicenseUsersEntity licenseUser, EntityManager em) {
        em.persist(licenseUser);
        em.flush();
        return licenseUser;
    }

    public LicenseUsersEntity updateLicenseUser(LicenseUsersEntity licenseUser, EntityManager em){
        em.merge(licenseUser);
        return licenseUser;
    }

    public void  deleteLicenseUser(LicenseUsersEntity licenseUser, EntityManager em){
        if(!em.contains(licenseUser))
            licenseUser = em.merge(licenseUser);
        em.remove(licenseUser);
        em.flush();
    }
}
