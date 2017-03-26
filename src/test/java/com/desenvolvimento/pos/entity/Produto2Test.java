package com.desenvolvimento.pos.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

public class Produto2Test extends BaseCrudTest<Produto2>{
	
	
	private static final String SELECT_P_FROM_PRODUTO_P = "SELECT p FROM Produto2 p";

	@Test
	public void deveSalvarProduto(){
		Produto2 produto = getNovoProduto();
		assertTrue("Não pode ter id definido", produto.isTransient());
		salvar(produto);
		assertNotNull("Deverá ter id definido", produto.getId());
	}
	
	@Test
	public void deveListarProdutos(){
		for (int i = 0; i < 10; i++) {
			salvar(getNovoProduto());
		}
		
		List<Produto2> produtos = consultaLista(SELECT_P_FROM_PRODUTO_P, Produto2.class);
		
		assertFalse("Não deverá ser uma lista vazia", produtos.isEmpty());
		assertTrue("Verificar se exitem 10 ou mais registros", produtos.size() >= 10);
	}
	
	@Test
	public void deveAlterarProduto() {
		salvar(getNovoProduto());
		
		Produto2 produto = consultaUnica(SELECT_P_FROM_PRODUTO_P, Produto2.class);
		assertNotNull("Não deverá ser vazio", produto);
		Integer versao = produto.getVersion();
		
		produto.setFabricante("Sony");
		Produto2 produtoAlterado = atualizar(produto);
		
		assertNotEquals("Verificar se as versões são diferentes", versao, produtoAlterado.getVersion());
	}

	@Test
	public void deveExcluirProduto() {
		salvar(getNovoProduto());
		
		Produto2 produtoIncluido = consultaUnica(SELECT_P_FROM_PRODUTO_P, Produto2.class);
		assertNotNull("Não deverá ser vazio", produtoIncluido);
		Long id = produtoIncluido.getId();
		
		Produto2 produto = consultaPorId(id, Produto2.class);
		excluir(produto);

		Produto2 produtoExcluido = consultaPorId(id, Produto2.class);
		
		assertNull("Verificar se não foi encontrado nenhum produto", produtoExcluido);
	}

	@Test
	public void deveLimparBaseTeste() {
		salvar(getNovoProduto());
		begin();
		Query query = getEm().createQuery("DELETE FROM Produto2 p");
		int qtdRegistrosExcluidos = query.executeUpdate();
		commit();
		
		assertTrue("Verifica se foi excluido registros", qtdRegistrosExcluidos > 0);
	}
	
	/**
	 * Método responsável por retornar uma nova instancia de um produto.
	 * 
	 * @return
	 */
	public Produto2 getNovoProduto() {
		return new Produto2("Produto Teste","Fabricante Teste");
	}
}
