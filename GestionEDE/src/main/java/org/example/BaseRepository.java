package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public abstract class BaseRepository<T, ID> {
    private final Class<T> type;
    private final SessionFactory sessionFactory;

    public BaseRepository(Class<T> type, SessionFactory sessionFactory) {
        this.type = type;
        this.sessionFactory = sessionFactory;
    }

    public void create(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
    }

    public Optional<T> read(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(type, id));
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();
        }
    }

    public void delete(ID id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = session.get(type, id);
            if (entity != null) {
                session.remove(entity);
            }
            session.getTransaction().commit();
        }
    }
}

