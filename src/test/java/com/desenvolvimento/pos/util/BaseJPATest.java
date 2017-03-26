package com.desenvolvimento.pos.util;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

public class BaseJPATest {

	private EntityManager em;

	@Before
	public void instanciarEntityManager() {
		em = JPAUtil.INSTANCE.getEntityManager();
	}
	
	@After
	public void fecharEntityManager() {
		if(em.isOpen()) {
			em.close();
		}
	}
	
	public Session getSession() {
		return (Session) getEm().getDelegate();
	}
	
	public Criteria createCriteria(Class<?> clazz) {
		return getSession().createCriteria(clazz);
	}
	
	public Criteria createCriteria(Class<?> clazz, String alias) {
		return getSession().createCriteria(clazz, alias);
	}

	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}

	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
