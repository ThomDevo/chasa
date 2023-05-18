package com.example.chasa.services;

import com.example.chasa.entities.UserEventsEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class UserEventsService {

    public List<UserEventsEntity> findAllFutureEvents(String researchEvent,EntityManager em){
         return em.createNamedQuery("UserEvent.SelectAllFutureEvents",UserEventsEntity.class)
                .setParameter("researchEvent", researchEvent.toLowerCase())
                .getResultList();
    }

    public List<UserEventsEntity> findAllMyFutureEvents(String researchEvent, int idUser,EntityManager em){
        return em.createNamedQuery("UserEvent.SelectAllMyFutureEvents",UserEventsEntity.class)
                .setParameter("researchEvent", researchEvent)
                .setParameter("idUser", idUser)
                .getResultList();
    }

    public UserEventsEntity findNumberOfParticipants(int idUserEvent, EntityManager em){
        return em.createNamedQuery("UserEvent.SelectCountUserEventById",UserEventsEntity.class)
                .setParameter("idUserEvent",idUserEvent)
                .getSingleResult();
    }

    public List<UserEventsEntity> findAllParticipants(String researchEvent,int idUserEvent,EntityManager em){
        return em.createNamedQuery("UserEvent.SelectListUserEventById",UserEventsEntity.class)
                .setParameter("researchUser", researchEvent)
                .setParameter("idUserEvent",idUserEvent)
                .getResultList();
    }

    public UserEventsEntity addUserEvents(UserEventsEntity userEvents, EntityManager em){
        em.persist(userEvents);
        em.flush();
        return userEvents;
    }

    public UserEventsEntity updateUserEvents(UserEventsEntity userEvents, EntityManager em){
        em.merge(userEvents);
        return userEvents;
    }

    public void  deleteUserEvents(UserEventsEntity userEvents, EntityManager em){
        if(!em.contains(userEvents))
            userEvents = em.merge(userEvents);
        em.remove(userEvents);
        em.flush();
    }
}
