package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	boolean existsByUserAndPass(String user, String pass);
}
