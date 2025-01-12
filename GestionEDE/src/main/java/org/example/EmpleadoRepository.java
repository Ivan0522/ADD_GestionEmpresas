package org.example;

import org.example.entidades.Empleado;
import org.hibernate.SessionFactory;

public class EmpleadoRepository extends BaseRepository<Empleado, String> {
    public EmpleadoRepository(SessionFactory sessionFactory) {
        super(Empleado.class, sessionFactory);
    }
}

