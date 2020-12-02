package com.conexaoporto.springboot.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.conexaoporto.springboot.model.entities.Profissional;
import com.conexaoporto.springboot.model.entities.Usuario;
import com.conexaoporto.springboot.model.helpers.AutenticacaoHelper;
import com.conexaoporto.springboot.model.repositories.ProfissionalRepository;

@Controller
public class TestController {// Esse controller contêm exemplos relacionados a area de test do site
	@Autowired
	ProfissionalRepository profissionalRepo;// contem os metodos que vão fazer o CRUD

	@GetMapping(value = "/test")
	public String testRedirect() {
		return "test/home";
	}

	@GetMapping("/testNovoProfissional") // <- anotação para tratamento de metodos GET
	public String mostrarCadastro(Profissional profissional) {// A magica do spring entende a
		return "test/novo-profissional";// o retorno deve ser uma string com o nome da pagina que você quer acessar
	}

	// EXEMPLO CADASTRO
	@PostMapping("/testCadastroProfissional") // <- anotação para tratamento de metodos POST
	private String novoProfissional(@Valid Profissional profissional, BindingResult result, Model model) { // BidingResult para validação de dados
																										   // Model é para passar dados de volta para o thymeleaf
		if (profissionalRepo.findByEmail(profissional.getEmail()) != null) {
			result.addError(new ObjectError("profissional.email", "email já cadastrado"));// TODO:descobrir como retornar esse erro para o thymeleaf e exibir na pagina
		}

		if (result.hasErrors()) {
			return "test/novo-profissional";
		}
		profissionalRepo.save(profissional);// salva no banco
		return "redirect:/testListarProfissionais";
	}

	// EXEMPLO LISTAR DADOS (todos)
	@GetMapping("/testListarProfissionais")
	public String listaProfissionais(Model model) {// Model é onde são guardados os dados para retornar ao thymeleaf
		model.addAttribute("profissionais", profissionalRepo.findAll());// o primeiro parametro de addAtribute é o nome
																		// do atributo que vai ser enviado ao thymeleaf
		return "test/lista-profissionais";
	}

	// EXEMPLO LISTAR DADOS (especifico)
	@GetMapping("/testPerfil")
	public String exibirDadosProfissional(HttpSession session, Model model) {
		
		model.addAttribute("usuario", profissionalRepo.findById(Long.parseLong(session.getAttribute("auth").toString())));
		return "test/perfil-profissional";
	}

	@GetMapping("/testPerfil/image/{userId}")
	private void showImage(@PathVariable long userId, HttpServletResponse response) throws IOException {
		
		response.setContentType("image/jpeg");
		Profissional profissional = profissionalRepo.findById(userId);
		if (!(profissional.getFoto() == null)) {
			InputStream is = new ByteArrayInputStream(profissional.getFoto());
			IOUtils.copy(is, response.getOutputStream());
		}
		
	}
	
	
	@PostMapping("/testpreloadAtualizarDados") // Isso é um 'intermediario' para carregar os dados do usuario e retornar eles para a pagina certa
	public String atualizarDadosProfissional2(@RequestParam(name = "codUsuario") long id, Model model) {// @RequestParam associa a variavel da barra de endereços ao parametro fornecido usando a tag name do html

		model.addAttribute("usuario", profissionalRepo.findById(id));// Encontra o usuario baseado no id e salva as informações dele no model
		return "test/atualizar-profissional";
	}

	// EXEMPLO ATUALIZAR DADOS
	@PostMapping("/testAtualizarProfissional")
	public String atualizarDadosProfissional(@RequestParam(name = "codUsuario") long id,
			@RequestParam(name = "nome") String nome, @RequestParam(name = "telefone") String telefone,
			@RequestParam(name = "senha") String senha, @RequestParam(name = "descricao") String descricao,
			@RequestParam(name = "areaDeInteresse") String areaDeInteresse,
			@RequestParam(name = "ocupacao") String ocupacao,
			@RequestParam(name = "nivelDeEscolaridade") String nivelDeEscolaridade,
			@RequestParam(name= "foto") MultipartFile foto,
			Model model) {
		Profissional profissional = profissionalRepo.findById(id); // Encontra os dados do ususario usando o ID
		// Atualiza os valores do objeto com os mais recente,
		// se o valor informado estiver em branco ele mantem o valor anterior
		profissional.setNome(!nome.isEmpty() ? nome : profissional.getNome());
		if (!foto.isEmpty()) {
			try {
				profissional.setFoto(foto.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		profissional.setSenha(!senha.isEmpty() ? senha : profissional.getSenha());
		profissional.setTelefone(!telefone.isEmpty() ? telefone : profissional.getTelefone());
		profissional.setDescricao(!descricao.isEmpty() ? descricao : profissional.getDescricao());
		profissional.setAreaDeInteresse(!areaDeInteresse.isEmpty() ? areaDeInteresse : profissional.getAreaDeInteresse());
		profissional.setOcupacao(!ocupacao.isEmpty() ? ocupacao : profissional.getOcupacao());
		profissional.setNivelDeEscolaridade(!nivelDeEscolaridade.isEmpty() ? nivelDeEscolaridade : profissional.getNivelDeEscolaridade());

		profissionalRepo.save(profissional);
		model.addAttribute("usuario", profissionalRepo.findById(id));
		return ("test/perfil-profissional");
	}

	@GetMapping("/testLogin")
	public String loginProfissional(Model model) {

		return "test/login-profissional";
	}

	@PostMapping("/testLogin")
	public String loginProfissional(@RequestParam(name = "email") String email,
			@RequestParam(name = "senha") String senha, Model model, HttpSession session) { // chama os dados sobre 
		Usuario usuario = profissionalRepo.findByEmail(email) != null ? (Usuario) profissionalRepo.findByEmail(email) : null;
		if ((usuario != null) && usuario.getSenha().contentEquals(senha)) {
			session.setAttribute("auth", usuario.getCodUsuario()); // autenticacao usando variavel de sessao
			session.setMaxInactiveInterval(300);// tempo de timeout da seção em segundos
		} else {
			return "test/login-profissional";
		}
		return "test/home";
	}

	@GetMapping("/userLogout")
	public String userLogout(HttpSession session) {
		session.invalidate(); //encerra a seção
		return "test/home";
	}
}
