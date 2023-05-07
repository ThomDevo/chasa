package com.example.chasa.converterCustom;

import com.example.chasa.entities.LicensesEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.LicensesService;
import com.example.chasa.services.RoleService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("LicenseConverter")
public class LicenseConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        LicensesService licensesService = new LicensesService();
        LicensesEntity license = null;
        try {
            license = licensesService.findById(Integer.parseInt(value), em);
        } catch (Exception e) {
            ProcessUtils.debug(" "+e);
        } finally {
            em.close();
        }
        return license;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null){
            return "0";
        }
        LicensesEntity license = (LicensesEntity) value;
        return String.valueOf(license.getIdLicense());
    }

}
