package com.example.chasa.converterCustom;

import com.example.chasa.entities.RolesEntity;
import com.example.chasa.entities.UsersEntity;
import com.example.chasa.services.RoleService;
import com.example.chasa.services.UsersService;
import com.example.chasa.utilities.EMF;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.persistence.EntityManager;

public class UsersConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return null;
    }


    public static UsersEntity getAsObjectStatic(String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        UsersService usersService = new UsersService();
        UsersEntity user = null;
        try {
            user = usersService.findUserById(Integer.parseInt(value), em);
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return user;
    }

    public String getAsStringStatic(Object value){
        if(value == null){
            return "0";
        }
        UsersEntity user = (UsersEntity) value;
        return String.valueOf(user.getIdUser());
    }
}
