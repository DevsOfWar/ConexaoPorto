package com.conexaoporto.springboot.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	BCryptPasswordEncoder bcrypt; //gerencia a codificação/decodificação das senhas
	
	
	@PostMapping("/CadastrarProfissional")
	public String cadastro (@RequestParam(name= "nome") String nome,
			@RequestParam(name="senha") String senha,
			@RequestParam(name="email") String email,
			Model model) {
		
		if (profissionalRepo.findByEmail(email) != null) {
			model.addAttribute("emailError", "O email informado já esta cadastrado."); //retorna uma msg de erro caso o email informado já esteja cadastrado no sistema
			return "cadastro";
		}
		
		senha = bcrypt.encode(senha); //adiciona um salt a senha e codifica ela
		profissionalRepo.save(new Profissional (nome, email, senha)); //salva os dados do usuario no banco de dados
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
		if ((usuario != null) && bcrypt.matches(senha, usuario.getSenha())) {//bcrypt compara a senha inserida com a senha guardada no banco para permitir autenticacao
			session.setAttribute("userId", usuario.getCodUsuario()); //inicia a sessão e preenche e adiciona a variavel userId tendo como valor o código do usuário
			session.setAttribute("userEmail", usuario.getEmail()); //adiciona o email do usuário a sessão
			session.setAttribute("tipoUsuario", "Profissional"); //adiciona o tipo do usuário (Profissional ou Empresa) a sessão
			session.setMaxInactiveInterval(900); //Tempo de inatividade maximo para encerrar a sessão (em segundos)
			return "redirect:/home"; //no login bem sucedido retorna o usuário para a pagina principal
		} else {
			model.addAttribute("erroDeAutenticacao", (usuario == null) ? "Esse e-mail não está cadastrado no sistema." : "Senha incorreta."); //retorna mensagem de erro caso o email ou a senha fornecidas estejam erradas
			return "login";//mantem o usuário na pagina login
		}
	}
	

	
}