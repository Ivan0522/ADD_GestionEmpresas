package org.example.repositorios;

import org.example.entidades.Empresa;
import org.hibernate.SessionFactory;

/**
 * Repositorio específico para gestionar las operaciones CRUD de la entidad Empresa.
 */
public class EmpresaRepository extends BaseRepository<Empresa, Integer> {

    /**
     * Constructor que inicializa el repositorio con la fábrica de sesiones de Hibernate.
     * @param sessionFactory Fábrica de sesiones utilizada para las operaciones de base de datos.
     */
    public EmpresaRepository(SessionFactory sessionFactory) {
        super(Empresa.class, sessionFactory);
    }
}


