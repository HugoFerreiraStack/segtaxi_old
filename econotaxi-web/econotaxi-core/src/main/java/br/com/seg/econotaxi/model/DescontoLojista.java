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

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "desconto_lojista")
public class DescontoLojista implements Serializable {

	// Constantes
	private static final long serialVersionUID = 7718308414082453259L;

	// Atributos
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    @Column(name = "id_lojista")
	private Long idLojista;
    @Column(name = "id_usuario_cliente")
	private Long idUsuarioCliente;
    @Column(name = "hash")
	private String hash;
    @Column(name = "data_cadastro")
	private Date dataCadastro;
    @Column(name = "data_utilizacao")
	private Date dataUtilizacao;
    
    @ManyToOne
	@JoinColumn(name = "id_lojista", referencedColumnName = "id", insertable = false, updatable = false)
	private Lojista lojista;
    
    @Transient
    private String mensagemRetorno;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdLojista() {
		return idLojista;
	}
	public void setIdLojista(Long idLojista) {
		this.idLojista = idLojista;
	}
	public Long getIdUsuarioCliente() {
		return idUsuarioCliente;
	}
	public void setIdUsuarioCliente(Long idUsuarioCliente) {
		this.idUsuarioCliente = idUsuarioCliente;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Date getDataUtilizacao() {
		return dataUtilizacao;
	}
	public void setDataUtilizacao(Date dataUtilizacao) {
		this.dataUtilizacao = dataUtilizacao;
	}
	public Lojista getLojista() {
		return lojista;
	}
	public void setLojista(Lojista lojista) {
		this.lojista = lojista;
	}
	public String getMensagemRetorno() {
		return mensagemRetorno;
	}
	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
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
		DescontoLojista other = (DescontoLojista) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}