package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "countries", schema = "chasa", catalog = "")
public class CountriesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_country", nullable = false)
    private int idCountry;
    @Basic
    @Column(name = "country_label", nullable = false, length = 255)
    private String countryLabel;
    @OneToMany(mappedBy = "countriesByIdCountry")
    private List<CitiesEntity> citiesByIdCountry;

    public int getIdCountry() {
        return idCountry;
    }

    public void setIdCountry(int idCountry) {
        this.idCountry = idCountry;
    }

    public String getCountryLabel() {
        return countryLabel;
    }

    public void setCountryLabel(String countryLabel) {
        this.countryLabel = countryLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountriesEntity that = (CountriesEntity) o;

        if (idCountry != that.idCountry) return false;
        if (countryLabel != null ? !countryLabel.equals(that.countryLabel) : that.countryLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCountry;
        result = 31 * result + (countryLabel != null ? countryLabel.hashCode() : 0);
        return result;
    }

    public List<CitiesEntity> getCitiesByIdCountry() {
        return citiesByIdCountry;
    }

    public void setCitiesByIdCountry(List<CitiesEntity> citiesByIdCountry) {
        this.citiesByIdCountry = citiesByIdCountry;
    }

}
