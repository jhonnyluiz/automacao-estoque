package com.desenvolvimento.pos.entity;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.desenvolvimento.pos.util.BaseJPATest;

public class BaseCrudTest<E> extends BaseJPATest{

	public static final String SELECT_E_FROM = "SELECT e FROM ";
	public static final String WHERE_PADRAO = " WHERE 1 = 1 ";
	
	/**
	 * Método responsável por salvar entidade.
	 * 
	 * @param entidade
	 */
	public E salvar(E entidade) {
		begin();
		getEm().persist(entidade);
		commit();
		return entidade;
	}
	
	/**
	 * Método responsável por atualizar entidade.
	 * 
	 * @param entidade
	 */
	public E atualizar(E entidade) {
		begin();
		E e = getEm().merge(entidade);
		commit();
		return e;
	}
	
	/**
	 * Responsável por realizar consulta unica com query.
	 * 
	 * @param strQuery
	 * @param clazz
	 * @return
	 */
	public E consultaUnica(String strQuery, Class<E> clazz) {
		TypedQuery<E> query = getEm().createQuery(strQuery, clazz).setMaxResults(1);
		return query.getSingleResult();
	}

	/**
	 * Responsável por realizar consulta unica com query.
	 * 
	 * @param strQuery
	 * @param clazz
	 * @return
	 */
	public void excluir(E entidade) {
		begin();
		getEm().remove(entidade);
		commit();
	}

	/**
	 * Responsável por realizar consulta por id.
	 * 
	 * @param strQuery
	 * @param clazz
	 * @return
	 */
	public E consultaPorId(Long id, Class<E> clazz) {
		return getEm().find(clazz, id);
	}
	
	/**
	 * Responsável por realizar consulta de uma lista de objetos.
	 * 
	 * @param strQuery
	 * @param clazz
	 * @return
	 */
	public List<E> consultaLista(String strQuery, Class<E> clazz) {
		TypedQuery<E> query = getEm().createQuery(strQuery, clazz);
		return query.getResultList();
	}
	
	/**
	 * Responsável por realizar consulta de uma lista de objetos.
	 * 
	 * @param strQuery
	 * @param clazz
	 * @return
	 */
	public List<E> consultaListaPorParametro(String strQuery, Map<String, Object> parametros, Class<E> clazz) {
		TypedQuery<E> query = getEm().createQuery(strQuery, clazz);
		if(parametros != null) {
			parametros.forEach( (chave, valor) -> {
				query.setParameter(chave, valor);
			});
		}
		return query.getResultList();
	}
	
	public String getAtributoLike(String atributo) {
		return "%"+atributo+"%";
	}
	
	/**
	 * Método responsável por realizar begin.
	 */
	public void begin() {
		if(!getEm().getTransaction().isActive()){
			getEm().getTransaction().begin();
		}
	}

	/**
	 * Método responsável por realizar commit.
	 */
	public void commit() {
		getEm().getTransaction().commit();
	}
}
