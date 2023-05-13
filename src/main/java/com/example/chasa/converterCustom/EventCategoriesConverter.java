package com.example.chasa.converterCustom;

import com.example.chasa.entities.EventCategoriesEntity;
import com.example.chasa.services.EventCategoriesService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("EventCategoriesConverter")
public class EventCategoriesConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        EventCategoriesService eventCategoriesService = new EventCategoriesService();
        EventCategoriesEntity eventCategories = null;
        try {
            eventCategories = eventCategoriesService.findEventCategoryById(Integer.parseInt(value), em);

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
        return eventCategories;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null){
            return "0";
        }
        EventCategoriesEntity eventCategories = (EventCategoriesEntity) value;
        //ProcessUtils.debug(""+city);
        return String.valueOf(eventCategories.getIdEventCategory());
    }
}
