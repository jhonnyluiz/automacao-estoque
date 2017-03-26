package com.desenvolvimento.pos.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.junit.Test;

import com.sun.org.apache.xml.internal.security.encryption.Transforms;

public class RelatorioTest extends EntityBuilder<Venda>{

	@SuppressWarnings("unchecked")
	@Test
	public void deveConsultarTodosClientes() {
		salvarClientes(3);
		
		Criteria criteria = createCriteria(Cliente.class, "c");
		
		List<Cliente> clientes = criteria
									//SELECT DISTINCT
									.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
									.list();
		
		assertTrue("Verificar se tem pelo menos tres entidades de cliente", clientes.size() >= 3);
		
		//MODELO VARIAS PARAMETROS E LINHAS
		clientes.forEach( (cliente) -> {
								assertFalse(cliente.isTransient());
							});

		//MODELO UNICA LINHA E PARAMETRO
		clientes.forEach( cliente -> assertFalse(cliente.isTransient()));
	}
	
	@Test
	public void deveConsultarMaiorIdCliente() {
		salvarClientes(3);
		
		Criteria criteria = createCriteria(Cliente.class, "c")
										//SELECTE MAX
										.setProjection(Projections.max("c.id"));
		
		Long maiorId = (Long) criteria
								.setResultTransformer(Criteria.PROJECTION)
								.uniqueResult();
		
		assertTrue("Verifica se o ID é maior que 2(pois salvou 3 clientes", maiorId >= 3);
	}
	
