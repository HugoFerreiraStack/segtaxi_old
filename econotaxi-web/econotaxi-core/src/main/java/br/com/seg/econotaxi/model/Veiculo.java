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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.seg.econotaxi.enums.StatusVeiculoEnum;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "veiculo")
public class Veiculo implements Serializable {

	// Constantes
	private static final long serialVersionUID = -4858060612969631865L;

	public Veiculo() {}
	
	public Veiculo(Veiculo veiculo, Usuario usuario) {
		super();
		this.id = veiculo.getId();
		this.tipo = veiculo.getTipo();
		this.descricao = veiculo.getDescricao();
		this.indicadorPadrao = veiculo.getIndicadorPadrao();
		this.indicadorExecutivo = veiculo.getIndicadorExecutivo();
		this.indicadorAdaptado = veiculo.getIndicadorAdaptado();
		this.indicadorBicicleta = veiculo.getIndicadorBicicleta();
		this.indicadorPool = veiculo.getIndicadorPool();
		this.indicadorSuv = veiculo.getIndicadorSuv();
		this.indicador4x4 = veiculo.getIndicador4x4();
		this.ativo = veiculo.getAtivo();
		this.nota = veiculo.getNota();
		this.ime = veiculo.getIme();
		this.dataSolicitacao = veiculo.getDataSolicitacao();
		this.dataAprovacao = veiculo.getDataAprovacao();
		this.placa = veiculo.getPlaca();
		this.renavan = veiculo.getRenavan();
		this.cor = veiculo.getCor();
		this.portas = veiculo.getPortas();
		this.marca = veiculo.getMarca();
		this.modelo = veiculo.getModelo();
		this.anoFabricacao = veiculo.getAnoFabricacao();
		this.indicadorAutomatico = veiculo.getIndicadorAutomatico();
		this.indicadorBancoCouro = veiculo.getIndicadorBancoCouro();
		this.indicadorArCondicionado = veiculo.getIndicadorArCondicionado();
		this.cidade = veiculo.getCidade();
		this.siglaUf = veiculo.getSiglaUf();
		this.status = veiculo.getStatus();
		this.dataUltimaPosicao = veiculo.getDataUltimaPosicao();
		this.latitudeCorrente = veiculo.getLatitudeCorrente();
		this.longitudeCorrente = veiculo.getLongitudeCorrente();
		this.motivoDesautorizacao = veiculo.getMotivoDesautorizacao();
		this.motivoBloqueio = veiculo.getMotivoBloqueio();
		this.imagemCrlv = veiculo.getImagemCrlv();
		this.imagemCiv = veiculo.getImagemCiv();
		this.motorista = veiculo.getMotorista();
		this.motorista.setNome(usuario.getNome());
		this.classificacao = veiculo.getClassificacao();
		this.qtdClassificacao = veiculo.getQtdClassificacao();
		this.indicadorCadeirinha = veiculo.getIndicadorCadeirinha();
		this.unidade = veiculo.getUnidade();
		this.indicadorRastreadorSeg = veiculo.getIndicadorRastreadorSeg();
		this.msgErroRastreador = veiculo.getMsgErroRastreador();
	}
	
