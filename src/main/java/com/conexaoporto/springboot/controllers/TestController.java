package com.conexaoporto.springboot.controllers;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.conexaoporto.springboot.model.entities.Empresa;
import com.conexaoporto.springboot.model.entities.Evento;
import com.conexaoporto.springboot.model.entities.Oficina;
import com.conexaoporto.springboot.model.entities.Profissional;
import com.conexaoporto.springboot.model.repositories.EmpresaRepository;
import com.conexaoporto.springboot.model.repositories.OficinaRepository;
import com.conexaoporto.springboot.model.repositories.ProfissionalRepository;
import com.conexaoporto.springboot.model.repositories.UsuarioRepository;

@Controller
public class TestController {// Esse controller contêm exemplos relacionados a area de test do site
	@Autowired
	ProfissionalRepository profissionalRepo;// contem os metodos que vão fazer o CRUD
	@Autowired
	EmpresaRepository empresaRepo;
	@Autowired
	UsuarioRepository usuarioRepo;
	@Autowired
	OficinaRepository oficinaRepo;
	
	@GetMapping(value = "/test")
	public String testRedirect() {
		return "test/home";
	}

	@GetMapping("/testNovoUsuario") // <- anotação para tratamento de metodos GET
	public String mostrarCadastro() {
		return "test/novo-usuario"; 
	}

	// EXEMPLO CADASTRO
	@PostMapping("/testCadastroUsuario") // <- anotação para tratamento de metodos POST
	private String novoProfissional(@RequestParam(name= "nome") String nome,
			@RequestParam(name= "email") String email,
			@RequestParam(name= "senha") String senha,
			@RequestParam(name= "tipoUsuario") String tipo,
			@RequestParam(name= "cnpj") String cnpj,
			Model model) { // Model é para passar dados de volta para o thymeleaf
		
		if (usuarioRepo.findByEmail(email) != null) {
			model.addAttribute("emailError", "email já cadastrado");
			return "test/novo-usuario";
		} else if (tipo.contentEquals("Empresa")) {
			empresaRepo.save(new Empresa(nome, email, senha, cnpj));
			return "redirect:/testListarUsuarios";
		} else if (tipo.contentEquals("Profissional")) {
			profissionalRepo.save(new Profissional(nome, email, senha));// salva no banco
		} else {
			model.addAttribute("emailError", "ISSO DEVERIA SER IMPOSSIVEL. ¯\\_(ツ)_/¯");
			return "test/novo-usuario";
		}

		
		
		return "redirect:/testListarUsuarios";
	}

