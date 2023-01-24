package com.example.chasa.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "addresses", schema = "chasa")
@NamedQueries({
                @NamedQuery(name = "Adresses.findAll",query = "SELECT a FROM AddressesEntity a ORDER BY a.street ASC"),
                @NamedQuery(name = "Adresses.findById",query = "SELECT a FROM AddressesEntity a WHERE a.idAddress = :idAddress"),
                @NamedQuery(name = "Addresses.findAllByStreet",query = "SELECT a FROM AddressesEntity a wHERE a.street = :street"),
                @NamedQuery(name = "Adresses.findAllByNumber",query = "SELECT a FROM AddressesEntity a WHERE a.number = :number"),
                @NamedQuery(name = "Adresses.findAllByBox",query = "SELECT a FROM AddressesEntity a WHERE a.box = :box"),
                @NamedQuery(name = "Adresses.findAllByCityId",query = "SELECT a FROM AddressesEntity a WHERE a.citiesByIdCity = :idCity")
              })
public class AddressesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_address", nullable = false)
    private int idAddress;
    @Basic
    @Column(name = "street", nullable = false, length = 255)
    private String street;
    @Basic
    @Column(name = "number", nullable = false)
    private int number;
    @Basic
    @Column(name = "box", nullable = true, length = 255)
    private String box;

    @ManyToOne
    @JoinColumn(name = "id_city", referencedColumnName = "id_city", nullable = false)
    private CitiesEntity citiesByIdCity;
    @OneToMany(mappedBy = "addressesByIdAddress")
    private List<DiveSitesEntity> diveSitesByIdAddress;
    @OneToMany(mappedBy = "addressesByIdAddress")
    private List<EventsEntity> eventsByIdAddress;
    @OneToMany(mappedBy = "addressesByIdAdress")
    private List<HyperbaricchambersEntity> hyperbaricchambersByIdAddress;
    @OneToMany(mappedBy = "addressesByIdAdress")
    private List<UsersEntity> usersByIdAddress;

    //Getters and setters

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public CitiesEntity getIdCity() {
        return citiesByIdCity;
    }

    public void setIdCity(CitiesEntity citiesByIdCity) {
        this.citiesByIdCity = citiesByIdCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressesEntity that = (AddressesEntity) o;

        if (idAddress != that.idAddress) return false;
        if (number != that.number) return false;
        if (citiesByIdCity != that.citiesByIdCity) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (box != null ? !box.equals(that.box) : that.box != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAddress;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + (box != null ? box.hashCode() : 0);
        return result;
    }

    public CitiesEntity getCitiesByIdCity() {
        return citiesByIdCity;
    }

    public void setCitiesByIdCity(CitiesEntity citiesByIdCity) {
        this.citiesByIdCity = citiesByIdCity;
    }

    public List<DiveSitesEntity> getDiveSitesByIdAddress() {
        return diveSitesByIdAddress;
    }

    public void setDiveSitesByIdAddress(List<DiveSitesEntity> diveSitesByIdAddress) {
        this.diveSitesByIdAddress = diveSitesByIdAddress;
    }

    public List<EventsEntity> getEventsByIdAddress() {
        return eventsByIdAddress;
    }

    public void setEventsByIdAddress(List<EventsEntity> eventsByIdAddress) {
        this.eventsByIdAddress = eventsByIdAddress;
    }

    public List<HyperbaricchambersEntity> getHyperbaricchambersByIdAddress() {
        return hyperbaricchambersByIdAddress;
    }

    public void setHyperbaricchambersByIdAddress(List<HyperbaricchambersEntity> hyperbaricchambersByIdAddress) {
        this.hyperbaricchambersByIdAddress = hyperbaricchambersByIdAddress;
    }

    public List<UsersEntity> getUsersByIdAddress() {
        return usersByIdAddress;
    }

    public void setUsersByIdAddress(List<UsersEntity> usersByIdAddress) {
        this.usersByIdAddress = usersByIdAddress;
    }
}
