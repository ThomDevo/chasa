package com.example.chasa.converterCustom;

import com.example.chasa.entities.AddressesEntity;
import com.example.chasa.services.AddressService;
import com.example.chasa.utilities.EMF;
import com.example.chasa.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("AddressConverter")
public class AddressConverter implements Converter {


    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            ProcessUtils.debug("Address");
            return null;
        }
        EntityManager em = EMF.getEM();
        AddressService addressService = new AddressService();
        AddressesEntity address = null;
        try {
            address = addressService.findAddressById(Integer.parseInt(value), em);
            ProcessUtils.debug(String.valueOf(address)+"Address");
        } catch (Exception e) {

        } finally {
            em.close();
        }
        return address;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if(value == null){
            return "0";
        }
        AddressesEntity address = (AddressesEntity) value;
        return String.valueOf(address.getIdAddress());
    }


}
