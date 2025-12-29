package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.clases.Cuadro;

public interface CuadroRepository extends JpaRepository<Cuadro, Long> {
}
