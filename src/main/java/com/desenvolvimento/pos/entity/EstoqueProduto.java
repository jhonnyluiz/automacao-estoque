package com.desenvolvimento.pos.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="estoque_produtos")
public class EstoqueProduto extends BaseEntity<Long>{

	private static final long serialVersionUID = 8486102399992665025L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_ESTOQUE_PRODUTO")
	private Long idEstoqueProduto;
	
	@Column(name="QT_ANTERIOR")
	private Double qtAnterior;
	
	@Column(name="QT_MOVIMENTADA")
	private Double qtMovimentada;

	@Column(name="LOTE")
	private String lote;
	
	@Column(name="QT_TOTAL", nullable=false)
	private Double qtTotal;
	
	@OneToOne(cascade = {CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinColumn(name="ID_VALOR_PRODUTO", referencedColumnName="ID_VALOR_PRODUTO", nullable=true)
	private ValorProduto valorProduto;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="ID_PRODUTO", referencedColumnName="ID_PRODUTO", nullable=true)
	private Produto produto;

	public Long getIdEstoqueProduto() {
		return idEstoqueProduto;
	}

	public void setIdEstoqueProduto(Long idEstoqueProduto) {
		this.idEstoqueProduto = idEstoqueProduto;
	}


	/**
	 * @return the qtAnterior
	 */
	public Double getQtAnterior() {
		return qtAnterior;
	}

	/**
	 * @param qtAnterior the qtAnterior to set
	 */
	public void setQtAnterior(Double qtAnterior) {
		this.qtAnterior = qtAnterior;
	}

	/**
	 * @return the qtMovimentada
	 */
	public Double getQtMovimentada() {
		return qtMovimentada;
	}

	/**
	 * @param qtMovimentada the qtMovimentada to set
	 */
	public void setQtMovimentada(Double qtMovimentada) {
		this.qtMovimentada = qtMovimentada;
	}

	/**
	 * @return the lote
	 */
	public String getLote() {
		return lote;
	}

	/**
	 * @param lote the lote to set
	 */
	public void setLote(String lote) {
		this.lote = lote;
	}

	/**
	 * @return the qtTotal
	 */
	public Double getQtTotal() {
		return qtTotal;
	}

	/**
	 * @param qtTotal the qtTotal to set
	 */
	public void setQtTotal(Double qtTotal) {
		this.qtTotal = qtTotal;
	}

	/**
	 * @return the valorProduto
	 */
	public ValorProduto getValorProduto() {
		return valorProduto;
	}

	/**
	 * @param valorProduto the valorProduto to set
	 */
	public void setValorProduto(ValorProduto valorProduto) {
		this.valorProduto = valorProduto;
	}

	/**
	 * @return the produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * @param produto the produto to set
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public Long getId() {
		return idEstoqueProduto;
	}

}
