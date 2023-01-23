package com.example.chasa.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "medical_certificates", schema = "chasa", catalog = "")
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
    private Object certificateType;
    @Basic
    @Column(name = "id_user", nullable = false)
    private int idUser;
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

    public Object getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Object certificateType) {
        this.certificateType = certificateType;
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

        MedicalCertificatesEntity that = (MedicalCertificatesEntity) o;

        if (idMedicalCertificate != that.idMedicalCertificate) return false;
        if (idUser != that.idUser) return false;
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
        result = 31 * result + idUser;
        return result;
    }

    public UsersEntity getUsersByIdUser() {
        return usersByIdUser;
    }

    public void setUsersByIdUser(UsersEntity usersByIdUser) {
        this.usersByIdUser = usersByIdUser;
    }
}
