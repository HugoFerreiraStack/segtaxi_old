/**
 * 
 */
package br.com.seg.econotaxi.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;

/**
 * @author bruno
 *
 */
public class CarrosVO implements Serializable {

	// Constantes
	private static final long serialVersionUID = 5827907179478510870L;

	public CarrosVO() {}
	
	public CarrosVO(Motorista m, Usuario u, Veiculo v) {
		
		this.idMotorista = m.getId();
		this.nomeMotorista = u.getNome();
		this.tipo = v.getTipo();
		this.sexo = u.getSexo();
		this.adaptado = v.getIndicadorAdaptado();
		this.latitude = u.getLatitudeCorrente();
		this.longitude = u.getLongitudeCorrente();
		this.dataUltimaPosicao = u.getDataUltimaPosicao();
		this.tipoTeleTaxi = m.getTipoTeleTaxi();
		this.celular = u.getCelular();
		this.idCidade = m.getIdCidade();
		this.unidade = v.getUnidade();
		this.placa = v.getPlaca();
		this.idVeiculo = v.getId();
	}

	private Long idMotorista;
	private Long idVeiculo;
	private String nomeMotorista;
	private Integer tipo;
	private String sexo;
	private Integer adaptado;
	private String latitude;
	private String longitude;
	private Date dataUltimaPosicao;
	private Double distancia;
	private Long idPontoApoio;
	private Date dataPontoApoio;
	private Integer tipoTeleTaxi;
	private String celular;
	private String nomePontoApoio;
	private Long idCidade;
	private String unidade;
	private String placa;
	
	/* Métodos get/set */
	public Long getIdMotorista() {
		return idMotorista;
	}
	public void setIdMotorista(Long idMotorista) {
		this.idMotorista = idMotorista;
	}
	public String getNomeMotorista() {
		return nomeMotorista;
	}
	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Integer getAdaptado() {
		return adaptado;
	}
	public void setAdaptado(Integer adaptado) {
		this.adaptado = adaptado;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Date getDataUltimaPosicao() {
		return dataUltimaPosicao;
	}
	public void setDataUltimaPosicao(Date dataUltimaPosicao) {
		this.dataUltimaPosicao = dataUltimaPosicao;
	}
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	public Long getIdPontoApoio() {
		return idPontoApoio;
	}
	public void setIdPontoApoio(Long idPontoApoio) {
		this.idPontoApoio = idPontoApoio;
	}
	public Date getDataPontoApoio() {
		return dataPontoApoio;
	}
	public void setDataPontoApoio(Date dataPontoApoio) {
		this.dataPontoApoio = dataPontoApoio;
	}
	public Integer getTipoTeleTaxi() {
		return tipoTeleTaxi;
	}
	public void setTipoTeleTaxi(Integer tipoTeleTaxi) {
		this.tipoTeleTaxi = tipoTeleTaxi;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getNomePontoApoio() {
		return nomePontoApoio;
	}
	public void setNomePontoApoio(String nomePontoApoio) {
		this.nomePontoApoio = nomePontoApoio;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	public void setIdCidade(Long idCidade) {
		this.idCidade = idCidade;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Long getIdVeiculo() {
		return idVeiculo;
	}
	public void setIdVeiculo(Long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}
	public String getDataUltimaPosicaoFormatada() {
		String data = "";
		if (getDataUltimaPosicao() != null) {
			data = new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(getDataUltimaPosicao());
		}
		return data;
	}
	public String getDataPontoApoioFormatada() {
		String data = "";
		if (getDataPontoApoio() != null) {
			data = new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(getDataPontoApoio());
		}
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMotorista == null) ? 0 : idMotorista.hashCode());
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
		CarrosVO other = (CarrosVO) obj;
		if (idMotorista == null) {
			if (other.idMotorista != null)
				return false;
		} else if (!idMotorista.equals(other.idMotorista))
			return false;
		return true;
	} 
	
}