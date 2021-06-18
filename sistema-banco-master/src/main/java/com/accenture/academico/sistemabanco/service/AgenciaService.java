package com.accenture.academico.sistemabanco.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.accenture.academico.sistemabanco.model.Agencia;
import com.accenture.academico.sistemabanco.repository.AgenciaRepository;
import com.accenture.academico.sistemabanco.service.exceptions.DefaultException;


@Service
public class AgenciaService {

	@Autowired
	private AgenciaRepository repository;

	public Agencia cadastrarAgencia(Agencia agencia)  {
		try {
			return repository.save(agencia);
		} catch (Exception e) {
			throw new DefaultException("Não foi possível cadastrar a Agencia "+agencia+HttpStatus.BAD_REQUEST);
		}

		
	}

	public List<Agencia> buscarTodasAsAgencias() {
		try {
			
		return repository.findAll();
		} catch (Exception e) {
			throw new DefaultException("Agencias não encontradas "+ HttpStatus.BAD_REQUEST);
		}


	}

	public Agencia buscarAgenciaPorId(Integer id) {
		return repository.findById(id).orElseThrow(() -> new DefaultException("Agência não encontrada, Id: " + id+ HttpStatus.NOT_FOUND));
	}

	public Agencia atualizarAgencia(Agencia agencia) {
		try {
			buscarAgenciaPorId(agencia.getIdAgencia());
			return repository.save(agencia);
		} catch (Exception e) {
			throw new DefaultException("Não foi possível atualizar a agência "+agencia+ HttpStatus.NOT_FOUND);
		}

	}

	public void removerAgenciaPorId(Integer id) {
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new DefaultException("Não foi possível excluir a agência "+id+ HttpStatus.NOT_FOUND);
		}

	}

}
