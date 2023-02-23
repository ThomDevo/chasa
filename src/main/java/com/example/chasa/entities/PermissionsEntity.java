package com.example.chasa.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@NamedQueries(value = {
        @NamedQuery(name = "Permission.selectPermissionAll", query = "SELECT p from PermissionsEntity p order by p.idPermission desc"),
        @NamedQuery(name = "Permission.selectPermissionById", query = "SELECT p from PermissionsEntity p where p.idPermission = :idPermission"),
        @NamedQuery(name = "Permission.selectPermissionByLabel", query = "SELECT p from PermissionsEntity p where p.permissionLabel = :permissionLabel")
})

@Entity
@Table(name = "permissions", schema = "chasa")
public class PermissionsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_permission", nullable = false)
    private int idPermission;
    @Basic
    @Column(name = "permission_label", nullable = false, length = 255)
    private String permissionLabel;
    @Basic
    @Column(name = "permission_description", nullable = true, length = 255)
    private String permissionDescription;
    @OneToMany(mappedBy = "permissionsByIdPermission")
    private List<RolePermissionEntity> rolePermissionsByIdPermission;

    public int getIdPermission() {
        return idPermission;
    }

    public void setIdPermission(int idPermission) {
        this.idPermission = idPermission;
    }

    public String getPermissionLabel() {
        return permissionLabel;
    }

    public void setPermissionLabel(String permissionLabel) {
        this.permissionLabel = permissionLabel;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionsEntity that = (PermissionsEntity) o;

        if (idPermission != that.idPermission) return false;
        if (permissionLabel != null ? !permissionLabel.equals(that.permissionLabel) : that.permissionLabel != null)
            return false;
        if (permissionDescription != null ? !permissionDescription.equals(that.permissionDescription) : that.permissionDescription != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPermission;
        result = 31 * result + (permissionLabel != null ? permissionLabel.hashCode() : 0);
        result = 31 * result + (permissionDescription != null ? permissionDescription.hashCode() : 0);
        return result;
    }

    public List<RolePermissionEntity> getRolePermissionsByIdPermission() {
        return rolePermissionsByIdPermission;
    }

    public void setRolePermissionsByIdPermission(List<RolePermissionEntity> rolePermissionsByIdPermission) {
        this.rolePermissionsByIdPermission = rolePermissionsByIdPermission;
    }

}
