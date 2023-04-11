package com.example.chasa.beans;

import com.example.chasa.converterCustom.CityConverter;
import com.example.chasa.converterCustom.RolesConverter;
import com.example.chasa.entities.CitiesEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.CityService;
import com.example.chasa.services.RoleService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class CitiesBean extends FilterOfTable<CitiesEntity> implements Serializable {

    private CitiesEntity city = new CitiesEntity();
    private CityService cityService = new CityService();
    //private String messageErrorRoleName = "hidden";

    public String index(){
        return "index";
    }




    public List<CitiesEntity> researchListOfCities(){
        EntityManager em = EMF.getEM();
        List<CitiesEntity> listOfCities = new ArrayList<CitiesEntity>();
        listOfCities =  cityService.findAllCities(em);
        return listOfCities;
    }

    public void loadCityId(){
        city= CityConverter.getAsObjectStatic(String.valueOf(this.getIdRedirection()));
    }
}
