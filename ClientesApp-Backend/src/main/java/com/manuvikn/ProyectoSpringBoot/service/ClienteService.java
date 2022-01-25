package com.manuvikn.ProyectoSpringBoot.service;

import com.manuvikn.ProyectoSpringBoot.entity.Cliente;
import com.manuvikn.ProyectoSpringBoot.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    Cliente findById(Long id);

    Long saveCliente(Cliente c);

    void removeCliente(Long id);

    List<Region> findAllRegiones();

}
