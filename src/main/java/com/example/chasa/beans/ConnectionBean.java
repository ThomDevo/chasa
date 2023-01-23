package com.example.chasa.beans;

import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.UsersService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.enterprise.context.SessionScoped;
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

    /**
     * Connection method
     */
    public String connection()
    {
        String redirect;
       /* EntityManager em = EMF.getEM();
        UsersService userService = new UsersService();
        String redirect;

        this.user = new UsersEntity();

        try
        {
            this.userForm = userService.findUserByLifrasNumber(this.userForm.getLifrasNumber(), em);
            this.user = userForm;
            this.messageErrorConnection = "hidden";
            redirect = "/VIEW/accueil";
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
        }*/
        redirect = "/VIEW/home";
        return redirect;
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
