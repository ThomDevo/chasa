package com.example.chasa.services;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.UsersEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class UsersService {


    /**
     * Method to find a User based on lifrasNumber
     * @param lifrasNumber
     * @param em
     * @return User
     */
    public UsersEntity findUserByLifrasNumber (int lifrasNumber, EntityManager em) {
        return em.createNamedQuery("User.SelectUser", UsersEntity.class)
                .setParameter("lifrasNumber", lifrasNumber)
                .getSingleResult();
    }


    /**
     * Method to find all users
     * @param em
     * @return List of Users
     */
    public List<UsersEntity> findAll(EntityManager em) {
        return em.createNamedQuery("User.SelectAll",UsersEntity.class)
                .getResultList();
    }

    public List<UsersEntity> findAllMembers(EntityManager em) {
        return em.createNamedQuery("User.SelectAllMembers",UsersEntity.class)
                .getResultList();
    }

    /**
     * Method to find User (MEMBRE) based on a filter and order by ASC
     * @param researchWord
     * @param em
     * @return List<UsersEntity>
     */
    public List<UsersEntity> findUserByFilter(String researchWord, EntityManager em){
        return em.createNamedQuery("User.FindUserByCharacteristic", UsersEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    public UsersEntity findUserById(int idUser, EntityManager em) {
        return em.createNamedQuery("User.findUserById", UsersEntity.class)
                .setParameter("idUser", idUser)
                .getSingleResult();
    }

    /**
     * Method to find User ALL based on a filter and order by ASC
     * @param researchWord
     * @param em
     * @return List<UsersEntity>
     */
    public List<UsersEntity> findUserByFilterAndOrderAscAdmin(String researchWord, EntityManager em){
        return em.createNamedQuery("User.FindMemberByCharacteristic", UsersEntity.class)
                .setParameter("researchWord", researchWord.toLowerCase())
                .getResultList();
    }

    public boolean isUserExist(int lifrasNumber, EntityManager em){
        Query query =em.createNamedQuery("User.IsUserExist", UsersEntity.class);
                query.setParameter("lifrasNumber", lifrasNumber);

        int count =((Number)query.getSingleResult()).intValue();
        return count > 0;
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
