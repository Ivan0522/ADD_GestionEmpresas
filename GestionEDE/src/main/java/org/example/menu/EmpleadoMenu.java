/**
 * Paquete que contiene la implementación del menú de opciones para la gestión de empleados.
 */
package org.example.menu;

import org.example.repositorios.DepartamentoRepository;
import org.example.entidades.Empleado;
import org.example.repositorios.EmpleadoRepository;

/**
 * Clase que implementa el menú para gestionar empleados, incluyendo las opciones de crear,
 * leer, actualizar y eliminar empleados.
 */
public class EmpleadoMenu extends Menu<Empleado, String> {

    /**
     * Repositorio para gestionar las operaciones de la entidad Empleado.
     */
    private final EmpleadoRepository repository;

    /**
     * Repositorio para gestionar las operaciones de la entidad Departamento.
     */
    private final DepartamentoRepository departamentoRepository;

    /**
     * Constructor que inicializa el menú con los repositorios de empleado y departamento.
     *
     * @param repository            Repositorio de empleados.
     * @param departamentoRepository Repositorio de departamentos.
     */
    public EmpleadoMenu(EmpleadoRepository repository, DepartamentoRepository departamentoRepository) {
        this.repository = repository;
        this.departamentoRepository = departamentoRepository;
    }

    /**
     * Permite crear un nuevo empleado asociado a un departamento existente.
     */
    @Override
    public void crear() {
        System.out.print("• Ingrese el DNI del empleado: ");
        String dni = sc.nextLine();

        // Verificar si el DNI ya está registrado
        if (repository.leer(dni).isPresent()) {
            System.out.println(">> El empleado con DNI " + dni + " ya está registrado en la base de datos.");
            return;
        }

        // Solicitar otros datos del empleado
        System.out.print("• Ingrese el nombre del empleado: ");
        String nombre = sc.nextLine();

        System.out.print("• Ingrese el apellido del empleado: ");
        String apellido = sc.nextLine();

        System.out.print("• Ingrese el puesto del empleado: ");
        String puesto = sc.nextLine();

        System.out.print("• Ingrese la ID del departamento asociado: ");
        int departamentoId = sc.nextInt();
        sc.nextLine(); // Consumir salto de línea

        // Verificar si el departamento existe
        departamentoRepository.leer(departamentoId).ifPresentOrElse(departamento -> {
            Empleado empleado = new Empleado(dni, nombre, apellido, puesto, departamento);
            try {
                repository.crear(empleado);
                System.out.println(">> Empleado creado exitosamente.");
            } catch (Exception e) {
                System.out.println("xxx Ocurrió un error al registrar el empleado: " + e.getMessage());
            }

        }, () -> System.out.println("xxx Departamento no encontrado. No se pudo registrar el empleado xxx"));
    }

    /**
     * Permite leer la información de un empleado por su DNI.
     */
    @Override
    public void leer() {
        System.out.print("• Ingrese el DNI del empleado: ");
        String dni = sc.nextLine();

        repository.leer(dni).ifPresentOrElse(
                empleado -> System.out.println(">> Empleado encontrado: " + empleado.getNombre() + " " + empleado.getApellido() +
                        ", Puesto: " + empleado.getPuesto() + ", Departamento: " + empleado.getDepartamento().getNombre()),
                () -> System.out.println("xxx Empleado no encontrado xxx")
        );
    }

    /**
     * Permite actualizar la información de un empleado.
     */
    @Override
    public void actualizar() {
        System.out.print("• Ingrese el DNI del empleado a actualizar: ");
        String dni = sc.nextLine();

        repository.leer(dni).ifPresentOrElse(empleado -> {
            System.out.println(">> Empleado encontrado: ");
            System.out.println("Nombre actual: " + empleado.getNombre());
            System.out.println("Apellido actual: " + empleado.getApellido());
            System.out.println("Puesto actual: " + empleado.getPuesto());
            System.out.println("Departamento actual: " + empleado.getDepartamento().getNombre());

            System.out.print("• Ingrese el nuevo nombre del empleado (o presione Enter para no cambiar): ");
            String nuevoNombre = sc.nextLine();
            if (!nuevoNombre.isEmpty()) {
                empleado.setNombre(nuevoNombre);
            }

            System.out.print("• Ingrese el nuevo apellido del empleado (o presione Enter para no cambiar): ");
            String nuevoApellido = sc.nextLine();
            if (!nuevoApellido.isEmpty()) {
                empleado.setApellido(nuevoApellido);
            }

            System.out.print("• Ingrese el nuevo puesto del empleado (o presione Enter para no cambiar): ");
            String nuevoPuesto = sc.nextLine();
            if (!nuevoPuesto.isEmpty()) {
                empleado.setPuesto(nuevoPuesto);
            }

            System.out.print("• Ingrese la ID del nuevo departamento (o presione Enter para no cambiar): ");
            String nuevoDepartamentoId = sc.nextLine();
            if (!nuevoDepartamentoId.isEmpty()) {
                int depId = Integer.parseInt(nuevoDepartamentoId);
                departamentoRepository.leer(depId).ifPresentOrElse(departamento -> {
                    empleado.setDepartamento(departamento);
                }, () -> System.out.println("xxx Departamento no encontrado. No se cambió el departamento xxx"));
            }

            repository.actualizar(empleado);
            System.out.println(">> Empleado actualizado exitosamente.");
        }, () -> System.out.println("xxx Empleado no encontrado xxx"));
    }

    /**
     * Permite eliminar un empleado por su DNI.
     */
    @Override
    public void borrar() {
        System.out.print("• Ingrese el DNI del empleado a eliminar: ");
        String dni = sc.nextLine();

        repository.leer(dni).ifPresentOrElse(empleado -> {
            repository.borrar(dni);
            System.out.println(">> Empleado eliminado exitosamente.");
        }, () -> System.out.println("xxx Empleado no encontrado xxx"));
    }
}
