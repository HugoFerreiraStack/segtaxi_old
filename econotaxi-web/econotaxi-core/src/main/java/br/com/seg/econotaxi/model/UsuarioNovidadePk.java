/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author bruno
 *
 */
@Embeddable
public class UsuarioNovidadePk implements Serializable {

	// Constantes
	private static final long serialVersionUID = 1L;

	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "id_novidade")
	private Long idNovidade;
	
	/* Métodos Get/Set */
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdNovidade() {
		return idNovidade;
	}
	public void setIdNovidade(Long idNovidade) {
		this.idNovidade = idNovidade;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idNovidade == null) ? 0 : idNovidade.hashCode());
		result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
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
		UsuarioNovidadePk other = (UsuarioNovidadePk) obj;
		if (idNovidade == null) {
			if (other.idNovidade != null)
				return false;
		} else if (!idNovidade.equals(other.idNovidade))
			return false;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		return true;
	}
	
}