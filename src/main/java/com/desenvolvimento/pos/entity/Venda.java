package com.desenvolvimento.pos.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="venda")
public class Venda extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_venda", unique=true, nullable=false)
	private Long id;
	
	@ManyToOne(cascade = {CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinColumn(name="id_cliente", referencedColumnName="id_cliente", insertable= true, updatable=false, nullable=false)
	private Cliente cliente;
	
	@ManyToMany(cascade = {CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinTable(name="venda_produto", joinColumns= @JoinColumn(name="id_venda"), inverseJoinColumns= @JoinColumn(name="id_produto"))
	private List<Produto2> produtos;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dh_venda")
	private Date dataHora;
	
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the produtos
	 */
	public List<Produto2> getProdutos() {
		if(produtos == null) {
			produtos = new ArrayList<Produto2>();
		}
		return produtos;
	}

	/**
	 * @param produtos the produtos to set
	 */
	public void setProdutos(List<Produto2> produtos) {
		this.produtos = produtos;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the dataHora
	 */
	public Date getDataHora() {
		return dataHora;
	}

	/**
	 * @param dataHora the dataHora to set
	 */
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

}
