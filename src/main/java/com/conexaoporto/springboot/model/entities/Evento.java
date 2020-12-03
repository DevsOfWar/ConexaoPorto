package com.conexaoporto.springboot.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Positive;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Evento {
	
	@Id
	@Column(name="cod_evento")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codEvento;
	
	private String autor;
	
	@Column(nullable = false, name="nome_do_evento")
	private String nomeDoEvento;
	
	@Lob
	@Column(name="foto_de_capa")
	private byte[] fotoDeCapa;
	
	@Column(length = 7, nullable = false)//add constraint plataforma so pode ser externa ou local
	private String plataforma;
	
	@Column(name= "link_de_acesso")
	private String linkDeAcesso;
	
	@Positive( message = "Pontuação deve ser um valor positivo") //Verificar se isso funciona aqui ou só no controller
	private int pontuacao;
	
	@Column(nullable = false, columnDefinition = "bit default 0")
	private boolean situacao;
	
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="cod_empresa", nullable = false)
	private Empresa empresa;

	public Evento() {
		super();
	}

	public Evento(byte[] fotoDeCapa, String plataforma, String linkDeAcesso, int pontuacao, String descricao) {
		super();
		this.fotoDeCapa = fotoDeCapa;
		this.plataforma = plataforma;
		this.linkDeAcesso = linkDeAcesso;
		this.pontuacao = pontuacao;
		this.descricao = descricao;
	}

	public long getCodEvento() {
		return codEvento;
	}

	public void setCodEvento(long codEvento) {
		this.codEvento = codEvento;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public byte[] getFotoDeCapa() {
		return fotoDeCapa;
	}

	public void setFotoDeCapa(byte[] fotoDeCapa) {
		this.fotoDeCapa = fotoDeCapa;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getLinkDeAcesso() {
		return linkDeAcesso;
	}

	public void setLinkDeAcesso(String linkDeAcesso) {
		this.linkDeAcesso = linkDeAcesso;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getNomeDoEvento() {
		return nomeDoEvento;
	}

	public void setNomeDoEvento(String nomeDoEvento) {
		this.nomeDoEvento = nomeDoEvento;
	}
	
	
}
