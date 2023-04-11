package com.example.chasa.beans;

import com.example.chasa.converterCustom.AddressConverter;
import com.example.chasa.converterCustom.RolesConverter;
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

    private AddressesEntity address = new AddressesEntity();
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

    /**
     * Method to have the ID for a redirection
     */
    public void loadAddressId(){
        address= AddressConverter.getAsObjectStatic(String.valueOf(this.getIdRedirection()));
    }

    /*---list Cities for select input.---*/
    private List<CitiesEntity> allCities;
    public List<CitiesEntity> getAllCities(){
        return this.allCities;
    }
    public void initAllEditor(){
        EntityManager em = EMF.getEM();
        CityService cityService = new CityService();
        try{
            this.allCities = cityService.findAllCities(em);
        }catch(Exception e){
            this.allCities = new ArrayList<>();
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
            if(addressService.isAddressExist(address.getStreet(),address.getNumber(),address.getBox(), address.getCitiesByIdCity().getIdCity(), em)){
                this.messageErrorExist = "";
                redirect = "null" ;

            }
            addressService.addAddress(address,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the connection method: "+ e);
            redirect = "null" ;
        }finally {
            if(transaction.isActive()){
                transaction.rollback();

            }
            em.close();

        }

        return redirect;
    }

    /*---Getters and setters---*/

    public AddressesEntity getAddress() {
        return address;
    }

    public void setAddress(AddressesEntity address) {
        this.address = address;
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
