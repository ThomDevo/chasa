package com.example.chasa.entities;

import javax.persistence.*;

@Entity
@Table(name = "role_permission", schema = "chasa")
public class RolePermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role_permission", nullable = false)
    private int idRolePermission;
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

    public RolesEntity getIdRole() {
        return rolesByIdRole;
    }

    public void setIdRole(RolesEntity rolesByIdRole) {
        this.rolesByIdRole = rolesByIdRole;
    }

    public PermissionsEntity getIdPermission() {
        return permissionsByIdPermission;
    }

    public void setIdPermission(PermissionsEntity permissionsByIdPermission) {
        this.permissionsByIdPermission = permissionsByIdPermission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolePermissionEntity that = (RolePermissionEntity) o;

        if (idRolePermission != that.idRolePermission) return false;
        if (rolesByIdRole != that.rolesByIdRole) return false;
        if (permissionsByIdPermission != that.permissionsByIdPermission) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRolePermission;
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
