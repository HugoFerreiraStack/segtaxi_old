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
import javax.persistence.Table;

import br.com.seg.econotaxi.enums.PromocaoEnum;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "promocao")
public class Promocao implements Serializable {

	// Constantes
	private static final long serialVersionUID = -6135159523031250121L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "texto")
	private String texto;
	@Column(name = "data_promocao")
	private Date dataPromocao;
	@Column(name = "icon")
	private String icon;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "porcentagem")
	private BigDecimal porcentagem;
	
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
	public Date getDataPromocao() {
		return dataPromocao;
	}
	public void setDataPromocao(Date dataPromocao) {
		this.dataPromocao = dataPromocao;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public BigDecimal getPorcentagem() {
		return porcentagem;
	}
	public void setPorcentagem(BigDecimal porcentagem) {
		this.porcentagem = porcentagem;
	}
	public String getDescricaoTipo() {
		
		String descricaoTipo = "";
		if (tipo != null) {
			descricaoTipo = PromocaoEnum.valueOfPromocao(tipo).getDescricao();
		}
		return descricaoTipo;
	}
	
}