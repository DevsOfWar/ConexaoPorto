package com.conexaoporto.springboot.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.conexaoporto.springboot.model.entities.Conteudo;

@Repository
public interface ConteudoRepository extends CrudRepository<Conteudo, Long> {
	
	public Conteudo findById(long id);
	
	@Query("SELECT c FROM Conteudo AS c WHERE cod_modulo = ?1")
	public Iterable<Conteudo> findAllByModulo(long idModulo);
	
}
