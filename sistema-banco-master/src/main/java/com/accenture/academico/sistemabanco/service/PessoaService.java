package com.accenture.academico.sistemabanco.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.accenture.academico.sistemabanco.model.Conta;
import com.accenture.academico.sistemabanco.model.Pessoa;
import com.accenture.academico.sistemabanco.repository.PessoaRepository;
import com.accenture.academico.sistemabanco.service.exceptions.DefaultException;


@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ContaService contaService;
	
	public Pessoa cadastrarPessoa(Pessoa pessoa, Integer idConta) { // salva no repositorio
		try {
			Conta conta = contaService.buscarContaPorId(idConta);
		
		pessoa.setConta(conta);
		
		return pessoaRepository.save(pessoa);
			
		} catch (Exception e) {
			throw new DefaultException("Não foi possível excluir a pessoa, "+"pessoa: "+pessoa+ HttpStatus.CONFLICT);
		}
		
	}
	
	public List<Pessoa> listarTodasAsPessoas() {
		try {
			return pessoaRepository.findAll(); // armazena em lista as pessoas cadastradas 
		} catch (Exception e) {
			throw new DefaultException("Pessoas não encontradas "+ HttpStatus.NOT_FOUND);
		}
		
	}
	
	public Pessoa buscarPessoaPorId(Integer id) {
		return pessoaRepository.findById(id).orElseThrow(() -> new DefaultException("Pessoa não encontrada, Id: " + id+ HttpStatus.NOT_FOUND));
	}
	
	public Pessoa alterarNomeETelefone(Pessoa pessoa, Integer id) {

		
			try {
				Pessoa pessoaAtualizada = buscarPessoaPorId(id);
			
			pessoaAtualizada.setNome(pessoa.getNome());
			pessoaAtualizada.setTelefone(pessoa.getTelefone());
			
			return pessoaRepository.save(pessoaAtualizada);
				
			} catch (Exception e) {
				throw new DefaultException("Não foi possível alterar os seus dados "+id + pessoa + HttpStatus.BAD_REQUEST);
			}
		}
	
		
	
	
	public void removerPessoaPorId(Integer id) {
		try{
			pessoaRepository.deleteById(id);
		}catch (Exception e) {
			throw new DefaultException("Não foi possível excluir a pessoa "+id+ HttpStatus.NOT_FOUND);
		}
		
	}

	

	
}
