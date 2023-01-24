package com.example.chasa.entities;

import javax.persistence.*;

@Entity
@Table(name = "dive_site_characteristics", schema = "chasa", catalog = "")
public class DiveSiteCharacteristicsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dive_site_characterisitc", nullable = false)
    private int idDiveSiteCharacterisitc;
    @Basic
    @Column(name = "value", nullable = false, length = 255)
    private String value;
    @ManyToOne
    @JoinColumn(name = "id_dive_site", referencedColumnName = "id_dive_site", nullable = false)
    private DiveSitesEntity diveSitesByIdDiveSite;
    @ManyToOne
    @JoinColumn(name = "id_characteristic", referencedColumnName = "id_characteristic", nullable = false)
    private CharacteristicsEntity characteristicsByIdCharacteristic;

    public int getIdDiveSiteCharacterisitc() {
        return idDiveSiteCharacterisitc;
    }

    public void setIdDiveSiteCharacterisitc(int idDiveSiteCharacterisitc) {
        this.idDiveSiteCharacterisitc = idDiveSiteCharacterisitc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DiveSitesEntity getIdDiveSite() {
        return diveSitesByIdDiveSite;
    }

    public void setIdDiveSite(DiveSitesEntity diveSitesByIdDiveSite) {
        this.diveSitesByIdDiveSite = diveSitesByIdDiveSite;
    }

    public CharacteristicsEntity getIdCharacteristic() {
        return characteristicsByIdCharacteristic;
    }

    public void setIdCharacteristic(CharacteristicsEntity characteristicsByIdCharacteristic) {
        this.characteristicsByIdCharacteristic = characteristicsByIdCharacteristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiveSiteCharacteristicsEntity that = (DiveSiteCharacteristicsEntity) o;

        if (idDiveSiteCharacterisitc != that.idDiveSiteCharacterisitc) return false;
        if (diveSitesByIdDiveSite != that.diveSitesByIdDiveSite) return false;
        if (characteristicsByIdCharacteristic != that.characteristicsByIdCharacteristic) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDiveSiteCharacterisitc;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public DiveSitesEntity getDiveSitesByIdDiveSite() {
        return diveSitesByIdDiveSite;
    }

    public void setDiveSitesByIdDiveSite(DiveSitesEntity diveSitesByIdDiveSite) {
        this.diveSitesByIdDiveSite = diveSitesByIdDiveSite;
    }

    public CharacteristicsEntity getCharacteristicsByIdCharacteristic() {
        return characteristicsByIdCharacteristic;
    }

    public void setCharacteristicsByIdCharacteristic(CharacteristicsEntity characteristicsByIdCharacteristic) {
        this.characteristicsByIdCharacteristic = characteristicsByIdCharacteristic;
    }
}
