package com.example.chasa.entities;

import com.example.chasa.beans.DiveSitesBean;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dive_sites", schema = "chasa")
@NamedQueries({
        @NamedQuery(name="DiveSites.findById", query="SELECT ds FROM DiveSitesEntity ds WHERE ds.idDiveSite = :idDiveSite"),
        @NamedQuery(name="DiveSites.findAllById", query="SELECT ds FROM DiveSitesEntity ds JOIN DiveSiteCharacteristicsEntity dsc ON (ds.idDiveSite = dsc.diveSitesByIdDiveSite.idDiveSite) WHERE ds.idDiveSite = :idDiveSite"),
        @NamedQuery(name="DiveSites.findAll", query="SELECT ds FROM DiveSitesEntity ds"),
        @NamedQuery(name="DiveSites.findByLabel", query="SELECT ds FROM DiveSitesEntity ds WHERE ((lower(ds.diveSiteLabel) LIKE CONCAT('%', :researchDiveSite,'%')))"),
})
public class DiveSitesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dive_site", nullable = false)
    private int idDiveSite;
    @Basic
    @Column(name = "dive_site_label", nullable = false, length = 200)
    private String diveSiteLabel;
    @OneToMany(mappedBy = "diveSitesByIdDiveSite")
    private List<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdDiveSite;
    @ManyToOne
    @JoinColumn(name = "id_hyperbaric_chamber", referencedColumnName = "id_hyperbaric_chamber", nullable = false)
    private HyperbaricchambersEntity hyperbaricchambersByIdHyperbaricChamber;
    @ManyToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id_address", nullable = false)
    private AddressesEntity addressesByIdAddress;
    @OneToMany(mappedBy = "diveSitesByIdDiveSite")
    private List<EventsEntity> eventsByIdDiveSite;

    public int getIdDiveSite() {
        return idDiveSite;
    }

    public void setIdDiveSite(int idDiveSite) {
        this.idDiveSite = idDiveSite;
    }

    public String getDiveSiteLabel() {
        return diveSiteLabel;
    }

    public void setDiveSiteLabel(String diveSiteLabel) {
        this.diveSiteLabel = diveSiteLabel;
    }

    public HyperbaricchambersEntity getIdHyperbaricChamber() {
        return hyperbaricchambersByIdHyperbaricChamber;
    }

    public void setIdHyperbaricChamber(HyperbaricchambersEntity hyperbaricchambersByIdHyperbaricChamber) {
        this.hyperbaricchambersByIdHyperbaricChamber = hyperbaricchambersByIdHyperbaricChamber;
    }

    public AddressesEntity getIdAddress() {
        return addressesByIdAddress;
    }

    public void setIdAddress(AddressesEntity addressesByIdAddress) {
        this.addressesByIdAddress = addressesByIdAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiveSitesEntity that = (DiveSitesEntity) o;

        if (idDiveSite != that.idDiveSite) return false;
        //if (hyperbaricchambersByIdHyperbaricChamber != that.hyperbaricchambersByIdHyperbaricChamber) return false;
        //if (addressesByIdAddress != that.addressesByIdAddress) return false;
        //if (diveSiteLabel != null ? !diveSiteLabel.equals(that.diveSiteLabel) : that.diveSiteLabel != null)
            //return false;

        return true;
    }

    @Transient
    public List<DiveSiteCharacteristicsEntity> listOfDiveSiteCharacteristics;

    @Transient
    public List<DiveSiteCharacteristicsEntity> getListOfDiveSiteCharacteristics(){
        if(this.listOfDiveSiteCharacteristics == null)
            DiveSitesBean.initListOfDiveSiteCharacteristics(this);
        return this.listOfDiveSiteCharacteristics;
    }

    @Override
    public int hashCode() {
        int result = idDiveSite;
        result = 31 * result + (diveSiteLabel != null ? diveSiteLabel.hashCode() : 0);
        return result;
    }

    public List<DiveSiteCharacteristicsEntity> getDiveSiteCharacteristicsByIdDiveSite() {
        return diveSiteCharacteristicsByIdDiveSite;
    }

    public void setDiveSiteCharacteristicsByIdDiveSite(List<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdDiveSite) {
        this.diveSiteCharacteristicsByIdDiveSite = diveSiteCharacteristicsByIdDiveSite;
    }


    public HyperbaricchambersEntity getHyperbaricchambersByIdHyperbaricChamber() {
        return hyperbaricchambersByIdHyperbaricChamber;
    }

    public void setHyperbaricchambersByIdHyperbaricChamber(HyperbaricchambersEntity hyperbaricchambersByIdHyperbaricChamber) {
        this.hyperbaricchambersByIdHyperbaricChamber = hyperbaricchambersByIdHyperbaricChamber;
    }

    public AddressesEntity getAddressesByIdAddress() {
        return addressesByIdAddress;
    }

    public void setAddressesByIdAddress(AddressesEntity addressesByIdAddress) {
        this.addressesByIdAddress = addressesByIdAddress;
    }

    public List<EventsEntity> getEventsByIdDiveSite() {
        return eventsByIdDiveSite;
    }

    public void setEventsByIdDiveSite(List<EventsEntity> eventsByIdDiveSite) {
        this.eventsByIdDiveSite = eventsByIdDiveSite;
    }

}
