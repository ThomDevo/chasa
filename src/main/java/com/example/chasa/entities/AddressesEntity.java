package com.example.chasa.entities;

import com.example.chasa.utilities.ProcessUtils;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "addresses", schema = "chasa")
@NamedQueries({
                @NamedQuery(name="Addresses.isAddressExist", query="SELECT COUNT(a) FROM AddressesEntity a WHERE a.street = :street AND a.number = :number AND a.box = :box "),
                @NamedQuery(name="Addresses.findAddressByFilter", query="SELECT a FROM AddressesEntity a WHERE ((lower(a.street) LIKE CONCAT('%', :researchAddress, '%')) OR ((lower(a.number) LIKE CONCAT('%', :researchAddress, '%'))) OR ((lower(a.box) LIKE CONCAT('%', :researchAddress, '%'))))"),
                @NamedQuery(name = "Addresses.findAll",query = "SELECT a FROM AddressesEntity a GROUP BY a.street, a.number,a.box,a.idCity"),
                @NamedQuery(name = "Addresses.findById",query = "SELECT a FROM AddressesEntity a WHERE a.idAddress = :idAddress"),
                @NamedQuery(name = "Addresses.findAllByStreet",query = "SELECT a FROM AddressesEntity a wHERE a.street = :street"),
                @NamedQuery(name = "Addresses.findAllByNumber",query = "SELECT a FROM AddressesEntity a WHERE a.number = :number"),
                @NamedQuery(name = "Addresses.findAllByBox",query = "SELECT a FROM AddressesEntity a WHERE a.box = :box")
              })
public class AddressesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_address", nullable = false)
    private int idAddress;

    @Pattern(regexp = "^[A-Z][A-z ',\\-.-éèçàâêîûôù]{1,255}$")
    @NotNull
    @Basic
    @Column(name = "street", nullable = false, length = 255)
    private String street;

    @Range(min=1)
    @Basic
    @Column(name = "number", nullable = false)
    private int number;

    @Basic
    @Column(name = "box", nullable = true, length = 255)
    private String box;

    @ManyToOne
    @JoinColumn(name = "id_city", referencedColumnName = "id_city", nullable = false)
    private CitiesEntity idCity;

    @OneToMany(mappedBy = "addressesByIdAddress")
    private List<DiveSitesEntity> diveSitesByIdAddress;

    @OneToMany(mappedBy = "addressesByIdAddress")
    private List<EventsEntity> eventsByIdAddress;

    @OneToMany(mappedBy = "addressesByIdAdress")
    private List<HyperbaricchambersEntity> hyperbaricchambersByIdAddress;

    @OneToMany(mappedBy = "addresses")
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

    public CitiesEntity getIdCityX() {
        return idCity;
    }

    public void setIdCityX(CitiesEntity idCity) {
        //ProcessUtils.debug(""+ idCity);
        this.idCity = idCity;
    }
    public CitiesEntity getIdCity() {
        return idCity;
    }

    public void setIdCity(CitiesEntity idCity) {
        //ProcessUtils.debug(" "+ idCity);
        this.idCity = idCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressesEntity that = (AddressesEntity) o;

        if (idAddress != that.idAddress) return false;
        //if (number != that.number) return false;
        //if (idCity != that.idCity) return false;
        //if (street != null ? !street.equals(that.street) : that.street != null) return false;
        //if (box != null ? !box.equals(that.box) : that.box != null) return false;

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
