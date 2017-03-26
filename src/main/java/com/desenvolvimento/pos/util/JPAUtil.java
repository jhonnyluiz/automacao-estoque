package com.desenvolvimento.pos.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum JPAUtil {
	
	INSTANCE;
	
	private EntityManagerFactory factory;
	
	private JPAUtil() {
		factory = Persistence.createEntityManagerFactory("DeltaPU");
	}
	
	/**
	 * Classe responsável por retonar instância do EntityManager.
	 * 
	 * @return {@link EntityManager}
	 */
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
