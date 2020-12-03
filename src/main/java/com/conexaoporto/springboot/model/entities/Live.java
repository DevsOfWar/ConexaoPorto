package com.conexaoporto.springboot.model.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "cod_live")
public class Live extends Evento {
	//TODO: Essa é uma entidade placeholder, a funcinalidade de 'Lives' não está presente no MVP I
	private long temp;

	public Live() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getTemp() {
		return temp;
	}

	public void setTemp(long temp) {
		this.temp = temp;
	}
	
	
}
