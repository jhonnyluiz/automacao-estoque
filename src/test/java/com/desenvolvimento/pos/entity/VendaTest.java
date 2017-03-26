package com.desenvolvimento.pos.entity;

import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Test;

import com.desenvolvimento.pos.util.JPAUtil;

public class VendaTest extends BaseCrudTest<Venda>{

	private static final String CPF_PADRAO = "012.345.678-99";
	
	@Test
	public void deveSalvarVendaComRelacionamentoEmCascata() {
		Venda venda = criarVenda();
		venda.getProdutos().add(criarProduto("Notebook","Dell"));
		venda.getProdutos().add(criarProduto("Mouse","Razer"));
		
		assertTrue("Verifica se não existe id", venda.isTransient());
		
		salvar(venda);

		assertFalse("Verifica deve ter id", venda.isTransient());
		assertFalse("Verifica deve ter id", venda.getCliente().isTransient());
		
		for (Produto2 produto : venda.getProdutos()) {
			assertFalse("Verifica deve ter id", produto.isTransient());
		}
	}
	
	@Test(expected= IllegalStateException.class)
	public void deveRetornarExcecao() {
		Venda venda = criarVenda();
		venda.getProdutos().add(criarProduto("Notebook","Dell"));
		venda.getProdutos().add(criarProduto("Mouse","Razer"));
		
		assertTrue("Verifica se não existe id", venda.isTransient());
		
		atualizar(venda);
		
		fail("Deveria ter retornado exceção IllegalStateException, uma nova venda com atribudos transient");
	}
	
	@Test
	public void deveConsultarQtdProdutosVendidos() {
		Venda venda = criarVenda("001.001.001-01");
		for (int i = 0; i < 10; i++) {
			venda.getProdutos().add(criarProduto("Produto"+i,"Marca"+i));
		}
		assertTrue("Verifica se não existe id", venda.isTransient());
		salvar(venda);

		int qtdProdutoVendido = venda.getProdutos().size();
		assertTrue("Verifica deve ter id", qtdProdutoVendido >0);
		
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT COUNT(p.id) ");
		jpql.append(" FROM Venda v ");
		jpql.append(" INNER JOIN v.produtos p ");
		jpql.append(" INNER JOIN v.cliente c");
		jpql.append(" WHERE c.cpf = :cpf");
		
		Query query = getEm().createQuery(jpql.toString());
		query.setParameter("cpf", "001.001.001-01");
		Long qtdProdutosVenda = (Long) query.getSingleResult();
		
		assertEquals("A quantidade deverá ser a mesma que a vendida", qtdProdutoVendido, qtdProdutosVenda.intValue());
	}
	
	
	private Produto2 criarProduto(String nome, String fabricante) {
		return new Produto2(nome, fabricante);
	}
	
	private Venda criarVenda() {
		return criarVenda(null);
	}
	
	private Venda criarVenda(String cpf) {
		String c = cpf == null ? CPF_PADRAO: cpf;
		Cliente cliente = new Cliente("Jhonny Luiz Cabral", c);
		assertTrue("Verifica se não existe id", cliente.isTransient());
		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setDataHora(new Date());
		return venda;
	}
	
	@AfterClass
	public static void deveLimparBaseTeste() {
		EntityManager em = JPAUtil.INSTANCE.getEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery("DELETE FROM Venda v");
		int qtdRegistros = query.executeUpdate();
		em.getTransaction().commit();
		assertTrue("Certifica que os registros foram apagados", qtdRegistros >0);
	}
}
