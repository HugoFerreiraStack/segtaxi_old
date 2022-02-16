/**
 * 
 */
package br.com.seg.econotaxi.vo;

import java.io.Serializable;

import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;

/**
 * @author bruno
 *
 */
public class MotoristaVO implements Serializable {

	// Constantes
	private static final long serialVersionUID = -950438458833187185L;

	// Atributos
	private Usuario usuario;
	private Motorista motorista;
	private Veiculo veiculo;
	
	// Métodos get/set
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Motorista getMotorista() {
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
}