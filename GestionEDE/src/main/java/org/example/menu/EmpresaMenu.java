package org.example.menu;

import org.example.entidades.Departamento;
import org.example.entidades.Empresa;
import org.example.EmpresaRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmpresaMenu extends Menu<Empresa, Integer> {
    private final EmpresaRepository repository;
    private final SessionFactory sessionFactory;

    public EmpresaMenu(EmpresaRepository repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create() {
        System.out.print("Ingrese el nombre de la empresa: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la industria de la empresa: ");
        String industria = scanner.nextLine();

        Empresa empresa = new Empresa(nombre, industria);
        repository.create(empresa);
        System.out.println("Empresa creada exitosamente.");
    }

    @Override
    public void read() {
        System.out.print("Ingrese el ID de la empresa: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repository.read(id).ifPresentOrElse(
                empresa -> System.out.println("Empresa encontrada: " + empresa.getNombre() + ", Industria: " + empresa.getIndustria()),
                () -> System.out.println("Empresa no encontrada.")
        );
    }

    @Override
    public void update() {
        System.out.print("Ingrese el ID de la empresa a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        repository.read(id).ifPresentOrElse(empresa -> {
            System.out.print("Ingrese el nuevo nombre de la empresa: ");
            empresa.setNombre(scanner.nextLine());
            System.out.print("Ingrese la nueva industria de la empresa: ");
            empresa.setIndustria(scanner.nextLine());
            repository.update(empresa);
            System.out.println("Empresa actualizada exitosamente.");
        }, () -> System.out.println("Empresa no encontrada."));
    }

    @Override
    public void delete() {
        System.out.print("Ingrese el ID de la empresa a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        repository.delete(id);
        System.out.println("Empresa eliminada exitosamente.");
    }

    @Override
    public void listaEmpleadosDepartamento() {

    }

    @Override
    public void listaDepartamentosEmpleados() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Ingrese el ID de la empresa: ");
            int companyId = scanner.nextInt();
            scanner.nextLine(); // Consumir salto de línea

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
                System.out.println("La empresa no tiene departamentos registrados o no existe.");
            } else {
                System.out.println("Departamentos de la empresa con ID " + companyId + ":");
                resultados.forEach(resultado -> {
                    String nombreDepartamento = (String) resultado[0];
                    Long numeroEmpleados = (Long) resultado[1]; // El conteo se devuelve como Long
                    System.out.println("- " + nombreDepartamento + " (Número de empleados: " + numeroEmpleados + ")");
                });
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Menú Empresa ---");
            System.out.println("1. Crear");
            System.out.println("2. Leer");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Mostrar departamentos y número de empleados");
            System.out.println("6. Salir");

            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1 -> create();
                case 2 -> read();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> listaDepartamentosEmpleados();
                case 6 -> {
                    System.out.println("Saliendo del menú...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }


}