	public Veiculo(Veiculo veiculo, Usuario usuario, Integer indicadorPossuiEmpresaAssociada) {
		super();
		this.id = veiculo.getId();
		this.tipo = veiculo.getTipo();
		this.descricao = veiculo.getDescricao();
		this.indicadorPadrao = veiculo.getIndicadorPadrao();
		this.indicadorExecutivo = veiculo.getIndicadorExecutivo();
		this.indicadorAdaptado = veiculo.getIndicadorAdaptado();
		this.indicadorBicicleta = veiculo.getIndicadorBicicleta();
		this.indicadorPool = veiculo.getIndicadorPool();
		this.indicadorSuv = veiculo.getIndicadorSuv();
		this.indicador4x4 = veiculo.getIndicador4x4();
		this.ativo = veiculo.getAtivo();
		this.nota = veiculo.getNota();
		this.ime = veiculo.getIme();
		this.dataSolicitacao = veiculo.getDataSolicitacao();
		this.dataAprovacao = veiculo.getDataAprovacao();
		this.placa = veiculo.getPlaca();
		this.renavan = veiculo.getRenavan();
		this.cor = veiculo.getCor();
		this.portas = veiculo.getPortas();
		this.marca = veiculo.getMarca();
		this.modelo = veiculo.getModelo();
		this.anoFabricacao = veiculo.getAnoFabricacao();
		this.indicadorAutomatico = veiculo.getIndicadorAutomatico();
		this.indicadorBancoCouro = veiculo.getIndicadorBancoCouro();
		this.indicadorArCondicionado = veiculo.getIndicadorArCondicionado();
		this.cidade = veiculo.getCidade();
		this.siglaUf = veiculo.getSiglaUf();
		this.status = veiculo.getStatus();
		this.dataUltimaPosicao = veiculo.getDataUltimaPosicao();
		this.latitudeCorrente = veiculo.getLatitudeCorrente();
		this.longitudeCorrente = veiculo.getLongitudeCorrente();
		this.motivoDesautorizacao = veiculo.getMotivoDesautorizacao();
		this.motivoBloqueio = veiculo.getMotivoBloqueio();
		this.imagemCrlv = veiculo.getImagemCrlv();
		this.imagemCiv = veiculo.getImagemCiv();
		this.motorista = veiculo.getMotorista();
		this.motorista.setNome(usuario.getNome());
		this.classificacao = veiculo.getClassificacao();
		this.qtdClassificacao = veiculo.getQtdClassificacao();
		this.indicadorCadeirinha = veiculo.getIndicadorCadeirinha();
		this.unidade = veiculo.getUnidade();
		this.indicadorRastreadorSeg = veiculo.getIndicadorRastreadorSeg();
		this.msgErroRastreador = veiculo.getMsgErroRastreador();
		this.indicadorPossuiEmpresaAssociada = indicadorPossuiEmpresaAssociada;
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "ind_padrao")
	private Integer indicadorPadrao;
	@Column(name = "ind_executivo")
	private Integer indicadorExecutivo;
	@Column(name = "ind_adaptado")
	private Integer indicadorAdaptado;
	@Column(name = "ind_bicicleta")
	private Integer indicadorBicicleta;
	@Column(name = "ind_pool")
	private Integer indicadorPool;
	@Column(name = "ind_suv")
	private Integer indicadorSuv;
	@Column(name = "ind_4x4")
	private Integer indicador4x4;
	@Column(name = "ativo")
	private Integer ativo;
	@Column(name = "nota")
	private Double nota;
	@Column(name = "ime")
	private String ime;
	@Column(name = "data_solicitacao")
	private Date dataSolicitacao;
	@Column(name = "data_aprovacao")
	private Date dataAprovacao;
	@Column(name = "placa")
	private String placa;
	@Column(name = "renavan")
	private String renavan;
	@Column(name = "cor")
	private String cor;
	@Column(name = "portas")
	private Integer portas;
	@Column(name = "marca")
	private String marca;
	@Column(name = "modelo")
	private String modelo;
	@Column(name = "ano_fabricacao")
	private String anoFabricacao;
	@Column(name = "ind_automatico")
	private Integer indicadorAutomatico;
	@Column(name = "ind_banco_couro")
	private Integer indicadorBancoCouro;
	@Column(name = "ind_ar_condicionado")
	private Integer indicadorArCondicionado;
	@Column(name = "cidade")
	private String cidade;
	@Column(name = "sigla_uf")
	private String siglaUf;
	@Column(name = "status")
	private Integer status;
	@Column(name = "data_ultima_posicao")
	private Date dataUltimaPosicao;
	@Column(name = "latitude_corrente")
	private String latitudeCorrente;
	@Column(name = "longitude_corrente")
	private String longitudeCorrente;
	@Column(name = "motivo_desautorizacao")
	private String motivoDesautorizacao;
	@Column(name = "motivo_bloqueio")
	private String motivoBloqueio;
	@Column(name = "imagem_crlv")
	private String imagemCrlv;
	@Column(name = "imagem_civ")
	private String imagemCiv;
	@Column(name = "classificacao")
	private Double classificacao;
	@Column(name = "qtd_classificacao")
	private Integer qtdClassificacao;
	@Column(name = "ind_cadeirinha")
	private Integer indicadorCadeirinha;
	@Column(name = "unidade")
	private String unidade;
	//@Column(name = "ind_rastreador_seg")
	@Transient
	private Integer indicadorRastreadorSeg;
	//@Column(name = "msg_erro_rastreador")
	@Transient
	private String msgErroRastreador;
	
	@ManyToOne
	@JoinColumn(name = "id_motorista", referencedColumnName = "id")
	private Motorista motorista;
	
