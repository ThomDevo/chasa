package com.example.chasa.services;

import com.example.chasa.entities.PermissionsEntity;
import javax.persistence.EntityManager;
import java.util.List;

public class PermissionService {
    /**
     * Permission request method by roleLabel
     * @param permissionLabel
     * @return List of permissions
     */
    public PermissionsEntity findPermissionByPermissionLabel(String permissionLabel, EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionByLabel", PermissionsEntity.class)
                .setParameter("permissionLabel", permissionLabel)
                .getSingleResult();
    }


    /**
     * Find permission by id of permission
     * @param idPermission
     * @param em
     * @return
     */
    public PermissionsEntity findPermissionById(int idPermission, EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionById", PermissionsEntity.class)
                .setParameter("idPermission", idPermission)
                .getSingleResult();
    }



    /**
     * Role request method for all
     */
    public List<PermissionsEntity> findPermissionAll (EntityManager em)
    {
        return em.createNamedQuery("Permission.selectPermissionAll", PermissionsEntity.class)
                .getResultList();
    }


    /**
     * Method to add a role
     * @param permission
     * @param em
     * @return permission
     */
    public PermissionsEntity addPermission (PermissionsEntity permission, EntityManager em){
        em.persist(permission);
        em.flush();
        return permission;
    }


    /**
     * Method to update a role
     * @param permission
     * @param em
     * @return permission
     */
    public PermissionsEntity updatePermission (PermissionsEntity permission, EntityManager em){
        em.merge(permission);
        return permission;
    }

}
