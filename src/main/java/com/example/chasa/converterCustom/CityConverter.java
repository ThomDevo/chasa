package com.example.chasa.converterCustom;

import com.example.chasa.entities.CitiesEntity;
import com.example.chasa.entities.RolesEntity;
import com.example.chasa.services.CityService;
import com.example.chasa.services.RoleService;
import com.example.chasa.utilities.EMF;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("CityConverter")
public class CityConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        return null;
    }

    public static CitiesEntity getAsObjectStatic(String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        CityService cityService = new CityService();
        CitiesEntity city = null;
        try {
            city = cityService.findCityById(Integer.parseInt(value), em);
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return city;
    }

    public String getAsStringStatic(Object value){
        if(value == null){
            return "0";
        }
        CitiesEntity city = (CitiesEntity) value;
        return String.valueOf(city.getIdCity());
    }
}
