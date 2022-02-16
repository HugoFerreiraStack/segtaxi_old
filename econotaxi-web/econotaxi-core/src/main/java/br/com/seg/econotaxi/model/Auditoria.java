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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.enums.TipoTransacaoEnum;

@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable {

	// Constantes
	private static final long serialVersionUID = 2365229747291603698L;
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "nu_tipo_transacao")
	private Integer tipoTransacao;
	@Column(name = "dt_evento")
	private Date dataEvento;
	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "ds_json_entidade")
	private String objeto;
	@Column(name = "ds_classe_entidade")
	private String classe;
	@Column(name = "nu_ip")
	private String ip;
	@Column(name = "nu_transacao_via_sistema")
	private Integer transacaoViaSistema;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
	private Usuario usuario;
	
	@Transient
	private Date dataInicioPeriodo;
	@Transient
	private Date dataFimPeriodo;
	
	// Métodos fet/set
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTipoTransacao() {
		return tipoTransacao;
	}
	public void setTipoTransacao(Integer tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}
	public Date getDataEvento() {
		return dataEvento;
	}
	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getTransacaoViaSistema() {
		return transacaoViaSistema;
	}
	public void setTransacaoViaSistema(Integer transacaoViaSistema) {
		this.transacaoViaSistema = transacaoViaSistema;
	}
	public Date getDataInicioPeriodo() {
		return dataInicioPeriodo;
	}
	public void setDataInicioPeriodo(Date dataInicioPeriodo) {
		this.dataInicioPeriodo = dataInicioPeriodo;
	}
	public Date getDataFimPeriodo() {
		return dataFimPeriodo;
	}
	public void setDataFimPeriodo(Date dataFimPeriodo) {
		this.dataFimPeriodo = dataFimPeriodo;
	}
	public String getDescricaoTipoTransacao() {
		
		String descricaoTipoTransacao = null;
		if (tipoTransacao != null) {
			descricaoTipoTransacao = TipoTransacaoEnum.valueOfTipoTransacao(tipoTransacao).getDescricao();
		}
		return descricaoTipoTransacao;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getClasseSimples() {
		
		String classeSimples = null;
		if (classe != null && !StringUtils.isEmpty(classe)) {
			classeSimples = classe.substring(classe.lastIndexOf(".") + 1);
		}
		return classeSimples;
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
		Auditoria other = (Auditoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}