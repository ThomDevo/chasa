package com.example.chasa.beans;

import com.example.chasa.converterCustom.RolesConverter;
import com.example.chasa.converterCustom.UsersConverter;
import com.example.chasa.entities.*;
import com.example.chasa.services.AddressService;
import com.example.chasa.services.LicensesService;
import com.example.chasa.services.RoleService;
import com.example.chasa.services.UsersService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class UsersBean extends FilterOfTable<UsersEntity> implements Serializable{


    private UsersEntity userCrud = new UsersEntity();
    private UsersService usersService = new UsersService();
    private Date date;
    private String messageErrorLastName = "hidden";
    private String messageErrorFirstName = "hidden";
    private String messageErrorBirthDate = "hidden";
    private String messageErrorEmail = "hidden";
    private String messageErrorPhoneNumber = "hidden";
    private String messageErrorLifrasNumber = "hidden";
    private String messageErrorPassword = "hidden";
    @Inject
    private AddressesBean addressesBean;
    @Inject
    private LicenseUserBean licenseUserBean;
    @Inject
    private LicenseBean licenseBean;

    /**
     * Method to find a User based on a filter
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
    /**
     * Method to find a Member based on a filter
     */
    public void ResearchFilterMember(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = usersService.findUserByFilterAndOrderAscAdmin(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }


    public void loadUserIdCurrent(UsersEntity userCurrent, AddressesBean address){
        userCrud = userCurrent;
        address.setAddressCrud(userCrud.getAddresses());
        //ProcessUtils.debug(""+userCrud.getFirstName());
    }


    /*---List of personnes for select input---*/
    private List<UsersEntity> allUsers;
    public List<UsersEntity> getAllUsers(){
        return this.allUsers;
    }
    public void initAllUser(){
        EntityManager em = EMF.getEM();
        UsersService usersService = new UsersService();
        try{
            this.allUsers = usersService.findAll(em);
        }catch(Exception e){
            this.allUsers = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    private List<UsersEntity> allMembers;
    public List<UsersEntity> getAllMembers(){
        return this.allMembers;
    }
    public void initAllMembers(){
        EntityManager em = EMF.getEM();
        UsersService usersService = new UsersService();
        try{
            this.allMembers = usersService.findAllMembers(em);
        }catch(Exception e){
            this.allMembers = new ArrayList<>();
        }finally{
            em.close();
        }
    }


    /*---list role for select input.---*/
   private List<RolesEntity> allRole;
    public List<RolesEntity> getAllRole(){
        return this.allRole;
    }
    public void initAllUsers(){
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



    public String submitFormAddUser(AddressesEntity address) {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        UsersService usersService = new UsersService();
        AddressService addressService = new AddressService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        //ProcessUtils.debug("now-n "+String.valueOf(now));
        LocalDate nowMin8Years = (now).minusYears(8);
        //ProcessUtils.debug("n-8 "+String.valueOf(nowMin8Years));
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(userCrud.getBirthDate());
        //ProcessUtils.debug("n-8 "+dateString);
        int resultNow =dateString.compareTo(String.valueOf(now));
        //ProcessUtils.debug("now "+String.valueOf(resultNow));
        int resultMin8Years = dateString.compareTo(String.valueOf(nowMin8Years));
        //ProcessUtils.debug("-8 "+String.valueOf(resultMin8Years));

        if(resultNow > 0)
        {
            this.messageErrorBirthDate="";
            redirect = "null";
            return redirect;
        }else if(resultMin8Years >= 0){
            this.messageErrorBirthDate="";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                userCrud.setPassword(ProcessUtils.cryptPassword(userCrud.getPassword()));
                ProcessUtils.debug(userCrud.getPassword());
                addressService.addAddress(address,em);
                userCrud.setAddresses(address);
                usersService.addUser(userCrud, em);
                transaction.commit();
                confirmAddUser();
            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the connection method: submitFormAddUser() "+ e);
                redirect = "null" ;
            }finally {
                if(transaction.isActive()){
                    transaction.rollback();

                }
                em.close();

            }
            return redirect;
        }
    }


    public String submitFormUpdateUser(AddressesEntity address){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        UsersService usersService = new UsersService();
        AddressService addressService = new AddressService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        //ProcessUtils.debug("now-n "+String.valueOf(now));
        LocalDate nowMin8Years = (now).minusYears(8);
        //ProcessUtils.debug("n-8 "+String.valueOf(nowMin8Years));
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(userCrud.getBirthDate());
        //ProcessUtils.debug("n-8 "+dateString);
        int resultNow =dateString.compareTo(String.valueOf(now));
        //ProcessUtils.debug("now "+String.valueOf(resultNow));
        int resultMin8Years = dateString.compareTo(String.valueOf(nowMin8Years));
        //ProcessUtils.debug("-8 "+String.valueOf(resultMin8Years));

        if(resultNow > 0)
        {
            this.messageErrorBirthDate="";
            redirect = "null";
            return redirect;
        }else if(resultMin8Years >= 0){
            this.messageErrorBirthDate="";
            redirect = "null";
            return redirect;
        }else{
        try{
            transaction.begin();
            //userCrud.setPassword(ProcessUtils.cryptPassword(userCrud.getPassword()));
            //ProcessUtils.debug(userCrud.getPassword());
            //addressService.updateAddress(address,em);
            userCrud.setAddresses(userCrud.getIdAdress());
            usersService.updateUser(userCrud, em);
            transaction.commit();
            confirmUpdate();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the connection method: submitFormUpdateUser() "+ e);
            redirect = "null" ;
        }finally {
            if(transaction.isActive()){
                transaction.rollback();

            }
            em.close();

        }
        return redirect;
    }
    }

    public String submitFormAddMember(AddressesEntity address) {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        UsersService usersService = new UsersService();
        RoleService roleService = new RoleService();
        AddressService addressService = new AddressService();
        EntityTransaction transaction = em.getTransaction();
        LocalDate now = LocalDate.now();
        //ProcessUtils.debug("now-n "+String.valueOf(now));
        LocalDate nowMin8Years = (now).minusYears(8);
        //ProcessUtils.debug("n-8 "+String.valueOf(nowMin8Years));
        String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String dateString = simpleDateFormat.format(userCrud.getBirthDate());
        //ProcessUtils.debug("n-8 "+dateString);
        int resultNow =dateString.compareTo(String.valueOf(now));
        //ProcessUtils.debug("now "+String.valueOf(resultNow));
        int resultMin8Years = dateString.compareTo(String.valueOf(nowMin8Years));
        //ProcessUtils.debug("-8 "+String.valueOf(resultMin8Years));

        if(resultNow > 0)
        {
            this.messageErrorBirthDate="";
            redirect = "null";
            return redirect;
        }else if(resultMin8Years >= 0){
            this.messageErrorBirthDate="";
            redirect = "null";
            return redirect;
        }else{
            try{
                transaction.begin();
                userCrud.setPassword(ProcessUtils.cryptPassword(userCrud.getPassword()));
                userCrud.setRoles(roleService.findRoleById(1,em));
                ProcessUtils.debug(userCrud.getPassword());
                addressService.addAddress(address,em);
                userCrud.setAddresses(address);
                usersService.addUser(userCrud, em);
                transaction.commit();
                confirmAddUser();
            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the connection method: submitFormAddUser() "+ e);
                redirect = "null" ;
            }finally {
                if(transaction.isActive()){
                    transaction.rollback();

                }
                em.close();

            }
            return redirect;
        }
    }

    public void getFilterLicencesByUser(){
        //ProcessUtils.debug("toto");
        LicensesService licensesService= new LicensesService();
        EntityManager em = EMF.getEM();
        //this.licenseUserBean.getLicenseUser().getUsersByIdUser().getIdUser();
        try{
            licenseBean.setAllLicense(licensesService.findLicenseNotOwnByUser(this.licenseUserBean.getLicenseUser().getUsersByIdUser().getIdUser(), em));
            //ProcessUtils.debug("List récup et update"+ licensesService.findLicenseNotOwnByUser(this.licenseUserBean.getLicenseUser().getUsersByIdUser().getIdUser(), em).size());
        }catch(Exception e){
            ProcessUtils.debug("catche methodgetFilterLicencesByUser " + e);

        }
        //ProcessUtils.debug(""+ this.licenseUserBean.getLicenseUser().getUsersByIdUser().getFirstName());

    }


    public void confirmAddUser() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Person");
        String pageTitle3 = bundle.getString("add");
        addMessage(pageTitle +" "+userCrud.getLastName()+" "+userCrud.getFirstName()+" "+pageTitle3,"Confirmation");
    }

    public void confirmUpdate() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //ProcessUtils.debug(""+ bundle);
        String pageTitle = bundle.getString("Person");
        String pageTitle3 = bundle.getString("update");
        addMessage(pageTitle +" "+userCrud.getLastName()+" "+userCrud.getFirstName()+" "+pageTitle3,"Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /*---Getters and Setters---*/

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

    public UsersEntity getUserCrud() {
        return userCrud;
    }

    public void setUserCrud(UsersEntity userCrud) {
        this.userCrud = userCrud;
    }

    /*public void setAllRole(List<RolesEntity> allRole) {
        this.allRole = allRole;
    }*/

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
