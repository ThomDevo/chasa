package com.example.chasa.enums;

public enum Sex {
    HOMME ("homme"),
    FEMME("femme"),
    INDETERMINE("indéterminé");

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
}
