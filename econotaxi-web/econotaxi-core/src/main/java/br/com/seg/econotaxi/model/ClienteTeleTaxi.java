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
@Table(name = "cliente_tele_taxi")
public class ClienteTeleTaxi implements Serializable {

	// Constantes
	private static final long serialVersionUID = 7530518482898545482L;
	
	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "email")
	private String email;
	@Column(name = "telefone")
	private String telefone;
	@Column(name = "celular")
	private String celular;
	@Column(name = "data_nascimento")
	private String dataNascimento;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Column(name = "empresa")
	private Integer empresa;
	@Column(name = "qtd_corridas")
	private Long qtdCorridas;
	@Column(name = "origem")
	private String origem;
	@Column(name = "origem_endereco")
	private String origemEndereco;
	@Column(name = "latitude_origem")
	private String latitudeOrigem;
	@Column(name = "longitude_origem")
	private String longitudeOrigem;
	@Column(name = "destino")
	private String destino;
	@Column(name = "destino_endereco")
	private String destinoEndereco;
	@Column(name = "latitude_destino")
	private String latitudeDestino;
	@Column(name = "longitude_destino")
	private String longitudeDestino;
	@Column(name = "previsao_corrida")
	private Long previsaoCorrida;
	@Column(name = "tempo_corrida")
	private Long tempoCorrida;
	@Column(name = "distancia_km")
	private String distanciaKm;
	@Column(name = "complemento")
	private String complemento;
	@Column(name = "observacao_tele")
	private String observacaoTele;
	
	/* Métodos Get/Set */
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Integer getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Integer empresa) {
		this.empresa = empresa;
	}
	public Long getQtdCorridas() {
		return qtdCorridas;
	}
	public void setQtdCorridas(Long qtdCorridas) {
		this.qtdCorridas = qtdCorridas;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getOrigemEndereco() {
		return origemEndereco;
	}
	public void setOrigemEndereco(String origemEndereco) {
		this.origemEndereco = origemEndereco;
	}
	public String getLatitudeOrigem() {
		return latitudeOrigem;
	}
	public void setLatitudeOrigem(String latitudeOrigem) {
		this.latitudeOrigem = latitudeOrigem;
	}
	public String getLongitudeOrigem() {
		return longitudeOrigem;
	}
	public void setLongitudeOrigem(String longitudeOrigem) {
		this.longitudeOrigem = longitudeOrigem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getDestinoEndereco() {
		return destinoEndereco;
	}
	public void setDestinoEndereco(String destinoEndereco) {
		this.destinoEndereco = destinoEndereco;
	}
	public String getLatitudeDestino() {
		return latitudeDestino;
	}
	public void setLatitudeDestino(String latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}
	public String getLongitudeDestino() {
		return longitudeDestino;
	}
	public void setLongitudeDestino(String longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}
	public Long getPrevisaoCorrida() {
		return previsaoCorrida;
	}
	public void setPrevisaoCorrida(Long previsaoCorrida) {
		this.previsaoCorrida = previsaoCorrida;
	}
	public Long getTempoCorrida() {
		return tempoCorrida;
	}
	public void setTempoCorrida(Long tempoCorrida) {
		this.tempoCorrida = tempoCorrida;
	}
	public String getDistanciaKm() {
		return distanciaKm;
	}
	public void setDistanciaKm(String distanciaKm) {
		this.distanciaKm = distanciaKm;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getObservacaoTele() {
		return observacaoTele;
	}
	public void setObservacaoTele(String observacaoTele) {
		this.observacaoTele = observacaoTele;
	}
	
}