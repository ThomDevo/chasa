package com.example.chasa.entities;

import com.example.chasa.enums.CertificateType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.*;

@NamedQueries({
        @NamedQuery(name="MedicalCertificates.isMedicalCertificatesExist", query="SELECT COUNT(mc) FROM MedicalCertificatesEntity mc WHERE mc.issueDate = :issueDate AND mc.expiryDate = :expiryDate AND mc.certificateType = :certificateType AND mc.usersByIdUser.idUser = :usersByIdUser"),
        @NamedQuery(name="MedicalCertificates.SelectAll", query="SELECT mc FROM MedicalCertificatesEntity mc"),
        @NamedQuery(name="MedicalCertificates.SelectById", query="SELECT mc FROM MedicalCertificatesEntity mc WHERE mc.idMedicalCertificate = :idMedicalCertificate"),
        @NamedQuery(name="MedicalCertificates.SelectAllByUser", query="SELECT mc FROM MedicalCertificatesEntity mc WHERE mc.usersByIdUser.idUser = :idUser GROUP BY mc.certificateType ORDER BY mc.expiryDate desc"),
        @NamedQuery(name = "MedicalCertificates.FindMedicalCertificatesByCharacteristic", query = "SELECT mc from MedicalCertificatesEntity mc JOIN UsersEntity u ON (mc.usersByIdUser.idUser = u.idUser)" +
                " where mc.expiryDate = (SELECT MAX(mc2.expiryDate) FROM MedicalCertificatesEntity mc2 WHERE mc.usersByIdUser.idUser = mc2.usersByIdUser.idUser and mc.certificateType = mc2.certificateType) AND" +
                "(((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.userPhone )like concat('%', :researchWord, '%')) or " +
                " (lower(u.email )like concat('%', :researchWord, '%')) or" +
                " (lower(u.roles.roleLabel) like concat('%', :researchWord, '%')) or" +
                " (lower(u.birthDate) like concat('%', :researchWord, '%')) or" +
                " (lower(u.lifrasNumber) like concat('%', :researchWord, '%'))))" +
                "  ORDER BY u.lifrasNumber, mc.certificateType"),
        @NamedQuery(name = "MedicalCertificates.FindMedicalCertificatesByCharacteristicMember", query = "SELECT mc from MedicalCertificatesEntity mc JOIN UsersEntity u ON (mc.usersByIdUser.idUser = u.idUser)" +
                " WHERE mc.expiryDate = (SELECT MAX(mc2.expiryDate) FROM MedicalCertificatesEntity mc2 WHERE mc.usersByIdUser.idUser = mc2.usersByIdUser.idUser and mc.certificateType = mc2.certificateType) AND" +
                "((lower(u.roles.roleLabel) LIKE 'MEMBRE') AND"+
                " ((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.userPhone )like concat('%', :researchWord, '%')) or " +
                " (lower(u.email )like concat('%', :researchWord, '%')) or" +
                " (lower(u.roles.roleLabel) like concat('%', :researchWord, '%')) or" +
                " (lower(u.birthDate) like concat('%', :researchWord, '%')) or" +
                " (lower(u.lifrasNumber) like concat('%', :researchWord, '%'))))" +
                "  ORDER BY u.lifrasNumber, mc.certificateType")
})

@Entity
@Table(name = "medical_certificates", schema = "chasa")
public class MedicalCertificatesEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_medical_certificate", nullable = false)
    private int idMedicalCertificate;

    @NotNull
    @Basic
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @NotNull
    @Basic
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @NotNull
    @Basic
    @Column(name = "certificate_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificateType certificateType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;

    /*---Getters and setters---*/

    public int getIdMedicalCertificate() {
        return idMedicalCertificate;
    }

    public void setIdMedicalCertificate(int idMedicalCertificate) {
        this.idMedicalCertificate = idMedicalCertificate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public UsersEntity getIdUser() {
        return usersByIdUser;
    }

    public void setIdUser(UsersEntity idUser) {
        this.usersByIdUser = usersByIdUser;
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

        MedicalCertificatesEntity that = (MedicalCertificatesEntity) o;

        if (idMedicalCertificate != that.idMedicalCertificate) return false;
        //if (usersByIdUser != that.usersByIdUser) return false;
        //if (issueDate != null ? !issueDate.equals(that.issueDate) : that.issueDate != null) return false;
        //if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        //if (certificateType != null ? !certificateType.equals(that.certificateType) : that.certificateType != null)
            //return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idMedicalCertificate;
        result = 31 * result + (issueDate != null ? issueDate.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        result = 31 * result + (certificateType != null ? certificateType.hashCode() : 0);
        return result;
    }

}
