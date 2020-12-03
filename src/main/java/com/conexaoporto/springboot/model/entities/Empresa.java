package com.conexaoporto.springboot.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="cod_empresa")
public class Empresa extends Usuario{
	
	@Column(name="razao_social")
	private String razaoSocial;
	
	@Column(nullable = false, length = 14)
	private String cnpj;
	
	@Column(name="area_de_atuacao")
	private String areaDeAtuacao;
	
	@OneToMany(mappedBy = "empresa")
	private Set<Evento> eventosDaEmpresa;
	
	public Empresa() {
		super();
	}

	public Empresa(String nome, String email, String senha, String cnpj) {
		super(nome, email, senha);
		this.cnpj = cnpj;
	}



	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getAreaDeAtuacao() {
		return areaDeAtuacao;
	}

	public void setAreaDeAtuacao(String areaDeAtuacao) {
		this.areaDeAtuacao = areaDeAtuacao;
	}

	public Set<Evento> getEventosDaEmpresa() {
		return eventosDaEmpresa;
	}

	public void setEventosDaEmpresa(Set<Evento> eventosDaEmpresa) {
		this.eventosDaEmpresa = eventosDaEmpresa;
	}
	
	
}
