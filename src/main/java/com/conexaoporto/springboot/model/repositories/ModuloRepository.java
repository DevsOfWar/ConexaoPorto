package com.conexaoporto.springboot.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.conexaoporto.springboot.model.entities.Modulo;

@Repository
public interface ModuloRepository extends CrudRepository<Modulo, Long> {
	
	public Modulo findById(long id);
	
	@Query("SELECT m FROM Modulo AS m WHERE cod_oficina = ?1")
	public Iterable<Modulo> findByOficinaId(long idOficina);
}
