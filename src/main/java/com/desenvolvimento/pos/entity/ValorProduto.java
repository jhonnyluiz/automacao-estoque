package com.desenvolvimento.pos.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="valor_produtos")
public class ValorProduto extends BaseEntity<Long>{

	private static final long serialVersionUID = 8486102399992665025L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_VALOR_PRODUTO")
	private Long idValorProduto;
	
	@Column(name="QT_ANTERIOR")
	private Double vlCusto;
	
	@Column(name="QT_MOVIMENTADA")
	private Double vlVenda;

	@Column(name="LOTE")
	private Double vlDesconto;
	
	@Column(name="QT_TOTAL", nullable=false)
	private Double vlLucro;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID_ESTOQUE_PRODUTO", referencedColumnName="ID_ESTOQUE_PRODUTO", nullable=true)
	private EstoqueProduto estoqueProduto;
	

	/**
	 * @return the idValorProduto
	 */
	public Long getIdValorProduto() {
		return idValorProduto;
	}


	/**
	 * @param idValorProduto the idValorProduto to set
	 */
	public void setIdValorProduto(Long idValorProduto) {
		this.idValorProduto = idValorProduto;
	}


	/**
	 * @return the vlCusto
	 */
	public Double getVlCusto() {
		return vlCusto;
	}


	/**
	 * @param vlCusto the vlCusto to set
	 */
	public void setVlCusto(Double vlCusto) {
		this.vlCusto = vlCusto;
	}


	/**
	 * @return the vlVenda
	 */
	public Double getVlVenda() {
		return vlVenda;
	}


	/**
	 * @param vlVenda the vlVenda to set
	 */
	public void setVlVenda(Double vlVenda) {
		this.vlVenda = vlVenda;
	}


	/**
	 * @return the vlDesconto
	 */
	public Double getVlDesconto() {
		return vlDesconto;
	}


	/**
	 * @param vlDesconto the vlDesconto to set
	 */
	public void setVlDesconto(Double vlDesconto) {
		this.vlDesconto = vlDesconto;
	}


	/**
	 * @return the vlLucro
	 */
	public Double getVlLucro() {
		return vlLucro;
	}


	/**
	 * @param vlLucro the vlLucro to set
	 */
	public void setVlLucro(Double vlLucro) {
		this.vlLucro = vlLucro;
	}


	/**
	 * @return the estoqueProduto
	 */
	public EstoqueProduto getEstoqueProduto() {
		return estoqueProduto;
	}


	/**
	 * @param estoqueProduto the estoqueProduto to set
	 */
	public void setEstoqueProduto(EstoqueProduto estoqueProduto) {
		this.estoqueProduto = estoqueProduto;
	}


	@Override
	public Long getId() {
		return idValorProduto;
	}

}
