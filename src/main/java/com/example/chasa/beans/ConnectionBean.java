package com.example.chasa.beans;

import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.PermissionRoleService;
import com.example.chasa.services.UsersService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Named
@SessionScoped
public class ConnectionBean implements Serializable {
    private UsersEntity user;
    private UsersEntity userForm = new UsersEntity();
    private String messageErrorConnection ="hidden";
    private String password;
    private UsersEntity current;

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/Connection.xhtml?faces-redirect=true";
    }

    /**
     * Connection method
     */
    public String connection()
    {

        EntityManager em = EMF.getEM();
        UsersService userService = new UsersService();
        PermissionRoleService permissionRoleService = new PermissionRoleService();
        String redirect;

        this.user = new UsersEntity();

        try
        {
            this.userForm = userService.findUserByLifrasNumber(this.userForm.getLifrasNumber(), this.password, em);
            this.userForm.listOfPermissions = permissionRoleService.findRolePermissionByIdRole(this.userForm.getRoles().getIdRole(), em);
            ProcessUtils.debug(String.valueOf(this.userForm.listOfPermissions.size()));
            this.user = userForm;
            this.messageErrorConnection = "hidden";
            redirect = "/VIEW/home";
        }
        catch(Exception e)
        {
            ProcessUtils.debug(" I'm in the catch of the connection method: " + e);

            //Put an error message
            this.messageErrorConnection = "";
            redirect = "Connection";
        }
        finally
        {
            em.close();
        }

        return redirect;
    }

    /**
     * Initialize list RolePermission
     */
    public static void initListPermissionRole(UsersEntity user)
    {
        EntityManager em = EMF.getEM();
        PermissionRoleService permissionroleService = new PermissionRoleService();
        EntityTransaction transaction = em.getTransaction();
        try
        {
            transaction.begin();
            //Call of the service that will use the NamedQuery of the "Permissionrole" entity
            user.listOfPermissions = permissionroleService.findRolePermissionByIdRole(user.getRoles().getIdRole(), em);
            transaction.commit();
        }
        catch(Exception e)
        {
            ProcessUtils.debug(" je suis dans le catch de l'initialization du rolePermission : " + e);
        }
        finally
        {
            if(transaction.isActive())
                transaction.rollback();
            em.close();
        }

    }

    /**
     * Method to control the permissions of the user
     * @param permissionName
     * @return
     */
    //ask is user log has permissions send.
    public boolean verifyPermissionUser(String permissionName){
       if(this.user == null || this.user.getIdUser()==0)
            return false;
        return this.user.verifyPermission(permissionName);
    }

    public boolean verifyNotPermissionUser(String permissionName){
        return !(verifyPermissionUser(permissionName));
    }

    /**
     * Getter and setter
     */


    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public String getMessageErrorConnection() {
        String message = this.messageErrorConnection;
        this.messageErrorConnection = "hidden";
        return message;
    }
    public void setMessageErrorConnection(String messageErrorConnection) {
        this.messageErrorConnection = messageErrorConnection;
    }

    public UsersEntity getUserForm() {
        return userForm;
    }

    public void setUserForm(UsersEntity userForm) {
        this.userForm = userForm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
