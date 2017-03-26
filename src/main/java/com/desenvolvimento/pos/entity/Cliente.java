package com.desenvolvimento.pos.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_cliente", unique=true, nullable=false)
	private Long id;
	
	@Column(name="nm_cliente", nullable=false, length=100)
	private String nome;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name="cpf_cliente", nullable=false, length=100)
	private String cpf;

	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY)
	private List<Venda> compras;
	
	/**
	 * Construtor vazio
	 */
	public Cliente() {
	}
	
	/**
	 * Construtor com preenchiemento de id e nome.
	 * 
	 * @param id
	 * @param nome
	 */
	public Cliente(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	/**
	 * Construtor com preenchiemento de nome e cpf.
	 * 
	 * @param id
	 * @param nome
	 */
	public Cliente(String nome, String cpf) {
		this.nome = nome;
		this.cpf = cpf;
	}

	/**
	 * Construtor com preenchimento de nome.
	 * 
	 * @param nome
	 */
	public Cliente(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return the id
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the compras
	 */
	public List<Venda> getCompras() {
		return compras;
	}

	/**
	 * @param compras the compras to set
	 */
	public void setCompras(List<Venda> compras) {
		this.compras = compras;
	}
	
}
