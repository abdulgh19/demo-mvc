package com.mballem.curso.boot.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService depService;

	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) { // Este metodo abre a pagina de cadastrar departamentos
		return "departamento/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) { // Este metodo abre a pagina de listar departamentos
		model.addAttribute("departamentos", depService.buscarTodos()); // departamentos e' o nome da variavel que e'
																		// enviada la para a pagina
		return "departamento/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento,BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			return "departamento/cadastro";
		}
		depService.salvar(departamento);
		attr.addFlashAttribute("success", "Departamento registado com Sucesso!");
		return "redirect:/departamentos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) { //Chama a tela de cadastro com o(s) campo(s) preenchido(s)
		model.addAttribute("departamento", depService.buscarPorId(id));
		return "departamento/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Departamento departamento,BindingResult result , RedirectAttributes attr) {
		if(result.hasErrors()) {
			return "departamento/cadastro";
		}
		depService.editar(departamento);
		attr.addFlashAttribute("success", "Departamento actualizado com Sucesso!");
		return "redirect:/departamentos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		if (depService.departamentoTemCargo(id)) {
			model.addAttribute("fail", "Departamento nao removido. Possui cargo(s) associado(s)!");
		}
		else {
			depService.excluir(id);
			model.addAttribute("success", "Departamento excluido com Sucesso!");
		}

		return listar(model);

	}

}
