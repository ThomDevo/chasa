package com.example.chasa.entities;

import com.example.chasa.beans.ConnectionBean;
import com.example.chasa.enums.Sex;
import org.hibernate.validator.constraints.Range;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Collection;
import java.util.*;

@NamedQueries(value = {
        @NamedQuery(name = "User.SelectUser", query = "SELECT u FROM UsersEntity u WHERE u.lifrasNumber = :lifrasNumber AND u.userStatus = TRUE"),
        @NamedQuery(name = "User.IsUserExist", query = "SELECT COUNT(u) FROM UsersEntity u WHERE u.lifrasNumber = :lifrasNumber"),
        @NamedQuery(name = "User.SelectAll", query = "SELECT u FROM UsersEntity u"),
        @NamedQuery(name = "User.SelectAllMembers", query = "SELECT u FROM UsersEntity u WHERE ((upper(u.roles.roleLabel) LIKE 'MEMBRE'))"),
        @NamedQuery(name = "User.findUserById", query = "SELECT u FROM UsersEntity u WHERE u.idUser = :idUser"),
        @NamedQuery(name = "User.findUserAddressById", query = "SELECT u FROM UsersEntity u JOIN AddressesEntity a ON (u.addresses.idAddress = a.idAddress) WHERE u.idUser = :idUser"),
        @NamedQuery(name = "User.FindUserByCharacteristic", query = "SELECT u from UsersEntity u " +
                " where ((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.userPhone )like concat('%', :researchWord, '%')) or " +
                " (lower(u.email )like concat('%', :researchWord, '%')) or" +
                " (lower(u.roles.roleLabel) like concat('%', :researchWord, '%')) or" +
                " (lower(u.birthDate) like concat('%', :researchWord, '%')) or" +
                " (lower(u.lifrasNumber) like concat('%', :researchWord, '%')))"),
        @NamedQuery(name = "User.FindMemberByCharacteristic", query ="SELECT u from UsersEntity u " +
                " where ((upper(u.roles.roleLabel) LIKE 'MEMBRE')) AND" +
                "((lower(u.lastName )like concat('%', :researchWord, '%')) or" +
                " (lower(u.firstName )like concat('%', :researchWord, '%')) or " +
                " (lower(u.userPhone )like concat('%', :researchWord, '%')) or " +
                " (lower(u.email )like concat('%', :researchWord, '%')) or"+
                " (lower(u.roles.roleLabel) like concat('%', :researchWord, '%')) or" +
                " (lower(u.birthDate) like concat('%', :researchWord, '%')) or" +
                " (lower(u.lifrasNumber) like concat('%', :researchWord, '%')))"),

})

