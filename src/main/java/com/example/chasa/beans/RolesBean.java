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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }


    }


    /*---list role for select input.---*/
    private List<RolesEntity> allRole;
    public List<RolesEntity> getAllRole(){
        return this.allRole;
    }
    public void initAllEditor(){
        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        try{
            this.allRole = roleService.findRoleAll(em);
        }catch(Exception e){
            this.allRole = new ArrayList<>();
        }finally{
            em.close();
        }
    }


    public String submitFormAddRole() {
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        RoleService roleService = new RoleService();
        EntityTransaction transaction = em.getTransaction();

        try{
            role.setRoleLabel(role.getRoleLabel().substring(0,1).toUpperCase()+role.getRoleLabel().substring(1).toLowerCase());
            transaction.begin();
            if(roleService.isRoleExist(role.getRoleLabel(), em)){
                this.messageErrorRoleName = "";
                redirect = "null" ;

            }
            roleService.addRole(role,em);
            transaction.commit();
            confirm();
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
            role.setRoleLabel(role.getRoleLabel().substring(0,1).toUpperCase()+role.getRoleLabel().substring(1).toLowerCase());
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
    public void confirm() {
        addMessage("Confirmation","Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
