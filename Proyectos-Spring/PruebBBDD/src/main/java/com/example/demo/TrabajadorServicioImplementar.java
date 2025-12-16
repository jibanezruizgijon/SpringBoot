package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrabajadorServicioImplementar implements TrabajadorServicio {

    @Autowired
    private TrabajadorRepository repositorio;

    @Override
    public List<Trabajador> listarTrabajador() {

        return repositorio.findAll();
    }

    @Override
    public Trabajador guardarTrabajador(Trabajador trabajador) {
        return repositorio.save(trabajador);
    }

    @Override
    public Trabajador obtenerTrabajador(Integer id) {
        return repositorio.findById(id).get();
    }

    @Override
    public Trabajador actualizarTrabajador(Trabajador trabajador) {
        return repositorio.save(trabajador);
    }

    @Override
    public void borrarTrabajador(Integer id) {
        repositorio.deleteById(id);
    }
    
    @Override
    public boolean existeEmail(String email) {
        return repositorio.existsByEmail(email);
    }

}