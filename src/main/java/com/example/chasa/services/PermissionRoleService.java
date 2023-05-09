package com.example.chasa.services;

import com.example.chasa.entities.RolePermissionEntity;
import com.example.chasa.entities.UsersEntity;

import javax.persistence.EntityManager;
import java.util.List;

import static org.apache.log4j.xml.DOMConfigurator.setParameter;

public class PermissionRoleService {

    /**
     * RolePermission request Method by Idrole
     * @param roleId
     * @param em
     * @return
     */
    public List<RolePermissionEntity> findRolePermissionByIdRole(int roleId, EntityManager em) {
        return em.createNamedQuery("RolePermission.SelectListPermissionByIdRole", RolePermissionEntity.class)
                .setParameter("idRole", roleId)
                .getResultList();
    }
    public RolePermissionEntity findRolePermissionByIdRoleAndByIdPermission(int roleId, int idPermission, EntityManager em){
        return em.createNamedQuery("RolePermission.SelectListPermissionByIdRoleANdByIdPermission", RolePermissionEntity.class)
                .setParameter("idRole", roleId)
                .setParameter("idPermission", idPermission)
                .getSingleResult();
    }

    /**
     * Method to have all role permissions
     * @param em
     * @return List of role permissions
     */
    public List<RolePermissionEntity> findAll(EntityManager em) {
        return em.createNamedQuery("RolePermission.SelectAll", RolePermissionEntity.class)
                .getResultList();
    }

    public RolePermissionEntity addRolePermission(RolePermissionEntity rolePermission, EntityManager em){
        em.persist(rolePermission);
        em.flush();
        return rolePermission;
    }

    public RolePermissionEntity updateRolePermission(RolePermissionEntity rolePermission, EntityManager em){
        em.merge(rolePermission);
        return rolePermission;
    }

    public void deleteRolePermission(RolePermissionEntity rolePermission, EntityManager em){
        if(!em.contains(rolePermission))
            rolePermission = em.merge(rolePermission);
        em.remove(rolePermission);
        em.flush();
    }
}
