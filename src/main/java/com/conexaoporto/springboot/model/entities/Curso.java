package com.conexaoporto.springboot.model.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Curso {
	
	@Id
	@Column(name= "cod_curso")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codCurso;
	
	@Column(name= "nome_do_curso")
	private String nomeDoCurso;
	
	@Column(name= "data_previsao_conclusao")
	private Date dataPrevisaoConclusao;
	
	@ManyToMany
	private Set<Profissional> profissional;
	
	@ManyToOne
	@JoinColumn(name= "cod_instituicao", insertable = false, updatable = false)
	private InstEnsino instituicaoDeEnsino;
}
