package com.conexaoporto.springboot.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cod_usuario")
	private long codUsuario;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(length = 12)
	private String telefone;
	
	@Column(nullable = false, length = 36)
	private String senha;
	
	@Lob
	private byte[] foto;
	
	@Column(nullable = false, columnDefinition = "boolean default 'false'")
	private boolean status;
	
	private String descricao;
	
	public Usuario() {
		
	}
	
	public Usuario(String nome, String email, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}



	public long getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(long codUsuario) {
		this.codUsuario = codUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
