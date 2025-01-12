package org.example.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Empleados")
public class Empleado {
    @Id
    @Column(name = "dni", nullable = false, unique = true)
    private String dni;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "puesto", nullable = false)
    private String puesto;

    @ManyToOne
    @JoinColumn(name = "departamento_id", nullable = false)
    private Departamento departamento;

    // Constructores
    public Empleado() {}

    public Empleado(String dni, String nombre, String apellido, String puesto, Departamento departamento) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.puesto = puesto;
        this.departamento = departamento;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
}


