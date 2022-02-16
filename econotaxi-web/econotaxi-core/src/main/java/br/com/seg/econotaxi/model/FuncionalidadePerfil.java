/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "funcionalidade_perfil")
public class FuncionalidadePerfil implements Serializable {

	// Constantes
	private static final long serialVersionUID = -2829082105483034191L;

	@EmbeddedId
	private FuncionalidadePerfilPk id;
	
	@ManyToOne
	@JoinColumn(name = "id_perfil", referencedColumnName = "id", insertable = false, updatable = false)
	private Perfil perfil;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionalidade", referencedColumnName = "id", insertable = false, updatable = false)
	private Funcionalidade funcionalidade;
	
	/* Métodos Get/Set */
	public FuncionalidadePerfilPk getId() {
		return id;
	}
	public void setId(FuncionalidadePerfilPk id) {
		this.id = id;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	public Funcionalidade getFuncionalidade() {
		return funcionalidade;
	}
	public void setFuncionalidade(Funcionalidade funcionalidade) {
		this.funcionalidade = funcionalidade;
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
		FuncionalidadePerfil other = (FuncionalidadePerfil) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}