package com.example.chasa.beans;

import com.example.chasa.entities.LicenseUsersEntity;
import com.example.chasa.entities.MedicalCertificatesEntity;
import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.MedicalCertificateService;
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
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class MedicalCertificateBean extends FilterOfTable<MedicalCertificatesEntity> implements Serializable {
    private MedicalCertificateService medicalCertificateService = new MedicalCertificateService();
    private MedicalCertificatesEntity medicalCertificates = new MedicalCertificatesEntity();
    private List<MedicalCertificatesEntity> allCertificates;
    private String messageErrorIssueDate = "hidden";
    private String messageErrorExpiryDate = "hidden";
    private String messageErrorExpiryDatePast = "hidden";


    public void initListOfMedicalCertificates(UsersEntity user){
        EntityManager em = EMF.getEM();
        MedicalCertificateService medicalCertificateService = new MedicalCertificateService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            this.allCertificates = medicalCertificateService.findAllByUser(user.getIdUser(),em);
            transaction.commit();
        }catch(Exception e){
            this.allCertificates = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /**
     * Method to find a medical certificate based on a filter
     */
    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = medicalCertificateService.findAllMedicalCertificatesByCharacteristic(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to find a medical certificate based on a filter
     */
    public void ResearchFilterMember(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = medicalCertificateService.findAllMedicalCertificatesByCharacteristicMember(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to add a medical certificate for a user
     * @return Medical certificate
     */
    public String submitFormAddMedicalCertificateUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        MedicalCertificateService medicalCertificatesService = new MedicalCertificateService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String issueDate = simpleDateFormat.format(medicalCertificates.getIssueDate());
        String ExpiryDate = simpleDateFormat.format(medicalCertificates.getExpiryDate());
        int resultIssueDate =issueDate.compareTo(String.valueOf(now));
        int resultExpiryDate =ExpiryDate.compareTo(String.valueOf(now));
        int resultIssueExpiryDate =issueDate.compareTo(String.valueOf(ExpiryDate));

        if(resultIssueDate > 0){
            this.messageErrorIssueDate="";
            this.messageErrorExpiryDatePast="hidden";
            this.messageErrorExpiryDate="hidden";
            redirect = "null";
            return redirect;
        }else if(resultExpiryDate < 0){
            this.messageErrorIssueDate="hidden";
            this.messageErrorExpiryDatePast = "";
            this.messageErrorExpiryDate="hidden";
            redirect = "null";
            return redirect;
        }else if(resultIssueExpiryDate > 0){
            this.messageErrorIssueDate="hidden";
            this.messageErrorExpiryDatePast="hidden";
            this.messageErrorExpiryDate = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                medicalCertificatesService.addMedicalCertificates(medicalCertificates,em);
                transaction.commit();
                confirm();
            }catch(Exception e){
                redirect = null;
            }finally{
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            return redirect;
        }

    }

    /**
     * Method to update a medical certificate
     * @return a medical certificate
     */
    public String submitFormUpdateMedicalCertificateUser(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        MedicalCertificateService medicalCertificatesService = new MedicalCertificateService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String issueDate = simpleDateFormat.format(medicalCertificates.getIssueDate());
        String ExpiryDate = simpleDateFormat.format(medicalCertificates.getExpiryDate());
        int resultIssueDate =issueDate.compareTo(String.valueOf(now));
        int resultExpiryDate =ExpiryDate.compareTo(String.valueOf(now));
        int resultIssueExpiryDate =issueDate.compareTo(String.valueOf(ExpiryDate));

        if(resultIssueDate > 0){
            this.messageErrorIssueDate="";
            this.messageErrorExpiryDatePast="hidden";
            this.messageErrorExpiryDate="hidden";
            redirect = "null";
            return redirect;
        }else if(resultExpiryDate < 0){
            this.messageErrorIssueDate="hidden";
            this.messageErrorExpiryDatePast = "";
            this.messageErrorExpiryDate="hidden";
            redirect = "null";
            return redirect;
        }else if(resultIssueExpiryDate > 0){
            this.messageErrorIssueDate="hidden";
            this.messageErrorExpiryDatePast="hidden";
            this.messageErrorExpiryDate = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                medicalCertificatesService.updateMedicalCertificates(medicalCertificates,em);
                transaction.commit();
                confirm();
            }catch(Exception e){
                redirect = null;
            }finally{
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            return redirect;
        }

    }

    /**
     * Method to message of confirmation of add or update
     */
    public void confirm() {
        addMessage("Confirmation","Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /*---Getters and setters---*/

    public MedicalCertificateService getMedicalCertificateService() {
        return medicalCertificateService;
    }

    public void setMedicalCertificateService(MedicalCertificateService medicalCertificateService) {
        this.medicalCertificateService = medicalCertificateService;
    }

    public MedicalCertificatesEntity getMedicalCertificates() {
        return medicalCertificates;
    }

    public void setMedicalCertificates(MedicalCertificatesEntity medicalCertificates) {
        this.medicalCertificates = medicalCertificates;
    }

    public String getMessageErrorIssueDate() {
        return messageErrorIssueDate;
    }

    public void setMessageErrorIssueDate(String messageErrorIssueDate) {
        this.messageErrorIssueDate = messageErrorIssueDate;
    }

    public String getMessageErrorExpiryDate() {
        return messageErrorExpiryDate;
    }

    public void setMessageErrorExpiryDate(String messageErrorExpiryDate) {
        this.messageErrorExpiryDate = messageErrorExpiryDate;
    }

    public String getMessageErrorExpiryDatePast() {
        return messageErrorExpiryDatePast;
    }

    public void setMessageErrorExpiryDatePast(String messageErrorExpiryDatePast) {
        this.messageErrorExpiryDatePast = messageErrorExpiryDatePast;
    }

    public List<MedicalCertificatesEntity> getAllCertificates() {
        return allCertificates;
    }

    public void setAllCertificates(List<MedicalCertificatesEntity> allCertificates) {
        this.allCertificates = allCertificates;
    }
}
