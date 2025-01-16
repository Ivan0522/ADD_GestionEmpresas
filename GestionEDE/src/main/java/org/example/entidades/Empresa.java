package org.example.entidades;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa una empresa en la base de datos.
 */
@Entity
@Table(name = "Empresas")
public class Empresa {

    /**
     * Identificador único de la empresa. Generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nombre de la empresa. No puede ser nulo.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Industria o sector al que pertenece la empresa. No puede ser nulo.
     */
    @Column(name = "industria", nullable = false)
    private String industria;

    /**
     * Lista de departamentos asociados a la empresa.
     * Relación @OneToMany con la entidad Departamento.
     */
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Departamento> departamentos;

    /**
     * Constructor por defecto.
     */
    public Empresa() {}

    /**
     * Constructor que inicializa una empresa con su nombre e industria.
     *
     * @param nombre    Nombre de la empresa.
     * @param industria Industria o sector al que pertenece la empresa.
     */
    public Empresa(String nombre, String industria) {
        this.nombre = nombre;
        this.industria = industria;
    }

    /**
     * Obtiene el identificador de la empresa.
     *
     * @return Identificador de la empresa.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la empresa.
     *
     * @param id Identificador de la empresa.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la empresa.
     *
     * @return Nombre de la empresa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la empresa.
     *
     * @param nombre Nombre de la empresa.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la industria o sector de la empresa.
     *
     * @return Industria o sector de la empresa.
     */
    public String getIndustria() {
        return industria;
    }

    /**
     * Establece la industria o sector de la empresa.
     *
     * @param industria Industria o sector de la empresa.
     */
    public void setIndustria(String industria) {
        this.industria = industria;
    }

    /**
     * Obtiene la lista de departamentos asociados a la empresa.
     *
     * @return Lista de departamentos asociados a la empresa.
     */
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    /**
     * Establece la lista de departamentos asociados a la empresa.
     *
     * @param departamentos Lista de departamentos asociados a la empresa.
     */
    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }
}
