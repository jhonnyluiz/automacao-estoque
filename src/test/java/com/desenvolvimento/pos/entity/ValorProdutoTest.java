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

public class ValorProdutoTest extends BaseCrudTest<ValorProduto>{
	
	private static final Double DESCONTO_0_25 = new Double("0.25");
	private static final String ENTIDADE = " ValorProduto ";
	private static final String SELECT_PADRAO = SELECT_E_FROM + ENTIDADE + " e ";
	
	@Test
	public void deveSalvarMarca(){
		ValorProduto entidade = getNovaEntidade();
		assertTrue("Não pode ter id definido", entidade.isTransient());
		salvar(entidade);
		assertNotNull("Deverá ter id definido", entidade.getId());
	}
	
	@Test
	public void deveAtualizarMarca(){
		ValorProduto entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		entidade.setVlDesconto(DESCONTO_0_25);
		atualizar(entidade);
		assertEquals("Deverá ter o mesmo valor alterado",entidade.getVlDesconto() , DESCONTO_0_25);
		
		ValorProduto entidadeConsultada = consultaPorId(entidade.getId(), ValorProduto.class);
		assertEquals("Deverá ter o mesmo valor quanto consultado",entidadeConsultada.getVlDesconto() , entidade.getVlDesconto());
	}
	
	@Test
	public void deveExcluirMarca(){
		ValorProduto entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		Long id = entidade.getId();
		excluir(entidade);
		
		ValorProduto entidadeConsultada = consultaPorId(id, ValorProduto.class);
		assertNull("Deverá ser um objeto nulo", entidadeConsultada);
	}
	
	@Test
	public void deveConsultarTodos(){
		for (int i = 0; i < 10; i++) {
			salvar(getNovaEntidade());
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append(SELECT_PADRAO);
		List<ValorProduto> lista = consultaLista(hql.toString(), ValorProduto.class);
		
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
		hql.append(" AND e.vlLucro > :lucro");
		parametros.put("lucro", new Double("5"));
		
		List<ValorProduto> lista = consultaListaPorParametro(hql.toString(), parametros, ValorProduto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}
	
	@Test
	public void deveConsultarPorLikeAtributo(){
		salvarEntidades(10);
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(SELECT_PADRAO);
		hql.append(" LEFT JOIN e.estoqueProduto estoqueP");
		hql.append(WHERE_PADRAO);
		hql.append(" AND estoqueP.lote LIKE :lote");
		parametros.put("lote", getAtributoLike("BR"));
		
		List<ValorProduto> lista = consultaListaPorParametro(hql.toString(), parametros, ValorProduto.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}

	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarRestringindoBusca() {
		salvarEntidades(10);
		
		Criteria criteria = createCriteria(ValorProduto.class, "vp")
					.add(Restrictions.ne("vp.id", 1L))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<ValorProduto> lista = criteria.list();
		
		assertTrue("Verifica quantidade, mínimo igual 9", lista.size() >= 9);
		lista.forEach(entidade -> {
			assertFalse("Nenhum registro poderá ter o id = 1", entidade.getId() == 1L);
		});
	}
	
	
	//#####################
	//## METÓDOS AUXILIARES
	//#####################
	public void salvarEntidades(Integer qt) {
		for (int i = 0; i < qt; i++) {
			salvar(getNovaEntidade());
		}
	}
	
	public ValorProduto getNovaEntidade() {
		ValorProduto entidade = new ValorProduto();
		entidade.setEstoqueProduto(getEstoqueProduto());
		entidade.setVlCusto(new Double("2.5"));
		entidade.setVlDesconto(new Double("0.5"));
		entidade.setVlLucro(new Double("5.5"));
		entidade.setVlVenda(new Double("8.5"));
		return entidade;
	}
		
	public Produto getProduto() {
		return new Produto("Produto 1", "Descrição", "A0001/2017", new Marca("Marca 1") , new Date());
	}
	
	public EstoqueProduto getEstoqueProduto() {
		EstoqueProduto entidade = new EstoqueProduto();
		entidade.setQtMovimentada(new Double("10"));
		entidade.setQtAnterior(new Double("0"));
		entidade.setQtTotal(new Double("10"));
		entidade.setLote("BR001");
		entidade.setProduto(getProduto());
		return entidade;
	}
}
