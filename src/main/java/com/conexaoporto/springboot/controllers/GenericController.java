package com.conexaoporto.springboot.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping
public class GenericController {
	
	@GetMapping(value= {"/", "/index", "/index.html", "/home", "/home.html"})//redireciona todos o requests GET do diretorio padrão (/) e (/index)
	public String indexRedirect(){
		return "home";
	}
	
	@GetMapping(value= {"/login.html", "login"})
	public String loginRedirect(HttpSession session) {
		if (session.getAttribute("userId") != null) {//caso já exista a variavel de sessão 'userId', ou seja a pessoa já fez login, não permite que ele abra a pagina de cadastro
			return "redirect:/home";
		}
		return "login";
	}
	
	@GetMapping(value= {"/cadastro.html", "/cadastro"})
	public String cadastroRedirect(HttpSession session) {
		if (session.getAttribute("userId") != null) {//caso já exista a variavel de sessão 'userId', ou seja a pessoa já fez login, não permite que ele abra a pagina de cadastro
			return "redirect:/home";
		}
		return "cadastro";
	}

	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate(); //encerra a sessão
		return "redirect:/home";
	}
	
	
	
	
	
}
