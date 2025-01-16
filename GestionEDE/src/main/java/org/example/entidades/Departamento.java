package org.example.entidades;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa un departamento dentro de una empresa.
 */
@Entity
@Table(name = "Departamentos")
public class Departamento {

    /**
     * Identificador único del departamento.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nombre del departamento. No puede ser nulo.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Empresa a la que pertenece el departamento.
     * Se define una relación @ManyToOne con la entidad Empresa.
     */
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    /**
     * Lista de empleados asociados al departamento.
     * Se define una relación @OneToMany con la entidad Empleado.
     */
    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Empleado> empleados;

    /**
     * Constructor por defecto.
     */
    public Departamento() {}

    /**
     * Constructor que inicializa un departamento con un nombre y una empresa asociada.
     *
     * @param nombre  Nombre del departamento.
     * @param empresa Empresa a la que pertenece el departamento.
     */
    public Departamento(String nombre, Empresa empresa) {
        this.nombre = nombre;
        this.empresa = empresa;
    }

    /**
     * Obtiene el identificador del departamento.
     *
     * @return Identificador del departamento.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del departamento.
     *
     * @param id Identificador del departamento.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del departamento.
     *
     * @return Nombre del departamento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del departamento.
     *
     * @param nombre Nombre del departamento.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la empresa asociada al departamento.
     *
     * @return Empresa asociada al departamento.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Establece la empresa asociada al departamento.
     *
     * @param empresa Empresa asociada al departamento.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtiene la lista de empleados asociados al departamento.
     *
     * @return Lista de empleados asociados al departamento.
     */
    public List<Empleado> getEmpleados() {
        return empleados;
    }

    /**
     * Establece la lista de empleados asociados al departamento.
     *
     * @param empleados Lista de empleados asociados al departamento.
     */
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
}
