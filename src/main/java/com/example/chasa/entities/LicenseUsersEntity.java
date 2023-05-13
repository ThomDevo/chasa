package com.example.chasa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.*;

@NamedQueries(value = {
        @NamedQuery(name = "LicenseUser.IsLicenseUserExists", query = "SELECT COUNT(lu) from LicenseUsersEntity lu WHERE (lu.usersByIdUser.idUser = :idUser AND lu.licensesByIdLicense.idLicense = :idLicense)"),
        @NamedQuery(name = "LicenseUser.SelectAll", query = "SELECT lu from LicenseUsersEntity lu "),
        @NamedQuery(name = "LicenseUser.FindLicenseUserByCharacteristic", query = "SELECT lu from LicenseUsersEntity lu JOIN UsersEntity u ON (lu.usersByIdUser.idUser = u.idUser)" +
                " where ((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.userPhone )like concat('%', :researchWord, '%')) or " +
                " (lower(u.email )like concat('%', :researchWord, '%')) or" +
                " (lower(u.roles.roleLabel) like concat('%', :researchWord, '%')) or" +
                " (lower(u.birthDate) like concat('%', :researchWord, '%')) or" +
                " (lower(u.lifrasNumber) like concat('%', :researchWord, '%')))" +
                "  ORDER BY u.lifrasNumber, lu.licensesByIdLicense.idLicense"),
        @NamedQuery(name = "LicenseUser.SelectById", query = "SELECT lu from LicenseUsersEntity lu WHERE lu.idLicenseUser = :idLicenseUser"),
        @NamedQuery(name = "LicenseUser.findUserByRole", query = "SELECT lu from LicenseUsersEntity lu JOIN UsersEntity u ON (lu.usersByIdUser.idUser = u.idUser) WHERE (lower(u.roles.roleLabel) NOT LIKE 'MEMBRE')"),
        @NamedQuery(name = "LicenseUser.findLicenseUserByUser", query = "SELECT lu from LicenseUsersEntity lu JOIN UsersEntity u ON (lu.usersByIdUser.idUser = u.idUser) WHERE u.idUser = :idUser ORDER BY lu.licensedDate"),
        @NamedQuery(name = "LicenseUser.FindLicenseUserByCharacteristicMember", query = "SELECT lu from LicenseUsersEntity lu JOIN UsersEntity u ON (lu.usersByIdUser.idUser = u.idUser)" +
                " WHERE (lower(u.roles.roleLabel) LIKE 'MEMBRE') AND"+
                " ((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.userPhone )like concat('%', :researchWord, '%')) or " +
                " (lower(u.email )like concat('%', :researchWord, '%')) or" +
                " (lower(u.roles.roleLabel) like concat('%', :researchWord, '%')) or" +
                " (lower(u.birthDate) like concat('%', :researchWord, '%')) or" +
                " (lower(u.lifrasNumber) like concat('%', :researchWord, '%')))" +
                "  ORDER BY u.lifrasNumber, lu.licensesByIdLicense.idLicense")
})

@Entity
@Table(name = "license_users", schema = "chasa")
public class LicenseUsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_license_user", nullable = false)
    private int idLicenseUser;

    @NotNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LicenseUsersEntity that = (LicenseUsersEntity) o;

        if (idLicenseUser != that.idLicenseUser) return false;
        //if (licensesByIdLicense != that.licensesByIdLicense) return false;
        //if (usersByIdUser != that.usersByIdUser) return false;
        //if (licensedDate != null ? !licensedDate.equals(that.licensedDate) : that.licensedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLicenseUser;
        result = 31 * result + (licensedDate != null ? licensedDate.hashCode() : 0);
        return result;
    }



}
