/**
 * 
 */
package br.com.seg.econotaxi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {

	// Constantes
	private static final long serialVersionUID = -6580049063996934098L;
	
	public Cidade() {
		super();
	}
	
	public Cidade(Long id, String nome, String siglaUf, String siglaPais, String latitude, String longitude,
			String raioKm, BigDecimal valorMinimoCorrida, BigDecimal valorHoraParada, BigDecimal valorBandeirada,
			BigDecimal valorKmBandeira1, BigDecimal valorKmBandeira2, BigDecimal valorAdicionalExecutivo,
			BigDecimal valorPorcentagemAdicionalExecutivo, BigDecimal valorAdicionalSuporteBike,
			BigDecimal valorPorcentagemAdicionalSuporteBike, BigDecimal valorMinimoEntrega,
			String horarioInicioBandeira2, String horarioFimBandeira2,
			BigDecimal valorKmEntrega,
			Integer kgMaximoEntrega, Integer indicadorPossuiRastreador, Integer indicadorAceitaPagamentoDinheiro, 
			Integer indicadorAceitaPagamentoDebito, BigDecimal porcentagemDescontoLojista, BigDecimal valorAdicionalCadeirinha,
			BigDecimal valorPorcentagemAdicionalCadeirinha, BigDecimal porcentagemDescontoDinheiro, 
			BigDecimal porcentagemDescontoAniversariante, BigDecimal porcentagemDesconto, String dataInicio, String horaInicio,
			String dataFim, String horaFim, BigDecimal distancia, Integer kmForaCidade, BigDecimal valorKmForaCidade, 
			Integer indicadorAceitaPagamentoCredito, Integer indicadorPossuiTaximetro,
			Integer indicadorPossuiCarroParticular, Integer indicadorPossuiTaxi, Integer indicadorPossuiMotoTaxi, Integer indicadorPossuiEntregaMoto,
			Integer indicadorPossuiEntregaCarro, Integer indicadorPossuiEntregaCaminhao, Integer indicadorPossuiGuincho, Integer indicadorPossuiTeleTaxi,
			BigDecimal valorHoraParadaParticular, BigDecimal valorBandeiradaParticular, BigDecimal valorKmBandeira1Particular, BigDecimal valorKmBandeira2Particular, 
			BigDecimal valorHoraParadaMotoTaxi, BigDecimal valorBandeiradaMotoTaxi, BigDecimal valorKmBandeira1MotoTaxi, BigDecimal valorKmBandeira2MotoTaxi,
			BigDecimal valorAdicionalExecutivoParticular, BigDecimal valorPorcentagemAdicionalExecutivoParticular, BigDecimal valorKmForaCidadeParticular, BigDecimal valorKmForaCidadeMotoTaxi,
			BigDecimal porcentagemDescontoDinheiroParticular, 
			BigDecimal porcentagemDescontoAniversarianteParticular, BigDecimal porcentagemDescontoParticular, String dataInicioParticular, String horaInicioParticular,
			String dataFimParticular, String horaFimParticular,
			BigDecimal porcentagemDescontoDinheiroMoto, 
			BigDecimal porcentagemDescontoAniversarianteMoto, BigDecimal porcentagemDescontoMoto, String dataInicioMoto, String horaInicioMoto,
			String dataFimMoto, String horaFimMoto, BigDecimal valorMinimoCorridaBandeira2) {
		super();
		this.id = id;
		this.nome = nome;
		this.siglaUf = siglaUf;
		this.siglaPais = siglaPais;
		this.latitude = latitude;
		this.longitude = longitude;
		this.raioKm = raioKm;
		this.valorMinimoCorrida = valorMinimoCorrida;
		this.valorHoraParada = valorHoraParada;
		this.valorBandeirada = valorBandeirada;
		this.valorKmBandeira1 = valorKmBandeira1;
		this.valorKmBandeira2 = valorKmBandeira2;
		this.valorAdicionalExecutivo = valorAdicionalExecutivo;
		this.valorPorcentagemAdicionalExecutivo = valorPorcentagemAdicionalExecutivo;
		this.valorAdicionalSuporteBike = valorAdicionalSuporteBike;
		this.valorPorcentagemAdicionalSuporteBike = valorPorcentagemAdicionalSuporteBike;
		this.valorMinimoEntrega = valorMinimoEntrega;
		this.horarioInicioBandeira2 = horarioInicioBandeira2;
		this.horarioFimBandeira2 = horarioFimBandeira2;
		this.distancia = distancia;
		this.valorKmEntrega = valorKmEntrega;
		this.kgMaximoEntrega = kgMaximoEntrega;
		this.indicadorPossuiRastreador = indicadorPossuiRastreador;
		this.indicadorAceitaPagamentoDinheiro = indicadorAceitaPagamentoDinheiro;
		this.indicadorAceitaPagamentoDebito = indicadorAceitaPagamentoDebito;
		this.porcentagemDescontoLojista = porcentagemDescontoLojista;
		this.valorAdicionalCadeirinha = valorAdicionalCadeirinha;
		this.valorPorcentagemAdicionalCadeirinha = valorPorcentagemAdicionalCadeirinha;
		this.porcentagemDescontoDinheiro = porcentagemDescontoDinheiro;
		this.porcentagemDescontoAniversariante = porcentagemDescontoAniversariante;
		this.porcentagemDesconto = porcentagemDesconto;
		this.dataInicio = dataInicio;
		this.horaInicio = horaInicio;
		this.dataFim = dataFim;
		this.horaFim = horaFim;
		this.kmForaCidade = kmForaCidade;
		this.valorKmForaCidade = valorKmForaCidade;
		this.indicadorAceitaPagamentoCredito = indicadorAceitaPagamentoCredito;
		this.indicadorPossuiTaximetro = indicadorPossuiTaximetro;
		
		this.indicadorPossuiCarroParticular = indicadorPossuiCarroParticular;
		this.indicadorPossuiTaxi = indicadorPossuiTaxi;
		this.indicadorPossuiMotoTaxi = indicadorPossuiMotoTaxi;
		this.indicadorPossuiEntregaMoto = indicadorPossuiEntregaMoto;
		this.indicadorPossuiEntregaCarro = indicadorPossuiEntregaCarro;
		this.indicadorPossuiEntregaCaminhao = indicadorPossuiEntregaCaminhao;
		this.indicadorPossuiGuincho = indicadorPossuiGuincho;
		this.indicadorPossuiTeleTaxi = indicadorPossuiTeleTaxi;
		
		this.valorHoraParadaParticular = valorHoraParadaParticular;
		this.valorBandeiradaParticular = valorBandeiradaParticular;
		this.valorKmBandeira1Particular = valorKmBandeira1Particular;
		this.valorKmBandeira2Particular = valorKmBandeira2Particular;
		
		this.valorHoraParadaMotoTaxi = valorHoraParadaMotoTaxi;
		this.valorBandeiradaMotoTaxi = valorBandeiradaMotoTaxi;
		this.valorKmBandeira1MotoTaxi = valorKmBandeira1MotoTaxi;
		this.valorKmBandeira2MotoTaxi = valorKmBandeira2MotoTaxi;
		
		this.valorAdicionalExecutivoParticular = valorAdicionalExecutivoParticular;
		this.valorPorcentagemAdicionalExecutivoParticular = valorPorcentagemAdicionalExecutivoParticular;
		this.valorKmForaCidadeParticular = valorKmForaCidadeParticular;
		this.valorKmForaCidadeMotoTaxi = valorKmForaCidadeMotoTaxi;
		
		this.porcentagemDescontoDinheiroParticular = porcentagemDescontoDinheiroParticular;
		this.porcentagemDescontoAniversarianteParticular = porcentagemDescontoAniversarianteParticular;
		this.porcentagemDescontoParticular = porcentagemDescontoParticular;
		this.dataInicioParticular = dataInicioParticular;
		this.horaInicioParticular = horaInicioParticular;
		this.dataFimParticular = dataFimParticular;
		this.horaFimParticular = horaFimParticular;
		
		this.porcentagemDescontoDinheiroMoto = porcentagemDescontoDinheiroMoto;
		this.porcentagemDescontoAniversarianteMoto = porcentagemDescontoAniversarianteMoto;
		this.porcentagemDescontoMoto = porcentagemDescontoMoto;
		this.dataInicioMoto = dataInicioMoto;
		this.horaInicioMoto = horaInicioMoto;
		this.dataFimMoto = dataFimMoto;
		this.horaFimMoto = horaFimMoto;
		this.valorMinimoCorridaBandeira2 = valorMinimoCorridaBandeira2;
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "sigla_uf")
	private String siglaUf;
	@Column(name = "sigla_pais")
	private String siglaPais;
	@Column(name = "descricao")
	private String descricao;
	@Column(name = "latitude")
	private String latitude;
	@Column(name = "longitude")
	private String longitude;
	@Column(name = "coordenadas")
	private String coordenadas;
	@Column(name = "raio_km")
	private String raioKm;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Column(name = "data_alteracao")
	private Date dataAlteracao;
	@Column(name = "vl_minimo_corrida")
	private BigDecimal valorMinimoCorrida;
	@Column(name = "vl_minimo_corrida_band_2")
	private BigDecimal valorMinimoCorridaBandeira2;
	@Column(name = "vl_hora_parada")
	private BigDecimal valorHoraParada;
	@Column(name = "vl_bandeirada")
	private BigDecimal valorBandeirada;
	@Column(name = "vl_km_bandeira_1")
	private BigDecimal valorKmBandeira1;
	@Column(name = "vl_km_bandeira_2")
	private BigDecimal valorKmBandeira2;
	@Column(name = "vl_adic_executivo")
	private BigDecimal valorAdicionalExecutivo;
	@Column(name = "vl_porcent_adic_executivo")
	private BigDecimal valorPorcentagemAdicionalExecutivo;
	@Column(name = "vl_adic_bike")
	private BigDecimal valorAdicionalSuporteBike;
	@Column(name = "vl_porcent_adic_bike")
	private BigDecimal valorPorcentagemAdicionalSuporteBike;
	@Column(name = "vl_minimo_entrega")
	private BigDecimal valorMinimoEntrega;
	@Column(name = "hr_inicio_bandeira_2")
	private String horarioInicioBandeira2;
	@Column(name = "hr_fim_bandeira_2")
	private String horarioFimBandeira2;
	@Column(name = "vl_km_entrega")
	private BigDecimal valorKmEntrega;
	@Column(name = "kg_maximo_entrega")
	private Integer kgMaximoEntrega;
	
	@Column(name = "ind_possui_rastreador")
	private Integer indicadorPossuiRastreador;
	@Column(name = "ind_aceita_pgto_dinheiro")
	private Integer indicadorAceitaPagamentoDinheiro;
	@Column(name = "ind_aceita_pgto_debito")
	private Integer indicadorAceitaPagamentoDebito;
	@Column(name = "vl_desconto_lojista")
	private BigDecimal porcentagemDescontoLojista;
	
	@Column(name = "vl_adic_cadeirinha")
	private BigDecimal valorAdicionalCadeirinha;
	@Column(name = "vl_porcent_adic_cadeirinha")
	private BigDecimal valorPorcentagemAdicionalCadeirinha;
	
	@Column(name = "vl_porcent_desc_dinheiro")
	private BigDecimal porcentagemDescontoDinheiro;
	
	@Column(name = "vl_porcent_desc_niver")
	private BigDecimal porcentagemDescontoAniversariante;
	
	@Column(name = "vl_porcent_desc_momento")
	private BigDecimal porcentagemDesconto;
	@Column(name = "dt_inicio_porc_desc_momento")
	private String dataInicio;
	@Column(name = "hr_inicio_porc_desc_momento")
	private String horaInicio;
	@Column(name = "dt_fim_porc_desc_momento")
	private String dataFim;
	@Column(name = "hr_fim_porc_desc_momento")
	private String horaFim;
	
	@Column(name = "km_fora_cidade")
	private Integer kmForaCidade;
	@Column(name = "vl_km_fora_cidade")
	private BigDecimal valorKmForaCidade;
	
	@Column(name = "vl_hora_parada_radio")
	private BigDecimal valorHoraParadaRadio;
	@Column(name = "vl_bandeirada_radio")
	private BigDecimal valorBandeiradaRadio;
	@Column(name = "vl_km_bandeira_1_radio")
	private BigDecimal valorKmBandeira1Radio;
	@Column(name = "vl_km_bandeira_2_radio")
	private BigDecimal valorKmBandeira2Radio;
	
	@Column(name = "ind_aceita_pgt_credito")
	private Integer indicadorAceitaPagamentoCredito;
	
	@Column(name = "ind_possui_taximetro")
	private Integer indicadorPossuiTaximetro;
	
	@Column(name = "ind_possui_carro_particular")
	private Integer indicadorPossuiCarroParticular;
	@Column(name = "ind_possui_taxi")
	private Integer indicadorPossuiTaxi;
	@Column(name = "ind_possui_mototaxi")
	private Integer indicadorPossuiMotoTaxi;
	@Column(name = "ind_possui_entrega_moto")
	private Integer indicadorPossuiEntregaMoto;
	@Column(name = "ind_possui_entrega_carro")
	private Integer indicadorPossuiEntregaCarro;
	@Column(name = "ind_possui_entrega_caminhao")
	private Integer indicadorPossuiEntregaCaminhao;
	@Column(name = "ind_possui_guincho")
	private Integer indicadorPossuiGuincho;
	@Column(name = "ind_possui_teletaxi")
	private Integer indicadorPossuiTeleTaxi;
	
	@Column(name = "vl_hora_parada_particular")
	private BigDecimal valorHoraParadaParticular;
	@Column(name = "vl_bandeirada_particular")
	private BigDecimal valorBandeiradaParticular;
	@Column(name = "vl_km_bandeira_1_particular")
	private BigDecimal valorKmBandeira1Particular;
	@Column(name = "vl_km_bandeira_2_particular")
	private BigDecimal valorKmBandeira2Particular;
	
	@Column(name = "vl_hora_parada_moto_taxi")
	private BigDecimal valorHoraParadaMotoTaxi;
	@Column(name = "vl_bandeirada_moto_taxi")
	private BigDecimal valorBandeiradaMotoTaxi;
	@Column(name = "vl_km_bandeira_1_moto_taxi")
	private BigDecimal valorKmBandeira1MotoTaxi;
	@Column(name = "vl_km_bandeira_2_moto_taxi")
	private BigDecimal valorKmBandeira2MotoTaxi;
	
	@Column(name = "vl_adicional_exec_particular")
	private BigDecimal valorAdicionalExecutivoParticular;
	@Column(name = "vl_adicional_exec_porc_particular")
	private BigDecimal valorPorcentagemAdicionalExecutivoParticular;
	@Column(name = "vl_km_fora_particular")
	private BigDecimal valorKmForaCidadeParticular;
	@Column(name = "vl_km_fora_moto_taxi")
	private BigDecimal valorKmForaCidadeMotoTaxi;
	
	@Column(name = "vl_porcent_desc_dinheiro_part")
	private BigDecimal porcentagemDescontoDinheiroParticular;
	
	@Column(name = "vl_porcent_desc_niver_part")
	private BigDecimal porcentagemDescontoAniversarianteParticular;
	
	@Column(name = "vl_porcent_desc_momento_part")
	private BigDecimal porcentagemDescontoParticular;
	@Column(name = "dt_inicio_porc_desc_momento_part")
	private String dataInicioParticular;
	@Column(name = "hr_inicio_porc_desc_momento_part")
	private String horaInicioParticular;
	@Column(name = "dt_fim_porc_desc_momento_part")
	private String dataFimParticular;
	@Column(name = "hr_fim_porc_desc_momento_part")
	private String horaFimParticular;
	
	@Column(name = "vl_porcent_desc_dinheiro_moto")
	private BigDecimal porcentagemDescontoDinheiroMoto;
	
	@Column(name = "vl_porcent_desc_niver_moto")
	private BigDecimal porcentagemDescontoAniversarianteMoto;
	
	@Column(name = "vl_porcent_desc_momento_moto")
	private BigDecimal porcentagemDescontoMoto;
	@Column(name = "dt_inicio_porc_desc_momento_moto")
	private String dataInicioMoto;
	@Column(name = "hr_inicio_porc_desc_momento_moto")
	private String horaInicioMoto;
	@Column(name = "dt_fim_porc_desc_momento_moto")
	private String dataFimMoto;
	@Column(name = "hr_fim_porc_desc_momento_moto")
	private String horaFimMoto;
	
	@Transient
	private BigDecimal distancia;
	@Transient
	private List<CarrosVO> carros;
	
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
	public String getSiglaUf() {
		return siglaUf;
	}
	public void setSiglaUf(String siglaUf) {
		this.siglaUf = siglaUf;
	}
	public String getSiglaPais() {
		return siglaPais;
	}
	public void setSiglaPais(String siglaPais) {
		this.siglaPais = siglaPais;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	public String getCoordenadas() {
		return coordenadas;
	}
	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}
	public String getRaioKm() {
		return raioKm;
	}
	public void setRaioKm(String raioKm) {
		this.raioKm = raioKm;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public Date getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public BigDecimal getValorMinimoCorrida() {
		return valorMinimoCorrida;
	}
	public void setValorMinimoCorrida(BigDecimal valorMinimoCorrida) {
		this.valorMinimoCorrida = valorMinimoCorrida;
	}
	public BigDecimal getValorHoraParada() {
		return valorHoraParada;
	}
	public void setValorHoraParada(BigDecimal valorHoraParada) {
		this.valorHoraParada = valorHoraParada;
	}
	public BigDecimal getValorBandeirada() {
		return valorBandeirada;
	}
	public void setValorBandeirada(BigDecimal valorBandeirada) {
		this.valorBandeirada = valorBandeirada;
	}
	public BigDecimal getValorKmBandeira1() {
		return valorKmBandeira1;
	}
	public void setValorKmBandeira1(BigDecimal valorKmBandeira1) {
		this.valorKmBandeira1 = valorKmBandeira1;
	}
	public BigDecimal getValorKmBandeira2() {
		return valorKmBandeira2;
	}
	public void setValorKmBandeira2(BigDecimal valorKmBandeira2) {
		this.valorKmBandeira2 = valorKmBandeira2;
	}
	public BigDecimal getValorAdicionalExecutivo() {
		return valorAdicionalExecutivo;
	}
	public void setValorAdicionalExecutivo(BigDecimal valorAdicionalExecutivo) {
		this.valorAdicionalExecutivo = valorAdicionalExecutivo;
	}
	public BigDecimal getValorPorcentagemAdicionalExecutivo() {
		return valorPorcentagemAdicionalExecutivo;
	}
	public void setValorPorcentagemAdicionalExecutivo(BigDecimal valorPorcentagemAdicionalExecutivo) {
		this.valorPorcentagemAdicionalExecutivo = valorPorcentagemAdicionalExecutivo;
	}
	public BigDecimal getValorAdicionalSuporteBike() {
		return valorAdicionalSuporteBike;
	}
	public void setValorAdicionalSuporteBike(BigDecimal valorAdicionalSuporteBike) {
		this.valorAdicionalSuporteBike = valorAdicionalSuporteBike;
	}
	public BigDecimal getValorPorcentagemAdicionalSuporteBike() {
		return valorPorcentagemAdicionalSuporteBike;
	}
	public void setValorPorcentagemAdicionalSuporteBike(BigDecimal valorPorcentagemAdicionalSuporteBike) {
		this.valorPorcentagemAdicionalSuporteBike = valorPorcentagemAdicionalSuporteBike;
	}
	public BigDecimal getValorMinimoEntrega() {
		return valorMinimoEntrega;
	}
	public void setValorMinimoEntrega(BigDecimal valorMinimoEntrega) {
		this.valorMinimoEntrega = valorMinimoEntrega;
	}
	public BigDecimal getDistancia() {
		return distancia;
	}
	public void setDistancia(BigDecimal distancia) {
		this.distancia = distancia;
	}
	public String getHorarioInicioBandeira2() {
		return horarioInicioBandeira2;
	}
	public void setHorarioInicioBandeira2(String horarioInicioBandeira2) {
		this.horarioInicioBandeira2 = horarioInicioBandeira2;
	}
	public String getHorarioFimBandeira2() {
		return horarioFimBandeira2;
	}
	public void setHorarioFimBandeira2(String horarioFimBandeira2) {
		this.horarioFimBandeira2 = horarioFimBandeira2;
	}
	public BigDecimal getValorKmEntrega() {
		return valorKmEntrega;
	}
	public void setValorKmEntrega(BigDecimal valorKmEntrega) {
		this.valorKmEntrega = valorKmEntrega;
	}
	public Integer getKgMaximoEntrega() {
		return kgMaximoEntrega;
	}
	public void setKgMaximoEntrega(Integer kgMaximoEntrega) {
		this.kgMaximoEntrega = kgMaximoEntrega;
	}
	public List<CarrosVO> getCarros() {
		return carros;
	}
	public void setCarros(List<CarrosVO> carros) {
		this.carros = carros;
	}
	public Integer getIndicadorPossuiRastreador() {
		return indicadorPossuiRastreador;
	}
	public void setIndicadorPossuiRastreador(Integer indicadorPossuiRastreador) {
		this.indicadorPossuiRastreador = indicadorPossuiRastreador;
	}
	public Integer getIndicadorAceitaPagamentoDinheiro() {
		return indicadorAceitaPagamentoDinheiro;
	}
	public void setIndicadorAceitaPagamentoDinheiro(Integer indicadorAceitaPagamentoDinheiro) {
		this.indicadorAceitaPagamentoDinheiro = indicadorAceitaPagamentoDinheiro;
	}
	public Integer getIndicadorAceitaPagamentoDebito() {
		return indicadorAceitaPagamentoDebito;
	}
	public void setIndicadorAceitaPagamentoDebito(Integer indicadorAceitaPagamentoDebito) {
		this.indicadorAceitaPagamentoDebito = indicadorAceitaPagamentoDebito;
	}
	public BigDecimal getPorcentagemDescontoLojista() {
		return porcentagemDescontoLojista;
	}
	public void setPorcentagemDescontoLojista(BigDecimal porcentagemDescontoLojista) {
		this.porcentagemDescontoLojista = porcentagemDescontoLojista;
	}
	public BigDecimal getValorAdicionalCadeirinha() {
		return valorAdicionalCadeirinha;
	}

	public void setValorAdicionalCadeirinha(BigDecimal valorAdicionalCadeirinha) {
		this.valorAdicionalCadeirinha = valorAdicionalCadeirinha;
	}

	public BigDecimal getValorPorcentagemAdicionalCadeirinha() {
		return valorPorcentagemAdicionalCadeirinha;
	}

	public void setValorPorcentagemAdicionalCadeirinha(BigDecimal valorPorcentagemAdicionalCadeirinha) {
		this.valorPorcentagemAdicionalCadeirinha = valorPorcentagemAdicionalCadeirinha;
	}
	
	public BigDecimal getPorcentagemDescontoDinheiro() {
		return porcentagemDescontoDinheiro;
	}

	public void setPorcentagemDescontoDinheiro(BigDecimal porcentagemDescontoDinheiro) {
		this.porcentagemDescontoDinheiro = porcentagemDescontoDinheiro;
	}

	public BigDecimal getPorcentagemDesconto() {
		return porcentagemDesconto;
	}

	public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
		this.porcentagemDesconto = porcentagemDesconto;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	
	public BigDecimal getPorcentagemDescontoAniversariante() {
		return porcentagemDescontoAniversariante;
	}

	public void setPorcentagemDescontoAniversariante(BigDecimal porcentagemDescontoAniversariante) {
		this.porcentagemDescontoAniversariante = porcentagemDescontoAniversariante;
	}
	
	public Integer getKmForaCidade() {
		return kmForaCidade;
	}

	public void setKmForaCidade(Integer kmForaCidade) {
		this.kmForaCidade = kmForaCidade;
	}

	public BigDecimal getValorKmForaCidade() {
		return valorKmForaCidade;
	}

	public void setValorKmForaCidade(BigDecimal valorKmForaCidade) {
		this.valorKmForaCidade = valorKmForaCidade;
	}
	
	public BigDecimal getValorHoraParadaRadio() {
		return valorHoraParadaRadio;
	}

	public void setValorHoraParadaRadio(BigDecimal valorHoraParadaRadio) {
		this.valorHoraParadaRadio = valorHoraParadaRadio;
	}

	public BigDecimal getValorBandeiradaRadio() {
		return valorBandeiradaRadio;
	}

	public void setValorBandeiradaRadio(BigDecimal valorBandeiradaRadio) {
		this.valorBandeiradaRadio = valorBandeiradaRadio;
	}

	public BigDecimal getValorKmBandeira1Radio() {
		return valorKmBandeira1Radio;
	}

	public void setValorKmBandeira1Radio(BigDecimal valorKmBandeira1Radio) {
		this.valorKmBandeira1Radio = valorKmBandeira1Radio;
	}

	public BigDecimal getValorKmBandeira2Radio() {
		return valorKmBandeira2Radio;
	}

	public void setValorKmBandeira2Radio(BigDecimal valorKmBandeira2Radio) {
		this.valorKmBandeira2Radio = valorKmBandeira2Radio;
	}

	public Integer getIndicadorAceitaPagamentoCredito() {
		return indicadorAceitaPagamentoCredito;
	}

	public void setIndicadorAceitaPagamentoCredito(Integer indicadorAceitaPagamentoCredito) {
		this.indicadorAceitaPagamentoCredito = indicadorAceitaPagamentoCredito;
	}
	
	public Integer getIndicadorPossuiTaximetro() {
		return indicadorPossuiTaximetro;
	}

	public void setIndicadorPossuiTaximetro(Integer indicadorPossuiTaximetro) {
		this.indicadorPossuiTaximetro = indicadorPossuiTaximetro;
	}
	
	public Integer getIndicadorPossuiCarroParticular() {
		return indicadorPossuiCarroParticular;
	}

	public void setIndicadorPossuiCarroParticular(Integer indicadorPossuiCarroParticular) {
		this.indicadorPossuiCarroParticular = indicadorPossuiCarroParticular;
	}

	public Integer getIndicadorPossuiTaxi() {
		return indicadorPossuiTaxi;
	}

	public void setIndicadorPossuiTaxi(Integer indicadorPossuiTaxi) {
		this.indicadorPossuiTaxi = indicadorPossuiTaxi;
	}

	public Integer getIndicadorPossuiMotoTaxi() {
		return indicadorPossuiMotoTaxi;
	}

	public void setIndicadorPossuiMotoTaxi(Integer indicadorPossuiMotoTaxi) {
		this.indicadorPossuiMotoTaxi = indicadorPossuiMotoTaxi;
	}

	public Integer getIndicadorPossuiEntregaMoto() {
		return indicadorPossuiEntregaMoto;
	}

	public void setIndicadorPossuiEntregaMoto(Integer indicadorPossuiEntregaMoto) {
		this.indicadorPossuiEntregaMoto = indicadorPossuiEntregaMoto;
	}

	public Integer getIndicadorPossuiEntregaCarro() {
		return indicadorPossuiEntregaCarro;
	}

	public void setIndicadorPossuiEntregaCarro(Integer indicadorPossuiEntregaCarro) {
		this.indicadorPossuiEntregaCarro = indicadorPossuiEntregaCarro;
	}

	public Integer getIndicadorPossuiEntregaCaminhao() {
		return indicadorPossuiEntregaCaminhao;
	}

	public void setIndicadorPossuiEntregaCaminhao(Integer indicadorPossuiEntregaCaminhao) {
		this.indicadorPossuiEntregaCaminhao = indicadorPossuiEntregaCaminhao;
	}

	public Integer getIndicadorPossuiGuincho() {
		return indicadorPossuiGuincho;
	}

	public void setIndicadorPossuiGuincho(Integer indicadorPossuiGuincho) {
		this.indicadorPossuiGuincho = indicadorPossuiGuincho;
	}

	public Integer getIndicadorPossuiTeleTaxi() {
		return indicadorPossuiTeleTaxi;
	}

	public void setIndicadorPossuiTeleTaxi(Integer indicadorPossuiTeleTaxi) {
		this.indicadorPossuiTeleTaxi = indicadorPossuiTeleTaxi;
	}
	
	public BigDecimal getValorHoraParadaParticular() {
		return valorHoraParadaParticular;
	}

	public void setValorHoraParadaParticular(BigDecimal valorHoraParadaParticular) {
		this.valorHoraParadaParticular = valorHoraParadaParticular;
	}

	public BigDecimal getValorBandeiradaParticular() {
		return valorBandeiradaParticular;
	}

	public void setValorBandeiradaParticular(BigDecimal valorBandeiradaParticular) {
		this.valorBandeiradaParticular = valorBandeiradaParticular;
	}

	public BigDecimal getValorKmBandeira1Particular() {
		return valorKmBandeira1Particular;
	}

	public void setValorKmBandeira1Particular(BigDecimal valorKmBandeira1Particular) {
		this.valorKmBandeira1Particular = valorKmBandeira1Particular;
	}

	public BigDecimal getValorKmBandeira2Particular() {
		return valorKmBandeira2Particular;
	}

	public void setValorKmBandeira2Particular(BigDecimal valorKmBandeira2Particular) {
		this.valorKmBandeira2Particular = valorKmBandeira2Particular;
	}

	public BigDecimal getValorHoraParadaMotoTaxi() {
		return valorHoraParadaMotoTaxi;
	}

	public void setValorHoraParadaMotoTaxi(BigDecimal valorHoraParadaMotoTaxi) {
		this.valorHoraParadaMotoTaxi = valorHoraParadaMotoTaxi;
	}

	public BigDecimal getValorBandeiradaMotoTaxi() {
		return valorBandeiradaMotoTaxi;
	}

	public void setValorBandeiradaMotoTaxi(BigDecimal valorBandeiradaMotoTaxi) {
		this.valorBandeiradaMotoTaxi = valorBandeiradaMotoTaxi;
	}

	public BigDecimal getValorKmBandeira1MotoTaxi() {
		return valorKmBandeira1MotoTaxi;
	}

	public void setValorKmBandeira1MotoTaxi(BigDecimal valorKmBandeira1MotoTaxi) {
		this.valorKmBandeira1MotoTaxi = valorKmBandeira1MotoTaxi;
	}

	public BigDecimal getValorKmBandeira2MotoTaxi() {
		return valorKmBandeira2MotoTaxi;
	}

	public void setValorKmBandeira2MotoTaxi(BigDecimal valorKmBandeira2MotoTaxi) {
		this.valorKmBandeira2MotoTaxi = valorKmBandeira2MotoTaxi;
	}

	public BigDecimal getValorAdicionalExecutivoParticular() {
		return valorAdicionalExecutivoParticular;
	}

	public void setValorAdicionalExecutivoParticular(BigDecimal valorAdicionalExecutivoParticular) {
		this.valorAdicionalExecutivoParticular = valorAdicionalExecutivoParticular;
	}

	public BigDecimal getValorPorcentagemAdicionalExecutivoParticular() {
		return valorPorcentagemAdicionalExecutivoParticular;
	}

	public void setValorPorcentagemAdicionalExecutivoParticular(BigDecimal valorPorcentagemAdicionalExecutivoParticular) {
		this.valorPorcentagemAdicionalExecutivoParticular = valorPorcentagemAdicionalExecutivoParticular;
	}

	public BigDecimal getValorKmForaCidadeParticular() {
		return valorKmForaCidadeParticular;
	}

	public void setValorKmForaCidadeParticular(BigDecimal valorKmForaCidadeParticular) {
		this.valorKmForaCidadeParticular = valorKmForaCidadeParticular;
	}

	public BigDecimal getValorKmForaCidadeMotoTaxi() {
		return valorKmForaCidadeMotoTaxi;
	}

	public void setValorKmForaCidadeMotoTaxi(BigDecimal valorKmForaCidadeMotoTaxi) {
		this.valorKmForaCidadeMotoTaxi = valorKmForaCidadeMotoTaxi;
	}
	
	public BigDecimal getPorcentagemDescontoDinheiroParticular() {
		return porcentagemDescontoDinheiroParticular;
	}

	public void setPorcentagemDescontoDinheiroParticular(BigDecimal porcentagemDescontoDinheiroParticular) {
		this.porcentagemDescontoDinheiroParticular = porcentagemDescontoDinheiroParticular;
	}

	public BigDecimal getPorcentagemDescontoAniversarianteParticular() {
		return porcentagemDescontoAniversarianteParticular;
	}

	public void setPorcentagemDescontoAniversarianteParticular(BigDecimal porcentagemDescontoAniversarianteParticular) {
		this.porcentagemDescontoAniversarianteParticular = porcentagemDescontoAniversarianteParticular;
	}

	public BigDecimal getPorcentagemDescontoParticular() {
		return porcentagemDescontoParticular;
	}

	public void setPorcentagemDescontoParticular(BigDecimal porcentagemDescontoParticular) {
		this.porcentagemDescontoParticular = porcentagemDescontoParticular;
	}

	public String getDataInicioParticular() {
		return dataInicioParticular;
	}

	public void setDataInicioParticular(String dataInicioParticular) {
		this.dataInicioParticular = dataInicioParticular;
	}

	public String getHoraInicioParticular() {
		return horaInicioParticular;
	}

	public void setHoraInicioParticular(String horaInicioParticular) {
		this.horaInicioParticular = horaInicioParticular;
	}

	public String getDataFimParticular() {
		return dataFimParticular;
	}

	public void setDataFimParticular(String dataFimParticular) {
		this.dataFimParticular = dataFimParticular;
	}

	public String getHoraFimParticular() {
		return horaFimParticular;
	}

	public void setHoraFimParticular(String horaFimParticular) {
		this.horaFimParticular = horaFimParticular;
	}

	public BigDecimal getPorcentagemDescontoDinheiroMoto() {
		return porcentagemDescontoDinheiroMoto;
	}

	public void setPorcentagemDescontoDinheiroMoto(BigDecimal porcentagemDescontoDinheiroMoto) {
		this.porcentagemDescontoDinheiroMoto = porcentagemDescontoDinheiroMoto;
	}

	public BigDecimal getPorcentagemDescontoAniversarianteMoto() {
		return porcentagemDescontoAniversarianteMoto;
	}

	public void setPorcentagemDescontoAniversarianteMoto(BigDecimal porcentagemDescontoAniversarianteMoto) {
		this.porcentagemDescontoAniversarianteMoto = porcentagemDescontoAniversarianteMoto;
	}

	public BigDecimal getPorcentagemDescontoMoto() {
		return porcentagemDescontoMoto;
	}

	public void setPorcentagemDescontoMoto(BigDecimal porcentagemDescontoMoto) {
		this.porcentagemDescontoMoto = porcentagemDescontoMoto;
	}

	public String getDataInicioMoto() {
		return dataInicioMoto;
	}

	public void setDataInicioMoto(String dataInicioMoto) {
		this.dataInicioMoto = dataInicioMoto;
	}

	public String getHoraInicioMoto() {
		return horaInicioMoto;
	}

	public void setHoraInicioMoto(String horaInicioMoto) {
		this.horaInicioMoto = horaInicioMoto;
	}

	public String getDataFimMoto() {
		return dataFimMoto;
	}

	public void setDataFimMoto(String dataFimMoto) {
		this.dataFimMoto = dataFimMoto;
	}

	public String getHoraFimMoto() {
		return horaFimMoto;
	}

	public void setHoraFimMoto(String horaFimMoto) {
		this.horaFimMoto = horaFimMoto;
	}
	
	public BigDecimal getValorMinimoCorridaBandeira2() {
		return valorMinimoCorridaBandeira2;
	}

	public void setValorMinimoCorridaBandeira2(BigDecimal valorMinimoCorridaBandeira2) {
		this.valorMinimoCorridaBandeira2 = valorMinimoCorridaBandeira2;
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}