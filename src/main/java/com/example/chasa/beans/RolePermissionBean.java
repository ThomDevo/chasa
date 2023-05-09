package com.example.chasa.beans;

import com.example.chasa.entities.PermissionsEntity;
import com.example.chasa.entities.RolePermissionEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.PermissionRoleService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.FilterOfTable;
import com.example.chasa.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ManagedBean
@SessionScoped
public class RolePermissionBean extends FilterOfTable<RolePermissionEntity> implements Serializable {
    private PermissionRoleService rolePermissionService = new PermissionRoleService();
    private RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
    private List<RolePermissionEntity> allRolePermissions;
    private List<RolePermissionEntity> allRolePermissionsPerRole;
    @Inject
    private PermissionsBean permissionsBean;
    @Inject
    private RolesBean rolesBean;

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
            allRolePermissionsPerRole = rolePermissionService.findRolePermissionByIdRole(roles.getIdRole(),em);
            permissionsBean.setAllPermissionsSelected(allRolePermissionsPerRole.stream().map(RolePermissionEntity::getPermissionsByIdPermission).collect(Collectors.toList()));
            transaction.commit();
        }catch(Exception e){
            allRolePermissionsPerRole = new ArrayList<>();
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
            filterOfTable = rolePermissionService.findAll(em);
            transaction.commit();
        }catch(Exception e){
            filterOfTable = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }


    }

    public String submitFormAddRolePermissions(){

        //ProcessUtils.debug("Submit form add role permissions"+permissionsBean.getAllPermissionsSelected().size());
        //ProcessUtils.debug("Submit form list permissions"+permissionsBean.getAllPermissions().size());
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        PermissionRoleService rolePermissionService = new PermissionRoleService();
        EntityTransaction transaction = em.getTransaction();
        RolePermissionEntity rolePermissionEntity;
        try{
            ProcessUtils.debug("Begin ");
            transaction.begin();
            for(int i=0;i< permissionsBean.getAllPermissionsSelected().size();i++){
                rolePermissionEntity = new RolePermissionEntity();
                ProcessUtils.debug("A "+ rolesBean.getRole());
                rolePermissionEntity.setRolesByIdRole(rolesBean.getRole());
                ProcessUtils.debug("B "+rolePermissionEntity.getRolesByIdRole());
                rolePermissionEntity.setPermissionsByIdPermission(permissionsBean.getAllPermissionsSelected().get(i));
                ProcessUtils.debug("C " + rolePermissionEntity.getPermissionsByIdPermission());
                rolePermissionService.addRolePermission(rolePermissionEntity,em);
                ProcessUtils.debug("D ");
            }

            transaction.commit();
            confirm();
        }catch(Exception e){
            ProcessUtils.debug("Catch "+e);
            redirect = "null" ;
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
    }

    public String submitFormUpdateRolePermissions(){

        //ProcessUtils.debug("Submit form add role permissions"+permissionsBean.getAllPermissionsSelected().size());
        //ProcessUtils.debug("Submit form list permissions"+permissionsBean.getAllPermissions().size());
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        PermissionRoleService rolePermissionService = new PermissionRoleService();
        EntityTransaction transaction = em.getTransaction();
        RolePermissionEntity rolePermissionEntity;
        boolean findMatch;

        try{
            ProcessUtils.debug("Begin ");
            transaction.begin();
            for(int i =0 ; i< permissionsBean.getAllPermissionsSelected().size(); i++){
                findMatch = false;
                for(int j= 0; j < allRolePermissionsPerRole.size(); j++){
                    if(permissionsBean.getAllPermissionsSelected().get(i).getIdPermission() == allRolePermissionsPerRole.get(j).getPermissionsByIdPermission().getIdPermission()){
                        findMatch = true;
                        break;
                    }
                }
                if(!findMatch){
                    rolePermissionEntity = new RolePermissionEntity();
                    //ProcessUtils.debug("A "+ rolesBean.getRole());
                    rolePermissionEntity.setRolesByIdRole(this.rolePermissionEntity.getRolesByIdRole());
                    //ProcessUtils.debug("B "+rolePermissionEntity.getRolesByIdRole());
                    rolePermissionEntity.setPermissionsByIdPermission(permissionsBean.getAllPermissionsSelected().get(i));
                    //ProcessUtils.debug("C " + rolePermissionEntity.getPermissionsByIdPermission());
                    rolePermissionService.addRolePermission(rolePermissionEntity,em);
                }
            }

            for(int j =0 ; j< allRolePermissionsPerRole.size(); j++){
                findMatch = false;
                for(int i= 0; i < permissionsBean.getAllPermissionsSelected().size(); i++){
                    if(permissionsBean.getAllPermissionsSelected().get(i).getIdPermission() == allRolePermissionsPerRole.get(j).getPermissionsByIdPermission().getIdPermission()){
                        findMatch = true;
                        break;
                    }
                }
                if(!findMatch){
                    rolePermissionEntity = rolePermissionService.findRolePermissionByIdRoleAndByIdPermission(getRolePermissionEntity().getRolesByIdRole().getIdRole(), allRolePermissionsPerRole.get(j).getPermissionsByIdPermission().getIdPermission(),em);
                    rolePermissionService.deleteRolePermission(rolePermissionEntity,em);
                }
            }

            transaction.commit();
            confirm();
        }catch(Exception e){
            ProcessUtils.debug("Catch "+e);
            redirect = "null" ;
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        return redirect;
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

    public PermissionsBean getPermissionsBean() {
        return permissionsBean;
    }

    public void setPermissionsBean(PermissionsBean permissionsBean) {
        this.permissionsBean = permissionsBean;
    }

    public void confirm() {
        addMessage("Confirmation","Confirmation");
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