	@Test
	public void deveConsultarVendasDaUltimaSemana() {
		salvarVendas(3);
		
		Calendar ultimaSemana = Calendar.getInstance();
		ultimaSemana.add(Calendar.WEEK_OF_YEAR, -1); //Setando data para 1 semana antes da atual
		
		Criteria criteria = createCriteria(Venda.class, "v")
								.add(Restrictions.between("v.dataHora", ultimaSemana.getTime(), new Date()))
								.setProjection(Projections.rowCount());
		
		Long qtdRegistros = (Long) criteria
										.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
										.uniqueResult();
		
		assertTrue("Verifica se a quantidade de vendas é pelo menos 3", qtdRegistros >= 3);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarNotebooks() {
		salvarProdutos(3);
		
		Criteria criteria = createCriteria(Produto2.class, "p")
								//WHERE IN
								.add(Restrictions.in("p.nome", "Notebook", "Netbook", "MacBook"))
								//ORDER BY
								.addOrder(Order.asc("p.fabricante"))
								.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Produto2> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de notebooks é pelo menos 3", produtos.size() >= 3);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarDezPrimeirosProdutos() {
		salvarProdutos(30);
		
		Criteria criteria = createCriteria(Produto2.class, "p")
				//OFFSET
				.setFirstResult(1)
				//LIMIT
				.setMaxResults(10)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Produto2> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de produtos é 10", produtos.size() == 10);
		produtos.forEach( produto -> assertFalse(produto.isTransient()));
	}
	
	@Test
	public void deveConsultarQuantidadeVendasCliente() {
		salvarVendas(3);
		
		Criteria criteria = createCriteria(Venda.class, "v")
				//JOIN
				.createAlias("v.cliente", "c")
				//WHERE =
				.add(Restrictions.eq("c.cpf", CPF_PADRAO))
				//COUNT(*)
				.setProjection(Projections.rowCount())
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		

		Long qtdRegistros = (Long) criteria
										.uniqueResult();
		
		assertTrue("Verifica se a quantidade de notebooks é pelo menos 3", qtdRegistros >= 3);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void deveConsultarProdutosContendoParteDoNome() {
		salvarVendas(3);
		
		Criteria criteria = createCriteria(Produto2.class, "p")
				//WHERE ILIKE %String%
				.add(Restrictions.ilike("p.nome", "book", MatchMode.ANYWHERE))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Produto2> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de notebooks é pelo menos 3", produtos.size() >= 3);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarProdutosDellouSamsung() {
		salvarProdutos(3);
		
		Criteria criteria = createCriteria(Produto2.class, "p")
								//WHERE OR
								.add(Restrictions.or(
										Restrictions.eq("p.fabricante", "Dell"),
										Restrictions.eq("p.fabricante", "Samsung")))
								.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Produto2> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de notebooks é pelo menos 3", produtos.size() >= 3);
		produtos.forEach( produto -> assertFalse(produto.isTransient()));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveVendasENomeClienteCasoExista() {
		salvarVendas(1);
		
		Criteria criteria = createCriteria(Venda.class, "v")
								//LEFT JOIN
								.createAlias("v.cliente", "c", JoinType.LEFT_OUTER_JOIN)
								//WHERE ILIKE %string%                       //Termina em Cabral
								.add(Restrictions.ilike("c.nome", "Cabral", MatchMode.END))
								.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Venda> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de vendas é pelo menos 1", produtos.size() >= 1);
		produtos.forEach( produto -> assertFalse(produto.isTransient()));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarIdENomeProduto() {
		salvarProdutos(1);
		
		ProjectionList projection = Projections.projectionList()
				//SELECT FIELDS
				.add(Projections.property("p.id").as("idProduto"))
				.add(Projections.property("p.nome").as("nmProduto"));
		
		Criteria criteria = createCriteria(Produto2.class, "p")
								.setProjection(projection)
								.setResultTransformer(Criteria.PROJECTION);
		
		List<Object[]> produtos = criteria.list();
		
		assertTrue("Verifica se a quantidade de produtos é pelo menos 1", produtos.size() >= 1);
		produtos.forEach( produto -> {
			assertTrue("Primeiro item deve ser um id",produto[0] instanceof Long);
			assertTrue("Segundo item deve ser um nome",produto[1] instanceof String);
		});
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarClientesChaveValor() {
		salvarClientes(5);
		
		ProjectionList projection = Projections.projectionList()
				//SELECT FIELDS
				.add(Projections.property("c.id").as("idCliente"))
				.add(Projections.property("c.nome").as("nmCliente"));
		
		Criteria criteria = createCriteria(Cliente.class, "c")
				.setProjection(projection)
				.setResultTransformer(Criteria.PROJECTION)
				.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		List<Map<String, Object>> clientes = criteria.list();
		
		assertTrue("Verifica se a quantidade de produtos é pelo menos 1", clientes.size() >= 1);
		clientes.forEach( clienteMap -> {
			clienteMap.forEach((chave, valor) -> {
				assertTrue("Primeiro item deve ser um string", chave instanceof String);
				assertTrue("Segundo item deve ser um String ou long", valor instanceof String || valor instanceof Long);
			});
		});
	}

	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarIdNomeConverterCliente() {
		salvarClientes(3);
		
		ProjectionList projection = Projections.projectionList()
				//SELECT FIELDS
				.add(Projections.property("c.id").as("id"))
				.add(Projections.property("c.nome").as("nome"));
		
		Criteria criteria = createCriteria(Cliente.class, "c")
				.setProjection(projection)
				.setResultTransformer(Transformers.aliasToBean(Cliente.class));
		
		List<Cliente> clientes = criteria.list();
		
		assertTrue("Verifica se a quantidade de produtos é pelo menos 3", clientes.size() >= 3);
		
		clientes.forEach(cliente -> {
				assertTrue("Id diferente de nulo", cliente.getId() != null);
				assertTrue("Nome diferente de nulo", cliente.getNome() != null);
				assertTrue("Cpf deve ser nulo ", cliente.getCpf() == null);
		});
	}

	@Test
	@SuppressWarnings("unchecked")
	public void deveConsultarVendasPorNomeClienteUsandoSubquery() {
		salvarVendas(1);
		
		
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Cliente.class, "c")
				//WHERE IN
				.add(Restrictions.in("c.id", 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))
				//SELECT CAMPO
				.setProjection(Projections.property("c.nome"));
		
		Criteria criteria = createCriteria(Venda.class, "v")
					//INNER JOIN
					.createAlias("v.cliente", "cli")
					// WHERE IN (SELECT)
					.add(Subqueries.propertyIn("cli.nome", detachedCriteria))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Venda> vendas = criteria.list();
		
		vendas.forEach(venda -> assertFalse("Trouxe os itens corretamente", venda.getCliente().isTransient()));
	}
	
	
}
