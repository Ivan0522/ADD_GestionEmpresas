package org.example.repositorios;

import org.example.entidades.Empleado;
import org.hibernate.SessionFactory;

/**
 * Repositorio específico para gestionar las operaciones CRUD de la entidad Empleado.
 */
public class EmpleadoRepository extends BaseRepository<Empleado, String> {

    /**
     * Constructor que inicializa el repositorio con la fábrica de sesiones de Hibernate.
     * @param sessionFactory Fábrica de sesiones utilizada para las operaciones de base de datos.
     */
    public EmpleadoRepository(SessionFactory sessionFactory) {
        super(Empleado.class, sessionFactory);
    }
}
