package com.example.chasa.beans;
import com.example.chasa.entities.LicenseUsersEntity;
import com.example.chasa.entities.LicensesEntity;
import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.LicenseUserService;
import com.example.chasa.services.LicensesService;
import com.example.chasa.services.UserEventsService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class LicenseUserBean extends FilterOfTable<LicenseUsersEntity> implements Serializable{

    private LicenseUserService licenseUserService = new LicenseUserService();
    private LicenseUsersEntity licenseUser = new LicenseUsersEntity();
    private String messageErrorAdmissionDate = "hidden";
    private List<LicenseUsersEntity> allLicensesByUser;
    public List<LicenseUsersEntity> getAllLicensesByUser(){
        return this.allLicensesByUser;
    }
    private String messageErrorExists = "hidden";

    /**
     * Initialize list LicenseUser
     */
    public void initListOfLicense(UsersEntity user)
    {
        EntityManager em = EMF.getEM();
        LicenseUserService licenseUserService = new LicenseUserService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            this.allLicensesByUser=licenseUserService.findAllByUser(user.getIdUser(),em);
            //ProcessUtils.debug("mes licenses "+allLicensesByUser.size());
            transaction.commit();
        }
        catch(Exception e)
        {
            this.allLicensesByUser = new ArrayList<>();
            //ProcessUtils.debug(" je suis dans le catch de l'initialization du rolePermission : " + e);
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }

    /**
     * Method to find a licenseUser based on a filter
     */
    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = licenseUserService.findUserByFilter(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to find a licenseUserMember based on a filter
     */
    public void ResearchFilterMember(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = licenseUserService.FindUserByCharacteristicMember(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    public void initForm(){
        this.licenseUser.setUsersByIdUser(new UsersEntity());
        this.licenseUser.setLicensesByIdLicense(new LicensesEntity());
        this.licenseUser.setLicensedDate(new Date());
    }


    public String submitFormAddLicenseUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        LicenseUserService licenseUserService = new LicenseUserService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(licenseUser.getLicensedDate());
        int resultNow =dateString.compareTo(String.valueOf(now));


        if(resultNow > 0)
        {
            this.messageErrorAdmissionDate="";
            redirect = "null";
            return redirect;
        } else if(licenseUserService.isLicenseUserExists(licenseUser.getUsersByIdUser().getIdUser(),licenseUser.getLicensesByIdLicense().getIdLicense(),em)){
            this.messageErrorExists = "";
            redirect = "null";
            return redirect;
        }
        else{
        try{
            this.messageErrorAdmissionDate = "hidden";
            transaction.begin();
            licenseUserService.addLicenseUser(licenseUser,em);
            transaction.commit();
            confirmAdd();
            initForm();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the submitFormAddLicenseUser method: "+ e);
            redirect = "null";
        }finally {
            if(transaction.isActive())
            {
                transaction.rollback();
            }
            em.close();
        }
            return redirect;
        }
    }

    public String submitFormUpdateLicenseUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        LicenseUserService licenseUserService = new LicenseUserService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd HH:mm:ss ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(licenseUser.getLicensedDate());
        int resultNow =dateString.compareTo(String.valueOf(now));

        if(resultNow > 0)
        {
            this.messageErrorAdmissionDate="";
            redirect = "null";
            return redirect;
        }else{
            try{
                this.messageErrorAdmissionDate = "hidden";
                transaction.begin();
                licenseUserService.updateLicenseUser(licenseUser,em);
                transaction.commit();
                confirmUpdate();
                initForm();
            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the submitFormUpdateLicenseUser method: "+ e);
                redirect = "null";
            }finally {
                if(transaction.isActive())
                {
                    transaction.rollback();
                }
                em.close();
            }
            return redirect;
        }
    }

    public String submitFormDeleteLicenseUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        LicenseUserService licenseUserService = new LicenseUserService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            licenseUserService.deleteLicenseUser(licenseUser,em);
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


    public LicenseUserService getLicenseUserService() {
        return licenseUserService;
    }

    public void setLicenseUserService(LicenseUserService licenseUserService) {
        this.licenseUserService = licenseUserService;
    }

    public LicenseUsersEntity getLicenseUser() {
        return licenseUser;
    }

    public void setLicenseUser(LicenseUsersEntity licenseUser) {
        this.licenseUser = licenseUser;
    }


    public String getMessageErrorAdmissionDate() {
        return messageErrorAdmissionDate;
    }

    public void setMessageErrorAdmissionDate(String messageErrorAdmissionDate) {
        this.messageErrorAdmissionDate = messageErrorAdmissionDate;
    }
        public void confirmAdd() {
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            //ProcessUtils.debug(""+ bundle);
            String pageTitle = bundle.getString("license");
            String pageTitle2 = bundle.getString("of");
            String pageTitle3 = bundle.getString("add");
            addMessage(pageTitle+" "+ licenseUser.getLicensesByIdLicense().getLicenseLabel().toUpperCase()+ " "+ pageTitle2 + " "+ licenseUser.getUsersByIdUser().getLastName() + licenseUser.getUsersByIdUser().getFirstName()+ " "+ pageTitle3 ,"Confirmation");
        }

    public void confirmUpdate() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("license");
        String pageTitle2 = bundle.getString("of");
        String pageTitle3 = bundle.getString("update");
        addMessage(pageTitle+" "+ licenseUser.getLicensesByIdLicense().getLicenseLabel().toUpperCase()+ " "+ pageTitle2 + " "+ licenseUser.getUsersByIdUser().getLastName() + licenseUser.getUsersByIdUser().getFirstName()+ " "+ pageTitle3 ,"Confirmation");
    }

    public void confirmDelete() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("license");
        String pageTitle2 = bundle.getString("of");
        String pageTitle3 = bundle.getString("delete");
        addMessage(pageTitle+" "+ licenseUser.getLicensesByIdLicense().getLicenseLabel()+ " "+ pageTitle2 + " "+ licenseUser.getUsersByIdUser().getLastName() + licenseUser.getUsersByIdUser().getFirstName()+ " "+ pageTitle3 ,"Confirmation");
    }

        public void addMessage(String summary, String detail) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    public void setAllLicensesByUser(List<LicenseUsersEntity> allLicensesByUser) {
        this.allLicensesByUser = allLicensesByUser;
    }

    public String getMessageErrorExists() {
        return messageErrorExists;
    }

    public void setMessageErrorExists(String messageErrorExists) {
        this.messageErrorExists = messageErrorExists;
    }
}
