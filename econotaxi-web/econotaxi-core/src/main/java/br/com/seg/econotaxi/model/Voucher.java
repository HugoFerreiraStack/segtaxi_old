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
@Table(name = "voucher")
public class Voucher implements Serializable {

	// Constantes
	private static final long serialVersionUID = 134868871143184967L;
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "voucher")
	private String voucher;
	@Column(name = "id_empresa_conveniada")
	private Long idEmpresaConveniada;
	@Column(name = "id_corrida")
	private Long idCorrida;
	@Column(name = "data_geracao")
	private Date dataGeracao;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public Long getIdEmpresaConveniada() {
		return idEmpresaConveniada;
	}
	public void setIdEmpresaConveniada(Long idEmpresaConveniada) {
		this.idEmpresaConveniada = idEmpresaConveniada;
	}
	public Long getIdCorrida() {
		return idCorrida;
	}
	public void setIdCorrida(Long idCorrida) {
		this.idCorrida = idCorrida;
	}
	public Date getDataGeracao() {
		return dataGeracao;
	}
	public void setDataGeracao(Date dataGeracao) {
		this.dataGeracao = dataGeracao;
	}
	
}