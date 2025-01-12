package org.example.menu;

import java.util.Scanner;

public abstract class Menu<T, ID> {
    protected final Scanner scanner = new Scanner(System.in);

    public abstract void create();

    public abstract void read();

    public abstract void update();

    public abstract void delete();

    public abstract void listaEmpleadosDepartamento();

    public abstract void listaDepartamentosEmpleados();

    public void showMenu() {
        while (true) {
            System.out.println("-------------------");
            System.out.println("------ Menú--------");
            System.out.println("-------------------");
            System.out.println("|| 1. Crear      ||");
            System.out.println("|| 2. Leer       ||");
            System.out.println("|| 3. Actualizar ||");
            System.out.println("|| 4. Eliminar   ||");
            System.out.println("|| 5. Salir      ||");
            System.out.println("-------------------");
            System.out.println("-------------------");

            System.out.print(">> Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1 -> create();
                case 2 -> read();
                case 3 -> update();
                case 4 -> delete();
                case 5 -> {
                    System.out.println(">> Saliendo del menú...");
                    return;
                }
                default -> System.out.println(">> Opción inválida.");
            }
        }
    }
}
