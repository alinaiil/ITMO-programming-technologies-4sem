package kz.alinaiil.kotiki.data.dao;

import jakarta.persistence.Query;
import kz.alinaiil.kotiki.data.models.Breed;
import kz.alinaiil.kotiki.data.models.Colour;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.utils.SessionFactoryInstance;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class KittyDao {
    public void add(Kitty kitty) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(kitty);
        transaction.commit();
        session.close();
    }

    public void update(Kitty kitty) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(kitty);
        transaction.commit();
        session.close();
    }

    public void remove(Kitty kitty) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(kitty);
        transaction.commit();
        session.close();
    }

    public Kitty getById(int id) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Kitty kitty = session.get(Kitty.class, id);
        session.close();
        return kitty;
    }

    public List<Kitty> getAll() {
        Session session = SessionFactoryInstance.getInstance().openSession();
        List<Kitty> kitties = (List<Kitty>) session.createQuery("FROM Kitty").list();
        session.close();
        return kitties;
    }

    public List<Kitty> getAllFriends(int id) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Query query = session.createQuery("SELECT k.friends FROM Kitty k WHERE k.id=:id");
        query.setParameter("id", id);
        List<Kitty> friends = (List<Kitty>) query.getResultList();
        session.close();
        return friends;
    }

    public List<Kitty> getByBreed(Breed breed) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Query query = session.createQuery("FROM Kitty k WHERE k.breed=:breed");
        query.setParameter("breed", breed);
        List<Kitty> kitties = (List<Kitty>) query.getResultList();
        session.close();
        return kitties;
    }

    public List<Kitty> getByColour(Colour colour) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Query query = session.createQuery("FROM Kitty k WHERE k.colour=:colour");
        query.setParameter("colour", colour);
        List<Kitty> kitties = (List<Kitty>) query.getResultList();
        session.close();
        return kitties;
    }
}
