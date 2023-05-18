package com.example.chasa.converterCustom;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.entities.DiveSitesEntity;
import com.example.chasa.services.AddressService;
import com.example.chasa.services.DiveSitesService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("DiveSiteConverter")
public class DiveSiteConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            ProcessUtils.debug("diveSites");
            return null;
        }
        EntityManager em = EMF.getEM();
        DiveSitesService diveSitesService = new DiveSitesService();
        DiveSitesEntity  diveSites = null;
        try {
            diveSites = diveSitesService.findById(Integer.parseInt(value), em);
            ProcessUtils.debug(String.valueOf(diveSites)+"diveSites");
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return diveSites;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null){
            return "0";
        }
        DiveSitesEntity diveSites = (DiveSitesEntity) value;
        return String.valueOf(diveSites.getIdDiveSite());
    }
}
