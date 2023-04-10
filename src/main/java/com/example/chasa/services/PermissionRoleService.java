package com.example.chasa.services;

import com.example.chasa.entities.RolePermissionEntity;

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
}