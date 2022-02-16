package br.com.seg.econotaxi.vo;

import java.io.Serializable;

/**
 * Classe VO com dados de filtro do Alerta
 * 
 * Criado em 02 de jul de 2017
 * @author wellington
 */
public class AlertaFiltroVO implements Serializable{
	
	// Constantes
	private static final long serialVersionUID = 3378336646245632982L;
	
	// Atributos
	private Integer statusAlertaNotificacao;
	private String nomeArquivo;
	private Integer tipoArquivo;
	private String mensagemErroArquivo;
	private Long idInstituicao;
	
	// Métodos get/set
	public Integer getStatusAlertaNotificacao() {
		return statusAlertaNotificacao;
	}
	public void setStatusAlertaNotificacao(Integer statusAlertaNotificacao) {
		this.statusAlertaNotificacao = statusAlertaNotificacao;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public Integer getTipoArquivo() {
		return tipoArquivo;
	}
	public void setTipoArquivo(Integer tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}
	public String getMensagemErroArquivo() {
		return mensagemErroArquivo;
	}
	public void setMensagemErroArquivo(String mensagemErroArquivo) {
		this.mensagemErroArquivo = mensagemErroArquivo;
	}
	public Long getIdInstituicao() {
		return idInstituicao;
	}
	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}
	
}