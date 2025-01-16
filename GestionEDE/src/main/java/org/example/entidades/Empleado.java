package org.example.entidades;

import jakarta.persistence.*;

/**
 * Entidad que representa un empleado en la base de datos.
 */
@Entity
@Table(name = "Empleados")
public class Empleado {

    /**
     * DNI del empleado, actúa como clave primaria y es único para cada empleado.
     */
    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    /**
     * Nombre del empleado. No puede ser nulo.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Apellido del empleado. No puede ser nulo.
     */
    @Column(name = "apellido", nullable = false)
    private String apellido;

    /**
     * Puesto o cargo del empleado. No puede ser nulo.
     */
    @Column(name = "puesto", nullable = false)
    private String puesto;

    /**
     * Departamento al que pertenece el empleado.
     * Relación @ManyToOne con la entidad Departamento.
     */
    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    /**
     * Constructor por defecto.
     */
    public Empleado() {}

    /**
     * Constructor que inicializa un empleado con todos sus atributos.
     *
     * @param dni          DNI del empleado.
     * @param nombre       Nombre del empleado.
     * @param apellido     Apellido del empleado.
     * @param puesto       Puesto o cargo del empleado.
     * @param departamento Departamento al que pertenece el empleado.
     */
    public Empleado(String dni, String nombre, String apellido, String puesto, Departamento departamento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.departamento = departamento;
    }

    /**
     * Obtiene el DNI del empleado.
     *
     * @return DNI del empleado.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del empleado.
     *
     * @param dni DNI del empleado.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return Nombre del empleado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre Nombre del empleado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del empleado.
     *
     * @return Apellido del empleado.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del empleado.
     *
     * @param apellido Apellido del empleado.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el puesto del empleado.
     *
     * @return Puesto del empleado.
     */
    public String getPuesto() {
        return puesto;
    }

    /**
     * Establece el puesto del empleado.
     *
     * @param puesto Puesto del empleado.
     */
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    /**
     * Obtiene el departamento al que pertenece el empleado.
     *
     * @return Departamento del empleado.
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     * Establece el departamento al que pertenece el empleado.
     *
     * @param departamento Departamento del empleado.
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}
