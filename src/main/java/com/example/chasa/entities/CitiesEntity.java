package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "cities", schema = "chasa")
@NamedQueries({
        @NamedQuery(name = "Cities.findAll",query = "SELECT a FROM CitiesEntity a "),
        @NamedQuery(name = "Cities.findById",query = "SELECT a FROM CitiesEntity a WHERE a.idCity = :idCity")
})
public class CitiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_city", nullable = false)
    private int idCity;

    @Basic
    @Column(name = "postal_code", nullable = false)
    private int postalCode;

    @Basic
    @Column(name = "city_label", nullable = false, length = 255)
    private String cityLabel;


    @OneToMany(mappedBy = "idCity")
    private List<AddressesEntity> addressesByIdCity;

    @ManyToOne
    @JoinColumn(name = "id_country", referencedColumnName = "id_country", nullable = false)
    private CountriesEntity countriesByIdCountry;

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(String cityLabel) {
        this.cityLabel = cityLabel;
    }

    public CountriesEntity getIdCountry() {
        return countriesByIdCountry;
    }

    public void setIdCountry(CountriesEntity idCountry) {
        this.countriesByIdCountry = idCountry;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitiesEntity that = (CitiesEntity) o;

        if (idCity != that.idCity) return false;
        //if (postalCode != that.postalCode) return false;
        //if (countriesByIdCountry != that.countriesByIdCountry) return false;
        //if (cityLabel != null ? !cityLabel.equals(that.cityLabel) : that.cityLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCity;
        result = 31 * result + postalCode;
        result = 31 * result + (cityLabel != null ? cityLabel.hashCode() : 0);
        return result;
    }

    public List<AddressesEntity> getAddressesByIdCity() {
        return addressesByIdCity;
    }

    public void setAddressesByIdCity(List<AddressesEntity> addressesByIdCity) {
        this.addressesByIdCity = addressesByIdCity;
    }

    public CountriesEntity getCountriesByIdCountry() {
        return countriesByIdCountry;
    }

    public void setCountriesByIdCountry(CountriesEntity countriesByIdCountry) {
        this.countriesByIdCountry = countriesByIdCountry;
    }
}
