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
public class FuncionalidadePerfilPk implements Serializable {

	// Constantes
	private static final long serialVersionUID = -6018467669323612933L;

	@Column(name = "id_funcionalidade")
	private Long idFuncionalidade;
	@Column(name = "id_perfil")
	private Long idPerfil;
	
	/* Métodos Get/Set */
	public Long getIdFuncionalidade() {
		return idFuncionalidade;
	}
	public void setIdFuncionalidade(Long idFuncionalidade) {
		this.idFuncionalidade = idFuncionalidade;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idFuncionalidade == null) ? 0 : idFuncionalidade.hashCode());
		result = prime * result + ((idPerfil == null) ? 0 : idPerfil.hashCode());
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
		FuncionalidadePerfilPk other = (FuncionalidadePerfilPk) obj;
		if (idFuncionalidade == null) {
			if (other.idFuncionalidade != null)
				return false;
		} else if (!idFuncionalidade.equals(other.idFuncionalidade))
			return false;
		if (idPerfil == null) {
			if (other.idPerfil != null)
				return false;
		} else if (!idPerfil.equals(other.idPerfil))
			return false;
		return true;
	}
	
}