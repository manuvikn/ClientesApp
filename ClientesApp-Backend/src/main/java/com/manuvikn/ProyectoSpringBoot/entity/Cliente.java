package com.manuvikn.ProyectoSpringBoot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty                //@NotEmpty(message = "no puede estar vacío")
    @Size(min = 4, max = 25)
    @Column(length = 25, nullable = false)
    private String nombre;

    @NotEmpty
    @Size(min = 3, max = 50)
    @Column(length = 50, nullable = false)
    private String apellidos;

    @NotEmpty
    @Email
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @NotNull
    @Column
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column
    private String foto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Region region;

//    @PrePersist
//    public void prePersist() {
//        this.fecha = new Date();
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Serial
    private static final long serialVersionUID = 597407451043639888L;
}
