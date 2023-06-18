package com.example.chasa.beans;

import com.example.chasa.converterCustom.AddressConverter;
import com.example.chasa.converterCustom.CityConverter;
import com.example.chasa.converterCustom.RolesConverter;
import com.example.chasa.converterCustom.UsersConverter;
import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.CitiesEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.AddressService;
import com.example.chasa.services.CityService;
import com.example.chasa.services.RoleService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class AddressesBean extends FilterOfTable<AddressesEntity> implements Serializable {

    private AddressesEntity addressCrud = new AddressesEntity();
    private AddressService addressService = new AddressService();
    private String messageErrorStreet = "hidden";
    private String messageErrorNumber = "hidden";
    private String messageErrorExist = "hidden";


    /**
     * Method to find an address with filter
     */
    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = addressService.findAddressByfilter(this.filter, em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

    /**
     * Method to find all Addresses
     * @return List of Addresses
     */
    public List<AddressesEntity> researchListOfAddresses(){
        EntityManager em = EMF.getEM();
        List<AddressesEntity> listOfAddresses = new ArrayList<AddressesEntity>();
        listOfAddresses = addressService.findAll(em);
        return listOfAddresses;
    }


    /*---list Cities for select input.---*/
    private List<CitiesEntity> allCities;
    public List<CitiesEntity> getAllCities(){
        return this.allCities;
    }

    public void initAllCity(){
        EntityManager em = EMF.getEM();
        CityService cityService = new CityService();
        try{
            this.allCities = cityService.findAllCities(em);
            //ProcessUtils.debug(String.valueOf(allCities.size())+" Size of the array of Addresses");
        }catch(Exception e){
            this.allCities = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    public void resetAddress(){
        addressCrud = new AddressesEntity();
    }

    private List<AddressesEntity> allAddresses;
    public List<AddressesEntity> getAllAddresses(){return allAddresses;}

    public void initAllAddresses(){
        EntityManager em = EMF.getEM();
        AddressService addressService = new AddressService();
        try{
            this.allAddresses = addressService.findAll(em);
            //ProcessUtils.debug(String.valueOf(allCities.size())+" Size of the array of Addresses");
        }catch(Exception e){
            this.allAddresses = new ArrayList<>();
        }finally{
            em.close();
        }
    }



    public String submitFormAddAddress() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        AddressService addressService = new AddressService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            addressService.addAddress(addressCrud,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the connection method: submitFormAddAddress() "+ e);
            redirect = "null" ;
        }finally {
            if(transaction.isActive()){
                transaction.rollback();

            }
            em.close();

        }

        return redirect;
    }

    public String submitFormUpdateAddress() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        AddressService addressService = new AddressService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            addressService.updateAddress(addressCrud,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the connection method: submitFormUpdateAddress() "+ e);
            redirect = "null" ;
        }finally {
            if(transaction.isActive()){
                transaction.rollback();

            }
            em.close();

        }

        return redirect;
    }

    public void initFormAddAddress(){
        this.addressCrud.setStreet("");
        this.addressCrud.setNumber(0);
        this.addressCrud.setBox("");
        this.addressCrud.setIdCity(null);
    }

    /*---Getters and setters---*/
    public AddressesEntity getAddressCrud() {
        return addressCrud;
    }

    public void setAddressCrud(AddressesEntity addressCrud) {
        this.addressCrud = addressCrud;
    }

    public void setAllCities(List<CitiesEntity> allCities) {
        this.allCities = allCities;
    }

    public AddressesEntity getAddress() {
        return addressCrud;
    }

    public void setAddress(AddressesEntity address) {
        this.addressCrud = address;
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    /*---Method to add/hide error messages---*/

    public String getMessageErrorStreet() {
        String message = this.messageErrorStreet;
        this.messageErrorStreet = "hidden";
        return message;
    }
    public void setMessageErrorStreet(String messageErrorStreet) {
        this.messageErrorStreet = messageErrorStreet;
    }

    public String getMessageErrorNumber() {
        String message = this.messageErrorNumber;
        this.messageErrorNumber = "hidden";
        return message;
    }
    public void setMessageErrorNumber(String messageErrorNumber) {
        this.messageErrorNumber = messageErrorNumber;
    }

    public String getMessageErrorExist() {
        String message = this.messageErrorExist;
        this.messageErrorExist = "hidden";
        return message;
    }
    public void setMessageErrorExist(String messageErrorExist) {
        this.messageErrorExist = messageErrorExist;
    }

}
