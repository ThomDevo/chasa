package com.example.chasa.beans;

import com.example.chasa.entities.*;
import com.example.chasa.mail.Mail;
import com.example.chasa.mail.MailSender;
import com.example.chasa.services.EventsService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;
import com.itextpdf.text.Document;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class EventsBean extends FilterOfTable<EventsEntity> implements Serializable {
    private EventsEntity events = new EventsEntity();
    private EventsService eventsService = new EventsService();
    private String messageErrorDateTimeEvent = "hidden";
    @Inject
    private ConnectionBean connectionBean;

    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = eventsService.findEventsByFilter(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void ResearchFilterMyPotentialEvents(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = eventsService.findEventsByFilterForUser(this.filter,connectionBean.getUser().getIdUser(), em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public String submitFormAddEvents(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EventsService eventsService = new EventsService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        int resultNow = dateEvent.compareTo(String.valueOf(now));
        ProcessUtils.debug("Ctrl date "+ resultNow);

        if(resultNow < 1)
        {
            this.messageErrorDateTimeEvent="";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                eventsService.addEvent(events,em);
                transaction.commit();
                initFormEvent();
                confirmAdd();
            }catch(Exception e){
                redirect = "null";
            }finally{
                if(transaction.isActive())
                {
                    transaction.rollback();
                }
                em.close();
            }
            return  redirect;
        }
    }

    public String submitFormUpdateEvents(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EventsService eventsService = new EventsService();
        EntityTransaction transaction = em.getTransaction();
        Mail email = new Mail();
        Document doc = new Document();
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        LocalDate now = LocalDate.now();
        String isoDatePattern = "dd/MM/yyyy";
        String isoTimePattern = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat(isoTimePattern);
        String dateTime = simpleTimeFormat.format(events.getDateTimeEvent());
        int resultNow = dateEvent.compareTo(String.valueOf(now));
        ProcessUtils.debug("Ctrl date "+ resultNow);

        if(resultNow < 1)
        {
            this.messageErrorDateTimeEvent="";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                eventsService.updateEvent(events,em);
                transaction.commit();
                confirmUpdate();

            }catch(Exception e){
                redirect = "null";
            }finally{
                if(transaction.isActive())
                {
                    transaction.rollback();
                }
                em.close();
            }
            String membre = bundle.getString("membre");
            String at = bundle.getString("at");
            String subject = bundle.getString("subject");
            email.setFrom("teamchasa@outlook.com");
            email.setMsgBody(membre+" "+ events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+" "+dateEvent+" "+at+" "+dateTime);
            email.setSubject(subject+" "+ events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+" "+ dateEvent+" "+at+" "+dateTime );
            email.setNick("Chasa");
            email.setReplyTo("teamchasa@outlook.com");
            email.getListTo().add("thomas.devogelaere@promsocatc.net");
            email.setEncodeUTF8(true);
            try {
                MailSender.sendMail(email);
            } catch (Exception e) {
                e.printStackTrace();
            }
            initFormEvent();
            return  redirect;
        }
    }

    public void initFormEvent(){
        this.events.setEventCategoriesByIdEventCategory(new EventCategoriesEntity());
        this.events.setDateTimeEvent(new Date());
        this.events.setMaxNumPeople(0);
        this.events.setPrice(0.00);
        this.events.setAddressesByIdAddress(new AddressesEntity());
        this.events.setLicensesByIdLicense(new LicensesEntity());
        this.events.setDiveSitesByIdDiveSite(new DiveSitesEntity());
        this.messageErrorDateTimeEvent = "hidden";
    }

    public void confirmAdd() {
        String isoDatePattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Event");
        String pageTitle2 = bundle.getString("category");
        String pageTitle3 = bundle.getString("add");
        addMessage(pageTitle +" "+dateEvent+" "+ pageTitle2+" "+events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+" "+pageTitle3,"Confirmation");
    }

    public void confirmUpdate() {
        String isoDatePattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(events.getDateTimeEvent());
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Event");
        String pageTitle2 = bundle.getString("category");
        String pageTitle3 = bundle.getString("update");
        addMessage(pageTitle + " "+dateEvent+" "+ pageTitle2+" "+events.getEventCategoriesByIdEventCategory().getEventCategoryLabel()+" "+pageTitle3,"Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

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
