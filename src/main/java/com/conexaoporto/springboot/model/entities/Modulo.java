package com.conexaoporto.springboot.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Modulo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codModulo;
	
	@Column(name= "nome_modulo", nullable = false)
	private String nomeModulo;
	
	@Column(name= "descricao_modulo")
	private String descricaoModulo;
	
	@OneToMany
	@JoinColumn(name = "cod_modulo")
	private Set<Conteudo> conteudos;

	@ManyToOne
	@JoinColumn(name = "cod_oficina")
	private Oficina oficina;
	
	public Modulo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Modulo(String nomeModulo, String descricaoModulo, Oficina oficina) {
		super();
		this.nomeModulo = nomeModulo;
		this.descricaoModulo = descricaoModulo;
		this.oficina = oficina;
	}

	public long getCodModulo() {
		return codModulo;
	}

	public void setCodModulo(long codModulo) {
		this.codModulo = codModulo;
	}

	public String getNomeModulo() {
		return nomeModulo;
	}

	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}

	public String getDescricaoModulo() {
		return descricaoModulo;
	}

	public void setDescricaoModulo(String descricaoModulo) {
		this.descricaoModulo = descricaoModulo;
	}

	public Set<Conteudo> getConteudos() {
		return conteudos;
	}

	public void setConteudos(Set<Conteudo> conteudos) {
		this.conteudos = conteudos;
	}

	public Oficina getOficina() {
		return oficina;
	}

	public void setOficina(Oficina oficina) {
		this.oficina = oficina;
	}
	
	
	
}
