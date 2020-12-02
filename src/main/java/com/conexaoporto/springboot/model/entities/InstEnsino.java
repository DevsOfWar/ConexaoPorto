package com.conexaoporto.springboot.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class InstEnsino {
	
	@Id
	@Column(name= "cod_instituicao")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codInstituicao;
	
	private String nome;
	
	@OneToMany
	@JoinColumn(name= "cod_instituicao")
	private Set<Curso> cursos;

	public InstEnsino() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InstEnsino(String nome) {
		super();
		this.nome = nome;
	}

	public long getCodInstituicao() {
		return codInstituicao;
	}

	public void setCodInstituicao(long codInstituicao) {
		this.codInstituicao = codInstituicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
	}
	
	
}
