package org.example.repositorios;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.Optional;

/**
 * Clase abstracta que proporciona las operaciones CRUD (Crear, Leer, Actualizar, Borrar) básicas
 * para cualquier entidad en el contexto de Hibernate.
 *
 * @param <T>  El tipo de entidad que manejará el repositorio.
 * @param <ID> El tipo del identificador único de la entidad.
 */
public abstract class BaseRepository<T, ID> {

    /**
     * Clase de la entidad gestionada por este repositorio.
     */
    private final Class<T> type;

    /**
     * Fábrica de sesiones de Hibernate utilizada para gestionar las operaciones de base de datos.
     */
    private final SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el repositorio con el tipo de entidad y la fábrica de sesiones.
     * @param type           Clase de la entidad gestionada.
     * @param sessionFactory Fábrica de sesiones de Hibernate.
     */
    public BaseRepository(Class<T> type, SessionFactory sessionFactory) {
        this.type = type;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Crea una nueva entidad en la base de datos.
     * @param entity Entidad a persistir.
     */
    public void crear(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity); // Persistir la entidad en la base de datos.
            session.getTransaction().commit(); // Confirmar la transacción.
        }
    }

    /**
     * Lee una entidad desde la base de datos por su identificador.
     * @param id Identificador de la entidad.
     * @return Un Optional que contiene la entidad si existe, o vacío en caso contrario.
     */
    public Optional<T> leer(ID id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(type, id)); // Buscar la entidad por su identificador.
        }
    }

    /**
     * Actualiza una entidad existente en la base de datos.
     * @param entity Entidad a actualizar.
     */
    public void actualizar(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity); // Actualizar la entidad en la base de datos.
            session.getTransaction().commit(); // Confirmar la transacción.
        }
    }

    /**
     * Borra una entidad de la base de datos por su identificador.
     * @param id Identificador de la entidad a borrar.
     */
    public void borrar(ID id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            T entity = session.get(type, id); // Buscar la entidad por su identificador.
            if (entity != null) {
                session.remove(entity); // Eliminar la entidad si existe.
            }
            session.getTransaction().commit(); // Confirmar la transacción.
        }
    }
}


