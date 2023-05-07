package com.example.chasa.converterCustom;

import com.example.chasa.entities.CitiesEntity;
import com.example.chasa.services.CityService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("CityConverter")
public class CityConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        //ProcessUtils.debug(" GetAsObject A"+value);
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        CityService cityService = new CityService();
        CitiesEntity city = null;
        try {
            city = cityService.findCityById(Integer.parseInt(value), em);

        } catch (Exception e) {
            ProcessUtils.debug(" "+e);
        } finally {
            em.close();
        }
        //ProcessUtils.debug(" GetAsObject B"+city.getCityLabel());
        //ProcessUtils.debug(" GetAsObject B1"+city.getIdCity());
        //ProcessUtils.debug(" GetAsObject B2"+city.getAddressesByIdCity());
        //ProcessUtils.debug(" GetAsObject B3"+city.getCountriesByIdCountry());
        //ProcessUtils.debug(" GetAsObject B4"+city.getIdCountry());
        //ProcessUtils.debug(" GetAsObject B5"+city.getPostalCode());
        //ProcessUtils.debug(" GetAsObject B6"+city.getClass());
        return city;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        //ProcessUtils.debug(" CityBis");
        if(value == null){
            return "0";
        }
        CitiesEntity city = (CitiesEntity) value;
        //ProcessUtils.debug(""+city);
        return String.valueOf(city.getIdCity());
    }


}
