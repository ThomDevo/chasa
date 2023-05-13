package com.example.chasa.beans;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.EventCategoriesEntity;
import com.example.chasa.services.EventCategoriesService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class EventsCategoryBean extends FilterOfTable<AddressesEntity> implements Serializable {

    private EventCategoriesEntity eventsCategory = new EventCategoriesEntity();
    private EventCategoriesService eventCategoryService = new EventCategoriesService();
    private List<EventCategoriesEntity> allEventCategories;

    public void initAllEventsCategory(){
        EntityManager em = EMF.getEM();
        EventCategoriesService eventCategoryService = new EventCategoriesService();

        try{
            this.allEventCategories = eventCategoryService.findAll(em);
        }catch(Exception e){
            this.allEventCategories = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    /*---Getters and setters---*/

    public EventCategoriesEntity getEventsCategory() {
        return eventsCategory;
    }

    public void setEventsCategory(EventCategoriesEntity eventsCategory) {
        this.eventsCategory = eventsCategory;
    }

    public EventCategoriesService getEventCategoryService() {
        return eventCategoryService;
    }

    public void setEventCategoryService(EventCategoriesService eventCategoryService) {
        this.eventCategoryService = eventCategoryService;
    }

    public List<EventCategoriesEntity> getAllEventCategories() {
        return allEventCategories;
    }

    public void setAllEventCategories(List<EventCategoriesEntity> allEventCategories) {
        this.allEventCategories = allEventCategories;
    }
}
