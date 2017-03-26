package com.desenvolvimento.pos.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

public class ProdutoTest extends BaseCrudTest<Produto>{
	
	private static final String PRODUTO_1 = "Produto 1";
	private static final String ENTIDADE = " Produto ";
	private static final String SELECT_PADRAO = SELECT_E_FROM + ENTIDADE + " e ";
	
	@Test
	public void deveSalvarProduto(){
		Produto entidade = getNovaEntidade();
		assertTrue("Não pode ter id definido", entidade.isTransient());
		salvar(entidade);
		assertNotNull("Deverá ter id definido", entidade.getId());
	}
	
	@Test
	public void deveAtualizarProduto(){
		Produto entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		entidade.setNmProduto("Lapis");
		atualizar(entidade);
		assertEquals("Deverá ter o mesmo valor alterado",entidade.getNmProduto() , "Lapis");
		
		Produto entidadeConsultada = consultaPorId(entidade.getId(), Produto.class);
		assertEquals("Deverá ter o mesmo valor quanto consultado",entidadeConsultada.getNmProduto() , entidade.getNmProduto());
	}
	
	@Test
	public void deveExcluirProduto(){
		Produto entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		Long id = entidade.getId();
		excluir(entidade);
		
		Produto entidadeConsultada = consultaPorId(id, Produto.class);
		assertNull("Deverá ser um objeto nulo", entidadeConsultada);
	}
	
	@Test
	public void deveConsultarTodos(){
		for (int i = 0; i < 10; i++) {
			salvar(getNovaEntidade());
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append(SELECT_PADRAO);
		List<Produto> lista = consultaLista(hql.toString(), Produto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}
	
	@Test
	public void deveConsultarPorAtributo(){
		for (int i = 0; i < 10; i++) {
			salvar(getNovaEntidade());
		}

		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(SELECT_PADRAO);
		hql.append(WHERE_PADRAO);
		hql.append(" AND e.nmProduto = :nmProduto");
		parametros.put("nmProduto", PRODUTO_1);
		
		List<Produto> lista = consultaListaPorParametro(hql.toString(), parametros, Produto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}
	
	@Test
	public void deveConsultarPorLikeAtributo(){
		salvarEntidades(10);
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(SELECT_PADRAO);
		hql.append(WHERE_PADRAO);
		hql.append(" AND e.nmProduto LIKE :nmProduto");
		parametros.put("nmProduto", getAtributoLike("duto"));
		
		List<Produto> lista = consultaListaPorParametro(hql.toString(), parametros, Produto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}

	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarRestringindoBusca() {
		salvarEntidades(10);
		
		Criteria criteria = createCriteria(Produto.class, "m")
					.add(Restrictions.ne("m.id", 3L))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Produto> lista = criteria.list();
		
		assertTrue("Verifica quantidade, mínimo igual 9", lista.size() >= 9);
		lista.forEach(entidade -> assertFalse("Nenhum registro poderá ter o id = 3", entidade.getId() == 3L));
	}
	
	
	//#####################
	//## METÓDOS AUXILIARES
	//#####################
	public void salvarEntidades(Integer qt) {
		for (int i = 0; i < qt; i++) {
			salvar(getNovaEntidade());
		}
	}
	
	public Produto getNovaEntidade() {
		return new Produto(PRODUTO_1, "Descrição", "A0001/2017", new Marca("Marca 1") , new Date());
	}
	
	public EstoqueProduto getEstoqueProduto() {
		EstoqueProduto entidade = new EstoqueProduto();
		entidade.setQtMovimentada(new Double("10"));
		entidade.setQtAnterior(new Double("0"));
		entidade.setQtTotal(new Double("10"));
		entidade.setLote("BR001");
		entidade.setProduto(getNovaEntidade());
		return entidade;
	}
}
