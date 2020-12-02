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
	@JoinColumn(name = "cod_oficina", insertable = false, updatable = false)
	private Oficina oficina;
	
	public Modulo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Modulo(String nomeModulo, String descricaoModulo) {
		super();
		this.nomeModulo = nomeModulo;
		this.descricaoModulo = descricaoModulo;
	}
	
	
}
