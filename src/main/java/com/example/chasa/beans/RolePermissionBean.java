package com.example.chasa.beans;

import com.example.chasa.entities.RolePermissionEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.PermissionRoleService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;

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
public class RolePermissionBean extends FilterOfTable<RolePermissionEntity> implements Serializable {
    private PermissionRoleService rolePermissionService = new PermissionRoleService();
    private RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
    private List<RolePermissionEntity> allRolePermissions;
    private List<RolePermissionEntity> allRolePermissionsPerRole;

    /**
     * Method to have all the permissions associated with the role
     * @param roles
     */
    public void initListOfPermissionsPerRole(RolesEntity roles){
        EntityManager em = EMF.getEM();
        PermissionRoleService rolePermissionService = new PermissionRoleService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            this.allRolePermissionsPerRole = rolePermissionService.findRolePermissionByIdRole(roles.getIdRole(),em);
            transaction.commit();
        }catch(Exception e){
            this.allRolePermissionsPerRole = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /**
     * Method to have all the role permissions
     */
    public void allRolePermissions(){
        EntityManager em = EMF.getEM();
        PermissionRoleService rolePermissionService = new PermissionRoleService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            this.allRolePermissions = rolePermissionService.findAll(em);
            transaction.commit();
        }catch(Exception e){
            this.allRolePermissions = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }


    /*---Getters and setters---*/

    public PermissionRoleService getRolePermissionService() {
        return rolePermissionService;
    }

    public void setRolePermissionService(PermissionRoleService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    public RolePermissionEntity getRolePermissionEntity() {
        return rolePermissionEntity;
    }

    public void setRolePermissionEntity(RolePermissionEntity rolePermissionEntity) {
        this.rolePermissionEntity = rolePermissionEntity;
    }

    public List<RolePermissionEntity> getAllRolePermissions() {
        return allRolePermissions;
    }

    public void setAllRolePermissions(List<RolePermissionEntity> allRolePermissions) {
        this.allRolePermissions = allRolePermissions;
    }

    public List<RolePermissionEntity> getAllRolePermissionsPerRole() {
        return allRolePermissionsPerRole;
    }

    public void setAllRolePermissionsPerRole(List<RolePermissionEntity> allRolePermissionsPerRole) {
        this.allRolePermissionsPerRole = allRolePermissionsPerRole;
    }
}