@Entity
@Table(name = "users", schema = "chasa")
public class UsersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_user", nullable = false)
    private int idUser;

    @Basic
    @NotNull
    @Pattern(regexp = "^[A-Z][A-z ',\\-.-éèçàâêîûôù]{1,255}$")
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Basic
    @NotNull
    @Pattern(regexp = "^[A-Z][A-z ',\\-.-éèçàâêîûôù]{1,255}$")
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Basic
    @NotNull
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Basic
    @NotNull
    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Basic
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Basic
    //@Pattern(regexp = "^[0-9+/? ?]{9,17}$")
    @Column(name = "user_phone", nullable = true, length = 17)
    private String userPhone;

    @Basic
    @Range(min=1,max= 99999)
    @NotNull
    @Column(name = "lifras_number", nullable = false, unique = true)
    private int lifrasNumber;

    @Basic
    @NotNull
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Basic
    @NotNull
    @Column(name = "user_status", nullable = false)
    private boolean userStatus = true;

    @OneToMany(mappedBy = "usersByIdUser")
    private List<LicenseUsersEntity> licenseUsersByIdUser;

    @OneToMany(mappedBy = "usersByIdUser")
    private List<MedicalCertificatesEntity> medicalCertificatesByIdUser;

    @OneToMany(mappedBy = "usersByIdUser")
    private List<UserEventsEntity> userEventsByIdUser;

    @ManyToOne ( cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "id_adress", referencedColumnName = "id_address", nullable = false)
    private AddressesEntity addresses;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RolesEntity roles;

    public UsersEntity(AddressesEntity idAddress) {
        this.addresses = idAddress;
    }

    /**
     * BLANK CONSTRUCTOR
     */
    public UsersEntity() {

    }

    /**
     * Getters and setters
     * @return users
     */

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public int getLifrasNumber() {
        return lifrasNumber;
    }

    public void setLifrasNumber(int lifrasNumber) {
        this.lifrasNumber = lifrasNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public AddressesEntity getIdAdress() {
        return addresses;
    }

    public void setIdAdress(AddressesEntity idAdress) {
        this.addresses = idAdress;
    }

    public RolesEntity getroles() {
        return roles;
    }

    public void setroles(RolesEntity roles) {
        this.roles = roles;
    }

    public List<LicenseUsersEntity> getLicenseUsersByIdUser() {
        return licenseUsersByIdUser;
    }

    public void setLicenseUsersByIdUser(List<LicenseUsersEntity> licenseUsersByIdUser) {
        this.licenseUsersByIdUser = licenseUsersByIdUser;
    }

    public List<MedicalCertificatesEntity> getMedicalCertificatesByIdUser() {
        return medicalCertificatesByIdUser;
    }

    public void setMedicalCertificatesByIdUser(List<MedicalCertificatesEntity> medicalCertificatesByIdUser) {
        this.medicalCertificatesByIdUser = medicalCertificatesByIdUser;
    }

    public List<UserEventsEntity> getUserEventsByIdUser() {
        return userEventsByIdUser;
    }

    public void setUserEventsByIdUser(List<UserEventsEntity> userEventsByIdUser) {
        this.userEventsByIdUser = userEventsByIdUser;
    }

    public AddressesEntity getAddresses() {
        return addresses;
    }

    public void setAddresses(AddressesEntity addresses) {
        this.addresses = addresses;
    }

    public RolesEntity getRoles() {
        return roles;
    }

    public void setRoles(RolesEntity roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (idUser != that.idUser) return false;
        //if (lifrasNumber != that.lifrasNumber) return false;
        //if (userStatus != that.userStatus) return false;
        //if (addresses != that.addresses) return false;
        //if (roles != that.roles) return false;
        //if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        //if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        //if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        //if (sex != null ? !sex.equals(that.sex) : that.sex != null) return false;
        //if (email != null ? !email.equals(that.email) : that.email != null) return false;
        //if (userPhone != null ? !userPhone.equals(that.userPhone) : that.userPhone != null) return false;
        //if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userPhone != null ? userPhone.hashCode() : 0);
        result = 31 * result + lifrasNumber;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    /**
     * Method to associate the list of RolePermissions with the User
     */
    @Transient
    public List<RolePermissionEntity> listOfPermissions;

    @Transient
    public List<RolePermissionEntity> getListOfRolePermissions() {
        if (this.listOfPermissions == null)
            ConnectionBean.initListPermissionRole(this);
        return this.listOfPermissions;
    }

    /**
     * Method to verify user access
     * @param permissionName
     * @return boolean
     */
    @Transient
    public boolean verifyPermission(String permissionName)
    {
        return this.getListOfRolePermissions().stream()
                .filter(pe -> pe.getIdPermission().getPermissionLabel().equals(permissionName))
                .findFirst()
                .orElse(null) != null;
    }

    @Transient
    private List<LicenseUsersEntity> listOfLicenseUser;

    @Transient
    public List<LicenseUsersEntity> getListOfLicenseUsers() {
        if(this.listOfLicenseUser==null)
            listOfLicenseUser = new ArrayList<>();
            //ConnectionBean.initListOfLicenseUsers(this);*/
            return this.listOfLicenseUser;

    }


}
