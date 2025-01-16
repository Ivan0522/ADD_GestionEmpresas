/**
 * Clase principal que inicia la aplicación y gestiona los menús de Empresa, Departamento y Empleado.
 */
package org.example;

import org.example.entidades.Departamento;
import org.example.entidades.Empleado;
import org.example.entidades.Empresa;
import org.example.menu.DepartamentoMenu;
import org.example.menu.EmpleadoMenu;
import org.example.menu.EmpresaMenu;
import org.example.repositorios.DepartamentoRepository;
import org.example.repositorios.EmpleadoRepository;
import org.example.repositorios.EmpresaRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {

    /**
     * Método principal que inicializa el programa.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Configuración de Hibernate y creación de la SessionFactory
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // Archivo de configuración de Hibernate
                .addAnnotatedClass(Empresa.class) // Clases anotadas para el mapeo
                .addAnnotatedClass(Departamento.class)
                .addAnnotatedClass(Empleado.class)
                .buildSessionFactory();

        // Inicialización de los repositorios
        EmpresaRepository empresaRepository = new EmpresaRepository(sessionFactory);
        DepartamentoRepository departamentoRepository = new DepartamentoRepository(sessionFactory);
        EmpleadoRepository empleadoRepository = new EmpleadoRepository(sessionFactory);

        // Ciclo principal para la selección del menú
        while (true) {
            System.out.println("\n************************************");
            System.out.println(">>>  Seleccione el menú a abrir  <<<");
            System.out.println("************************************");
            System.out.println("1. Empresa");
            System.out.println("2. Departamento");
            System.out.println("3. Empleado");
            System.out.println("4. Salir");
            System.out.println("************************************");

            // Entrada del usuario para seleccionar una opción
            Scanner scanner = new Scanner(System.in);
            System.out.print(">> Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            // Lógica para manejar la selección del menú
            switch (opcion) {
                case 1 -> new EmpresaMenu(empresaRepository, sessionFactory).mostrarMenu();
                case 2 -> new DepartamentoMenu(departamentoRepository, empresaRepository, sessionFactory).mostrarMenu();
                case 3 -> new EmpleadoMenu(empleadoRepository, departamentoRepository).mostrarMenu();
                case 4 -> {
                    System.out.println("--> Saliendo ...");
                    sessionFactory.close(); // Cerrar la SessionFactory antes de salir
                    return;
                }
                default -> System.out.println("xxx  Opción inválida  xxx");
            }
        }
    }
}
