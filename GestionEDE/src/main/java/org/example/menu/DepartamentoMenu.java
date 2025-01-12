package org.example.menu;

import org.example.entidades.Departamento;
import org.example.DepartamentoRepository;
import org.example.EmpresaRepository;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;

import java.util.List;

public class DepartamentoMenu extends Menu<Departamento, Integer> {
    private final DepartamentoRepository repository;
    private final EmpresaRepository empresaRepository;
    private final SessionFactory sessionFactory;

    public DepartamentoMenu(DepartamentoRepository repository, EmpresaRepository empresaRepository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create() {
        System.out.print("Ingrese el nombre del departamento: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el ID de la empresa asociada: ");
        int empresaId = scanner.nextInt();
        scanner.nextLine();

        empresaRepository.read(empresaId).ifPresentOrElse(empresa -> {
            Departamento departamento = new Departamento(nombre, empresa);
            repository.create(departamento);
            System.out.println("Departamento creado exitosamente.");
        }, () -> System.out.println("Empresa no encontrada."));
    }

    @Override
    public void read() {
        System.out.print("Ingrese el ID del departamento: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repository.read(id).ifPresentOrElse(
                departamento -> System.out.println("Departamento encontrado: " + departamento.getNombre() +
                        ", Empresa: " + departamento.getEmpresa().getNombre()),
                () -> System.out.println("Departamento no encontrado.")
        );
    }

    @Override
    public void update() {
        System.out.print("Ingrese el ID del departamento a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repository.read(id).ifPresentOrElse(departamento -> {
            System.out.print("Ingrese el nuevo nombre del departamento: ");
            departamento.setNombre(scanner.nextLine());

            System.out.print("Ingrese el ID de la nueva empresa asociada: ");
            int empresaId = scanner.nextInt();
            scanner.nextLine();

            empresaRepository.read(empresaId).ifPresentOrElse(empresa -> {
                departamento.setEmpresa(empresa);
                repository.update(departamento);
                System.out.println("Departamento actualizado exitosamente.");
            }, () -> System.out.println("Empresa no encontrada."));
        }, () -> System.out.println("Departamento no encontrado."));
    }

    @Override
    public void delete() {
        System.out.print("Ingrese el ID del departamento a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        repository.delete(id);
        System.out.println("Departamento eliminado exitosamente.");
    }

    @Override
    public void listaEmpleadosDepartamento() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Código de consulta usando DTO
            System.out.print("Ingrese el ID del departamento: ");
            int departmentId = scanner.nextInt();
            scanner.nextLine();

            List<Object[]> empleados = session.createQuery(
                            "SELECT e.nombre, e.apellido, e.puesto FROM Empleado e WHERE e.departamento.id = :id", Object[].class)
                    .setParameter("id", departmentId)
                    .getResultList();

            if (empleados.isEmpty()) {
                System.out.println("No hay empleados asignados a este departamento.");
            } else {
                System.out.println("Empleados en el departamento:");
                empleados.forEach(empleado -> System.out.println(
                        "- Nombre: " + empleado[0] + ", Apellido: " + empleado[1] + ", Puesto: " + empleado[2]));
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void listaDepartamentosEmpleados() {

    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menú Departamento ---");
            System.out.println("1. Crear");
            System.out.println("2. Leer");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Consultar empleados de un departamento");
            System.out.println("6. Salir");

            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1 -> create();
                case 2 -> read();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> listaEmpleadosDepartamento();
                case 6 -> {
                    System.out.println("Saliendo del menú...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

}

