package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@NamedQueries(value = {
        @NamedQuery(name = "License.SelectAll", query = "SELECT l from LicensesEntity l "),
        @NamedQuery(name = "License.SelectById", query = "SELECT l from LicensesEntity l WHERE l.idLicense = :idLicense"),
})

@Entity
@Table(name = "licenses", schema = "chasa")
public class LicensesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_license", nullable = false)
    private int idLicense;
    @Basic
    @Column(name = "license_label", nullable = false, length = 255)
    private String licenseLabel;
    @OneToMany(mappedBy = "licensesByIdLicense")
    private List<EventsEntity> eventsByIdLicense;
    @OneToMany(mappedBy = "licensesByIdLicense")
    private List<LicenseUsersEntity> licenseUsersByIdLicense;

    public int getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(int idLicense) {
        this.idLicense = idLicense;
    }

    public String getLicenseLabel() {
        return licenseLabel;
    }

    public void setLicenseLabel(String licenseLabel) {
        this.licenseLabel = licenseLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LicensesEntity that = (LicensesEntity) o;

        if (idLicense != that.idLicense) return false;
        //if (licenseLabel != null ? !licenseLabel.equals(that.licenseLabel) : that.licenseLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLicense;
        result = 31 * result + (licenseLabel != null ? licenseLabel.hashCode() : 0);
        return result;
    }

    public List<EventsEntity> getEventsByIdLicense() {
        return eventsByIdLicense;
    }

    public void setEventsByIdLicense(List<EventsEntity> eventsByIdLicense) {
        this.eventsByIdLicense = eventsByIdLicense;
    }

    public List<LicenseUsersEntity> getLicenseUsersByIdLicense() {
        return licenseUsersByIdLicense;
    }

    public void setLicenseUsersByIdLicense(List<LicenseUsersEntity> licenseUsersByIdLicense) {
        this.licenseUsersByIdLicense = licenseUsersByIdLicense;
    }


}
