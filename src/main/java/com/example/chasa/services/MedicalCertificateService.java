package com.example.chasa.services;

import com.example.chasa.entities.LicenseUsersEntity;
import com.example.chasa.entities.MedicalCertificatesEntity;
import com.example.chasa.entities.UsersEntity;
import com.example.chasa.enums.CertificateType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class MedicalCertificateService {

    /**
     * Method to find all MedicalCertificates
     * @param em
     * @return List of MedicalCertificates
     */
    public List<MedicalCertificatesEntity> findAll(EntityManager em){
        return em.createNamedQuery("MedicalCertificates.SelectAll",MedicalCertificatesEntity.class)
                .getResultList();
    }

    /**
     * Method to know if the MedicalCertificates exists
     * @param issuedDate
     * @param expiryDate
     * @param certificateType
     * @param user
     * @param em
     * @return boolean
     */
    public boolean isMedicalCertificatesExist(Date issuedDate, Date expiryDate, CertificateType certificateType, int user,EntityManager em) {
        Query query = em.createNamedQuery("MedicalCertificates.isMedicalCertificatesExist",MedicalCertificatesEntity.class)
                        .setParameter("issueDate", issuedDate)
                        .setParameter("expiryDate",expiryDate)
                        .setParameter("certificateType", certificateType)
                        .setParameter("usersByIdUser", user);
        int count = ((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to find one MedicalCertificates based on the id
     * @param medicalCertificates
     * @param em
     * @return 1 MedicalCertificate
     */
    public MedicalCertificatesEntity findById(MedicalCertificatesEntity medicalCertificates, EntityManager em) {
        return em.createNamedQuery("MedicalCertificates.SelectById", MedicalCertificatesEntity.class)
                .setParameter("idMedicalCertificate", medicalCertificates)
                .getSingleResult();
    }

    /**
     * Method to find all MedicalCertificates based on the usersByIdUser
     * @param idUser
     * @param em
     * @return List of MedicalCertificates
     */
    public List<MedicalCertificatesEntity> findAllByUser(int idUser, EntityManager em){
        return em.createNamedQuery("MedicalCertificates.SelectAllByUser",MedicalCertificatesEntity.class)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    /**
     * Method to find all MedicalCertificates based on the lifrasNumber
     * @param researchWord
     * @param em
     * @return List of MedicalCertificates
     */
    public List<MedicalCertificatesEntity> findAllMedicalCertificatesByCharacteristic(String researchWord, EntityManager em){
        return em.createNamedQuery("MedicalCertificates.FindMedicalCertificatesByCharacteristic",MedicalCertificatesEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    /**
     * Method to find all MedicalCertificates based on the lifrasNumber and the role different of ADMINISTRATEUR
     * @param researchWord
     * @param em
     * @return List of MedicalCertificates
     */
    public List<MedicalCertificatesEntity> findAllMedicalCertificatesByCharacteristicMember(String researchWord, EntityManager em){
        return em.createNamedQuery("MedicalCertificates.FindMedicalCertificatesByCharacteristicMember",MedicalCertificatesEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }


    /**
     * Method to add a new MedicalCertificates
     * @param medicalCertificates
     * @param em
     * @return a new MedicalCertificate
     */
    public MedicalCertificatesEntity addMedicalCertificates(MedicalCertificatesEntity medicalCertificates, EntityManager em){
        em.persist(medicalCertificates);
        em.flush();
        return medicalCertificates;
    }

    public MedicalCertificatesEntity updateMedicalCertificates(MedicalCertificatesEntity medicalCertificates, EntityManager em){
        em.merge(medicalCertificates);
        return medicalCertificates;
    }
}
