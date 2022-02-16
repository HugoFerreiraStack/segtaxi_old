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
@Table(name = "visualizacao_corrida")
public class VisualizacaoCorrida implements Serializable {

	// Constantes
	private static final long serialVersionUID = 8865111309070266927L;

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(name = "id_corrida")
	private Long idCorrida;
	@Column(name = "id_motorista")
	private Long idMotorista;
	@Column(name = "nome_motorista")
	private String nomeMotorista;
	@Column(name = "placa_veiculo")
	private String placaVeiculo;
	@Column(name = "unidade_veiculo")
	private String unidadeVeiculo;
	@Column(name = "data_visualizacao")
	private Date dataVisualizacao;
	@Column(name = "ponto_apoio")
	private String pontoApoio;
	@Column(name = "latitude")
	private String latitude;
	@Column(name = "longitude")
	private String longitude;
	@Column(name = "data_entrou_pa")
	private Date dataEntrouPA;
	@Column(name = "data_ultima_posicao")
	private Date dataUltimaPosicao;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdCorrida() {
		return idCorrida;
	}
	public void setIdCorrida(Long idCorrida) {
		this.idCorrida = idCorrida;
	}
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
	public String getPlacaVeiculo() {
		return placaVeiculo;
	}
	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}
	public String getUnidadeVeiculo() {
		return unidadeVeiculo;
	}
	public void setUnidadeVeiculo(String unidadeVeiculo) {
		this.unidadeVeiculo = unidadeVeiculo;
	}
	public Date getDataVisualizacao() {
		return dataVisualizacao;
	}
	public void setDataVisualizacao(Date dataVisualizacao) {
		this.dataVisualizacao = dataVisualizacao;
	}
	public String getPontoApoio() {
		return pontoApoio;
	}
	public void setPontoApoio(String pontoApoio) {
		this.pontoApoio = pontoApoio;
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
	public Date getDataEntrouPA() {
		return dataEntrouPA;
	}
	public void setDataEntrouPA(Date dataEntrouPA) {
		this.dataEntrouPA = dataEntrouPA;
	}
	public Date getDataUltimaPosicao() {
		return dataUltimaPosicao;
	}
	public void setDataUltimaPosicao(Date dataUltimaPosicao) {
		this.dataUltimaPosicao = dataUltimaPosicao;
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
		VisualizacaoCorrida other = (VisualizacaoCorrida) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}