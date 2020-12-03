package com.conexaoporto.springboot.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Endereco {
	
	@Id
	@Column(name= "cod_usuario")
	private long id;
	
	private String logradouro;
	private String complemento;
	private String numero;
	private String cidade;
	
	@Column(length= 2)
	private String uf;
	
	@Column(length= 8)
	private String cep;
	
	@OneToOne
	@MapsId
	@JoinColumn(name ="cod_usuario")
	private Usuario usuario;

	public Endereco() {
		super();
	}

	public Endereco(String logradouro, String complemento, String numero, String cidade, String uf, String cep) {
		super();
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.numero = numero;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
