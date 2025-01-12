package org.example;

import org.example.entidades.Departamento;
import org.hibernate.SessionFactory;

public class DepartamentoRepository extends BaseRepository<Departamento, Integer> {
    public DepartamentoRepository(SessionFactory sessionFactory) {
        super(Departamento.class, sessionFactory);
    }
}


