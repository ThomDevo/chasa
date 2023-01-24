package com.example.chasa.entities;

import com.example.chasa.enums.CertificateType;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "medical_certificates", schema = "chasa")
public class MedicalCertificatesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_medical_certificate", nullable = false)
    private int idMedicalCertificate;
    @Basic
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;
    @Basic
    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;
    @Basic
    @Column(name = "certificate_type", nullable = false)
    private CertificateType certificateType;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private UsersEntity usersByIdUser;

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
        if (usersByIdUser != that.usersByIdUser) return false;
        if (issueDate != null ? !issueDate.equals(that.issueDate) : that.issueDate != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;
        if (certificateType != null ? !certificateType.equals(that.certificateType) : that.certificateType != null)
            return false;

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
