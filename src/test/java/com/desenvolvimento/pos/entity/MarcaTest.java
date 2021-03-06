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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.junit.Test;

public class MarcaTest extends BaseCrudTest<Marca>{
	
	private static final String ENTIDADE = " Marca ";
	private static final String SELECT_PADRAO = SELECT_E_FROM + ENTIDADE + " e ";
	
	@Test
	public void deveSalvarMarca(){
		Marca entidade = getNovaEntidade();
		assertTrue("Não pode ter id definido", entidade.isTransient());
		salvar(entidade);
		assertNotNull("Deverá ter id definido", entidade.getId());
	}
	
	@Test
	public void deveAtualizarMarca(){
		Marca entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		entidade.setNmMarca("Marca 2");
		atualizar(entidade);
		assertEquals("Deverá ter o mesmo valor alterado",entidade.getNmMarca() , "Marca 2");
		
		Marca entidadeConsultada = consultaPorId(entidade.getId(), Marca.class);
		assertEquals("Deverá ter o mesmo valor quanto consultado",entidadeConsultada.getNmMarca() , entidade.getNmMarca());
	}
	
	@Test
	public void deveExcluirMarca(){
		Marca entidade = salvar(getNovaEntidade());
		assertNotNull("Deverá ter id definido", entidade.getId());
		
		Long id = entidade.getId();
		excluir(entidade);
		
		Marca entidadeConsultada = consultaPorId(id, Marca.class);
		assertNull("Deverá ser um objeto nulo", entidadeConsultada);
	}
	
	@Test
	public void deveConsultarTodos(){
		for (int i = 0; i < 10; i++) {
			salvar(getNovaEntidade());
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append(SELECT_PADRAO);
		List<Marca> lista = consultaLista(hql.toString(), Marca.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}
	
	@Test
	public void deveConsultarPorAtributo(){
		String nmMarca = "Marca Produto";
		for (int i = 0; i < 10; i++) {
			salvar(getNovaEntidade(nmMarca));
		}

		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(SELECT_PADRAO);
		hql.append(WHERE_PADRAO);
		hql.append(" AND e.nmMarca = :nomeMarca");
		parametros.put("nomeMarca", nmMarca);
		
		List<Marca> lista = consultaListaPorParametro(hql.toString(), parametros, Marca.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}
	
	@Test
	public void deveConsultarPorLikeAtributo(){
		String nmMarca = "Marca Produto";
		salvarEntidades(10, nmMarca);
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(SELECT_PADRAO);
		hql.append(WHERE_PADRAO);
		hql.append(" AND e.nmMarca LIKE :nomeMarca");
		parametros.put("nomeMarca", getAtributoLike("arc"));
		
		List<Marca> lista = consultaListaPorParametro(hql.toString(), parametros, Marca.class);
		
		assertTrue("Resultado deverá ser maior ou igual a 10", lista.size() >= 10);
	}

	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarRestringindoBusca() {
		salvarEntidades(10);
		
		Criteria criteria = createCriteria(Marca.class, "m")
					.add(Restrictions.ne("m.id", 5L))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Marca> lista = criteria.list();
		
		assertTrue("Verifica quantidade, mínimo igual 9", lista.size() >= 9);
		lista.forEach(entidade -> assertFalse("Nenhum registro poderá ter o id = 5", entidade.getId() == 5L));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarProdutoPorMarcas() {
		salvarProdutos(3);
		
		Criteria criteria = createCriteria(Produto.class, "p")
								.createAlias("p.marca", "m", JoinType.LEFT_OUTER_JOIN)
								.add(Restrictions.ilike("m.nmMarca", "ca 1", MatchMode.END))
								.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Produto> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de vendas é pelo menos 1", produtos.size() >= 1);
		produtos.forEach( produto -> assertFalse(produto.isTransient()));
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarIdEQtTotal() {
		salvarEntidades(3);
		
		ProjectionList projection = Projections.projectionList()
				.add(Projections.property("m.idMarca").as("idMarca"))
				.add(Projections.property("m.nmMarca").as("nmMarca"));
		
		Criteria criteria = createCriteria(Marca.class, "m")
								.setProjection(projection)
								.setResultTransformer(Criteria.PROJECTION);
		
		List<Object[]> marcas = criteria.list();
		
		assertTrue("Verifica se a quantidade de produtos é pelo menos 3", marcas.size() >= 3);
		marcas.forEach( marca -> {
			assertTrue("Primeiro item deve ser um id",marca[0] instanceof Long);
			assertTrue("Segundo item deve ser um nome",marca[1] instanceof String);
		});
	}
	
	//#####################
	//## METÓDOS AUXILIARES
	//#####################
	public void salvarEntidades(Integer qt) {
		salvarEntidades(qt, "Marca Produto");
	}
	
	public void salvarEntidades(Integer qt, String nmMarca) {
		for (int i = 0; i < qt; i++) {
			salvar(getNovaEntidade(nmMarca));
		}
	}
	
	public Marca getNovaEntidade(String nmMarca) {
		Marca entidade = new Marca();
		entidade.setNmMarca(nmMarca);
		return entidade;
	}
	
	public Marca getNovaEntidade() {
		return getNovaEntidade("Marca 1");
	}
	
	public void salvarProdutos(Integer qt) {
		begin();
		for (int i = 0; i < qt; i++) {
			getEm().persist(new Produto("Produto 1", "Descrição", "A0001/2017", new Marca("Marca 1") , new Date()));
		}
		commit();
	}
	
	public void salvarEstoqueProduto() {
		EstoqueProduto entidade = new EstoqueProduto();
		entidade.setQtMovimentada(new Double("10"));
		entidade.setQtAnterior(new Double("0"));
		entidade.setQtTotal(new Double("10"));
		entidade.setLote("BR001");
		entidade.setProduto(new Produto("Produto 1", "Descrição", "A0001/2017", new Marca("Marca 1") , new Date()));
		begin();
		getEm().persist(entidade);
		commit();
	}
}
