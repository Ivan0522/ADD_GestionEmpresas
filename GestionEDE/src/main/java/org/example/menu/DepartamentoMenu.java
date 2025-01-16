/**
 * Paquete que contiene la implementación del menú de opciones para la gestión de departamentos.
 */
package org.example.menu;

import org.example.entidades.Departamento;
import org.example.repositorios.DepartamentoRepository;
import org.example.repositorios.EmpresaRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Clase que implementa el menú para gestionar departamentos, incluyendo las opciones de crear,
 * leer, actualizar, eliminar y listar empleados de un departamento.
 */
public class DepartamentoMenu extends Menu<Departamento, Integer> {

    /**
     * Repositorio para gestionar las operaciones de la entidad Departamento.
     */
    private final DepartamentoRepository departamentoRepository;

    /**
     * Repositorio para gestionar las operaciones de la entidad Empresa.
     */
    private final EmpresaRepository empresaRepository;

    /**
     * Fábrica de sesiones de Hibernate para realizar consultas personalizadas.
     */
    private final SessionFactory sessionFactory;

    /**
     * Constructor que inicializa el menú con los repositorios y la fábrica de sesiones.
     *
     * @param departamentoRepository Repositorio de departamentos.
     * @param empresaRepository      Repositorio de empresas.
     * @param sessionFactory         Fábrica de sesiones de Hibernate.
     */
    public DepartamentoMenu(DepartamentoRepository departamentoRepository, EmpresaRepository empresaRepository, SessionFactory sessionFactory) {
        this.departamentoRepository = departamentoRepository;
        this.empresaRepository = empresaRepository;
        this.sessionFactory = sessionFactory;
    }

    /**
     * Permite crear un nuevo departamento asociado a una empresa existente.
     */
    @Override
    public void crear() {
        System.out.print("• Ingrese el nombre del departamento: ");
        String nombre = sc.nextLine();

        System.out.print("• Ingrese la ID de la empresa asociada: ");
        int empresaId = sc.nextInt();
        sc.nextLine();

        empresaRepository.leer(empresaId).ifPresentOrElse(empresa -> {
            Departamento departamento = new Departamento(nombre, empresa);
            departamentoRepository.crear(departamento);
            System.out.println(">> Departamento creado exitosamente.");
        }, () -> System.out.println("xxx Empresa no encontrada xxx"));
    }

    /**
     * Permite leer la información de un departamento por su ID.
     */
    @Override
    public void leer() {
        System.out.print("• Ingrese la ID del departamento: ");
        int id = sc.nextInt();
        sc.nextLine();

        departamentoRepository.leer(id).ifPresentOrElse(
                departamento ->
                        System.out.println(">> Departamento encontrado: " + departamento.getNombre() +
                                ", Empresa: " + departamento.getEmpresa().getNombre()),
                () -> System.out.println("xxx Departamento no encontrado xxx")
        );
    }

    /**
     * Permite actualizar la información de un departamento.
     */
    @Override
    public void actualizar() {
        System.out.print("• Ingrese la ID del departamento a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();

        departamentoRepository.leer(id).ifPresentOrElse(departamento -> {

            System.out.print("• Ingrese el nuevo nombre del departamento: ");
            departamento.setNombre(sc.nextLine());

            System.out.print("• Ingrese la ID de la nueva empresa asociada: ");
            int empresaId = sc.nextInt();
            sc.nextLine();

            empresaRepository.leer(empresaId).ifPresentOrElse(empresa -> {
                departamento.setEmpresa(empresa);
                departamentoRepository.actualizar(departamento);
                System.out.println(">> Departamento actualizado exitosamente.");
            }, () -> System.out.println("xxx Empresa no encontrada xxx"));
        }, () -> System.out.println("xxx Departamento no encontrado xxx"));
    }

    /**
     * Permite eliminar un departamento por su ID.
     */
    @Override
    public void borrar() {
        System.out.print("• Ingrese la ID del departamento a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();
        departamentoRepository.borrar(id);
        System.out.println(">> Departamento eliminado exitosamente.");
    }

    /**
     * Lista los empleados de un departamento específico.
     */
    // Método para listar los empleados asociados a un departamento específico
    public void listaEmpleadosDepartamento() {
        // Solicitar al usuario el ID del departamento que desea consultar
        System.out.print("• Ingrese la ID del departamento: ");
        int departmentId = sc.nextInt();
        sc.nextLine();

        // Abrir una sesión de Hibernate para interactuar con la base de datos
        try (Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            // Obtener la información del departamento especificado por el usuario
            Departamento departamento = session.createQuery(
                            "SELECT d FROM Departamento d WHERE d.id = :id", Departamento.class)
                    .setParameter("id", departmentId)
                    .uniqueResult();

            // Obtener la lista de empleados asociados al departamento mediante una consulta HQL
            List<Object[]> empleados = session.createQuery(
                            "SELECT e.nombre, e.apellido, e.puesto FROM Empleado e WHERE e.departamento.id = :id", Object[].class)
                    .setParameter("id", departmentId)
                    .getResultList();

            // Verificar si el departamento tiene empleados asignados
            if (empleados.isEmpty()) {
                System.out.println("No hay empleados asignados a este departamento.");
            } else {
                System.out.println("Empleados en el departamento de " + departamento.getNombre() + ":");
                // Iterar y mostrar cada empleado asociado al departamento
                empleados.forEach(empleado -> System.out.println(
                        "- Nombre: " + empleado[0] + ", Apellido: " + empleado[1] + ", Puesto: " + empleado[2]));
            }

            // Confirmar la transacción después de realizar las operaciones
            session.getTransaction().commit();
        } catch (Exception e) {
            // Log de error mejorado para entender la causa de la excepción
            System.err.println("xxx Error al listar empleados del departamento: " + e.getMessage());
        }
    }


    /**
     * Muestra el menú principal de opciones para gestionar departamentos.
     */
    @Override
    public void mostrarMenu() {
        while (true) {
            System.out.println("\n----------------------------------------------------");
            System.out.println("---------------- Menú Departamento -----------------");
            System.out.println("----------------------------------------------------");
            System.out.println("|| 1. Crear                                       ||");
            System.out.println("|| 2. Leer                                        ||");
            System.out.println("|| 3. Actualizar                                  ||");
            System.out.println("|| 4. Eliminar                                    ||");
            System.out.println("|| 5. Consultar empleados de un departamento      ||");
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
                case 5 -> listaEmpleadosDepartamento();
                case 6 -> {
                    System.out.println("--> Saliendo del menú ...");
                    return;
                }
                default -> System.out.println("xxx  Opción inválida  xxx");
            }
        }
    }
}
