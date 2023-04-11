package com.example.chasa.services;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.CitiesEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class CityService {

    /**
     * Method to find a city based on the IdCity
     * @param idCity
     * @param em
     * @return a city
     */
    public CitiesEntity findCityById(int idCity, EntityManager em) {
        return em.createNamedQuery("Cities.findById", CitiesEntity.class)
                .setParameter("idCity", idCity)
                .getSingleResult();
    }

    /**
     * Method to find all cities
     * @param em
     * @return List of cities
     */
    public List<CitiesEntity> findAllCities(EntityManager em) {
        return em.createNamedQuery("Cities.findAll", CitiesEntity.class)
                .getResultList();
    }
}
