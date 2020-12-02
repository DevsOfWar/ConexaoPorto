package com.conexaoporto.springboot.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.conexaoporto.springboot.model.entities.Profissional;

@Repository
public interface ProfissionalRepository extends CrudRepository<Profissional, Long> {
	public Profissional findById(long id);//Faz o retorno de findById ser do tipo Profissional
	
	@Query("SELECT P FROM Profissional AS P WHERE email = ?1")//para retornar a entidade toda você deve dar select na entidade inteira
	public Profissional findByEmail(String email);//você também pode escrever a sua propria query customizada
}
