package com.conexaoporto.springboot.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.conexaoporto.springboot.model.entities.Empresa;
import com.conexaoporto.springboot.model.entities.Oficina;

@Repository
public interface EmpresaRepository extends CrudRepository<Empresa, Long> {
	
	 public Empresa findById(long id);

	 @Query("SELECT e FROM Empresa AS e WHERE email = ?1")
	 public Empresa findByEmail(String email);
	
	 
}
