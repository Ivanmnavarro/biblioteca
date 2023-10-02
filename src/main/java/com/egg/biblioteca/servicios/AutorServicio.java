/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {

        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepositorio.save(autor);
    }

    public List<Autor> listaAutores() {

        List<Autor> autores = new ArrayList();

        autores = autorRepositorio.findAll();

        return autores;

    }

    public void modificarAutor(String nombre, String id) throws MiException {
       
        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepositorio.save(autor);

        }
    }

    private void validar(String nombre) throws MiException {

        if (nombre.isEmpty() || nombre == null) {

            throw new MiException("el nombre no puede ser nulo o estar vacio");

        }
    }

}
