package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "roles", schema = "chasa")
public class RolesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @Basic
    @Column(name = "role_label", nullable = false, length = 255)
    private String roleLabel;
    @OneToMany(mappedBy = "rolesByIdRole")
    private List<RolePermissionEntity> rolePermissionsByIdRole;
    @OneToMany(mappedBy = "rolesByIdRole")
    private List<UsersEntity> usersByIdRole;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public String getRoleLabel() {
        return roleLabel;
    }

    public void setRoleLabel(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesEntity that = (RolesEntity) o;

        if (idRole != that.idRole) return false;
        if (roleLabel != null ? !roleLabel.equals(that.roleLabel) : that.roleLabel != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRole;
        result = 31 * result + (roleLabel != null ? roleLabel.hashCode() : 0);
        return result;
    }

    public List<RolePermissionEntity> getRolePermissionsByIdRole() {
        return rolePermissionsByIdRole;
    }

    public void setRolePermissionsByIdRole(List<RolePermissionEntity> rolePermissionsByIdRole) {
        this.rolePermissionsByIdRole = rolePermissionsByIdRole;
    }

    public List<UsersEntity> getUsersByIdRole() {
        return usersByIdRole;
    }

    public void setUsersByIdRole(List<UsersEntity> usersByIdRole) {
        this.usersByIdRole = usersByIdRole;
    }
}
