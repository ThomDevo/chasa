package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "licenses", schema = "chasa", catalog = "")
public class LicensesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_license", nullable = false)
    private int idLicense;
    @Basic
    @Column(name = "license_label", nullable = false, length = 255)
    private String licenseLabel;
    @OneToMany(mappedBy = "licensesByIdLicense")
    private Collection<EventsEntity> eventsByIdLicense;
    @OneToMany(mappedBy = "licensesByIdLicense")
    private Collection<LicenseUsersEntity> licenseUsersByIdLicense;

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
        if (licenseLabel != null ? !licenseLabel.equals(that.licenseLabel) : that.licenseLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLicense;
        result = 31 * result + (licenseLabel != null ? licenseLabel.hashCode() : 0);
        return result;
    }

    public Collection<EventsEntity> getEventsByIdLicense() {
        return eventsByIdLicense;
    }

    public void setEventsByIdLicense(List<EventsEntity> eventsByIdLicense) {
        this.eventsByIdLicense = eventsByIdLicense;
    }

    public void setEventsByIdLicense(Collection<EventsEntity> eventsByIdLicense) {
        this.eventsByIdLicense = eventsByIdLicense;
    }

    public Collection<LicenseUsersEntity> getLicenseUsersByIdLicense() {
        return licenseUsersByIdLicense;
    }

    public void setLicenseUsersByIdLicense(List<LicenseUsersEntity> licenseUsersByIdLicense) {
        this.licenseUsersByIdLicense = licenseUsersByIdLicense;
    }

    public void setLicenseUsersByIdLicense(Collection<LicenseUsersEntity> licenseUsersByIdLicense) {
        this.licenseUsersByIdLicense = licenseUsersByIdLicense;
    }
}
