/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "usuario_novidade")
public class UsuarioNovidade implements Serializable {

	// Constantes
	private static final long serialVersionUID = -2148266820387772512L;

	@EmbeddedId
	private UsuarioNovidadePk id;
	
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

	/* Métodos Get/Set */
	public UsuarioNovidadePk getId() {
		return id;
	}
	public void setId(UsuarioNovidadePk id) {
		this.id = id;
	}
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
		UsuarioNovidade other = (UsuarioNovidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}