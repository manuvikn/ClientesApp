package com.manuvikn.ProyectoSpringBoot.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Regiones")
public class Region implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long region_id;

    @Column
    private String nombre;

    public Long getId() {
        return region_id;
    }

    public void setId(Long id) {
        this.region_id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
