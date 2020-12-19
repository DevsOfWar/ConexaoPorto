package com.conexaoporto.springboot.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.conexaoporto.springboot.model.entities.Profissional;
import com.conexaoporto.springboot.model.repositories.ProfissionalRepository;

@Controller
@RequestMapping
public class ProfissionalController {
	
	@Autowired
	ProfissionalRepository profissionalRepo;
	
	@PostMapping("/CadastrarProfissional")
	public String cadastro (@RequestParam(name= "nome") String nome,
			@RequestParam(name="senha") String senha,
			@RequestParam(name="email") String email,
			Model model)
			 {
		if (profissionalRepo.findByEmail(email) != null) {
			model.addAttribute("emailError", "O email informado já esta cadastrado.");
			return "cadastro";
		}
		
		profissionalRepo.save(new Profissional(nome, email, senha));
		return "redirect:/login";
	}
	
	@PostMapping("loginUsuario")
	public String login(@RequestParam(name= "email") String email,
			@RequestParam(name= "senha") String senha,
			Model model,
			HttpSession session) {
		if (email.isEmpty() || senha.isEmpty()) {
			model.addAttribute("erroDeAutenticacao", "E-mail ou senha inválidos."); //retorna uma mensagem de erro caso email ou senha estejam em branco
			return "login";
		}
		
		Profissional usuario = profissionalRepo.findByEmail(email) != null ? profissionalRepo.findByEmail(email) : null; //busca as informações do profissional no banco usando o email fornecido
		if ((usuario != null) && usuario.getSenha().contentEquals(senha)) {
			session.setAttribute("userId", usuario.getCodUsuario()); //inicia a seção e preenche e adiciona a variavel userId tendo como valor o código do usuário
			session.setAttribute("userEmail", usuario.getEmail()); //adiciona o email do usuário a seção
			session.setAttribute("tipoUsuario", "Profissional"); //adiciona o tipo do usuário (Profissional ou Empresa) a seção
			session.setMaxInactiveInterval(900); //Tempo de inatividade maximo para encerrar a seção (em segundos)
			return "redirect:/home"; //no login bem sucedido retorna o usuário para a pagina principal
		} else {
			model.addAttribute("erroDeAutenticacao", (usuario == null) ? "Esse e-mail não está cadastrado no sistema." : "Senha incorreta."); //retorna mensagem de erro caso o email ou a senha fornecidas estejam erradas
			return "login";//mantem o usuário na pagina login
		}
	}
	

	
}