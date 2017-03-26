package com.desenvolvimento.pos.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="produto2")
public class Produto2 extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_produto", unique=true, nullable=false)
	private Long id;
	
	@Column(name="nm_produto", nullable=false, length=100)
	private String nome;
	
	@Column(name="nm_fabricante", length=50)
	@Basic(fetch=FetchType.LAZY)
	private String fabricante;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_validade")
	@Basic(fetch=FetchType.LAZY)
	private Date validade;
	
	public Produto2(){}

	public Produto2(String nome) {
		this.nome = nome;
	}

	public Produto2(String nome, String fabricante) {
		this.nome = nome;
		this.fabricante = fabricante;
	}
	
	@Override
	public Long getId() {
		return id;
	}


	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the fabricante
	 */
	public String getFabricante() {
		return fabricante;
	}


	/**
	 * @param fabricante the fabricante to set
	 */
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}


	/**
	 * @return the validade
	 */
	public Date getValidade() {
		return validade;
	}


	/**
	 * @param validade the validade to set
	 */
	public void setValidade(Date validade) {
		this.validade = validade;
	}

}
