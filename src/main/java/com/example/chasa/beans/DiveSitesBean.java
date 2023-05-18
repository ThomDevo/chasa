package com.example.chasa.beans;

import com.example.chasa.entities.DiveSitesEntity;
import com.example.chasa.services.DiveSitesService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;

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
public class DiveSitesBean extends FilterOfTable<DiveSitesEntity> implements Serializable {
    private DiveSitesEntity diveSites = new DiveSitesEntity();
    private DiveSitesService diveSitesService = new DiveSitesService();
    private List<DiveSitesEntity> allDiveSites;
    private List<DiveSitesEntity> allDiveSitesCaracteristics;

    public void initAllDiveSites(){
        EntityManager em = EMF.getEM();
        DiveSitesService diveSitesService = new DiveSitesService();
        try{
            this.allDiveSites = diveSitesService.findAll(em);
        }catch(Exception e){
            this.allDiveSites = new ArrayList<>();
        }finally{
            em.close();
        }
    }

    public void initAllDiveSitesCaracteristics(int idDiveSites){
        EntityManager em = EMF.getEM();
        DiveSitesService diveSitesService = new DiveSitesService();
        try{
            this.allDiveSitesCaracteristics = diveSitesService.findAllByIdDiveSites(idDiveSites,em);
        }catch(Exception e){
            this.allDiveSitesCaracteristics = new ArrayList<>();
        }finally{
            em.close();
        }
    }


    /*---Getters and setters---*/

    public DiveSitesEntity getDiveSites() {
        return diveSites;
    }

    public void setDiveSites(DiveSitesEntity diveSites) {
        this.diveSites = diveSites;
    }

    public DiveSitesService getDiveSitesService() {
        return diveSitesService;
    }

    public void setDiveSitesService(DiveSitesService diveSitesService) {
        this.diveSitesService = diveSitesService;
    }

    public List<DiveSitesEntity> getAllDiveSites() {
        return allDiveSites;
    }

    public void setAllDiveSites(List<DiveSitesEntity> allDiveSites) {
        this.allDiveSites = allDiveSites;
    }

    public List<DiveSitesEntity> getAllDiveSitesCaracteristics() {
        return allDiveSitesCaracteristics;
    }

    public void setAllDiveSitesCaracteristics(List<DiveSitesEntity> allDiveSitesCaracteristics) {
        this.allDiveSitesCaracteristics = allDiveSitesCaracteristics;
    }
}
