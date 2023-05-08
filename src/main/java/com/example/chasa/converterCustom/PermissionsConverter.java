package com.example.chasa.converterCustom;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.PermissionsEntity;
import com.example.chasa.services.AddressService;
import com.example.chasa.services.PermissionService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("PermissionsConverter")
public class PermissionsConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            //ProcessUtils.debug("Address");
            return null;
        }
        EntityManager em = EMF.getEM();
        PermissionService permissionService = new PermissionService();
        PermissionsEntity permission = null;
        try {
            permission = permissionService.findPermissionById(Integer.parseInt(value), em);
            //ProcessUtils.debug(String.valueOf(permission)+"Permission");
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return permission;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null){
            return "0";
        }
        PermissionsEntity permission = (PermissionsEntity) value;
        return String.valueOf(permission.getIdPermission());
    }
}
