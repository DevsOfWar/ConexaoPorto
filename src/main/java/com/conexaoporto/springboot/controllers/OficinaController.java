package com.conexaoporto.springboot.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.conexaoporto.springboot.model.entities.Oficina;
import com.conexaoporto.springboot.model.helpers.Autenticacao;
import com.conexaoporto.springboot.model.repositories.ConteudoRepository;
import com.conexaoporto.springboot.model.repositories.ModuloRepository;
import com.conexaoporto.springboot.model.repositories.OficinaRepository;

@Controller
public class OficinaController {
	
	@Autowired
	OficinaRepository oficinaRepo;
	@Autowired
	ModuloRepository moduloRepo;
	@Autowired
	ConteudoRepository conteudoRepo;
	
	
	//TODO: ISSO APARENTEMENTE É REDUNDANTE, TESTAR E REMOVER SE NECESSARIO
	//O METODO getImagemOficina É O QUE JÁ É USADO NO CÓDIGO DA PAGINA (oficinas e oficinaInside)
	@GetMapping("/oficina/capa/{oficinaId}")//busca a imagem de capa da oficina
	private void getImageOficina(@PathVariable long oficinaId, HttpServletResponse response) throws IOException {
		
		response.setContentType("image/jpeg");
		Oficina oficina = oficinaRepo.findById(oficinaId);
		if (!(oficina.getFotoDeCapa() == null)) {
			InputStream is = new ByteArrayInputStream(oficina.getFotoDeCapa());
			IOUtils.copy(is, response.getOutputStream());
		}
		
	}
	
	@GetMapping({"/oficinas", "/oficinas.html"})
	public String listaOficinas(HttpSession session, Model model) {
		if (!Autenticacao.autenticado(session, "Profissional")) {//so permite acesso se o usuario for do tipo 'Profissional'
			return "redirect:/home";
		}
		
		model.addAttribute("oficinas", oficinaRepo.findAll()); //procura todas as oficinas no banco e manda elas para a pagina
		return "oficinas";
	}
	
	@GetMapping("/oficina/image/{oficinaId}") //busca a imagem de capa da oficina
	private void getImagemOficina(@PathVariable long oficinaId, HttpServletResponse response) throws IOException {
		
		response.setContentType("image/jpeg");
		Oficina oficina = oficinaRepo.findById(oficinaId);
		if (!(oficina.getFotoDeCapa() == null)) {
			InputStream is = new ByteArrayInputStream(oficina.getFotoDeCapa());
			IOUtils.copy(is, response.getOutputStream());
		}
		
	}
	
	
	@GetMapping("/oficinaInside/{idOficina}")//busca dados da oficina com o id informado
	public String getOficinaInside(HttpSession session, Model model, @PathVariable long idOficina) {
		if (!Autenticacao.autenticado(session, "Profissional")) {
			return "redirect:/home";
		}
		model.addAttribute("oficina", oficinaRepo.findById(idOficina));
		return "oficinaInside";
	}
	
}