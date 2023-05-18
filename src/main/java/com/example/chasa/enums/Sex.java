package com.example.chasa.enums;

import java.util.Arrays;

public enum Sex {
    HOMME ("Homme"),
    FEMME("Femme"),
    INDETERMINE("Indéterminé");

    /**
     * Field
     */
    private String type;

    /**
     * constructor with 1 argument
     *
     * @param type
     */
    Sex(String type){
        this.type = type;
    }

    /**
     * Getter
     * @return type
     */
    public String getSexUser(){
        return type;
    }

    public static Sex strToEnum(String type){
        if(type==null)
            return null;
        return Arrays.stream(Sex.values())
                .filter(s -> s.getSexUser().toLowerCase().equals(type))
                .findFirst()
                .orElse(null);
    }
}
