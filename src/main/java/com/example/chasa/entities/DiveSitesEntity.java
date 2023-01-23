package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dive_sites", schema = "chasa", catalog = "")
public class DiveSitesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_dive_site", nullable = false)
    private int idDiveSite;
    @Basic
    @Column(name = "dive_site_label", nullable = false, length = 255)
    private String diveSiteLabel;
    @Basic
    @Column(name = "id_hyperbaric_chamber", nullable = false)
    private int idHyperbaricChamber;
    @Basic
    @Column(name = "id_address", nullable = false)
    private int idAddress;
    @OneToMany(mappedBy = "diveSitesByIdDiveSite")
    private Collection<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdDiveSite;
    @ManyToOne
    @JoinColumn(name = "id_hyperbaric_chamber", referencedColumnName = "id_hyperbaric_chamber", nullable = false)
    private HyperbaricchambersEntity hyperbaricchambersByIdHyperbaricChamber;
    @ManyToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id_address", nullable = false)
    private AddressesEntity addressesByIdAddress;
    @OneToMany(mappedBy = "diveSitesByIdDiveSite")
    private Collection<EventsEntity> eventsByIdDiveSite;

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

    public int getIdHyperbaricChamber() {
        return idHyperbaricChamber;
    }

    public void setIdHyperbaricChamber(int idHyperbaricChamber) {
        this.idHyperbaricChamber = idHyperbaricChamber;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiveSitesEntity that = (DiveSitesEntity) o;

        if (idDiveSite != that.idDiveSite) return false;
        if (idHyperbaricChamber != that.idHyperbaricChamber) return false;
        if (idAddress != that.idAddress) return false;
        if (diveSiteLabel != null ? !diveSiteLabel.equals(that.diveSiteLabel) : that.diveSiteLabel != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDiveSite;
        result = 31 * result + (diveSiteLabel != null ? diveSiteLabel.hashCode() : 0);
        result = 31 * result + idHyperbaricChamber;
        result = 31 * result + idAddress;
        return result;
    }

    public Collection<DiveSiteCharacteristicsEntity> getDiveSiteCharacteristicsByIdDiveSite() {
        return diveSiteCharacteristicsByIdDiveSite;
    }

    public void setDiveSiteCharacteristicsByIdDiveSite(List<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdDiveSite) {
        this.diveSiteCharacteristicsByIdDiveSite = diveSiteCharacteristicsByIdDiveSite;
    }

    public void setDiveSiteCharacteristicsByIdDiveSite(Collection<DiveSiteCharacteristicsEntity> diveSiteCharacteristicsByIdDiveSite) {
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

    public Collection<EventsEntity> getEventsByIdDiveSite() {
        return eventsByIdDiveSite;
    }

    public void setEventsByIdDiveSite(List<EventsEntity> eventsByIdDiveSite) {
        this.eventsByIdDiveSite = eventsByIdDiveSite;
    }

    public void setEventsByIdDiveSite(Collection<EventsEntity> eventsByIdDiveSite) {
        this.eventsByIdDiveSite = eventsByIdDiveSite;
    }
}
