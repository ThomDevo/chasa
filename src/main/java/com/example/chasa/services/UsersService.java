package com.example.chasa.services;

import com.example.chasa.entities.UsersEntity;

import javax.persistence.EntityManager;
import java.util.List;


public class UsersService {


    /**
     * Method to find a User based on lifrasNumber
     * @param lifrasNumber
     * @param em
     * @return User
     */
    public UsersEntity findUserByLifrasNumber (int lifrasNumber, String password, EntityManager em) {
        return em.createNamedQuery("User.SelectUser", UsersEntity.class)
                .setParameter("lifrasNumber", lifrasNumber)
                .setParameter("password", password)
                .getSingleResult();
    }

    /**
     * Method to find User (MEMBRE) based on a filter and order by ASC
     * @param researchWord
     * @param orderBy
     * @param em
     * @return List<UsersEntity>
     */
    public List<UsersEntity> findUserByFilterAndOrderAsc(String researchWord, String orderBy, EntityManager em){
        return em.createNamedQuery("User.FindUserByCharacteristic", UsersEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                .getResultList();
    }

    /**
     * Method to find User ALL based on a filter and order by ASC
     * @param researchWord
     * @param orderBy
     * @param em
     * @return List<UsersEntity>
     */
    public List<UsersEntity> findUserByFilterAndOrderAscAdmin(String researchWord, String orderBy, EntityManager em){
        return em.createNamedQuery("User.FindUserByCharacteristicAdmin", UsersEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .setParameter("orderBy", orderBy)
                .getResultList();
    }

    /**
     * Method to find User ALL based on a status
     * @param userStatus
     * @param em
     * @return List<UsersEntity>
     */
    public List<UsersEntity> findUserByStatus(boolean userStatus, EntityManager em){
        return em.createNamedQuery("User.FindUserByStatus", UsersEntity.class)
                .setParameter("userStatus", userStatus)
                .getResultList();
    }

    /**
     * Method to add a new user
     * @param user
     * @param em
     * @return user
     */
    public UsersEntity addUser(UsersEntity user, EntityManager em){
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Method to update a user
     * @param user
     * @param em
     * @return user
     */
    public UsersEntity updateUser(UsersEntity user, EntityManager em){
        em.merge(user);
        return user;
    }
}
