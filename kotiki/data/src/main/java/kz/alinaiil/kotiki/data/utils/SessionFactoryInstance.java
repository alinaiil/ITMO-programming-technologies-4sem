package kz.alinaiil.kotiki.data.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionFactoryInstance {
    private static final SessionFactory instance = new Configuration().configure().buildSessionFactory();

    public static SessionFactory getInstance() {
        return instance;
    }

    public static void close() {
        instance.close();
    }
}
