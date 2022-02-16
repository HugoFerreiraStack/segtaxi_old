/*
* MetodoAcesso.java:
*
* Criação: 28 de jun de 2017
*/
package br.com.seg.econotaxi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parametro")
public class Parametro implements Serializable {

	// Constantes
	private static final long serialVersionUID = -9191477461954973732L;
	
	// Atributos
	@Id
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "servidor_smtp")
	private String urlSmtp;
	@Column(name = "porta_servidor_smtp")
	private String portaSmtp;
	@Column(name = "usuario_email")
	private String usuarioEmail;
	@Column(name = "senha_email")
	private String senhaEmail;

	@Column(name = "id_maxipago")
	private String idMaxipago;

	@Column(name = "chave_maxipago")
	private String chaveMaxipago;

	@Column(name = "ambiente_maxipago")
	private String ambienteMaxipago;
	
	@Column(name = "versao_app")
	private String versaoApp;
	
	@Column(name = "segundos_dentro_pa")
	private Long segundosDentroPa;
	
	@Column(name = "ind_tipo_corrida_tele")
	private Integer indicadorTipoCorridaTele;
	
	@Column(name = "versao_app_ios")
	private String versaoAppIos;
	
	@Column(name = "usuario_sms")
	private String usuarioSms;
	@Column(name = "senha_sms")
	private String senhaSms;
	
	@Column(name = "nome_aplicativo")
	private String nomeAplicativo;
	
	@Column(name = "logo_menu")
	private String logoMenu;
	
	@Column(name = "logo_login")
	private String logoLogin;
	
	@Column(name = "email_corridas_tele")
	private String emailCorridasTele;
	
	@Column(name = "host")
	private String host;
	
	@Column(name = "icon")
	private String icon;
	
	@Column(name = "versao_app_passageiro")
	private String versaoAppPassageiro;
	
	// Métodos get/set
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrlSmtp() {
		return urlSmtp;
	}
	public void setUrlSmtp(String urlSmtp) {
		this.urlSmtp = urlSmtp;
	}
	public String getPortaSmtp() {
		return portaSmtp;
	}
	public void setPortaSmtp(String portaSmtp) {
		this.portaSmtp = portaSmtp;
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

	public String getIdMaxipago() {
		return idMaxipago;
	}

	public void setIdMaxipago(String idMaxipago) {
		this.idMaxipago = idMaxipago;
	}

	public String getChaveMaxipago() {
		return chaveMaxipago;
	}

	public void setChaveMaxipago(String chaveMaxipago) {
		this.chaveMaxipago = chaveMaxipago;
	}

	public String getAmbienteMaxipago() {
		return ambienteMaxipago;
	}

	public void setAmbienteMaxipago(String ambienteMaxipago) {
		this.ambienteMaxipago = ambienteMaxipago;
	}
	
	public String getVersaoApp() {
		return versaoApp;
	}
	public void setVersaoApp(String versaoApp) {
		this.versaoApp = versaoApp;
	}
	public Long getSegundosDentroPa() {
		return segundosDentroPa;
	}
	public void setSegundosDentroPa(Long segundosDentroPa) {
		this.segundosDentroPa = segundosDentroPa;
	}
	public Integer getIndicadorTipoCorridaTele() {
		return indicadorTipoCorridaTele;
	}
	public void setIndicadorTipoCorridaTele(Integer indicadorTipoCorridaTele) {
		this.indicadorTipoCorridaTele = indicadorTipoCorridaTele;
	}
	public String getVersaoAppIos() {
		return versaoAppIos;
	}
	public void setVersaoAppIos(String versaoAppIos) {
		this.versaoAppIos = versaoAppIos;
	}
	public String getUsuarioSms() {
		return usuarioSms;
	}
	public void setUsuarioSms(String usuarioSms) {
		this.usuarioSms = usuarioSms;
	}
	public String getSenhaSms() {
		return senhaSms;
	}
	public void setSenhaSms(String senhaSms) {
		this.senhaSms = senhaSms;
	}
	public String getNomeAplicativo() {
		return nomeAplicativo;
	}
	public void setNomeAplicativo(String nomeAplicativo) {
		this.nomeAplicativo = nomeAplicativo;
	}
	public String getLogoMenu() {
		return logoMenu;
	}
	public void setLogoMenu(String logoMenu) {
		this.logoMenu = logoMenu;
	}
	public String getLogoLogin() {
		return logoLogin;
	}
	public void setLogoLogin(String logoLogin) {
		this.logoLogin = logoLogin;
	}
	public String getEmailCorridasTele() {
		return emailCorridasTele;
	}
	public void setEmailCorridasTele(String emailCorridasTele) {
		this.emailCorridasTele = emailCorridasTele;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getVersaoAppPassageiro() {
		return versaoAppPassageiro;
	}
	public void setVersaoAppPassageiro(String versaoAppPassageiro) {
		this.versaoAppPassageiro = versaoAppPassageiro;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parametro other = (Parametro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}