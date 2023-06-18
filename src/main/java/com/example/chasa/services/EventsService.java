package com.example.chasa.services;

import com.example.chasa.entities.EventsEntity;
import com.example.chasa.entities.LicenseUsersEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class EventsService {

    public EventsEntity findEventsById(int idEvent, EntityManager em){
        return em.createNamedQuery("Event.SelectEventById", EventsEntity.class)
                .setParameter("idEvent", idEvent)
                .getSingleResult();
    }

    public List<EventsEntity> findEventsByFilter(String researchEvent, EntityManager em){
        return em.createNamedQuery("Event.SelectAllFutureEvents",EventsEntity.class)
                .setParameter("researchEvent", researchEvent.toLowerCase())
                .getResultList();
    }

    public List<EventsEntity> findEventsByFilterForUser(String researchEvent, int idUser, EntityManager em){
        return em.createNamedQuery("Event.SelectAllPotentialsFutureEvents",EventsEntity.class)
                .setParameter("researchEvent", researchEvent.toLowerCase())
                .setParameter("idUser", idUser)
                .getResultList();
    }

    public EventsEntity addEvent(EventsEntity event, EntityManager em){
        em.persist(event);
        em.flush();
        return event;
    }

    public EventsEntity updateEvent(EventsEntity event, EntityManager em){
        em.merge(event);
        return event;
    }

    public void deleteEvent(EventsEntity event, EntityManager em){
        if(!em.contains(event))
            event = em.merge(event);
        em.remove(event);
        em.flush();
    }


}
