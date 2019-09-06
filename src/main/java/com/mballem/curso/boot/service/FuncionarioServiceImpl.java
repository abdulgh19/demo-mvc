package com.mballem.curso.boot.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.curso.boot.dao.FuncionarioDao;
import com.mballem.curso.boot.domain.Funcionario;

@Service
@Transactional(readOnly = false)
public class FuncionarioServiceImpl implements FuncionarioService {

	@Autowired
	private FuncionarioDao funcDao;

	@Override
	public void salvar(Funcionario funcionario) {
		funcDao.save(funcionario);

	}

	@Override
	public void editar(Funcionario funcionario) {
		funcDao.update(funcionario);

	}

	@Override
	public void excluir(Long id) {
		funcDao.delete(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Funcionario buscarPorId(Long id) {

		return funcDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarTodos() {

		return funcDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarPorNome(String nome) {
		
		return funcDao.findByNome(nome);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarPorCargo(Long id) {
		
		return funcDao.findByCargoId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Funcionario> buscarPorDatas(LocalDate entrada, LocalDate saida) {

		if(entrada != null && saida !=null) {
			return funcDao.findByDataEntradaDataSaida(entrada, saida);
		}else if (entrada != null) {
			return funcDao.findByDataEntrada(entrada);
		} else if (saida !=null) {
			return funcDao.findByDataSaida(saida);
		} else {
			return new ArrayList<>();
		}		
	}

}
