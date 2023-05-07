package com.example.chasa.beans;

import com.example.chasa.entities.PermissionsEntity;
import com.example.chasa.entities.RolePermissionEntity;
import com.example.chasa.services.PermissionService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PermissionsBean extends FilterOfTable<PermissionsEntity> implements Serializable {
    private PermissionService permissionService = new PermissionService();
    private PermissionsEntity permissionEntity = new PermissionsEntity();
    private List<PermissionsEntity> allPermissions;

    public void initAllPermissions(){
        EntityManager em = EMF.getEM();
        PermissionsEntity permissionEntity = new PermissionsEntity();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            this.allPermissions = permissionService.findPermissionAll(em);
            transaction.commit();
        }catch(Exception e){
            this.allPermissions = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }


    /*---Getters and Setters---*/

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    public PermissionsEntity getPermissionEntity() {
        return permissionEntity;
    }

    public void setPermissionEntity(PermissionsEntity permissionEntity) {
        this.permissionEntity = permissionEntity;
    }

    public List<PermissionsEntity> getAllPermissions() {
        return allPermissions;
    }

    public void setAllPermissions(List<PermissionsEntity> allPermissions) {
        this.allPermissions = allPermissions;
    }
}
