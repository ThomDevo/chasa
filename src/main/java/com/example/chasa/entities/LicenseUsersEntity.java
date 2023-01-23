package com.example.chasa.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "license_users", schema = "chasa", catalog = "")
public class LicenseUsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_license_user", nullable = false)
    private int idLicenseUser;
    @Basic
    @Column(name = "licensed_date", nullable = false)
    private Date licensedDate;
    @Basic
    @Column(name = "id_license", nullable = false)
    private int idLicense;
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
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

    public int getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(int idLicense) {
        this.idLicense = idLicense;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LicenseUsersEntity that = (LicenseUsersEntity) o;

        if (idLicenseUser != that.idLicenseUser) return false;
        if (idLicense != that.idLicense) return false;
        if (idUser != that.idUser) return false;
        if (licensedDate != null ? !licensedDate.equals(that.licensedDate) : that.licensedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLicenseUser;
        result = 31 * result + (licensedDate != null ? licensedDate.hashCode() : 0);
        result = 31 * result + idLicense;
        result = 31 * result + idUser;
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
