/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorio.AutorRepositorio;
import com.egg.biblioteca.repositorio.EditorialRepositorio;
import com.egg.biblioteca.repositorio.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroReposirotio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Autor autor = autorRepositorio.findById(idAutor).get();
        
        Editorial editorial = editorialRepositorio.findById(idAutor).get();
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroReposirotio.save(libro);
    }
    
    public List<Libro> listarLibros() {
        
        List<Libro> libros = new ArrayList();
        
        libros = libroReposirotio.findAll();
        
        return libros;
        
    }
    
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException {
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respuesta = libroReposirotio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
            
        }
        if (respuesta.isPresent()) {
            
            Libro libro = respuesta.get();
            
            libro.setTitulo(titulo);
            
            libro.setAutor(autor);
            
            libro.setEditorial(editorial);
            
            libro.setEjemplares(ejemplares);
            
            libroReposirotio.save(libro);
            
        }
        
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        
        if (isbn == null) {
            
            throw new MiException("el isbn no puede ser nulo");
            
        }
        
        if (titulo.isEmpty() || titulo == null) {
            
            throw new MiException("el titulo no puede ser nulo o estar vacio");
            
        }
        if (ejemplares == null) {
            
            throw new MiException("los ejemplares no puede ser nulo");
            
        }
        
        if (idAutor.isEmpty() || idAutor == null) {
            
            throw new MiException("el id Autor no puede ser nulo o estar vacio");
            
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            
            throw new MiException("el id Editorial no puede ser nulo o estar vacio");
            
        }
    }
}
