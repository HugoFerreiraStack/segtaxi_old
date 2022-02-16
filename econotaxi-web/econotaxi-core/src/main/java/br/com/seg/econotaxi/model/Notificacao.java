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
@Table(name = "notificacao")
public class Notificacao implements Serializable {

	// Constantes
	private static final long serialVersionUID = 1099905557421557258L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "texto")
	private String texto;
	@Column(name = "data_notificacao")
	private Date dataNotificacao;
	@Column(name = "icon")
	private String icon;
	@Column(name = "ind_android")
	private Integer indicadorAndroid;
	@Column(name = "ind_ios")
	private Integer indicadorIos;
	@Column(name = "data_envio")
	private Date dataEnvio;
	@Column(name = "publico")
	private Integer publico;
	
	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getDataNotificacao() {
		return dataNotificacao;
	}
	public void setDataNotificacao(Date dataNotificacao) {
		this.dataNotificacao = dataNotificacao;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getIndicadorAndroid() {
		return indicadorAndroid;
	}
	public void setIndicadorAndroid(Integer indicadorAndroid) {
		this.indicadorAndroid = indicadorAndroid;
	}
	public Integer getIndicadorIos() {
		return indicadorIos;
	}
	public void setIndicadorIos(Integer indicadorIos) {
		this.indicadorIos = indicadorIos;
	}
	public Date getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public Integer getPublico() {
		return publico;
	}
	public void setPublico(Integer publico) {
		this.publico = publico;
	}
	
}