package com.accenture.academico.sistemabanco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.accenture.academico.sistemabanco.model.Agencia;
import com.accenture.academico.sistemabanco.model.Conta;
import com.accenture.academico.sistemabanco.repository.ContaRepository;
import com.accenture.academico.sistemabanco.service.exceptions.DefaultException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private AgenciaService agenciaService;

	public Conta cadastrarConta(Conta conta, Integer idConta) {
		try {
			Agencia agencia = agenciaService.buscarAgenciaPorId(idConta);

		conta.setAgencia(agencia);
		conta.setSaldo(0.0);

		return contaRepository.save(conta);
			
		} catch (Exception e) {
			throw new DefaultException("Não foi possível cadastrar a sua conta "+conta + HttpStatus.BAD_REQUEST);
		}

		
	}

	public List<Conta> buscarTodasAsContas() {
		try {
			return contaRepository.findAll();
		} catch (Exception e) {
			throw new DefaultException("Contas não encontradas "+ HttpStatus.NOT_FOUND);
		}
		
	}

	public Conta buscarContaPorId(Integer id) {
		return contaRepository.findById(id).orElseThrow(() -> new DefaultException("Conta não encontrada, Id: " + id +  HttpStatus.NOT_FOUND));
	}
	
	public Conta buscarContaPorNumero(Integer numConta) {
		return contaRepository.findByNumeroConta(numConta).orElseThrow(() -> new DefaultException("Numero da conta não encontrada, Id: " + numConta + HttpStatus.NOT_FOUND));
	}

	public void removerContaPorId(Integer id) {
		try {
			contaRepository.deleteById(id);
		} catch (Exception e) {
			throw new DefaultException("Não foi possível remover a conta "+ id + HttpStatus.NOT_FOUND);
		}
		
	}

	public void realizarDeposito(Integer idConta, Double valor) {
		try {
			Conta conta = buscarContaPorId(idConta);

		conta.setSaldo(conta.getSaldo() + valor);

		contaRepository.save(conta);
			
		} catch (Exception e) {
			throw new DefaultException("Não foi possível realizar o depósito "+ HttpStatus.BAD_REQUEST);
		}
		
	}
	
	public void realizarSaque(Integer idConta, Double valor) {
		try {
			Conta conta = buscarContaPorId(idConta);
			conta.setSaldo(conta.getSaldo() - valor);
			
			contaRepository.save(conta);
			
		} catch (Exception e) {
			throw new DefaultException("Não foi possível realizar o saque "+ HttpStatus.BAD_REQUEST);
		}
	
	}
	
	public void realizarTransferencia(Conta contaOrigem, Conta contaDestino, Double valor) {
		try {
			contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
		contaDestino.setSaldo(contaDestino.getSaldo() + valor);
		
		contaRepository.save(contaOrigem);
		contaRepository.save(contaDestino);
			
		} catch (Exception e) {
			throw new DefaultException("Não foi possível realizar a transferencia "+ HttpStatus.BAD_REQUEST);
		}
		
	}

}
