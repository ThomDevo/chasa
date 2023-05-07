package com.example.chasa.enums;
import java.util.Arrays;

public enum CertificateType {
    ANNUAL ("annuel"),
    ECG("ecg");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    CertificateType(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getCertificateType(){
        return type;
    }

    public static CertificateType strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(CertificateType.values())
                .filter(s -> s.getCertificateType().toLowerCase().equals(type))
                .findFirst()
                .orElse(null);
    }
}
