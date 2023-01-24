package com.example.chasa.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "license_users", schema = "chasa")
public class LicenseUsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_license_user", nullable = false)
    private int idLicenseUser;
    @Basic
    @Column(name = "licensed_date", nullable = false)
    private Date licensedDate;
    @ManyToOne
    @JoinColumn(name = "id_license", referencedColumnName = "id_license", nullable = false)
    private LicensesEntity licensesByIdLicense;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;

    public int getIdLicenseUser() {
        return idLicenseUser;
    }

    public void setIdLicenseUser(int idLicenseUser) {
        this.idLicenseUser = idLicenseUser;
    }

    public Date getLicensedDate() {
        return licensedDate;
    }

    public void setLicensedDate(Date licensedDate) {
        this.licensedDate = licensedDate;
    }

    public LicensesEntity getIdLicense() {
        return licensesByIdLicense;
    }

    public void setIdLicense(LicensesEntity licensesByIdLicense) {
        this.licensesByIdLicense = licensesByIdLicense;
    }

    public UsersEntity getIdUser() {
        return usersByIdUser;
    }

    public void setIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LicenseUsersEntity that = (LicenseUsersEntity) o;

        if (idLicenseUser != that.idLicenseUser) return false;
        if (licensesByIdLicense != that.licensesByIdLicense) return false;
        if (usersByIdUser != that.usersByIdUser) return false;
        if (licensedDate != null ? !licensedDate.equals(that.licensedDate) : that.licensedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLicenseUser;
        result = 31 * result + (licensedDate != null ? licensedDate.hashCode() : 0);
        return result;
    }

    public LicensesEntity getLicensesByIdLicense() {
        return licensesByIdLicense;
    }

    public void setLicensesByIdLicense(LicensesEntity licensesByIdLicense) {
        this.licensesByIdLicense = licensesByIdLicense;
    }

    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
