package com.example.chasa.converterCustom;

import com.example.chasa.entities.RolesEntity;
import com.example.chasa.utilities.EMF;
import com.example.chasa.services.RoleService;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("RolesConverter")
public class RolesConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return getAsObjectStatic(value);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return getAsStringStatic(value);
    }


    public static RolesEntity getAsObjectStatic(String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        RoleService roleService = new RoleService();
        RolesEntity role = null;
        try {
            role = roleService.findRoleById(Integer.parseInt(value), em);
        } catch (Exception e) {
            ProcessUtils.debug(" "+e);
        } finally {
            em.close();
        }
        return role;
    }

    public String getAsStringStatic(Object value){
        if(value == null){
            return "0";
        }
        RolesEntity role = (RolesEntity) value;
        return String.valueOf(role.getIdRole());
    }
}
