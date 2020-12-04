package com.conexaoporto.springboot.model.helpers;

import javax.servlet.http.HttpSession;

public abstract class Autenticacao {

	public static boolean autenticado(HttpSession session, String tipo) {
		if ((session.getAttribute("tipoUsuario") == null) || !session.getAttribute("tipoUsuario").toString().contentEquals(tipo)) {
			return false;
		} else {
			return true;
		}
	}
}
