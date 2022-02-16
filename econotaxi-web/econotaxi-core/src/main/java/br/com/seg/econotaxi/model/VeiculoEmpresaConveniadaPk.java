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
public class VeiculoEmpresaConveniadaPk implements Serializable {

	// Constantes
	private static final long serialVersionUID = -3550171418362327458L;
	
	@Column(name = "id_veiculo")
	private Long idVeiculo;
	@Column(name = "id_empresa_conveniada")
	private Long idEmpresaConveniada;
	
	/* Métodos Get/Set */
	public Long getIdVeiculo() {
		return idVeiculo;
	}
	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}
	public Long getIdEmpresaConveniada() {
		return idEmpresaConveniada;
	}
	public void setIdEmpresaConveniada(Long idEmpresaConveniada) {
		this.idEmpresaConveniada = idEmpresaConveniada;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEmpresaConveniada == null) ? 0 : idEmpresaConveniada.hashCode());
		result = prime * result + ((idVeiculo == null) ? 0 : idVeiculo.hashCode());
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
		VeiculoEmpresaConveniadaPk other = (VeiculoEmpresaConveniadaPk) obj;
		if (idEmpresaConveniada == null) {
			if (other.idEmpresaConveniada != null)
				return false;
		} else if (!idEmpresaConveniada.equals(other.idEmpresaConveniada))
			return false;
		if (idVeiculo == null) {
			if (other.idVeiculo != null)
				return false;
		} else if (!idVeiculo.equals(other.idVeiculo))
			return false;
		return true;
	}
	
}