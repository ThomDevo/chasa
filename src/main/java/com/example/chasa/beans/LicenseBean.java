package com.example.chasa.beans;

import com.example.chasa.entities.LicensesEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.LicensesService;
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
public class LicenseBean extends FilterOfTable<UsersEntity> implements Serializable {

    private LicensesEntity license = new LicensesEntity();
    private LicensesService licenseService = new LicensesService();

    /*---list licenses for select input.---*/
    private List<LicensesEntity> allLicense = new ArrayList<>();
    public List<LicensesEntity> getAllLicense(){
        return this.allLicense;
    }
    public void initAllLicences(){
        ProcessUtils.debug("A update");
        EntityManager em = EMF.getEM();
        LicensesService licenseService = new LicensesService();
        try{
            this.allLicense = licenseService.findAll(em);
        }catch(Exception e){
            this.allLicense = new ArrayList<>();
        }finally{
            em.close();
        }
    }



    /*---Getters ans Setters---*/

    public LicensesEntity getLicense() {
        return license;
    }

    public void setLicense(LicensesEntity license) {
        this.license = license;
    }

    public LicensesService getLicenseService() {
        return licenseService;
    }

    public void setLicenseService(LicensesService licenseService) {
        this.licenseService = licenseService;
    }

    public void setAllLicense(List<LicensesEntity> allLicense) {
        this.allLicense = allLicense;
    }


}
