package com.manuvikn.ProyectoSpringBoot.dao;

import com.manuvikn.ProyectoSpringBoot.entity.Cliente;
import com.manuvikn.ProyectoSpringBoot.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Long>, CrudRepository<Cliente, Long> {

    @Query("select r from Region r")
    List<Region> findAllRegiones();

}
