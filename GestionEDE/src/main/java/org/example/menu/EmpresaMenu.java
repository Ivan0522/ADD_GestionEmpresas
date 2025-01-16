package org.example.menu;

import org.example.entidades.Empresa;
import org.example.repositorios.EmpresaRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Clase que implementa el menú para gestionar empresas, incluyendo las opciones de crear,
 * leer, actualizar, eliminar y listar departamentos junto con el conteo de empleados.
 */
public class EmpresaMenu extends Menu<Empresa, Integer> {

    /**
     * Repositorio para gestionar las operaciones de la entidad Empresa.
     */
    private final EmpresaRepository repository;

    /**
     * Fábrica de sesiones de Hibernate para realizar consultas personalizadas.
     */
    private final SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el menú con el repositorio y la fábrica de sesiones.
     *
     * @param repository     Repositorio de empresas.
     * @param sessionFactory Fábrica de sesiones de Hibernate.
     */
    public EmpresaMenu(EmpresaRepository repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Permite crear una nueva empresa.
     */
    @Override
    public void crear() {
        System.out.print("• Ingrese el nombre de la empresa: ");
        String nombre = sc.nextLine();
        System.out.print("• Ingrese la industria, ¿Qué hace la empresa?: ");
        String industria = sc.nextLine();

        Empresa empresa = new Empresa(nombre, industria);
        repository.crear(empresa);
        System.out.println(">> Empresa creada exitosamente.");
    }

    /**
     * Permite leer la información de una empresa por su ID.
     */
    @Override
    public void leer() {
        System.out.print("• Ingrese la ID de la empresa: ");
        int id = sc.nextInt();
        sc.nextLine();

        repository.leer(id).ifPresentOrElse(
                empresa -> System.out.println(">> Empresa encontrada: " + empresa.getNombre() +
                        ", Industria: " + empresa.getIndustria()),
                () -> System.out.println("xxx Empresa no encontrada xxx")
        );
    }

    /**
     * Permite actualizar la información de una empresa.
     */
    @Override
    public void actualizar() {
        System.out.print("• Ingrese la ID de la empresa a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        repository.leer(id).ifPresentOrElse(empresa -> {
            System.out.print("• Ingrese el nuevo nombre de la empresa: ");
            empresa.setNombre(sc.nextLine());
            System.out.print("• Ingrese la nueva industria, ¿Qué hace la empresa?: ");
            empresa.setIndustria(sc.nextLine());
            repository.actualizar(empresa);
            System.out.println(">> Empresa actualizada exitosamente.");
        }, () -> System.out.println("xxx Empresa no encontrada xxx"));
    }

    /**
     * Permite eliminar una empresa por su ID.
     */
    @Override
    public void borrar() {
        System.out.print("• Ingrese la ID de la empresa a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();
        repository.borrar(id);
        System.out.println(">> Empresa eliminada exitosamente.");
    }

    /**
     * Lista los departamentos de una empresa junto con el número de empleados en cada uno.
     */
    public void listaDepartamentosEmpleados() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("• Ingrese la ID de la empresa: ");
            int companyId = sc.nextInt();
            sc.nextLine(); // Consumir salto de línea

            // Consulta HQL para obtener el nombre de la empresa
            Empresa empresa = session.createQuery(
                            "SELECT e FROM Empresa e WHERE e.id = :id", Empresa.class)
                    .setParameter("id", companyId)
                    .uniqueResult();

            // Realizar la consulta con HQL para obtener departamentos y conteo de empleados
            List<Object[]> resultados = session.createQuery(
                            "SELECT d.nombre, COUNT(e) " +
                                    "FROM Departamento d " +
                                    "LEFT JOIN d.empleados e " +
                                    "WHERE d.empresa.id = :empresaId " +
                                    "GROUP BY d.id", Object[].class)
                    .setParameter("empresaId", companyId)
                    .getResultList();

            if (resultados.isEmpty()) {
                System.out.println("xxx La empresa no tiene departamentos registrados o no existe xxx");
            } else {
                System.out.println(">> Departamentos de la empresa " + empresa.getNombre() + " con ID " + companyId + ":");
                resultados.forEach(resultado -> {
                    String nombreDepartamento = (String) resultado[0];
                    Long numeroEmpleados = (Long) resultado[1]; // El conteo se devuelve como Long
                    System.out.println("- " + nombreDepartamento + " (Número de empleados: " + numeroEmpleados + ")");
                });
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            // Log de error mejorado para entender la causa de la excepción
            System.err.println("xxx Error al listar los departamentos de una empresa: " + e.getMessage());
        }
    }

    /**
     * Muestra el menú principal de opciones para gestionar empresas.
     */
    @Override
    public void mostrarMenu() {
        while (true) {
            System.out.println("\n----------------------------------------------------");
            System.out.println("------------------- Menú Empresa -------------------");
            System.out.println("----------------------------------------------------");
            System.out.println("|| 1. Crear                                       ||");
            System.out.println("|| 2. Leer                                        ||");
            System.out.println("|| 3. Actualizar                                  ||");
            System.out.println("|| 4. Eliminar                                    ||");
            System.out.println("|| 5. Mostrar departamentos y número de empleados ||");
            System.out.println("|| 6. Salir                                       ||");
            System.out.println("----------------------------------------------------");

            System.out.print(">> Seleccione una opción: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1 -> crear();
                case 2 -> leer();
                case 3 -> actualizar();
                case 4 -> borrar();
                case 5 -> listaDepartamentosEmpleados();
                case 6 -> {
                    System.out.println("--> Saliendo del menú ...");
                    return;
                }
                default -> System.out.println("xxx  Opción inválida  xxx");
            }
        }
    }
}
