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
import com.conexaoporto.springboot.model.repositories.OficinaRepository;

@Controller
public class OficinaController {
	
	@Autowired
	OficinaRepository oficinaRepo;
	
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
		if (!Autenticacao.autenticado(session, "Profissional")) {
			return "redirect:/home";
		}
		
		model.addAttribute("oficinas", oficinaRepo.findAll());
		return "oficinas";
	}
	
	
}
