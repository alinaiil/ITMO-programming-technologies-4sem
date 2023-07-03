package kz.alinaiil.kotiki.data.dao;

import jakarta.persistence.Query;
import kz.alinaiil.kotiki.data.models.Kitty;
import kz.alinaiil.kotiki.data.models.Owner;
import kz.alinaiil.kotiki.data.utils.SessionFactoryInstance;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OwnerDao {
    public void add(Owner owner) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(owner);
        transaction.commit();
        session.close();
    }

    public void update(Owner owner) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(owner);
        transaction.commit();
        session.close();
    }

    public void remove(Owner owner) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(owner);
        transaction.commit();
        session.close();
    }

    public Owner getById(int id) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Owner owner = session.get(Owner.class, id);
        session.close();
        return owner;
    }

    public List<Owner> getAll() {
        Session session = SessionFactoryInstance.getInstance().openSession();
        List<Owner> owners = session.createQuery("FROM Owner").list();
        session.close();
        return owners;
    }

    public List<Kitty> getAllKitties(int id) {
        Session session = SessionFactoryInstance.getInstance().openSession();
        Query query = session.createQuery("SELECT o.kitties FROM Owner o WHERE o.id=:id");
        query.setParameter("id", id);
        List<Kitty> kitties = (List<Kitty>) query.getResultList();
        session.close();
        return kitties;
    }
}
