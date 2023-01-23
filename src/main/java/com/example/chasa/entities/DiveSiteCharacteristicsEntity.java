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
    @Basic
    @Column(name = "id_dive_site", nullable = false)
    private int idDiveSite;
    @Basic
    @Column(name = "id_characteristic", nullable = false)
    private int idCharacteristic;
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

    public int getIdDiveSite() {
        return idDiveSite;
    }

    public void setIdDiveSite(int idDiveSite) {
        this.idDiveSite = idDiveSite;
    }

    public int getIdCharacteristic() {
        return idCharacteristic;
    }

    public void setIdCharacteristic(int idCharacteristic) {
        this.idCharacteristic = idCharacteristic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiveSiteCharacteristicsEntity that = (DiveSiteCharacteristicsEntity) o;

        if (idDiveSiteCharacterisitc != that.idDiveSiteCharacterisitc) return false;
        if (idDiveSite != that.idDiveSite) return false;
        if (idCharacteristic != that.idCharacteristic) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDiveSiteCharacterisitc;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + idDiveSite;
        result = 31 * result + idCharacteristic;
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
