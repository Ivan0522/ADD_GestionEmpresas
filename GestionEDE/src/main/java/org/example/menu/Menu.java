/**
 * Paquete que contiene la clase abstracta Menu.
 */
package org.example.menu;

import java.util.Scanner;

/**
 * Clase abstracta que proporciona una estructura base para un menú genérico.
 *
 * @param <T>  Tipo de los elementos que manejará el menú.
 * @param <ID> Tipo del identificador único de los elementos que manejará el menú.
 */
public abstract class Menu<T, ID> {

    /**
     * Escáner utilizado para capturar la entrada del usuario desde la consola.
     */
    protected final Scanner sc = new Scanner(System.in);

    /**
     * Método abstracto para crear un elemento.
     * Debe ser implementado por las clases concretas que hereden esta clase.
     */
    public abstract void crear();

    /**
     * Método abstracto para leer elementos.
     * Debe ser implementado por las clases concretas que hereden esta clase.
     */
    public abstract void leer();

    /**
     * Método abstracto para actualizar un elemento.
     * Debe ser implementado por las clases concretas que hereden esta clase.
     */
    public abstract void actualizar();

    /**
     * Método abstracto para borrar un elemento.
     * Debe ser implementado por las clases concretas que hereden esta clase.
     */
    public abstract void borrar();

    /**
     * Muestra el menú principal y gestiona las opciones seleccionadas por el usuario.
     * Este método contiene un bucle que sigue mostrando el menú hasta que el usuario elija salir.
     */
    public void mostrarMenu() {
        while (true) {
            System.out.println("----------------------------------------------------");
            System.out.println("----------------------- Menú -----------------------");
            System.out.println("----------------------------------------------------");
            System.out.println("|| 1. Crear\t\t\t\t\t  ||");
            System.out.println("|| 2. Leer       \t\t\t\t  ||");
            System.out.println("|| 3. Actualizar \t\t\t\t  ||");
            System.out.println("|| 4. Eliminar   \t\t\t\t  ||");
            System.out.println("|| 5. Salir      \t\t\t\t  ||");
            System.out.println("----------------------------------------------------");

            System.out.print(">> Seleccione una opción: ");
            int option = sc.nextInt();
            sc.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1 -> crear();
                case 2 -> leer();
                case 3 -> actualizar();
                case 4 -> borrar();
                case 5 -> {
                    System.out.println("--> Saliendo del menú ...");
                    return;
                }
                default -> System.out.println("xxx  Opción inválida  xxx");
            }
        }
    }
}
