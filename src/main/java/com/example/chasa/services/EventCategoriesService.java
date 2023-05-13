package com.example.chasa.services;

import com.example.chasa.entities.EventCategoriesEntity;

import javax.persistence.EntityManager;

import java.util.List;

import static org.apache.log4j.xml.DOMConfigurator.setParameter;

public class EventCategoriesService {

    public EventCategoriesEntity findEventCategoryById(int idEventCategory, EntityManager em){
        return em.createNamedQuery("EventCategory.findById",EventCategoriesEntity.class)
                .setParameter("idEventCategory",idEventCategory)
                .getSingleResult();
    }

    public List<EventCategoriesEntity> findAll(EntityManager em){
        return em.createNamedQuery("EventCategory.findAll", EventCategoriesEntity.class)
                .getResultList();
    }
}
