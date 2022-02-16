/**
 * 
 */
package br.com.seg.econotaxi.vo;

import java.util.List;

/**
 * @author bruno
 *
 */
public class PontoApoioVO {

	private String pontoApoio;
	private List<CarrosVO> carros;
	
	/* Métodos get/set */
	public String getPontoApoio() {
		return pontoApoio;
	}
	public void setPontoApoio(String pontoApoio) {
		this.pontoApoio = pontoApoio;
	}
	public List<CarrosVO> getCarros() {
		return carros;
	}
	public void setCarros(List<CarrosVO> carros) {
		this.carros = carros;
	}
	
}