	// EXEMPLO LISTAR DADOS (todos)
	@GetMapping("/testListarUsuarios")
	public String listaProfissionais(Model model) {// Model é onde são guardados os dados para retornar ao thymeleaf
		model.addAttribute("profissionais", profissionalRepo.findAll());// o primeiro parametro de addAtribute é o nome do atributo que vai ser enviado ao thymeleaf
		model.addAttribute("empresas", empresaRepo.findAll());
		return "test/lista-usuarios";
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
	public String loginProfissional() {

		return "test/login-profissional";
	}

	@PostMapping("/testLogin")
	public String loginProfissional(@RequestParam(name = "email") String email,
			@RequestParam(name = "senha") String senha, Model model, HttpSession session) { // chama os dados sobre 
		Profissional usuario = profissionalRepo.findByEmail(email) != null ? profissionalRepo.findByEmail(email) : null;
		if ((usuario != null) && usuario.getSenha().contentEquals(senha)) {
			session.setAttribute("auth", usuario.getCodUsuario()); // autenticacao usando variavel de sessao
			session.setAttribute("tipoUsuario", "Profissional");
			session.setMaxInactiveInterval(300);// tempo de timeout da seção em segundos
		} else {
			return "test/login-profissional";
		}
		return "redirect:/test";
	}
	
	@GetMapping("/testLoginEmpresa")
	public String loginEmpresarial() {

		return "test/login-empresa";
	}
	
	@PostMapping("/testLoginEmpresa")
	public String loginEmpresarial(@RequestParam(name = "email") String email,
			@RequestParam(name = "senha") String senha, Model model, HttpSession session) { // chama os dados sobre 
		Empresa usuario = empresaRepo.findByEmail(email) != null ? empresaRepo.findByEmail(email) : null;
		if ((usuario != null) && usuario.getSenha().contentEquals(senha)) {
			session.setAttribute("auth", usuario.getCodUsuario()); // autenticacao usando variavel de sessao
			session.setAttribute("tipoUsuario", "Empresa");
			session.setMaxInactiveInterval(300);// tempo de timeout da seção em segundos
		} else {
			return "test/login-empresa";
		}
		return "redirect:/test";
	}
	
	@GetMapping("/testGerenciarOficinas")
	public String gerenciarOficinas(HttpSession session, Model model) {

		if (!autenticado(session, "Empresa")) {
			return "redirect:/test";
		}
			
		return "test/gerenciar-oficinas";
	}
	
	@GetMapping("/testCadastroOficina")
	public String cadastroOficina(HttpSession session, Model model) {
		if (!autenticado(session, "Empresa")) {
			return "redirect:/test";
		}
		return "test/nova-oficina";
	}
	
	@PostMapping("/testCadastroOficina")
	public String cadastroOficina(@RequestParam(name= "nome") String nome,
			@RequestParam(name= "plataforma") String plataforma,
			@RequestParam(name= "linkPlataforma") String linkPlataforma,
			@RequestParam(name= "pontuacao") int pontuacao,
			@RequestParam(name= "cargaHoraria") int cargaHoraria,
			@RequestParam(name= "dataInicio") Date dataInicio,
			@RequestParam(name= "dataTermino") Date dataTermino,
			HttpSession session,
			Model model) {
		Oficina oficina = new Oficina();
		Empresa empresa = empresaRepo.findById(Long.parseLong(session.getAttribute("auth").toString()));
		
		oficina.setAutor(empresa.getNome());
		oficina.setNomeDoEvento(nome);
		oficina.setPlataforma(plataforma);
		oficina.setLinkDeAcesso(linkPlataforma);
		oficina.setPontuacao(pontuacao);
		oficina.setCargaHoraria(cargaHoraria);
		oficina.setDataInicio(dataInicio);
		oficina.setDataTermino(dataTermino);
		oficina.setEmpresa(empresa);
		try {
			oficinaRepo.save(oficina);
		} catch (Exception ex) {
			model.addAttribute("error", "Ocorreu um erro ao se comunicar com o servidor.");
			return "test/nova-oficina";
		}
		
		return "redirect:/testGerenciarOficinas";
	}
	
	@GetMapping("/testMinhasOficinas")
	public String empresaOficinas(HttpSession session, Model model) {
		if (!autenticado(session, "Empresa")) {
			return "redirect:/test";
		}
		
		model.addAttribute("oficinas", oficinaRepo.findByUserId(Long.parseLong(session.getAttribute("auth").toString())));
		return "test/minhas-oficinas";
	}
	
	@GetMapping("/testAtualizarOficina/{idOficina}")
	public String atualizarOficinas(@PathVariable long idOficina, HttpSession session, Model model) {

		if (!autenticado(session, "Empresa")) {
			return "redirect:/test";
		}
		
		model.addAttribute("oficina", oficinaRepo.findById(idOficina));
		session.setAttribute("idOficina", idOficina);
		return "test/atualizar-oficina";
	}
	
	@PostMapping("/testAtualizarOficina/testAtualizarOficina")
	public String atualizarOficinas(@RequestParam(name ="descricao") String descricao,
			@RequestParam(name ="foto", required = false) MultipartFile foto,
			@RequestParam(name ="linkDeAcesso", required = false) String linkDeAcesso,
			@RequestParam(name ="cargaHoraria") int cargaHoraria,
			@RequestParam(name ="pontuacao") int pontuacao,
			@RequestParam(name ="dataInicio") Date dataInicio,
			@RequestParam(name ="dataTermino") Date dataTermino,
			HttpSession session,
			Model model) {
		
		
		long idOficina = Long.parseLong(session.getAttribute("idOficina").toString());
		long idUsuario = Long.parseLong(session.getAttribute("auth").toString());
		Oficina oficina = oficinaRepo.findById(idOficina);
		if (oficina.getEmpresa().getCodUsuario() != idUsuario) {
			session.invalidate();
			return "redirect:/test";
		}
		
		oficina.setDescricao(!descricao.isEmpty() ? descricao : oficina.getDescricao());
		oficina.setLinkDeAcesso(linkDeAcesso != null ? linkDeAcesso : oficina.getLinkDeAcesso());
		oficina.setCargaHoraria(!(cargaHoraria <= 0) ? cargaHoraria : oficina.getCargaHoraria());
		oficina.setPontuacao(!(pontuacao <= 0) ? pontuacao : oficina.getPontuacao());
		oficina.setDataInicio(dataInicio);
		oficina.setDataTermino(dataTermino);
		if (foto != null) {
			try {
				oficina.setFotoDeCapa(foto.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		oficinaRepo.save(oficina);
		return "redirect:/testMinhasOficinas";
	}
	
	private boolean autenticado(HttpSession session, String tipo) {
		if ((session.getAttribute("tipoUsuario") == null) || !session.getAttribute("tipoUsuario").toString().contentEquals(tipo)) {
			return false;
		} else {
			return true;
		}
	}
	
	
	@GetMapping("/userLogout")
	public String userLogout(HttpSession session) {
		session.invalidate(); //encerra a seção
		return "redirect:/test";
	}
}
