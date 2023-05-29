package com.example.chasa.beans;

import com.example.chasa.entities.DiveSitesEntity;
import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.DiveSiteCharacteristicService;
import com.example.chasa.services.DiveSitesService;
import com.example.chasa.services.PermissionRoleService;
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

    public static void initListOfDiveSiteCharacteristics(DiveSitesEntity diveSites)
    {
        EntityManager em = EMF.getEM();
        DiveSiteCharacteristicService diveSiteCharacteristicService = new DiveSiteCharacteristicService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "DiveSiteCharacteristicService" entity
            diveSites.listOfDiveSiteCharacteristics = diveSiteCharacteristicService.findallDiveSiteCharacteristicsByIdDiveSite(diveSites.getIdDiveSite(), em);
            transaction.commit();
        }
        catch(Exception e)
        {
            ProcessUtils.debug(" je suis dans le catch de l'initialisation du diveSiteCharacteristic : " + e);
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
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
