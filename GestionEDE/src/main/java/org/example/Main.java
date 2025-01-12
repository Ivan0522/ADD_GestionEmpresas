package org.example;

import org.example.entidades.Departamento;
import org.example.entidades.Empleado;
import org.example.entidades.Empresa;
import org.example.menu.DepartamentoMenu;
import org.example.menu.EmpleadoMenu;
import org.example.menu.EmpresaMenu;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Empresa.class)
                .addAnnotatedClass(Departamento.class)
                .addAnnotatedClass(Empleado.class)
                .buildSessionFactory();

        EmpresaRepository empresaRepository = new EmpresaRepository(sessionFactory);
        DepartamentoRepository departamentoRepository = new DepartamentoRepository(sessionFactory);
        EmpleadoRepository empleadoRepository = new EmpleadoRepository(sessionFactory);

        while (true) {
            System.out.println("\nSeleccione el menú a abrir:");
            System.out.println("1. Empresa");
            System.out.println("2. Departamento");
            System.out.println("3. Empleado");
            System.out.println("4. Salir");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> new EmpresaMenu(empresaRepository, sessionFactory).showMenu();
                case 2 -> new DepartamentoMenu(departamentoRepository, empresaRepository, sessionFactory).showMenu();
                case 3 -> new EmpleadoMenu(empleadoRepository, departamentoRepository).showMenu();
                case 4 -> {
                    System.out.println("Saliendo...");
                    sessionFactory.close();
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}


