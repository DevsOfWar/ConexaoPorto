package com.conexaoporto.springboot.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Conteudo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codConteudo;
	
	@Column(name= "link_do_video", nullable = false)
	private String linkVideo;
	
	private String descricao;
	
	private String titulo;
	
	@ManyToOne
	@JoinColumn(name= "cod_modulo")
	private Modulo modulo;

	public Conteudo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Conteudo(String linkVideo, String descricao, String titulo, Modulo modulo) {
		super();
		this.linkVideo = linkVideo;
		this.descricao = descricao;
		this.titulo = titulo;
		this.modulo = modulo;
	}

	public long getCodConteudo() {
		return codConteudo;
	}

	public void setCodConteudo(long codConteudo) {
		this.codConteudo = codConteudo;
	}

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
}
