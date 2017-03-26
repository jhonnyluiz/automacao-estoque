package com.desenvolvimento.pos.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.Type.PersistenceType;

@Entity
@Table(name="marcas")
public class Marca extends BaseEntity<Long>{

	private static final long serialVersionUID = 8486102399992665025L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_MARCA")
	private Long idMarca;
	
	@Column(name="NM_MARCA", nullable=false)
	private String nmMarca;
	
	@OneToMany(mappedBy="marca", fetch=FetchType.LAZY)
	private List<Produto> listaProdutos;
	
	@Override
	public Long getId() {
		return idMarca;
	}
	
	public Marca() {
	}

	public Marca(Long idMarca) {
		this.idMarca = idMarca;
	}
	
	public Marca(String nmMarca) {
		this.nmMarca = nmMarca;
	}
	/**
	 * @return the idMarca
	 */
	public Long getIdMarca() {
		return idMarca;
	}

	/**
	 * @param idMarca the idMarca to set
	 */
	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}

	/**
	 * @return the nmMarca
	 */
	public String getNmMarca() {
		return nmMarca;
	}

	/**
	 * @param nmMarca the nmMarca to set
	 */
	public void setNmMarca(String nmMarca) {
		this.nmMarca = nmMarca;
	}

	/**
	 * @return the listaProdutos
	 */
	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	/**
	 * @param listaProdutos the listaProdutos to set
	 */
	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	
}
