package com.manuvikn.ProyectoSpringBoot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true, length = 20)
    private String username;

    @NotEmpty
    @Column(length = 60)
    private String password;

    @Column
    private Boolean enabled;

//    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name ="role_id"))
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "roles_id"})})
    private List<Rol> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
