package com.example.chasa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries(value = {
        @NamedQuery(name = "RolePermission.SelectAll", query = "SELECT pe FROM RolePermissionEntity pe Where (lower(pe.rolesByIdRole.roleLabel  )like concat('%', :researchWord, '%'))  GROUP BY pe.rolesByIdRole"),
        @NamedQuery(name = "RolePermission.SelectListPermissionByIdRole", query = "SELECT pe FROM RolePermissionEntity pe WHERE pe.rolesByIdRole.idRole = :idRole"),
        @NamedQuery(name = "RolePermission.SelectListPermissionByIdRoleANdByIdPermission", query = "SELECT pe FROM RolePermissionEntity pe WHERE pe.rolesByIdRole.idRole = :idRole AND pe.permissionsByIdPermission.idPermission = :idPermission")
})

@Entity
@Table(name = "role_permission", schema = "chasa")
public class RolePermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_role_permission", nullable = false)
    private int idRolePermission;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    private RolesEntity rolesByIdRole;

    @NotNull
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
