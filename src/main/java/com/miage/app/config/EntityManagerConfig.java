package com.miage.app.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerConfig {
    private static EntityManagerFactory em = null; 
 
    public static EntityManagerFactory getEmf() { 
        if(em == null){ 
            em = Persistence.createEntityManagerFactory("projetFSR"); 
        } 
        return em; 
    } 
 
    public static void close(){ 
        if(em!=null){ 
            em.close(); 
            em=null; 
        } 
    } 

}