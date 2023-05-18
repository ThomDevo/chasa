package com.example.chasa.beans;

import com.example.chasa.entities.UserEventsEntity;
import com.example.chasa.services.UserEventsService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;

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
public class UserEventsBean extends FilterOfTable<UserEventsEntity> implements Serializable {

    private UserEventsEntity userEvents = new UserEventsEntity();
    private UserEventsService userEventsService = new UserEventsService();
    private UserEventsEntity numberOfParticipants;
    @Inject
    private ConnectionBean connectionBean;

    public void findNumberOfParticipants(){
        EntityManager em = EMF.getEM();
        try{
            this.numberOfParticipants = userEventsService.findNumberOfParticipants(userEvents.getEventsByIdEvent().getIdEvent(),em);
            ProcessUtils.debug(""+ numberOfParticipants);
        }catch(Exception e){

        }finally{
            em.close();
        }
    }

    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = userEventsService.findAllFutureEvents(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void ResearchMyFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = userEventsService.findAllMyFutureEvents(this.filter,connectionBean.getUser().getIdUser(), em);
            ProcessUtils.debug(this.filter);

        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void ResearchListOfUsersFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = userEventsService.findAllParticipants(this.filter,userEvents.getEventsByIdEvent().getIdEvent(), em);
            ProcessUtils.debug(this.filter);

        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public String submitFormAddUserEvents(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        UserEventsService userEventsService = new UserEventsService();
        EntityTransaction transaction = em.getTransaction();
        Date now = new Date();
        try{
            transaction.begin();
            userEvents.setDateTimeEventSubscription(now);
            userEvents.setUserEventStatus(true);
            userEvents.setUsersByIdUser(connectionBean.getUser());
            userEventsService.addUserEvents(userEvents,em);
            transaction.commit();
            confirmAdd();
        }catch(Exception e){
            redirect = "null";
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    public String submitFormDeleteUserEvents(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        UserEventsService userEventsService = new UserEventsService();
        EntityTransaction transaction = em.getTransaction();
        Date now = new Date();
        try{
            transaction.begin();
            userEventsService.deleteUserEvents(userEvents,em);
            transaction.commit();
            confirmDelete();
        }catch(Exception e){
            redirect = "null";
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    public void confirmAdd() {
        String isoDatePattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(userEvents.getEventsByIdEvent().getDateTimeEvent());
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Event");
        String pageTitle3 = bundle.getString("addPlanning");
        addMessage(pageTitle+" "+ dateEvent+ " "+ pageTitle3 ,"Confirmation");
    }

    public void confirmDelete() {
        String isoDatePattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateEvent = simpleDateFormat.format(userEvents.getEventsByIdEvent().getDateTimeEvent());
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Event");
        String pageTitle3 = bundle.getString("deletePlanning");
        addMessage(pageTitle+" "+dateEvent+ " "+ pageTitle3 ,"Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /*---Getters and setters---*/

    public UserEventsEntity getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(UserEventsEntity userEvents) {
        this.userEvents = userEvents;
    }

    public UserEventsService getUserEventsService() {
        return userEventsService;
    }

    public void setUserEventsService(UserEventsService userEventsService) {
        this.userEventsService = userEventsService;
    }

    public UserEventsEntity getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(UserEventsEntity numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }
}
