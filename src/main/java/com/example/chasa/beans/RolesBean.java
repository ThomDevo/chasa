package com.example.chasa.beans;

import com.example.chasa.converterCustom.RolesConverter;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.RoleService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ManagingCRU;
import com.example.chasa.utilities.ProcessUtils;
import org.primefaces.PrimeFaces;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
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
public class RolesBean extends FilterOfTable<RolesEntity> implements Serializable {


    private RolesEntity role = new RolesEntity();
    private RoleService roleService = new RoleService();
    private String messageErrorRoleName = "hidden";

    public String index(){
        return "index";
    }

    public void ResearchFilter(){

        EntityManager em = EMF.getEM();
        try{
            filterOfTable = roleService.findRoleByFilter(this.filter, em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }


    }


    public List<RolesEntity> researchListOfRoles(){
        EntityManager em = EMF.getEM();
        List<RolesEntity> listOfRoles = new ArrayList<RolesEntity>();
        listOfRoles = roleService.findRoleAll(em);
        return listOfRoles;
    }

    public void loadRoleId(){
        role= RolesConverter.getAsObjectStatic(String.valueOf(this.getIdRedirection()));
    }

    public String submitFormAddRole() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RoleService roleService = new RoleService();
        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();
            roleService.addRole(role,em);
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

    public String submitFormUpdateRole(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RoleService roleService = new RoleService();
        EntityTransaction transaction = em.getTransaction();


        try{
            transaction.begin();
            if(roleService.isRoleExist(role.getRoleLabel(), em)){
                this.messageErrorRoleName = "";
                redirect = "null" ;

            }
            roleService.updateRole(role,em);
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



    public RolesEntity getRole() {
        return role;
    }

    public void setRole(RolesEntity role) {
        this.role = role;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public String getMessageErrorRoleName() {
        String message = this.messageErrorRoleName;
        this.messageErrorRoleName = "hidden";
        return message;
    }
    public void setMessageErrorRoleName(String messageErrorConnection) {
        this.messageErrorRoleName = messageErrorConnection;
    }
}
