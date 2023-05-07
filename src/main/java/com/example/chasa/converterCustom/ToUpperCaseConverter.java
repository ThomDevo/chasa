package com.example.chasa.converterCustom;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("ToUpperCaseConverter")
public class ToUpperCaseConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        return (submittedValue != null) ? submittedValue.toUpperCase(): null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null || ((String) modelValue).isEmpty()) {
            return null;
        }

        String string = (String) modelValue;
        return new StringBuilder()
                .append(Character.toTitleCase(string.charAt(0)))
                .append(string.substring(1))
                .toString();
    }

}
