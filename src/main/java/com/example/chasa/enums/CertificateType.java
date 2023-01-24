package com.example.chasa.enums;

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
}
