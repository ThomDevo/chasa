package com.example.chasa.entities;

import javax.persistence.*;

@Entity
@Table(name = "role_permission", schema = "chasa", catalog = "")
public class RolePermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role_permission", nullable = false)
    private int idRolePermission;
    @Basic
    @Column(name = "id_role", nullable = false)
    private int idRole;
    @Basic
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RolesEntity rolesByIdRole;
    @ManyToOne
    @JoinColumn(name = "id_permission", referencedColumnName = "id_permission", nullable = false)
    private PermissionsEntity permissionsByIdPermission;

    public int getIdRolePermission() {
        return idRolePermission;
    }

    public void setIdRolePermission(int idRolePermission) {
        this.idRolePermission = idRolePermission;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePermissionEntity that = (RolePermissionEntity) o;

        if (idRolePermission != that.idRolePermission) return false;
        if (idRole != that.idRole) return false;
        if (idPermission != that.idPermission) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRolePermission;
        result = 31 * result + idRole;
        result = 31 * result + idPermission;
        return result;
    }

    public RolesEntity getRolesByIdRole() {
        return rolesByIdRole;
    }

    public void setRolesByIdRole(RolesEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    public PermissionsEntity getPermissionsByIdPermission() {
        return permissionsByIdPermission;
    }

    public void setPermissionsByIdPermission(PermissionsEntity permissionsByIdPermission) {
        this.permissionsByIdPermission = permissionsByIdPermission;
    }
}
