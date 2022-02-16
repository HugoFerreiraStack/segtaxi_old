/**
 * 
 */
package br.com.seg.econotaxi.vo;

import java.io.Serializable;

/**
 * Classe VO com dados de e-mail
 * 
 * Criado em 05 de jul de 2017
 * @author Bruno Rocha
 */
public class EmailVO implements Serializable {

	// Constantes
	private static final long serialVersionUID = 8912709770132148356L;

	// Atributos
	private String servidorSMTP;
	private String portaServidorSMTP;
	private String nomeRemetente;
	private String usuarioEmail;
	private String senhaEmail;
	private String destinatarios;
	private String mensagem;
	private String assunto;
	private String titulo;
	
	/* Métodos Get e Set */
	public String getServidorSMTP() {
		return servidorSMTP;
	}
	public void setServidorSMTP(String servidorSMTP) {
		this.servidorSMTP = servidorSMTP;
	}
	public String getPortaServidorSMTP() {
		return portaServidorSMTP;
	}
	public void setPortaServidorSMTP(String portaServidorSMTP) {
		this.portaServidorSMTP = portaServidorSMTP;
	}
	public String getNomeRemetente() {
		return nomeRemetente;
	}
	public void setNomeRemetente(String nomeRemetente) {
		this.nomeRemetente = nomeRemetente;
	}
	public String getUsuarioEmail() {
		return usuarioEmail;
	}
	public void setUsuarioEmail(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}
	public String getSenhaEmail() {
		return senhaEmail;
	}
	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}