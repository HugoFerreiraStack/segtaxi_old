/**
 * 
 */
package br.com.seg.econotaxi.vo;

import java.util.Date;

/**
 * @author bruno
 *
 */
public class MensagemCorridaVO {

	private Long idCorrida;
	private Date dataMensagem;
	private String nome;
	private Boolean visto;
	private Boolean origemPassageiro;
	private String mensagem;
	private Long idMotorista;
	
	/* Métodos get/set */
	public Long getIdCorrida() {
		return idCorrida;
	}
	public void setIdCorrida(Long idCorrida) {
		this.idCorrida = idCorrida;
	}
	public Date getDataMensagem() {
		return dataMensagem;
	}
	public void setDataMensagem(Date dataMensagem) {
		this.dataMensagem = dataMensagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getVisto() {
		return visto;
	}
	public void setVisto(Boolean visto) {
		this.visto = visto;
	}
	public Boolean getOrigemPassageiro() {
		return origemPassageiro;
	}
	public void setOrigemPassageiro(Boolean origemPassageiro) {
		this.origemPassageiro = origemPassageiro;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Long getIdMotorista() {
		return idMotorista;
	}
	public void setIdMotorista(Long idMotorista) {
		this.idMotorista = idMotorista;
	}
	
}