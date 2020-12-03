package com.conexaoporto.springboot.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.conexaoporto.springboot.model.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	public Usuario findById(long id);
	
	@Query("SELECT u FROM Usuario AS u WHERE email = ?1")
	public Usuario findByEmail(String email);
}
