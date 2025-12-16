package com.example.demo;

import java.util.List;

public interface TrabajadorServicio {

    public List<Trabajador> listarTrabajador();

    public Trabajador guardarTrabajador(Trabajador trabajador);

    public Trabajador obtenerTrabajador(Integer id);

    public Trabajador actualizarTrabajador(Trabajador trabajador);

    public void borrarTrabajador(Integer id);
    
    public boolean existeEmail(String email);
}
