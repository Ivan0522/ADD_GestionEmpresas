package org.example;

import org.example.entidades.Empresa;
import org.hibernate.SessionFactory;

public class EmpresaRepository extends BaseRepository<Empresa, Integer> {
    public EmpresaRepository(SessionFactory sessionFactory) {
        super(Empresa.class, sessionFactory);
    }
}


