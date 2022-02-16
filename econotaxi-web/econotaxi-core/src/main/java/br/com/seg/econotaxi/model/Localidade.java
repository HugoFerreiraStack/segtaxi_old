/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "localidade")
public class Localidade implements Serializable {

	// Constantes
	private static final long serialVersionUID = -5749099434495860764L;

	// Atributos
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "latitude1")
	private String latitude1;
	@Column(name = "longitude1")
	private String longitude1;
	@Column(name = "latitude2")
	private String latitude2;
	@Column(name = "longitude2")
	private String longitude2;
	@Column(name = "latitude3")
	private String latitude3;
	@Column(name = "longitude3")
	private String longitude3;
	@Column(name = "latitude4")
	private String latitude4;
	@Column(name = "longitude4")
	private String longitude4;
	@Column(name = "horario_funcionamento")
	private Integer horarioFuncionamento;
	@Column(name = "dias_funcionamento")
	private Integer diasFuncionamento;
	@Column(name = "observacoes")
	private String observacoes;
	@Column(name = "coordenadas")
	private String coordenadas;
	@Column(name = "forma_desenho")
	private Integer formaDesenho;
	
	@Column(name = "id_pa_1")
	private Long idPa1;
	@Column(name = "id_pa_2")
	private Long idPa2;
	@Column(name = "id_pa_3")
	private Long idPa3;
	@Column(name = "id_pa_4")
	private Long idPa4;
	
	@ManyToOne
	@JoinColumn(name = "id_cidade", referencedColumnName = "id")
	private Cidade cidade;
	
	@Transient
	private List<CarrosVO> carros;
	
	@Transient
	private Double distancia;
	
	/* Métodos get/set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getLatitude1() {
		return latitude1;
	}
	public void setLatitude1(String latitude1) {
		this.latitude1 = latitude1;
	}
	public String getLongitude1() {
		return longitude1;
	}
	public void setLongitude1(String longitude1) {
		this.longitude1 = longitude1;
	}
	public String getLatitude2() {
		return latitude2;
	}
	public void setLatitude2(String latitude2) {
		this.latitude2 = latitude2;
	}
	public String getLongitude2() {
		return longitude2;
	}
	public void setLongitude2(String longitude2) {
		this.longitude2 = longitude2;
	}
	public String getLatitude3() {
		return latitude3;
	}
	public void setLatitude3(String latitude3) {
		this.latitude3 = latitude3;
	}
	public String getLongitude3() {
		return longitude3;
	}
	public void setLongitude3(String longitude3) {
		this.longitude3 = longitude3;
	}
	public String getLatitude4() {
		return latitude4;
	}
	public void setLatitude4(String latitude4) {
		this.latitude4 = latitude4;
	}
	public String getLongitude4() {
		return longitude4;
	}
	public void setLongitude4(String longitude4) {
		this.longitude4 = longitude4;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Integer getHorarioFuncionamento() {
		return horarioFuncionamento;
	}
	public void setHorarioFuncionamento(Integer horarioFuncionamento) {
		this.horarioFuncionamento = horarioFuncionamento;
	}
	public Integer getDiasFuncionamento() {
		return diasFuncionamento;
	}
	public void setDiasFuncionamento(Integer diasFuncionamento) {
		this.diasFuncionamento = diasFuncionamento;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}
	public Integer getFormaDesenho() {
		return formaDesenho;
	}
	public void setFormaDesenho(Integer formaDesenho) {
		this.formaDesenho = formaDesenho;
	}
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	public List<CarrosVO> getCarros() {
		return carros;
	}
	public void setCarros(List<CarrosVO> carros) {
		this.carros = carros;
	}
	public Long getIdPa1() {
		return idPa1;
	}
	public void setIdPa1(Long idPa1) {
		this.idPa1 = idPa1;
	}
	public Long getIdPa2() {
		return idPa2;
	}
	public void setIdPa2(Long idPa2) {
		this.idPa2 = idPa2;
	}
	public Long getIdPa3() {
		return idPa3;
	}
	public void setIdPa3(Long idPa3) {
		this.idPa3 = idPa3;
	}
	public Long getIdPa4() {
		return idPa4;
	}
	public void setIdPa4(Long idPa4) {
		this.idPa4 = idPa4;
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
		Localidade other = (Localidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}