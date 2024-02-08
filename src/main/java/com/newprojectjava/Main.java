package com.newprojectjava;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.newprojectjava.entities.CategoryEntity;
import com.newprojectjava.utlis.HibernateUtil;

public class Main {
    public static void main(String[] args) {
       var sf = HibernateUtil.getSessionFactory();
        
        try(Session session = sf.openSession())
        {
            session.beginTransaction();
            
            CategoryEntity category = new CategoryEntity();
            category.setName("Одяг");
            category.setImage("1.jpg");
            
            session.save(category);
            session.getTransaction().commit();
        }
        sf.close();
    }
}