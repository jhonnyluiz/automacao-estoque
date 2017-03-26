package com.desenvolvimento.pos.entity;

import static org.junit.Assert.*;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.LazyInitializationException;
import org.junit.Test;

public class ClienteTest extends BaseCrudTest<Cliente>{
	private static final String CPF_PADRAO = "012.345.678-99";

	@Test
	public void deveSalvarCliente(){
		Cliente cliente = getNovoCliente();
		assertTrue("Não pode ter id definido", cliente.isTransient());
		salvar(cliente);
		assertNotNull("Deverá ter id definido", cliente.getId());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void deveConsultarCpf(){
		salvar(getNovoCliente());
		String filtro = "Luiz";
		
		Query query = getEm().createQuery("SELECT c.cpf FROM Cliente c WHERE c.nome LIKE :nome");
		query.setParameter("nome", "%"+filtro+"%");
		List<String> clientes = query.getResultList();
		
		assertFalse("Verificar se encontrou objeto por cpf", clientes.isEmpty());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void deveConsultarComIdNome(){
		salvar(getNovoCliente());
		
		Query query = getEm().createQuery("SELECT new Cliente(c.id, c.nome) FROM Cliente c WHERE c.cpf = :cpf");
		query.setParameter("cpf", CPF_PADRAO);
		List<Cliente> clientes = query.getResultList();
		
		assertFalse("Não pode ter id definido", clientes.isEmpty());
		
		for (Cliente cliente : clientes) {
			assertNull("Verificar se o cpf do cliente está null", cliente.getCpf());
			
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void deveConsultarObjectComIdNome(){
		salvar(getNovoCliente());
		
		Query query = getEm().createQuery("SELECT c.id, c.nome FROM Cliente c WHERE c.cpf = :cpf");
		query.setParameter("cpf", CPF_PADRAO);
		List<Object[]> clientes = query.getResultList();
		
		assertFalse("Não pode ter id definido", clientes.isEmpty());
		
		for (Object[] cliente : clientes) {
			assertTrue("Verificar que o primeiro item é um ID", cliente[0] instanceof Long);
			assertTrue("Verificar se o segundo item é um nome", cliente[1] instanceof String);
			
			assertNotNull("Verificar se criou uma entidade", new Cliente((Long) cliente[0], (String) cliente[1]));
		}
	}
	
	@Test
	public void deveVerificarExistenciaCliente(){
		salvar(getNovoCliente());
		
		Query query = getEm().createQuery("SELECT COUNT(c.id) FROM Cliente c WHERE c.cpf = :cpf");
		query.setParameter("cpf", CPF_PADRAO);
		Long qtdResultado = (Long) query.getSingleResult();
		
		assertTrue("Verifica se encontrou registro", qtdResultado > 0);
	}
	
	@Test(expected=NoResultException.class)
	public void deveRetornarExcecaoNenhumRegistroEncontrado(){
		salvar(getNovoCliente());
		salvar(getNovoCliente());
		
		Query query = getEm().createQuery("SELECT c.id FROM Cliente c WHERE c.cpf = :cpf");
		query.setParameter("cpf", "000.000.000-00");
		query.getSingleResult();
		
		fail("o metodo getResultList deveria ter retornado exceção");
	}

	private Cliente getNovoCliente() {
		return new Cliente("Jhonny Luiz Cabral",CPF_PADRAO);
	}
}
