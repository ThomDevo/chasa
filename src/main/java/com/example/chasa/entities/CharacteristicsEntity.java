package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "characteristics", schema = "chasa", catalog = "")
public class CharacteristicsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_characteristic", nullable = false)
    private int idCharacteristic;
    @Basic
    @Column(name = "characteristic_label", nullable = false, length = 255)
    private String characteristicLabel;
    @OneToMany(mappedBy = "characteristicsByIdCharacteristic")
    private Collection<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdCharacteristic;

    public int getIdCharacteristic() {
        return idCharacteristic;
    }

    public void setIdCharacteristic(int idCharacteristic) {
        this.idCharacteristic = idCharacteristic;
    }

    public String getCharacteristicLabel() {
        return characteristicLabel;
    }

    public void setCharacteristicLabel(String characteristicLabel) {
        this.characteristicLabel = characteristicLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharacteristicsEntity that = (CharacteristicsEntity) o;

        if (idCharacteristic != that.idCharacteristic) return false;
        if (characteristicLabel != null ? !characteristicLabel.equals(that.characteristicLabel) : that.characteristicLabel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCharacteristic;
        result = 31 * result + (characteristicLabel != null ? characteristicLabel.hashCode() : 0);
        return result;
    }

    public Collection<DiveSiteCharacteristicsEntity> getDiveSiteCharacteristicsByIdCharacteristic() {
        return diveSiteCharacteristicsByIdCharacteristic;
    }

    public void setDiveSiteCharacteristicsByIdCharacteristic(List<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdCharacteristic) {
        this.diveSiteCharacteristicsByIdCharacteristic = diveSiteCharacteristicsByIdCharacteristic;
    }

    public void setDiveSiteCharacteristicsByIdCharacteristic(Collection<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdCharacteristic) {
        this.diveSiteCharacteristicsByIdCharacteristic = diveSiteCharacteristicsByIdCharacteristic;
    }
}
