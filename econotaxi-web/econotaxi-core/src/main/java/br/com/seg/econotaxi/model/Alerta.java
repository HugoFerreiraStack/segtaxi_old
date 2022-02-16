package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

//@Entity
//@Table(name = "tb_alerta")
//@SequenceGenerator(name = "seqAlerta", sequenceName = "sq_alerta", initialValue = 1, allocationSize = 1)
public class Alerta implements Serializable {

	// Constantes
	private static final long serialVersionUID = 1787338418699115612L;
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAlerta")
	@Column(name = "id_alerta", length = 18)
	private Long id;
	@Column(name = "nu_tipo_arquivo", length = 1)
	private Integer tipoArquivo;
	@Column(name = "ds_arquivo", length = 100)
	private String nomeArquivo;
	@Column(name = "dt_ocorrencia_alerta")
	private Date dataOcorrencia;
	@Column(name = "ds_mensagem_erro_arquivo", length = 500)
	private String mensagemErroArquivo;
	@Column(name = "nu_status_notificacao", length = 1)
	private Integer statusNotificacao;
	@Column(name = "nu_status_alerta", length = 1)
	private Integer statusAlerta;
	@Column(name = "id_controle_arquivo", length = 18)
	private Long idControleArquivo;
	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "ds_motivo_resolucao")
	private String motivoResolucao;
	@Column(name = "nu_status_email", length = 1)
	private Integer statusEmail;
	@Column(name = "id_instituicao")
	private Long idInstituicao;
	@Column(name = "nu_funcao_arquivo", length = 1, precision = 1)
    private Integer funcaoArquivo;
	
	@Transient
	private boolean problemaConhecido;
	@Transient
	private boolean problemaResolvido;

	// Métodos Get/Set
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTipoArquivo() {
		return tipoArquivo;
	}
	public void setTipoArquivo(Integer tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public Date getDataOcorrencia() {
		return dataOcorrencia;
	}
	public void setDataOcorrencia(Date dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
	}
	public String getMensagemErroArquivo() {
		return mensagemErroArquivo;
	}
	public void setMensagemErroArquivo(String mensagemErroArquivo) {
		this.mensagemErroArquivo = mensagemErroArquivo;
	}
	public Integer getStatusNotificacao() {
		return statusNotificacao;
	}
	public void setStatusNotificacao(Integer statusNotificacao) {
		this.statusNotificacao = statusNotificacao;
		if (statusNotificacao.equals(2)) {
			setProblemaResolvido(Boolean.TRUE);
		}
	}
	public Integer getStatusAlerta() {
		return statusAlerta;
	}
	public void setStatusAlerta(Integer statusAlerta) {
		this.statusAlerta = statusAlerta;
		if (statusAlerta.equals(2)) {
			setProblemaConhecido(Boolean.TRUE);
		}
	}
	public Long getIdControleArquivo() {
		return idControleArquivo;
	}
	public void setIdControleArquivo(Long idControleArquivo) {
		this.idControleArquivo = idControleArquivo;
	}
	public boolean isProblemaConhecido() {
		return problemaConhecido;
	}
	public void setProblemaConhecido(boolean problemaConhecido) {
		this.problemaConhecido = problemaConhecido;
	}
	public boolean isProblemaResolvido() {
		return problemaResolvido;
	}
	public void setProblemaResolvido(boolean problemaResolvido) {
		this.problemaResolvido = problemaResolvido;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getMotivoResolucao() {
		return motivoResolucao;
	}
	public void setMotivoResolucao(String motivoResolucao) {
		this.motivoResolucao = motivoResolucao;
	}
	public Integer getStatusEmail() {
		return statusEmail;
	}
	public void setStatusEmail(Integer statusEmail) {
		this.statusEmail = statusEmail;
	}
	public Long getIdInstituicao() {
		return idInstituicao;
	}
	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}
	public Integer getFuncaoArquivo() {
		return funcaoArquivo;
	}
	public void setFuncaoArquivo(Integer funcaoArquivo) {
		this.funcaoArquivo = funcaoArquivo;
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
		Alerta other = (Alerta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}