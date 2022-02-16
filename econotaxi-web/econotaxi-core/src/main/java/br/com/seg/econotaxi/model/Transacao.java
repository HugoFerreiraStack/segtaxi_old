/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.seg.econotaxi.enums.StatusTransacaoEnum;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {

	// Constantes
	private static final long serialVersionUID = 6954449915724921206L;

	public Transacao() {}
	
	public Transacao(Transacao transacao, Usuario usuarioMotorista) {
		super();
		this.id = transacao.getId();
		this.dataTransacao = transacao.getDataTransacao();
		this.valor = transacao.getValor();
		this.status = transacao.getStatus();
		this.corrida = transacao.getCorrida();
		this.pagamento = transacao.getPagamento();
		if (usuarioMotorista != null) {
			this.nome = usuarioMotorista.getNome();
		}
		this.indicadorEmailEnviado = transacao.getIndicadorEmailEnviado();
		this.transactionIdMaxipago = transacao.getTransactionIdMaxipago();
		this.orderIdMaxipago = transacao.getOrderIdMaxipago();
		this.errorMaxipago = transacao.getErrorMaxipago();
	}
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "data_transacao")
	private Date dataTransacao;
	@Column(name = "valor")
	private BigDecimal valor;
	@Column(name = "status")
	private Integer status;
	@Column(name = "ind_email_enviado")
	private Integer indicadorEmailEnviado;

	@Column(name = "order_id_maxipago")
	private String orderIdMaxipago;

	@Column(name = "transaction_id_maxipago")
	private String transactionIdMaxipago;

	@Column(name = "error_maxipago")
	private String errorMaxipago;
	
	@ManyToOne
	@JoinColumn(name = "id_corrida", referencedColumnName = "id")
	private Corrida corrida;
	@ManyToOne
	@JoinColumn(name = "id_pagamento", referencedColumnName = "id")
	private Pagamento pagamento;
	
	@Transient
	private String nome;
	
	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataTransacao() {
		return dataTransacao;
	}
	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Corrida getCorrida() {
		return corrida;
	}
	public void setCorrida(Corrida corrida) {
		this.corrida = corrida;
	}
	public Pagamento getPagamento() {
		return pagamento;
	}
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	public String getDescricaoStatus() {
		
		String descricaoStatus = "";
		if (status != null) {
			descricaoStatus = StatusTransacaoEnum.valueOfStatus(status).getDescricao();
		}
		return descricaoStatus;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIndicadorEmailEnviado() {
		return indicadorEmailEnviado;
	}
	public void setIndicadorEmailEnviado(Integer indicadorEmailEnviado) {
		this.indicadorEmailEnviado = indicadorEmailEnviado;
	}

	public String getOrderIdMaxipago() {
		return orderIdMaxipago;
	}

	public void setOrderIdMaxipago(String orderIdMaxipago) {
		this.orderIdMaxipago = orderIdMaxipago;
	}

	public String getTransactionIdMaxipago() {
		return transactionIdMaxipago;
	}

	public void setTransactionIdMaxipago(String transactionIdMaxipago) {
		this.transactionIdMaxipago = transactionIdMaxipago;
	}

	public String getErrorMaxipago() {
		return errorMaxipago;
	}

	public void setErrorMaxipago(String errorMaxipago) {
		this.errorMaxipago = errorMaxipago;
	}
}