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
@Table(name = "usuario_notificacao")
public class UsuarioNotificacao implements Serializable {

	// Constantes
	private static final long serialVersionUID = 8962834924199051547L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "id_notificacao")
	private Long idNotificacao;
	
	@Column(name = "ind_visto")
	private Integer indicadorVisto;
	@Column(name = "ind_like")
	private Integer indicadorLike;
	@Column(name = "comentario")
	private String comentario;
	@Column(name = "data_comentario")
	private Date dataComentario;
	@Column(name = "data_visto")
	private Date dataVisto;
	@Column(name = "texto")
	private String texto;
	@Column(name = "data_notificacao")
	private Date dataNotificacao;

	/* Métodos Get/Set */
	public Integer getIndicadorVisto() {
		return indicadorVisto;
	}
	public void setIndicadorVisto(Integer indicadorVisto) {
		this.indicadorVisto = indicadorVisto;
	}
	public Integer getIndicadorLike() {
		return indicadorLike;
	}
	public void setIndicadorLike(Integer indicadorLike) {
		this.indicadorLike = indicadorLike;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Date getDataComentario() {
		return dataComentario;
	}
	public void setDataComentario(Date dataComentario) {
		this.dataComentario = dataComentario;
	}
	public Date getDataVisto() {
		return dataVisto;
	}
	public void setDataVisto(Date dataVisto) {
		this.dataVisto = dataVisto;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdNotificacao() {
		return idNotificacao;
	}
	public void setIdNotificacao(Long idNotificacao) {
		this.idNotificacao = idNotificacao;
	}
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
		UsuarioNotificacao other = (UsuarioNotificacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}