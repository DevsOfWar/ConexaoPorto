package com.conexaoporto.springboot.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "cod_profissional")
public class Profissional extends Usuario {
	
	
	@Column(name="area_de_interesse")
	private String areaDeInteresse;
	
	@Column(name="ocupacao")
	private String ocupacao;
	
	@Column(name="nivel_de_escolaridade")
	private String nivelDeEscolaridade;
	
	@ManyToMany
	private Set<Curso> cursos;
	
	public Profissional() {
		super();
	}

	public Profissional(String nome, String email, String senha) {
		super(nome, email, senha);
	}
	
	public String getNivelDeEscolaridade() {
		return nivelDeEscolaridade;
	}

	public void setNivelDeEscolaridade(String nivelDeEscolaridade) {
		this.nivelDeEscolaridade = nivelDeEscolaridade;
	}

	public Set<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
	}

	public String getAreaDeInteresse() {
		return areaDeInteresse;
	}

	public void setAreaDeInteresse(String areaDeInteresse) {
		this.areaDeInteresse = areaDeInteresse;
	}

	public String getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}
	
	
}
