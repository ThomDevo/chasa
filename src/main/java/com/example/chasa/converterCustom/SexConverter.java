package com.example.chasa.converterCustom;
import com.example.chasa.enums.Sex;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("Sexconverter")
public class SexConverter implements Converter {

    @Override
    public Sex getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return Sex.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        Sex sex = (Sex) value;
        return String.valueOf(sex.getSexUser());
    }

}
