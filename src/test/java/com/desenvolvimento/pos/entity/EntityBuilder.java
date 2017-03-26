package com.desenvolvimento.pos.entity;

import static org.junit.Assert.assertTrue;

import java.util.Date;

public class EntityBuilder<E> extends BaseCrudTest<E>{

	public static final String CPF_PADRAO = "012.345.678-99";
	
	public void salvarVendas(int quantidade) {
		getEm().getTransaction().begin();
		
		for (int i = 0; i < quantidade; i++) {
			Venda venda = salvarVenda();
			venda.getProdutos().add(salvarProduto("Notebook", "Dell"));
			venda.getProdutos().add(salvarProduto("Mouse", "Razer"));
			getEm().persist(venda);
		}
		getEm().getTransaction().commit();
	}
	
	public void salvarClientes(int quantidade) {
		getEm().getTransaction().begin();
		for (int i = 0; i < quantidade; i++) {
			Cliente cliente = new Cliente();
			cliente.setNome("Jhonny");
			cliente.setCpf(CPF_PADRAO);
			getEm().persist(cliente);
		}
		getEm().getTransaction().commit();
	}

	public void salvarProdutos(int quantidade) {
		getEm().getTransaction().begin();
		
		for (int i = 0; i < quantidade; i++) {
			Produto2 produto = salvarProduto("Notebook", "Dell");
			getEm().persist(produto);
		}
		getEm().getTransaction().commit();
	}
	
	
	private Produto2 salvarProduto(String nome, String fabricante) {
		return new Produto2(nome, fabricante);
	}
	
	private Venda salvarVenda() {
		return salvarVenda(null);
	}
	
	private Venda salvarVenda(String cpf) {
		String c = cpf == null ? CPF_PADRAO: cpf;
		Cliente cliente = new Cliente("Jhonny Luiz Cabral", c);
		assertTrue("Verifica se não existe id", cliente.isTransient());
		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setDataHora(new Date());
		return venda;
	}
	
}
