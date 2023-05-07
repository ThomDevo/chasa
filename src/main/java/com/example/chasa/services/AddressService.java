package com.example.chasa.services;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.CitiesEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.apache.log4j.xml.DOMConfigurator.setParameter;

public class AddressService {

    /**
     * Method to check if the address exists in the database
     * @param street
     * @param number
     * @param box
     * @param idCity
     * @param em
     * @return boolean
     */
    public boolean isAddressExist(String street, int number, String box, CitiesEntity idCity, EntityManager em){
        Query query = em.createNamedQuery("Addresses.isAddressExist", AddressesEntity.class);
        int count = ((Number)query.getSingleResult()).intValue();
        return count > 0;
    }

    /**
     * Method to find all addresses in the database with filter parameters
     * @param researchAddress
     * @param em
     * @return List of addresses
     */
    public List<AddressesEntity> findAddressByfilter(String researchAddress, EntityManager em){
        return em.createNamedQuery("Addresses.findAddressByFilter", AddressesEntity.class)
                .setParameter("researchAddress",researchAddress)
                .getResultList();
    }

    /**
     * Method to have all addresses
     * @param em
     * @return List of addresses
     */
    public List<AddressesEntity> findAll(EntityManager em){
        return em.createNamedQuery("Addresses.findAll", AddressesEntity.class)
                .getResultList();
    }

    /**
     * Method to have a single address based on the ID
     * @param idAdress
     * @param em
     * @return One Address
     */
    public AddressesEntity findAddressById(int idAdress, EntityManager em) {
        return em.createNamedQuery("Addresses.findById", AddressesEntity.class)
                .setParameter("idAddress", idAdress)
                .getSingleResult();
    }

    /**
     * Method to have all addresses based on the name of the street
     * @param street
     * @param em
     * @return List of addresses
     */
    public List<AddressesEntity> findAllAddressByStreet (String street, EntityManager em){
        return em.createNamedQuery("Addresses.findAllByStreet", AddressesEntity.class)
                .setParameter("street", street)
                .getResultList();
    }

    /**
     * Method to have all addresses based on the name of the number
     * @param number
     * @param em
     * @return List of addresses
     */
    public List<AddressesEntity> findAllAddressByNumber (int number, EntityManager em){
        return em.createNamedQuery("Addresses.findAllByNumber", AddressesEntity.class)
                .setParameter("number", number)
                .getResultList();
    }

    /**
     * Method to have all addresses based on the box
     * @param Box
     * @param em
     * @return List of addresses
     */
    public List<AddressesEntity> findAllAddressByBox (String Box, EntityManager em){
        return em.createNamedQuery("Addresses.findAllByBox", AddressesEntity.class)
                .setParameter("box", Box)
                .getResultList();
    }

    /**
     * Method to add an address
     * @param address
     * @param em
     * @return address
     */
    public AddressesEntity addAddress (AddressesEntity address, EntityManager em) {
        em.persist(address);
        em.flush();
        return address;
    }

    /**
     * Method to update an address
     * @param address
     * @param em
     * @return address
     */
    public AddressesEntity updateAddress (AddressesEntity address, EntityManager em){
        em.merge(address);
        return address;
    }
}
