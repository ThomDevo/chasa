package com.example.chasa.converterCustom;

import com.example.chasa.enums.CertificateType;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("MedicalCertificateConverter")
public class MedicalCertificateConverter implements Converter {
    @Override
    public CertificateType getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value==null || value.equals("null") || value.equals(""))
            return null;
        return CertificateType.strToEnum(value);
    }

    //cast from object to string.
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if(value==null){
            return "0";
        }
        CertificateType certificateType = (CertificateType) value;
        return String.valueOf(certificateType.getCertificateType());
    }
}
