package com.example.chasa.beans;

import com.example.chasa.entities.*;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Named
public class UsersBean {
    private UsersEntity user;
}
