package com.desenvolvimento.pos.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="produtos")
public class Produto extends BaseEntity<Long>{

	private static final long serialVersionUID = 8486102399992665025L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_PRODUTO")
	private Long idProduto;
	
	@Column(name="NM_PRODUTO", nullable=false)
	private String nmProduto;
	
	@Column(name="DS_PRODUTO")
	private String dsProduto;
	
	@Column(name="CD_PRODUTO")
	private String cdProduto;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID_MARCA", referencedColumnName="ID_MARCA", nullable=true)
	private Marca marca;
	
	@OneToMany(mappedBy="produto", fetch=FetchType.LAZY, cascade=CascadeType.REMOVE)
	private List<EstoqueProduto> estoqueProduto;
	
	@Column(name="DT_REGISTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtRegistro;
	
	public Produto() {
		
	}
	
	public Produto(String nmProduto, String dsProduto, String cdProduto, Marca marca, Date dtRegistro) {
		this.nmProduto = nmProduto;
		this.dsProduto = dsProduto;
		this.cdProduto = cdProduto;
		this.marca = marca;
		this.dtRegistro = dtRegistro;
	}
	
	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNmProduto() {
		return nmProduto;
	}

	public void setNmProduto(String nmProduto) {
		this.nmProduto = nmProduto;
	}

	public String getDsProduto() {
		return dsProduto;
	}

	public void setDsProduto(String dsProduto) {
		this.dsProduto = dsProduto;
	}

	public String getCdProduto() {
		return cdProduto;
	}

	public void setCdProduto(String cdProduto) {
		this.cdProduto = cdProduto;
	}

	public Date getDtRegistro() {
		return dtRegistro;
	}

	public void setDtRegistro(Date dtRegistro) {
		this.dtRegistro = dtRegistro;
	}

	@Override
	public Long getId() {
		return idProduto;
	}

}
