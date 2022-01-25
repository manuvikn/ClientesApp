package com.manuvikn.ProyectoSpringBoot.service;

import com.manuvikn.ProyectoSpringBoot.dao.ClienteRepository;
import com.manuvikn.ProyectoSpringBoot.entity.Cliente;
import com.manuvikn.ProyectoSpringBoot.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return this.clienteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return this.clienteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Long saveCliente(Cliente c) {

        if (c == null) return null;

        return this.clienteRepository.save(c).getId();
    }

    @Override
    @Transactional
    public void removeCliente(Long id) {

        if (id != null) this.clienteRepository.deleteById(id);
    }

    @Override
    public List<Region> findAllRegiones() {
        return clienteRepository.findAllRegiones();
    }

}
