package com.manuvikn.ProyectoSpringBoot.controller;

import com.manuvikn.ProyectoSpringBoot.entity.Cliente;
import com.manuvikn.ProyectoSpringBoot.entity.Region;
import com.manuvikn.ProyectoSpringBoot.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(path = "/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    private final Logger log = LoggerFactory.getLogger(ClienteController.class);

    @GetMapping
    public List<Cliente> listAll() {
        return this.clienteService.findAll();
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<?> listAllPaginated(@PathVariable Integer page) {
        Map<String, Object> response = new HashMap<>();
        Page<Cliente> pageCliente;

        try {
            pageCliente = this.clienteService.findAll(PageRequest.of(page, 8));
            return new ResponseEntity<>(pageCliente, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {

        Map<String, Object> responseError = new HashMap<>();
        Cliente cliente = null;

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            responseError.put("mensaje", "Error al realizar la consulta en base de datos");
            responseError.put("error", "Mensaje: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            responseError.put("mensaje", "El cliente con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(responseError, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

//    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Cliente c, BindingResult result) {

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(f -> "Campo ".concat(f.getField().concat(": ").concat(f.getDefaultMessage()))).collect(Collectors.toList());
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("errores", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Long id = clienteService.saveCliente(c);
            return new ResponseEntity(id, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", "Mensaje: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente c, BindingResult result, @PathVariable Long id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(f -> "Campo ".concat(f.getField().concat(": ").concat(f.getDefaultMessage()))).collect(Collectors.toList());
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("errores", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", "Mensaje: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente con ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        c.setId(cliente.getId());

        try {
            Long idCliente = clienteService.saveCliente(c);
            return new ResponseEntity<>(idCliente, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", "Mensaje: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Map<String, Object> response = new HashMap<>();

        try {
            clienteService.removeCliente(id);
            response.put("mensaje", "OK");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", "Mensaje: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo,
                                    @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Cliente cliente = null;

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", "Mensaje: ".concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!archivo.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString().concat("_").concat(archivo.getOriginalFilename()).replace(" ", "");
            Path rutaArchivo = Paths.get("uploads").resolve(nombreArchivo).toAbsolutePath();
            log.info(rutaArchivo.toString());

            try {
                Files.copy(archivo.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                response.put("mensaje", "Error al subir el archivo");
                response.put("error", "Mensaje: ".concat(e.getCause().getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (cliente.getFoto() != null) {
                Path rutaAnterior = Paths.get("uploads").resolve(cliente.getFoto()).toAbsolutePath();
                File archivoAnterior = rutaAnterior.toFile();
                if (archivoAnterior.exists() && archivoAnterior.canRead()) archivoAnterior.delete();
            }

            cliente.setFoto(nombreArchivo);
            clienteService.saveCliente(cliente);
            response.put("mensaje", "OK");
            response.put("cliente", cliente);
        } else {
            response.put("mensaje", "El fichero no existe");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/upload/img/{foto:.+}")
    public ResponseEntity<Resource> downloadFoto(@PathVariable String foto ) {

        Path rutaArchivo = Paths.get("uploads").resolve(foto).toAbsolutePath();
        log.info(rutaArchivo.toString());

        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!recurso.exists() && !recurso.isReadable()) throw new RuntimeException("Error al obtener el fichero");

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

        return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);

    }

    @GetMapping("/regiones")
    public ResponseEntity<?> listarRegiones() {

        Map<String, Object> response = new HashMap<>();
        List<Region> listRegiones;

        try {
            listRegiones = this.clienteService.findAllRegiones();
            return new ResponseEntity<>(listRegiones, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
