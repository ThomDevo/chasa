package com.example.chasa.beans;

import com.example.chasa.converterCustom.UsersConverter;
import com.example.chasa.entities.*;
import com.example.chasa.services.RoleService;
import com.example.chasa.services.UsersService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;
import org.jboss.weld.context.RequestContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class UsersBean extends FilterOfTable<UsersEntity> implements Serializable{
    private UsersEntity user = new UsersEntity();
    private UsersService usersService = new UsersService();
    private Date date;
    private String messageErrorLastName = "hidden";
    private String messageErrorFirstName = "hidden";
    private String messageErrorBirthDate = "hidden";
    private String messageErrorEmail = "hidden";
    private String messageErrorPhoneNumber = "hidden";
    private String messageErrorLifrasNumber = "hidden";
    private String messageErrorPassword = "hidden";

    /**
     * Method to find a Member based on a filter
     */
    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = usersService.findUserByFilter(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void loadUserId(){
        user = UsersConverter.getAsObjectStatic(String.valueOf(this.getIdRedirection()));
    }

    /*public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    public void click() {
        PrimeFaces.current().ajax().update("form:display");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }
*/
    public String submitFormAddUser() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";

        return redirect;
    }

    /*---list role for select input.---*/
    private List<RolesEntity> allRole;
    public List<RolesEntity> getAllRole(){
        return this.allRole;
    }
    public void initAllEditor(){
        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        try{
            this.allRole = roleService.findRoleAll(em);
        }catch(Exception e){
            this.allRole = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    /*---Getters and Setters---*/

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /*---Method To Add error message---*/

    /**
     * Method to add/hide an error message on the Last Name
     * @return messageErrorLastName
     */
    public String getMessageErrorLastName() {
        String message = this.messageErrorLastName;
        this.messageErrorLastName = "hidden";
        return message;
    }

    public void setMessageErrorLastName(String messageErrorLastName) {
        this.messageErrorLastName = messageErrorLastName;
    }

    public String getMessageErrorFirstName() {
        String message = this.messageErrorFirstName;
        this.messageErrorFirstName = "hidden";
        return message;
    }

    public void setMessageErrorFirstName(String messageErrorFirstName) {
        this.messageErrorFirstName = messageErrorFirstName;
    }

    public String getMessageErrorBirthDate() {
        String message = this.messageErrorBirthDate;
        this.messageErrorBirthDate = "hidden";
        return message;
    }

    public void setMessageErrorBirthDate(String messageErrorBirthDate) {
        this.messageErrorBirthDate = messageErrorBirthDate;
    }

    public String getMessageErrorEmail() {
        String message = this.messageErrorEmail;
        this.messageErrorEmail = "hidden";
        return message;
    }

    public void setMessageErrorEmail(String messageErrorEmail) {
        this.messageErrorEmail = messageErrorEmail;
    }

    public String getMessageErrorPhoneNumber() {
        String message = this.messageErrorPhoneNumber;
        this.messageErrorPhoneNumber = "hidden";
        return message;
    }

    public void setMessageErrorPhoneNumber(String messageErrorPhoneNumber) {
        this.messageErrorPhoneNumber = messageErrorPhoneNumber;
    }

    public String getMessageErrorLifrasNumber() {
        String message = this.messageErrorLifrasNumber;
        this.messageErrorLifrasNumber = "hidden";
        return message;
    }

    public void setMessageErrorLifrasNumber(String messageErrorLifrasNumber) {
        this.messageErrorLifrasNumber = messageErrorLifrasNumber;
    }

    public String getMessageErrorPassword() {
        String message = this.messageErrorPassword;
        this.messageErrorPassword = "hidden";
        return message;
    }

    public void setMessageErrorPassword(String messageErrorPassword) {
        this.messageErrorPassword = messageErrorPassword;
    }

}
