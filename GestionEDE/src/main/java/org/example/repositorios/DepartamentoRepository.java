package org.example.repositorios;

import org.example.entidades.Departamento;
import org.hibernate.SessionFactory;

/**
 * Repositorio específico para gestionar las operaciones CRUD de la entidad Departamento.
 */
public class DepartamentoRepository extends BaseRepository<Departamento, Integer> {

    /**
     * Constructor que inicializa el repositorio con la fábrica de sesiones de Hibernate.
     * @param sessionFactory Fábrica de sesiones utilizada para las operaciones de base de datos.
     */
    public DepartamentoRepository(SessionFactory sessionFactory) {
        super(Departamento.class, sessionFactory);
    }
}

