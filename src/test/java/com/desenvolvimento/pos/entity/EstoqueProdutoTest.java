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

public class EstoqueProdutoTest extends BaseCrudTest<EstoqueProduto>{
	
	private static final String LOTE_PADRAO = "BR001";
	private static final String ENTIDADE = " EstoqueProduto ";
	private static final String SELECT_PADRAO = SELECT_E_FROM + ENTIDADE + " e ";
	
	@Test
	public void deveSalvarEstoqueProduto(){
		EstoqueProduto entidade = getNovaEntidade();
		assertTrue("Não pode ter id definido", entidade.isTransient());
		salvar(entidade);
		assertNotNull("Deverá ter id definido", entidade.getId());
	}
	
	@Test
	public void deveAtualizarEstoqueProduto(){
		EstoqueProduto entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		entidade.setLote("BR002");
		atualizar(entidade);
		assertEquals("Deverá ter o mesmo valor alterado",entidade.getLote() , "BR002");
		
		EstoqueProduto entidadeConsultada = consultaPorId(entidade.getId(), EstoqueProduto.class);
		assertEquals("Deverá ter o mesmo valor quanto consultado",entidadeConsultada.getLote() , entidade.getLote());
	}
	
	@Test
	public void deveExcluirEstoqueProduto(){
		EstoqueProduto entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		Long id = entidade.getId();
		excluir(entidade);
		
		EstoqueProduto entidadeConsultada = consultaPorId(id, EstoqueProduto.class);
		assertNull("Deverá ser um objeto nulo", entidadeConsultada);
	}
	
	@Test
	public void deveConsultarTodos(){
		for (int i = 0; i < 10; i++) {
			salvar(getNovaEntidade());
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append(SELECT_PADRAO);
		List<EstoqueProduto> lista = consultaLista(hql.toString(), EstoqueProduto.class);
		
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
		hql.append(" AND e.lote = :lote");
		parametros.put("lote", LOTE_PADRAO);
		
		List<EstoqueProduto> lista = consultaListaPorParametro(hql.toString(), parametros, EstoqueProduto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}
	
	@Test
	public void deveConsultarPorLikeAtributo(){
		salvarEntidades(10);
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(SELECT_PADRAO);
		hql.append(WHERE_PADRAO);
		hql.append(" AND e.lote LIKE :lote");
		parametros.put("lote", getAtributoLike("BR"));
		
		List<EstoqueProduto> lista = consultaListaPorParametro(hql.toString(), parametros, EstoqueProduto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}

	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarRestringindoBusca() {
		salvarEntidades(10);
		
		Criteria criteria = createCriteria(EstoqueProduto.class, "ep")
					.add(Restrictions.ne("ep.id", 9L))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<EstoqueProduto> lista = criteria.list();
		
		assertTrue("Verifica quantidade, mínimo igual 9", lista.size() >= 9);
		lista.forEach((entidade) -> {
			assertFalse("Nenhum registro poderá ter o id = 9", entidade.getId() == 9L);
		});
	}
	
	
	//#####################
	//## METÓDOS AUXILIARES
	//#####################
	public void salvarEntidades(Integer qt) {
		salvarEntidades(qt, new Double(10), new Double(0), new Double(10), getProduto());
	}
	
	public void salvarEntidades(Integer qt, Double qtMovimentada, Double qtAnterior, Double qtTotal, Produto produto) {
		for (int i = 0; i < qt; i++) {
			salvar(getNovaEntidade(qtMovimentada, qtAnterior, qtTotal, LOTE_PADRAO, produto));
		}
	}
	
	public EstoqueProduto getNovaEntidade(Double qtMovimentada, Double qtAnterior, Double qtTotal, String lote, Produto produto) {
		EstoqueProduto entidade = new EstoqueProduto();
		entidade.setQtMovimentada(qtMovimentada);
		entidade.setQtAnterior(qtAnterior);
		entidade.setQtTotal(qtTotal);
		entidade.setLote(lote);
		entidade.setProduto(produto);
		return entidade;
	}
	
	public EstoqueProduto getNovaEntidade() {
		return getNovaEntidade(new Double(10), new Double(0), new Double(10), LOTE_PADRAO, getProduto());
	}
	
	public Produto getProduto() {
		return new Produto("Produto 1", "Descrição", "A0001/2017", new Marca("Marca 1") , new Date());
	}

}
