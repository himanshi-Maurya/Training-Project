package com.example.demo.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("org.hibernate.tutorial.jpa");
        Alien1 alien1 = new Alien1();

        alien1.setAname("name");

        Alien2 alien2 = new Alien2();

        alien2.setBname("name2");


        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();


        entityManager.persist(alien1);
        entityManager.persist(alien2);
        entityManager.getTransaction().commit();

        entityManagerFactory.close();
    }
}
