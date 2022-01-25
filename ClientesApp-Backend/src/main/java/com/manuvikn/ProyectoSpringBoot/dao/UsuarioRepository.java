package com.manuvikn.ProyectoSpringBoot.dao;

import com.manuvikn.ProyectoSpringBoot.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, CrudRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    @Query("select u from Usuario u where u.username=?1")
    Usuario getUserByUsername(String username);

}
