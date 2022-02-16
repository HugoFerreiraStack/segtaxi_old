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
 * @author 55619
 *
 */
@Entity
@Table(name = "veiculo_empresa_conveniada")
public class VeiculoEmpresaConveniada implements Serializable {

	// Constantes
	private static final long serialVersionUID = -9065249280958001829L;
	
	@EmbeddedId
	private VeiculoEmpresaConveniadaPk id;
	
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	/* Métodos Get/Set */
	public VeiculoEmpresaConveniadaPk getId() {
		return id;
	}
	public void setId(VeiculoEmpresaConveniadaPk id) {
		this.id = id;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
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
		VeiculoEmpresaConveniada other = (VeiculoEmpresaConveniada) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}