/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "acao")
public class Acao implements Serializable {

	// Constantes
	private static final long serialVersionUID = 4660745857138935138L;
	
	// Atributos
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "executado")
	private Integer executado;
	@Column(name = "tipo_acao")
	private Integer tipoAcao;
	@Column(name = "mensagem")
	private String mensagem;
	@Column(name = "publico")
	private Integer publico;
	@Column(name = "id_cidade")
	private Long idCidade;
	@Column(name = "qtd_executado")
	private Integer qtdExecutado;
	@Column(name = "data_acao")
	private Date dataAcao;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getExecutado() {
		return executado;
	}
	public void setExecutado(Integer executado) {
		this.executado = executado;
	}
	public Integer getTipoAcao() {
		return tipoAcao;
	}
	public void setTipoAcao(Integer tipoAcao) {
		this.tipoAcao = tipoAcao;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Integer getPublico() {
		return publico;
	}
	public void setPublico(Integer publico) {
		this.publico = publico;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	public Integer getQtdExecutado() {
		return qtdExecutado;
	}
	public void setQtdExecutado(Integer qtdExecutado) {
		this.qtdExecutado = qtdExecutado;
	}
	public Date getDataAcao() {
		return dataAcao;
	}
	public void setDataAcao(Date dataAcao) {
		this.dataAcao = dataAcao;
	}
	
}