package com.example.chasa.services;

import com.example.chasa.entities.RolesEntity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RoleService {
    /**
     * Role request method by roleLabel
     * @param roleLabel
     * @return List of roles
     */
    public RolesEntity findRoleByRoleName(String roleLabel, EntityManager em)
    {
        return em.createNamedQuery("Role.SelectRoleByRoleName", RolesEntity.class)
                .setParameter("roleLabel", roleLabel)
                .getSingleResult();
    }


    /**
     * Find role by id of role
     * @param idRole
     * @param em
     * @return a role
     */
    public RolesEntity findRoleById(int idRole, EntityManager em)
    {
        return em.createNamedQuery("Role.SelectRoleById", RolesEntity.class)
                .setParameter("idRole", idRole)
                .getSingleResult();
    }



    /**
     * Role request method for all
     */
    public List<RolesEntity> findRoleAll (EntityManager em)
    {
        return em.createNamedQuery("Role.SelectRoleAll", RolesEntity.class)
                .getResultList();
    }


    /**
     * Method to filter thz whole list of roles
     * @param researchRole
     * @param em
     * @return List of roles
     */
    public List<RolesEntity> findRoleByFilter (String researchRole,EntityManager em){
        return em.createNamedQuery("Role.SelectAllRoleFilter", RolesEntity.class)
                .setParameter("researchRole", researchRole.toLowerCase())
                .getResultList();
    }


    /**
     * Method to check if role exists
     * @param roleLabel
     * @param em
     * @return boolean
     */
    public boolean isRoleExist(String roleLabel, EntityManager em){
         Query query = em.createNamedQuery("Role.isRoleExist", RolesEntity.class);
         query.setParameter("roleLabel", roleLabel);

         int count = ((Number)query.getSingleResult()).intValue();
         return count > 0;
    }


    /**
     * Method to add a role
     * @param role
     * @param em
     * @return role
     */
    public RolesEntity addRole (RolesEntity role, EntityManager em){
        em.persist(role);
        em.flush();
        return role;
    }


    /**
     * Method to update a role
     * @param role
     * @param em
     * @return role
     */
    public RolesEntity updateRole (RolesEntity role, EntityManager em){
        em.merge(role);
        return role;
    }
}
