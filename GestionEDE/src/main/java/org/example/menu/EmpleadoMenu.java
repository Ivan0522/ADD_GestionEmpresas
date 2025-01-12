package org.example.menu;

import org.example.DepartamentoRepository;
import org.example.entidades.Empleado;
import org.example.EmpleadoRepository;

public class EmpleadoMenu extends Menu<Empleado, String> {
    private final EmpleadoRepository repository;
    private final DepartamentoRepository departamentoRepository;

    public EmpleadoMenu(EmpleadoRepository repository, DepartamentoRepository departamentoRepository) {
        this.repository = repository;
        this.departamentoRepository = departamentoRepository;
    }

    @Override
    public void create() {
        System.out.print("Ingrese el DNI del empleado: ");
        String dni = scanner.nextLine();

        // Verificar si el DNI ya está registrado
        if (repository.read(dni).isPresent()) {
            System.out.println("El empleado con DNI " + dni + " ya está registrado en la base de datos.");
            return;
        }

        // Solicitar otros datos del empleado
        System.out.print("Ingrese el nombre del empleado: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el apellido del empleado: ");
        String apellido = scanner.nextLine();

        System.out.print("Ingrese el puesto del empleado: ");
        String puesto = scanner.nextLine();

        System.out.print("Ingrese el ID del departamento asociado: ");
        int departamentoId = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        // Verificar si el departamento existe
        departamentoRepository.read(departamentoId).ifPresentOrElse(departamento -> {
            Empleado empleado = new Empleado(dni, nombre, apellido, puesto, departamento);
            try {
                repository.create(empleado);
                System.out.println("Empleado creado exitosamente.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error al registrar el empleado: " + e.getMessage());
            }

        }, () -> System.out.println("Departamento no encontrado. No se pudo registrar el empleado."));
    }

    @Override
    public void read() {
        System.out.print("Ingrese el DNI del empleado: ");
        String dni = scanner.nextLine();

        repository.read(dni).ifPresentOrElse(
                empleado -> System.out.println("Empleado encontrado: " + empleado.getNombre() + " " + empleado.getApellido() +
                        ", Puesto: " + empleado.getPuesto() + ", Departamento: " + empleado.getDepartamento().getNombre()),
                () -> System.out.println("Empleado no encontrado.")
        );
    }

    @Override
    public void update() {
        System.out.print("Ingrese el DNI del empleado a actualizar: ");
        String dni = scanner.nextLine();

        repository.read(dni).ifPresentOrElse(empleado -> {
            System.out.println("Empleado encontrado: ");
            System.out.println("Nombre actual: " + empleado.getNombre());
            System.out.println("Apellido actual: " + empleado.getApellido());
            System.out.println("Puesto actual: " + empleado.getPuesto());
            System.out.println("Departamento actual: " + empleado.getDepartamento().getNombre());

            System.out.print("Ingrese el nuevo nombre del empleado (o presione Enter para no cambiar): ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.isEmpty()) {
                empleado.setNombre(nuevoNombre);
            }

            System.out.print("Ingrese el nuevo apellido del empleado (o presione Enter para no cambiar): ");
            String nuevoApellido = scanner.nextLine();
            if (!nuevoApellido.isEmpty()) {
                empleado.setApellido(nuevoApellido);
            }

            System.out.print("Ingrese el nuevo puesto del empleado (o presione Enter para no cambiar): ");
            String nuevoPuesto = scanner.nextLine();
            if (!nuevoPuesto.isEmpty()) {
                empleado.setPuesto(nuevoPuesto);
            }

            System.out.print("Ingrese el ID del nuevo departamento (o presione Enter para no cambiar): ");
            String nuevoDepartamentoId = scanner.nextLine();
            if (!nuevoDepartamentoId.isEmpty()) {
                int depId = Integer.parseInt(nuevoDepartamentoId);
                departamentoRepository.read(depId).ifPresentOrElse(departamento -> {
                    empleado.setDepartamento(departamento);
                }, () -> System.out.println("Departamento no encontrado. No se cambió el departamento."));
            }

            repository.update(empleado);
            System.out.println("Empleado actualizado exitosamente.");
        }, () -> System.out.println("Empleado no encontrado."));
    }

    @Override
    public void delete() {
        System.out.print("Ingrese el DNI del empleado a eliminar: ");
        String dni = scanner.nextLine();

        repository.read(dni).ifPresentOrElse(empleado -> {
            repository.delete(dni);
            System.out.println("Empleado eliminado exitosamente.");
        }, () -> System.out.println("Empleado no encontrado."));
    }

    @Override
    public void listaEmpleadosDepartamento() {

    }

    @Override
    public void listaDepartamentosEmpleados() {

    }
}

