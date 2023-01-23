package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "hyperbaricchambers", schema = "chasa", catalog = "")
public class HyperbaricchambersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_hyperbaric_chamber", nullable = false)
    private int idHyperbaricChamber;
    @Basic
    @Column(name = "hyperbaric_chamber_label", nullable = false, length = 255)
    private String hyperbaricChamberLabel;
    @Basic
    @Column(name = "hyperbaric_chamber_phone", nullable = true, length = 255)
    private String hyperbaricChamberPhone;
    @Basic
    @Column(name = "id_adress", nullable = false)
    private int idAdress;
    @OneToMany(mappedBy = "hyperbaricchambersByIdHyperbaricChamber")
    private Collection<DiveSitesEntity> diveSitesByIdHyperbaricChamber;
    @ManyToOne
    @JoinColumn(name = "id_adress", referencedColumnName = "id_address", nullable = false)
    private AddressesEntity addressesByIdAdress;

    public int getIdHyperbaricChamber() {
        return idHyperbaricChamber;
    }

    public void setIdHyperbaricChamber(int idHyperbaricChamber) {
        this.idHyperbaricChamber = idHyperbaricChamber;
    }

    public String getHyperbaricChamberLabel() {
        return hyperbaricChamberLabel;
    }

    public void setHyperbaricChamberLabel(String hyperbaricChamberLabel) {
        this.hyperbaricChamberLabel = hyperbaricChamberLabel;
    }

    public String getHyperbaricChamberPhone() {
        return hyperbaricChamberPhone;
    }

    public void setHyperbaricChamberPhone(String hyperbaricChamberPhone) {
        this.hyperbaricChamberPhone = hyperbaricChamberPhone;
    }

    public int getIdAdress() {
        return idAdress;
    }

    public void setIdAdress(int idAdress) {
        this.idAdress = idAdress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HyperbaricchambersEntity that = (HyperbaricchambersEntity) o;

        if (idHyperbaricChamber != that.idHyperbaricChamber) return false;
        if (idAdress != that.idAdress) return false;
        if (hyperbaricChamberLabel != null ? !hyperbaricChamberLabel.equals(that.hyperbaricChamberLabel) : that.hyperbaricChamberLabel != null)
            return false;
        if (hyperbaricChamberPhone != null ? !hyperbaricChamberPhone.equals(that.hyperbaricChamberPhone) : that.hyperbaricChamberPhone != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idHyperbaricChamber;
        result = 31 * result + (hyperbaricChamberLabel != null ? hyperbaricChamberLabel.hashCode() : 0);
        result = 31 * result + (hyperbaricChamberPhone != null ? hyperbaricChamberPhone.hashCode() : 0);
        result = 31 * result + idAdress;
        return result;
    }

    public Collection<DiveSitesEntity> getDiveSitesByIdHyperbaricChamber() {
        return diveSitesByIdHyperbaricChamber;
    }

    public void setDiveSitesByIdHyperbaricChamber(List<DiveSitesEntity> diveSitesByIdHyperbaricChamber) {
        this.diveSitesByIdHyperbaricChamber = diveSitesByIdHyperbaricChamber;
    }

    public void setDiveSitesByIdHyperbaricChamber(Collection<DiveSitesEntity> diveSitesByIdHyperbaricChamber) {
        this.diveSitesByIdHyperbaricChamber = diveSitesByIdHyperbaricChamber;
    }

    public AddressesEntity getAddressesByIdAdress() {
        return addressesByIdAdress;
    }

    public void setAddressesByIdAdress(AddressesEntity addressesByIdAdress) {
        this.addressesByIdAdress = addressesByIdAdress;
    }
}
