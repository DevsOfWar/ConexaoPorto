package com.conexaoporto.springboot.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.conexaoporto.springboot.model.entities.Oficina;

@Repository
public interface OficinaRepository extends CrudRepository<Oficina, Long> {

	public Oficina findById(long id);
	
	@Query("SELECT o FROM Oficina AS o WHERE cod_empresa = ?1")
	public Iterable<Oficina> findByUserId(long id);
}
