package com.example.chasa.beans;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.EventsEntity;
import com.example.chasa.services.EventsService;
import com.example.chasa.utilities.FilterOfTable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ManagedBean
@SessionScoped
public class EventsBean extends FilterOfTable<AddressesEntity> implements Serializable {
    private EventsEntity events = new EventsEntity();
    private EventsService eventsService = new EventsService();
    private String messageErrorDateTimeEvent = "hidden";

    /*---Getters and setters---*/

    public EventsEntity getEvents() {
        return events;
    }

    public void setEvents(EventsEntity events) {
        this.events = events;
    }

    public EventsService getEventsService() {
        return eventsService;
    }

    public void setEventsService(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    public String getMessageErrorDateTimeEvent() {
        return messageErrorDateTimeEvent;
    }

    public void setMessageErrorDateTimeEvent(String messageErrorDateTimeEvent) {
        this.messageErrorDateTimeEvent = messageErrorDateTimeEvent;
    }
}