	@Transient
	private Integer indicadorLogado;
	@Transient
	private String nomeMotoristaLogado;
	@Transient
	private Integer indicadorPossuiEmpresaAssociada;
	@Transient
	private Long idEmpresaConveniada;
	
	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIndicadorPadrao() {
		return indicadorPadrao;
	}
	public void setIndicadorPadrao(Integer indicadorPadrao) {
		this.indicadorPadrao = indicadorPadrao;
	}
	public Integer getIndicadorExecutivo() {
		return indicadorExecutivo;
	}
	public void setIndicadorExecutivo(Integer indicadorExecutivo) {
		this.indicadorExecutivo = indicadorExecutivo;
	}
	public Integer getIndicadorAdaptado() {
		return indicadorAdaptado;
	}
	public void setIndicadorAdaptado(Integer indicadorAdaptado) {
		this.indicadorAdaptado = indicadorAdaptado;
	}
	public Integer getIndicadorBicicleta() {
		return indicadorBicicleta;
	}
	public void setIndicadorBicicleta(Integer indicadorBicicleta) {
		this.indicadorBicicleta = indicadorBicicleta;
	}
	public Integer getIndicadorPool() {
		return indicadorPool;
	}
	public void setIndicadorPool(Integer indicadorPool) {
		this.indicadorPool = indicadorPool;
	}
	public Integer getIndicadorSuv() {
		return indicadorSuv;
	}
	public void setIndicadorSuv(Integer indicadorSuv) {
		this.indicadorSuv = indicadorSuv;
	}
	public Integer getIndicador4x4() {
		return indicador4x4;
	}
	public void setIndicador4x4(Integer indicador4x4) {
		this.indicador4x4 = indicador4x4;
	}
	public Integer getAtivo() {
		return ativo;
	}
	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}
	public Double getNota() {
		return nota;
	}
	public void setNota(Double nota) {
		this.nota = nota;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public Date getDataAprovacao() {
		return dataAprovacao;
	}
	public void setDataAprovacao(Date dataAprovacao) {
		this.dataAprovacao = dataAprovacao;
	}
	public Motorista getMotorista() {
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getRenavan() {
		return renavan;
	}
	public void setRenavan(String renavan) {
		this.renavan = renavan;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public Integer getPortas() {
		return portas;
	}
	public void setPortas(Integer portas) {
		this.portas = portas;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(String anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	public Integer getIndicadorAutomatico() {
		return indicadorAutomatico;
	}
	public void setIndicadorAutomatico(Integer indicadorAutomatico) {
		this.indicadorAutomatico = indicadorAutomatico;
	}
	public Integer getIndicadorBancoCouro() {
		return indicadorBancoCouro;
	}
	public void setIndicadorBancoCouro(Integer indicadorBancoCouro) {
		this.indicadorBancoCouro = indicadorBancoCouro;
	}
	public Integer getIndicadorArCondicionado() {
		return indicadorArCondicionado;
	}
	public void setIndicadorArCondicionado(Integer indicadorArCondicionado) {
		this.indicadorArCondicionado = indicadorArCondicionado;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getSiglaUf() {
		return siglaUf;
	}
	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getDataUltimaPosicao() {
		return dataUltimaPosicao;
	}
	public void setDataUltimaPosicao(Date dataUltimaPosicao) {
		this.dataUltimaPosicao = dataUltimaPosicao;
	}
	public String getLatitudeCorrente() {
		return latitudeCorrente;
	}
	public void setLatitudeCorrente(String latitudeCorrente) {
		this.latitudeCorrente = latitudeCorrente;
	}
	public String getLongitudeCorrente() {
		return longitudeCorrente;
	}
	public void setLongitudeCorrente(String longitudeCorrente) {
		this.longitudeCorrente = longitudeCorrente;
	}
	public String getDescricaoStatus() {
		String descricaoStatus = "";
		if (status != null) {
			descricaoStatus = StatusVeiculoEnum.valueOfStatus(status).getDescricao();
		}
		return descricaoStatus;
	}
	public String getMotivoDesautorizacao() {
		return motivoDesautorizacao;
	}
	public void setMotivoDesautorizacao(String motivoDesautorizacao) {
		this.motivoDesautorizacao = motivoDesautorizacao;
	}
	public String getMotivoBloqueio() {
		return motivoBloqueio;
	}
	public void setMotivoBloqueio(String motivoBloqueio) {
		this.motivoBloqueio = motivoBloqueio;
	}
	public String getImagemCrlv() {
		return imagemCrlv;
	}
	public void setImagemCrlv(String imagemCrlv) {
		this.imagemCrlv = imagemCrlv;
	}
	public String getImagemCiv() {
		return imagemCiv;
	}
	public void setImagemCiv(String imagemCiv) {
		this.imagemCiv = imagemCiv;
	}
	public Double getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(Double classificacao) {
		this.classificacao = classificacao;
	}
	public Integer getQtdClassificacao() {
		return qtdClassificacao;
	}
	public void setQtdClassificacao(Integer qtdClassificacao) {
		this.qtdClassificacao = qtdClassificacao;
	}
	public Integer getIndicadorCadeirinha() {
		return indicadorCadeirinha;
	}

	public void setIndicadorCadeirinha(Integer indicadorCadeirinha) {
		this.indicadorCadeirinha = indicadorCadeirinha;
	}
	
	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	public Integer getIndicadorRastreadorSeg() {
		return 1;
	}

	public void setIndicadorRastreadorSeg(Integer indicadorRastreadorSeg) {
		this.indicadorRastreadorSeg = 1;
	}

	public String getMsgErroRastreador() {
		return null;
	}

	public void setMsgErroRastreador(String msgErroRastreador) {
		this.msgErroRastreador = null;
	}
	
	public Integer getIndicadorLogado() {
		return indicadorLogado;
	}

	public void setIndicadorLogado(Integer indicadorLogado) {
		this.indicadorLogado = indicadorLogado;
	}

	public String getNomeMotoristaLogado() {
		return nomeMotoristaLogado;
	}

	public void setNomeMotoristaLogado(String nomeMotoristaLogado) {
		this.nomeMotoristaLogado = nomeMotoristaLogado;
	}
	
	public Integer getIndicadorPossuiEmpresaAssociada() {
		return indicadorPossuiEmpresaAssociada;
	}

	public void setIndicadorPossuiEmpresaAssociada(Integer indicadorPossuiEmpresaAssociada) {
		this.indicadorPossuiEmpresaAssociada = indicadorPossuiEmpresaAssociada;
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
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}