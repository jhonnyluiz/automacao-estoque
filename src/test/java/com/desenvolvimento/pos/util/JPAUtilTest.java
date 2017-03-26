package com.desenvolvimento.pos.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class JPAUtilTest extends BaseJPATest{
	
	@Test
	public 	void deveTerInstanciaDoEntityManager() {
		assertNotNull("Instância do EntityManager não deve ser nula", getEm());
	}
	
	@Test
	public void deveFecharEntityManager(){
		getEm().close();
		assertFalse("Instância do EntityManager deve estar fechada", getEm().isOpen());
	}
	
	@Test
	public void deveAbrirUmaNovaTransacao() {
		assertFalse("Transação deve estar fechada", getEm().getTransaction().isActive());
		getEm().getTransaction().begin();
		assertTrue("Transação deve estar aberta", getEm().getTransaction().isActive());
	}

}
