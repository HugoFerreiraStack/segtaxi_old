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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
@Entity
@Table(name = "corrida")
public class Corrida implements Serializable {

	// Constantes
	private static final long serialVersionUID = -2809670066416572469L;
	//public static final String URL = "http://localhost:8282/";
	public static final String URL = "https://app.segtaxi.com.br/";
	
	public Corrida() {}

	public Corrida(Corrida corrida, Usuario usuarioMotorista) {
		super();
		this.id = corrida.getId();
		this.origem = corrida.getOrigem();
		this.destino = corrida.getDestino();
		this.origemLatitude = corrida.getOrigemLatitude();
		this.origemLongitude = corrida.getOrigemLongitude();
		this.destinoLatitude = corrida.getDestinoLatitude();
		this.destinoLongitude = corrida.getDestinoLongitude();
		this.dataSolicitacao = corrida.getDataSolicitacao();
		this.dataFinalizacao = corrida.getDataFinalizacao();
		this.previsaoCorrida = corrida.getPrevisaoCorrida();
		this.tempoCorrida = corrida.getTempoCorrida();
		this.classificacaoCorrida = corrida.getClassificacaoCorrida();
		this.classificacaoDescricao = corrida.getClassificacaoDescricao();
		this.latitudeCorrente = corrida.getLatitudeCorrente();
		this.longitudeCorrente = corrida.getLongitudeCorrente();
		this.indicadorCorridaInicio = corrida.getIndicadorCorridaInicio();
		this.indicadorCorridaFim = corrida.getIndicadorCorridaFim();
		this.tipo = corrida.getTipo();
		this.tipoEntrega = corrida.getTipoEntrega();
		this.larguraItem = corrida.getLarguraItem();
		this.alturaItem = corrida.getAlturaItem();
		this.profundidadeItem = corrida.getProfundidadeItem();
		this.pesoItem = corrida.getPesoItem();
		this.tipoDenuncia = corrida.getTipoDenuncia();
		this.denuncia = corrida.getDenuncia();
		this.status = corrida.getStatus();
		this.tipoVeiculo = corrida.getTipoVeiculo();
		this.dataInicio = corrida.getDataInicio();
		this.indicadorPanico = corrida.getIndicadorPanico();
		this.valorFinal = corrida.getValorFinal();
		this.indicadorBicicleta = corrida.getIndicadorBicicleta();
		this.indicadorAdaptado = corrida.getIndicadorAdaptado();
		this.sexoMotorista = corrida.getSexoMotorista();
		this.motorista = corrida.getMotorista();
		this.usuario = corrida.getUsuario();
		this.cidade = corrida.getCidade();
		this.veiculo = corrida.getVeiculo();
		this.latitudesPercurso = corrida.getLatitudesPercurso();
		this.longitudesPercurso = corrida.getLongitudesPercurso();
		this.idUsuarioMotorista = corrida.getIdUsuarioMotorista();
		this.usuarioMotorista = usuarioMotorista;
		this.origemEndereco = corrida.getOrigemEndereco();
		this.destinoEndereco = corrida.getDestinoEndereco();
		this.indicadorPossuiPromocao = corrida.getIndicadorPossuiPromocao();
		this.porcentagemPromocao = corrida.getPorcentagemPromocao();
		this.motivoPromocao = corrida.getMotivoPromocao();
		this.idPromocao = corrida.getIdPromocao();
		this.distanciaKm = corrida.getDistanciaKm();
		this.indicadorPacote = corrida.getIndicadorPacote();
		this.valorCorridaPacote = corrida.getValorCorridaPacote();
		this.descricaoPacote = corrida.getDescricaoPacote();
		this.tituloPacote = corrida.getTituloPacote();
		this.duracaoHorasPacote = corrida.getDuracaoHorasPacote();
		this.formaPagamento = corrida.getFormaPagamento();
		this.classificacaoVeiculo = corrida.getClassificacaoVeiculo();
		this.classificacaoDescricaoVeiculo = corrida.getClassificacaoDescricaoVeiculo();
		this.complemento = corrida.getComplemento();
		this.indicadorTeleTaxi = corrida.getIndicadorTeleTaxi();
		this.tipoTeleTaxi = corrida.getTipoTeleTaxi();
		this.nomePassageiro = corrida.getNomePassageiro();
		this.indicadorCadeirinha = corrida.getIndicadorCadeirinha();
		this.segundosParados = corrida.getSegundosParados();
		this.distanciaPercorrida = corrida.getDistanciaPercorrida();
		this.voucher = corrida.getVoucher();
		this.idEmpresaConveniada = corrida.getIdEmpresaConveniada();
		this.celularPassageiro = corrida.getCelularPassageiro();
		this.pontosApoio = corrida.getPontosApoio();
		this.dataRecuperacao = corrida.getDataRecuperacao();
		this.motivoCancelamento = corrida.getMotivoCancelamento();
		this.motivoRecuperacao = corrida.getMotivoRecuperacao();
		this.idMotoristaRecuperacao = corrida.getIdMotoristaRecuperacao();
		this.observacaoTele = corrida.getObservacaoTele();
		this.indicadorPossuiMsgChat = corrida.getIndicadorPossuiMsgChat();
		this.motoristaLigue = corrida.getMotoristaLigue();
		this.veiculoLigue = corrida.getVeiculoLigue();
		this.empresaConveniada = corrida.getEmpresaConveniada();
		
		this.destino2 = corrida.getDestino2();
		this.destinoEndereco2 = corrida.getDestinoEndereco2();
		this.destinoLatitude2 = corrida.getDestinoLatitude2();
		this.destinoLongitude2 = corrida.getDestinoLongitude2();
		this.distanciaKm2 = corrida.getDistanciaKm2();
		this.previsaoCorrida2 = corrida.getPrevisaoCorrida2();
		
		this.destino3 = corrida.getDestino3();
		this.destinoEndereco3 = corrida.getDestinoEndereco3();
		this.destinoLatitude3 = corrida.getDestinoLatitude3();
		this.destinoLongitude3 = corrida.getDestinoLongitude3();
		this.distanciaKm3 = corrida.getDistanciaKm3();
		this.previsaoCorrida3 = corrida.getPrevisaoCorrida3();
		
		this.destino4 = corrida.getDestino4();
		this.destinoEndereco4 = corrida.getDestinoEndereco4();
		this.destinoLatitude4 = corrida.getDestinoLatitude4();
		this.destinoLongitude4 = corrida.getDestinoLongitude4();
		this.distanciaKm4 = corrida.getDistanciaKm4();
		this.previsaoCorrida4 = corrida.getPrevisaoCorrida4();
		
		this.dataFinalizacao1 = corrida.getDataFinalizacao1();
		this.dataFinalizacao2 = corrida.getDataFinalizacao2();
		this.dataFinalizacao3 = corrida.getDataFinalizacao3();
		this.dataFinalizacao4 = corrida.getDataFinalizacao4();
		
		this.dataAgendamento = corrida.getDataAgendamento();
		this.horaAgendamento = corrida.getHoraAgendamento();
		this.historicoCancelamentos = corrida.getHistoricoCancelamentos();
		
		this.idCentroCusto = corrida.getIdCentroCusto();
		this.centroCusto = corrida.getCentroCusto();
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name = "origem")
	private String origem;
	@Column(name = "origem_endereco")
	private String origemEndereco;
	@Column(name = "destino")
	private String destino;
	@Column(name = "destino_endereco")
	private String destinoEndereco;
	@Column(name = "origem_latitude")
	private String origemLatitude;
	@Column(name = "origem_longitude")
	private String origemLongitude;
	@Column(name = "destino_latitude")
	private String destinoLatitude;
	@Column(name = "destino_longitude")
	private String destinoLongitude;
	@Column(name = "data_solicitacao")
	private Date dataSolicitacao;
	@Column(name = "data_finalizacao")
	private Date dataFinalizacao;
	@Column(name = "data_recuperacao")
	private Date dataRecuperacao;
	@Column(name = "id_motorista_recuperacao")
	private Long idMotoristaRecuperacao;
	@Column(name = "motivo_cancelamento")
	private String motivoCancelamento;
	@Column(name = "motivo_recuperacao")
	private String motivoRecuperacao;
	@Column(name = "previsao_corrida")
	private Long previsaoCorrida;
	@Column(name = "tempo_corrida")
	private Long tempoCorrida;
	@Column(name = "classificacao_corrida")
	private Double classificacaoCorrida;
	@Column(name = "classificacao_descricao")
	private String classificacaoDescricao;
	@Column(name = "latitude_corrente")
	private String latitudeCorrente;
	@Column(name = "longitude_corrente")
	private String longitudeCorrente;
	@Column(name = "ind_corrida_inicio")
	private Integer indicadorCorridaInicio;
	@Column(name = "ind_corrida_fim")
	private Integer indicadorCorridaFim;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "tipo_entrega")
	private Integer tipoEntrega;
	@Column(name = "largura_item")
	private Double larguraItem;
	@Column(name = "altura_item")
	private Double alturaItem;
	@Column(name = "profundidade_item")
	private Double profundidadeItem;
	@Column(name = "peso_item")
	private Double pesoItem;
	@Column(name = "tipo_denuncia")
	private Integer tipoDenuncia;
	@Column(name = "denuncia")
	private String denuncia;
	@Column(name = "status")
	private Integer status;
	@Column(name = "tipo_veiculo")
	private Integer tipoVeiculo;
	@Column(name = "data_inicio")
	private Date dataInicio;
	@Column(name = "ind_panico")
	private Integer indicadorPanico;
	@Column(name = "vl_final")
	private BigDecimal valorFinal;
	@Column(name = "ind_bicicleta")
	private Integer indicadorBicicleta;
	@Column(name = "ind_adaptado")
	private Integer indicadorAdaptado;
	@Column(name = "sexo_motorista")
	private String sexoMotorista;
	
	@Column(name = "ind_possui_promocao")
	private Integer indicadorPossuiPromocao;
	@Column(name = "porcentagem_promocao")
	private BigDecimal porcentagemPromocao;
	@Column(name = "motivo_promocao")
	private String motivoPromocao;
	@Column(name = "id_promocao")
	private Long idPromocao;
	
	@Column(name = "ind_pacote")
	private Integer indicadorPacote;
	@Column(name = "vl_corrida_pacote")
	private BigDecimal valorCorridaPacote;
	
	@Column(name = "descricao_pacote")
	private String descricaoPacote;
	@Column(name = "titulo_pacote")
	private String tituloPacote;
	@Column(name = "duracao_horas_pacote")
	private String duracaoHorasPacote;
	
	@Column(name = "distancia_km")
	private String distanciaKm;
	
	@Column(name = "vl_credito_utilizado")
	private BigDecimal valorCreditoUtilizado;
	
	@Column(name = "forma_pagamento")
	private Integer formaPagamento;
	
	@Column(name = "segundos_parados")
	private Long segundosParados;
	
	@Column(name = "distancia_percorrida")
	private BigDecimal distanciaPercorrida;
	
	@Column(name = "classificacao_veiculo")
	private Double classificacaoVeiculo;
	@Column(name = "classificacao_descricao_veiculo")
	private String classificacaoDescricaoVeiculo;
	
	@Column(name = "complemento")
	private String complemento;
	
	@Column(name = "ind_tele_taxi")
	private Integer indicadorTeleTaxi;
	
	@Column(name = "tipo_tele_taxi")
	private Integer tipoTeleTaxi;
	
	@Column(name = "nome_passageiro")
	private String nomePassageiro;
	
	@Column(name = "ind_cadeirinha")
	private Integer indicadorCadeirinha;
	
	@Column(name = "voucher")
	private String voucher;
	
	@Column(name = "id_empresa_conveniada")
	private Long idEmpresaConveniada;
	
	@Column(name = "id_voucher")
	private Long idVoucher;
	
	@Column(name = "celular_passageiro")
	private String celularPassageiro;
	
	@Column(name = "pontos_apoio")
	private String pontosApoio;
	
	@Column(name = "id_responsavel_cancelamento")
	private Long idResponsavelCancelamento;
	
	@Column(name = "observacao_tele")
	private String observacaoTele;
	
	@Column(name = "ind_possui_msg_chat")
	private Integer indicadorPossuiMsgChat;
	
	@Column(name = "hora_parada_1")
	private BigDecimal horaParada1;
	@Column(name = "hora_parada_2")
	private BigDecimal horaParada2;
	@Column(name = "hora_parada_3")
	private BigDecimal horaParada3;
	@Column(name = "hora_parada_4")
	private BigDecimal horaParada4;
	
	@Transient
	private String origem2;
	@Transient
	private String origemEndereco2;
	@Transient
	private String origemLatitude2;
	@Transient
	private String origemLongitude2;
	@Column(name = "destino2")
	private String destino2;
	@Column(name = "destino_endereco2")
	private String destinoEndereco2;
	@Column(name = "destino_latitude2")
	private String destinoLatitude2;
	@Column(name = "destino_longitude2")
	private String destinoLongitude2;
	@Column(name = "distancia_km2")
	private String distanciaKm2;
	@Column(name = "previsao_corrida2")
	private Long previsaoCorrida2;
	
	@Transient
	private String origem3;
	@Transient
	private String origemEndereco3;
	@Transient
	private String origemLatitude3;
	@Transient
	private String origemLongitude3;
	@Column(name = "destino3")
	private String destino3;
	@Column(name = "destino_endereco3")
	private String destinoEndereco3;
	@Column(name = "destino_latitude3")
	private String destinoLatitude3;
	@Column(name = "destino_longitude3")
	private String destinoLongitude3;
	@Column(name = "distancia_km3")
	private String distanciaKm3;
	@Column(name = "previsao_corrida3")
	private Long previsaoCorrida3;
	
	@Column(name = "data_finalizacao1")
	private Date dataFinalizacao1;
	@Column(name = "data_finalizacao2")
	private Date dataFinalizacao2;
	@Column(name = "data_finalizacao3")
	private Date dataFinalizacao3;
	@Column(name = "data_finalizacao4")
	private Date dataFinalizacao4;
	
	@Transient
	private String origem4;
	@Transient
	private String origemEndereco4;
	@Transient
	private String origemLatitude4;
	@Transient
	private String origemLongitude4;
	@Column(name = "destino4")
	private String destino4;
	@Column(name = "destino_endereco4")
	private String destinoEndereco4;
	@Column(name = "destino_latitude4")
	private String destinoLatitude4;
	@Column(name = "destino_longitude4")
	private String destinoLongitude4;
	@Column(name = "distancia_km4")
	private String distanciaKm4;
	@Column(name = "previsao_corrida4")
	private Long previsaoCorrida4;
	
	@Column(name = "data_agendamento")
	private String dataAgendamento;
	@Column(name = "hora_agendamento")
	private String horaAgendamento;
	@Column(name = "historico_cancelamentos")
	private String historicoCancelamentos;
	
	@Column(name = "id_centro_custo")
	private Long idCentroCusto;
	
	@Column(name = "ind_sem_desconto")
	private Integer indicadorSemDesconto;
	
	@ManyToOne
	@JoinColumn(name = "id_motorista", referencedColumnName = "id")
	private Motorista motorista;
	@ManyToOne
	@JoinColumn(name = "id_motorista_ligue", referencedColumnName = "id")
	private MotoristaLigue motoristaLigue;
	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private Usuario usuario;
	@ManyToOne
	@JoinColumn(name = "id_cidade", referencedColumnName = "id")
	private Cidade cidade;
	@ManyToOne
	@JoinColumn(name = "id_veiculo", referencedColumnName = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Veiculo veiculo;
	@ManyToOne
	@JoinColumn(name = "id_veiculo_ligue", referencedColumnName = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private VeiculoLigue veiculoLigue;
	@ManyToOne
	@JoinColumn(name = "id_pagamento", referencedColumnName = "id")
	private Pagamento pagamento;
	@ManyToOne
	@JoinColumn(name = "id_desconto_lojista", referencedColumnName = "id")
	private DescontoLojista descontoLojista;
	@ManyToOne
	@JoinColumn(name = "id_empresa_conveniada", referencedColumnName = "id", updatable = false, insertable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private EmpresaConveniada empresaConveniada;
	@ManyToOne
	@JoinColumn(name = "id_centro_custo", referencedColumnName = "id", updatable = false, insertable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private CentroCusto centroCusto;
	
	@Transient
	private String latitudesPercurso;
	@Transient
	private String longitudesPercurso;
	@Transient
	private Long idUsuarioMotorista;
	@Transient
	private Usuario usuarioMotorista;
	@Transient
	private String vlMinimoCorridaPadrao;
	@Transient
	private String vlMedioCorridaPadrao;
	@Transient
	private String vlMaximoCorridaPadrao;
	@Transient
	private BigDecimal descontoCorrida;
	@Transient
	private Localidade pontoApoio1;
	@Transient
	private Localidade pontoApoio2;
	@Transient
	private Localidade pontoApoio3;
	@Transient
	private Localidade pontoApoio4;
	@Transient
	private Integer cheguei;
	@Transient
	private String email;
	@Transient
	private String mesReferencia;
	@Transient
	private boolean corridaVoucher;
	@Transient
	private Integer indicadorVoucherPapel;
	@Transient
	private String unidadeMotorista;
	@Transient
	private String placaVeiculo;
	@Transient
	private String observacoes;
	@Transient
	private List<CarrosVO> carrosProximos;
	@Transient
	private List<CarrosVO> carrosSelecionados;
	@Transient
	private List<CarrosVO> carrosSelecionadosLivres;
	@Transient
	private List<CarrosVO> carrosRecusados;
	@Transient
	private Date dataInicioPeriodo;
	@Transient
	private Date dataFimPeriodo;
	@Transient
	private Date dataAgendamentoFinal;
	@Transient
	private Integer qtdTrechos;
	@Transient
	private Integer indicadorCarrosSelecionados;
	
	/* Métodos Get/Set */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getOrigemLatitude() {
		return origemLatitude;
	}
	public void setOrigemLatitude(String origemLatitude) {
		this.origemLatitude = origemLatitude;
	}
	public String getOrigemLongitude() {
		return origemLongitude;
	}
	public void setOrigemLongitude(String origemLongitude) {
		this.origemLongitude = origemLongitude;
	}
	public String getDestinoLatitude() {
		return destinoLatitude;
	}
	public void setDestinoLatitude(String destinoLatitude) {
		this.destinoLatitude = destinoLatitude;
	}
	public String getDestinoLongitude() {
		return destinoLongitude;
	}
	public void setDestinoLongitude(String destinoLongitude) {
		this.destinoLongitude = destinoLongitude;
	}
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	public Long getPrevisaoCorrida() {
		if (previsaoCorrida != null && (indicadorTeleTaxi == null || indicadorTeleTaxi != 1)) {
			previsaoCorrida = previsaoCorrida / 60;
		} else if (previsaoCorrida != null && previsaoCorrida > 100 &&  (tipoTeleTaxi != null && tipoTeleTaxi == 99)) {
			previsaoCorrida = previsaoCorrida / 60;
		}
		return previsaoCorrida;
	}
	public void setPrevisaoCorrida(Long previsaoCorrida) {
		this.previsaoCorrida = previsaoCorrida;
	}
	public Long getTempoCorrida() {
		if (tempoCorrida != null && (indicadorTeleTaxi == null || indicadorTeleTaxi != 1)) {
			tempoCorrida = tempoCorrida / 60;
		} else if (tempoCorrida != null && tempoCorrida > 100 &&  (tipoTeleTaxi != null && tipoTeleTaxi == 99)) {
			tempoCorrida = tempoCorrida / 60;
		}
		return tempoCorrida;
	}
	public void setTempoCorrida(Long tempoCorrida) {
		this.tempoCorrida = tempoCorrida;
	}
	public Double getClassificacaoCorrida() {
		return classificacaoCorrida;
	}
	public void setClassificacaoCorrida(Double classificacaoCorrida) {
		this.classificacaoCorrida = classificacaoCorrida;
	}
	public String getClassificacaoDescricao() {
		return classificacaoDescricao;
	}
	public void setClassificacaoDescricao(String classificacaoDescricao) {
		this.classificacaoDescricao = classificacaoDescricao;
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
	public Integer getIndicadorCorridaInicio() {
		return indicadorCorridaInicio;
	}
	public void setIndicadorCorridaInicio(Integer indicadorCorridaInicio) {
		this.indicadorCorridaInicio = indicadorCorridaInicio;
	}
	public Integer getIndicadorCorridaFim() {
		return indicadorCorridaFim;
	}
	public void setIndicadorCorridaFim(Integer indicadorCorridaFim) {
		this.indicadorCorridaFim = indicadorCorridaFim;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Integer getTipoEntrega() {
		return tipoEntrega;
	}
	public void setTipoEntrega(Integer tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}
	public Double getLarguraItem() {
		return larguraItem;
	}
	public void setLarguraItem(Double larguraItem) {
		this.larguraItem = larguraItem;
	}
	public Double getAlturaItem() {
		return alturaItem;
	}
	public void setAlturaItem(Double alturaItem) {
		this.alturaItem = alturaItem;
	}
	public Double getProfundidadeItem() {
		return profundidadeItem;
	}
	public void setProfundidadeItem(Double profundidadeItem) {
		this.profundidadeItem = profundidadeItem;
	}
	public Double getPesoItem() {
		return pesoItem;
	}
	public void setPesoItem(Double pesoItem) {
		this.pesoItem = pesoItem;
	}
	public Motorista getMotorista() {
		return motorista;
	}
	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTipoDenuncia() {
		return tipoDenuncia;
	}
	public void setTipoDenuncia(Integer tipoDenuncia) {
		this.tipoDenuncia = tipoDenuncia;
	}
	public String getDenuncia() {
		return denuncia;
	}
	public void setDenuncia(String denuncia) {
		this.denuncia = denuncia;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public String getDescricaoStatus() {
		
		String descricaoStatus = "";
		if (status != null) {
			descricaoStatus = StatusCorridaEnum.valueOfStatus(status).getDescricao();
		}
		return descricaoStatus;
	}
	public String getLatitudesPercurso() {
		return latitudesPercurso;
	}
	public void setLatitudesPercurso(String latitudesPercurso) {
		this.latitudesPercurso = latitudesPercurso;
	}
	public String getLongitudesPercurso() {
		return longitudesPercurso;
	}
	public void setLongitudesPercurso(String longitudesPercurso) {
		this.longitudesPercurso = longitudesPercurso;
	}
	public Integer getTipoVeiculo() {
		return tipoVeiculo;
	}
	public void setTipoVeiculo(Integer tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public Integer getIndicadorPanico() {
		return indicadorPanico;
	}
	public void setIndicadorPanico(Integer indicadorPanico) {
		this.indicadorPanico = indicadorPanico;
	}
	public BigDecimal getValorFinal() {
		return valorFinal;
	}
	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}
	public Integer getIndicadorBicicleta() {
		return indicadorBicicleta;
	}
	public void setIndicadorBicicleta(Integer indicadorBicicleta) {
		this.indicadorBicicleta = indicadorBicicleta;
	}
	public Integer getIndicadorAdaptado() {
		return indicadorAdaptado;
	}
	public void setIndicadorAdaptado(Integer indicadorAdaptado) {
		this.indicadorAdaptado = indicadorAdaptado;
	}
	public String getSexoMotorista() {
		return sexoMotorista;
	}
	public void setSexoMotorista(String sexoMotorista) {
		this.sexoMotorista = sexoMotorista;
	}
	public Long getIdUsuarioMotorista() {
		return idUsuarioMotorista;
	}
	public void setIdUsuarioMotorista(Long idUsuarioMotorista) {
		this.idUsuarioMotorista = idUsuarioMotorista;
	}
	public Usuario getUsuarioMotorista() {
		return usuarioMotorista;
	}
	public void setUsuarioMotorista(Usuario usuarioMotorista) {
		this.usuarioMotorista = usuarioMotorista;
	}
	public String getOrigemEndereco() {
		return origemEndereco;
	}
	public void setOrigemEndereco(String origemEndereco) {
		this.origemEndereco = origemEndereco;
	}
	public String getDestinoEndereco() {
		return destinoEndereco;
	}
	public void setDestinoEndereco(String destinoEndereco) {
		this.destinoEndereco = destinoEndereco;
	}
	public Integer getIndicadorPossuiPromocao() {
		return indicadorPossuiPromocao;
	}
	public void setIndicadorPossuiPromocao(Integer indicadorPossuiPromocao) {
		this.indicadorPossuiPromocao = indicadorPossuiPromocao;
	}
	public BigDecimal getPorcentagemPromocao() {
		return porcentagemPromocao;
	}
	public void setPorcentagemPromocao(BigDecimal porcentagemPromocao) {
		this.porcentagemPromocao = porcentagemPromocao;
	}
	public String getMotivoPromocao() {
		return motivoPromocao;
	}
	public void setMotivoPromocao(String motivoPromocao) {
		this.motivoPromocao = motivoPromocao;
	}
	public Long getIdPromocao() {
		return idPromocao;
	}
	public void setIdPromocao(Long idPromocao) {
		this.idPromocao = idPromocao;
	}
	public Integer getIndicadorPacote() {
		return indicadorPacote;
	}
	public void setIndicadorPacote(Integer indicadorPacote) {
		this.indicadorPacote = indicadorPacote;
	}
	public BigDecimal getValorCorridaPacote() {
		return valorCorridaPacote;
	}
	public void setValorCorridaPacote(BigDecimal valorCorridaPacote) {
		this.valorCorridaPacote = valorCorridaPacote;
	}
	public String getDistanciaKm() {
		return distanciaKm;
	}
	public void setDistanciaKm(String distanciaKm) {
		this.distanciaKm = distanciaKm;
	}
	public String getDescricaoPacote() {
		return descricaoPacote;
	}
	public void setDescricaoPacote(String descricaoPacote) {
		this.descricaoPacote = descricaoPacote;
	}
	public String getTituloPacote() {
		return tituloPacote;
	}
	public void setTituloPacote(String tituloPacote) {
		this.tituloPacote = tituloPacote;
	}
	public String getDuracaoHorasPacote() {
		return duracaoHorasPacote;
	}
	public void setDuracaoHorasPacote(String duracaoHorasPacote) {
		this.duracaoHorasPacote = duracaoHorasPacote;
	}
	public Pagamento getPagamento() {
		return pagamento;
	}
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	public BigDecimal getValorCreditoUtilizado() {
		return valorCreditoUtilizado;
	}
	public void setValorCreditoUtilizado(BigDecimal valorCreditoUtilizado) {
		this.valorCreditoUtilizado = valorCreditoUtilizado;
	}
	public DescontoLojista getDescontoLojista() {
		return descontoLojista;
	}
	public void setDescontoLojista(DescontoLojista descontoLojista) {
		this.descontoLojista = descontoLojista;
	}
	public Integer getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(Integer formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public Long getSegundosParados() {
		return segundosParados;
	}

	public void setSegundosParados(Long segundosParados) {
		this.segundosParados = segundosParados;
	}

	public BigDecimal getDistanciaPercorrida() {
		return distanciaPercorrida;
	}

	public void setDistanciaPercorrida(BigDecimal distanciaPercorrida) {
		this.distanciaPercorrida = distanciaPercorrida;
	}
	
	public Double getClassificacaoVeiculo() {
		return classificacaoVeiculo;
	}

	public void setClassificacaoVeiculo(Double classificacaoVeiculo) {
		this.classificacaoVeiculo = classificacaoVeiculo;
	}

	public String getClassificacaoDescricaoVeiculo() {
		return classificacaoDescricaoVeiculo;
	}

	public void setClassificacaoDescricaoVeiculo(String classificacaoDescricaoVeiculo) {
		this.classificacaoDescricaoVeiculo = classificacaoDescricaoVeiculo;
	}
	
	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public Integer getIndicadorTeleTaxi() {
		return indicadorTeleTaxi;
	}

	public void setIndicadorTeleTaxi(Integer indicadorTeleTaxi) {
		this.indicadorTeleTaxi = indicadorTeleTaxi;
	}
	
	public Integer getTipoTeleTaxi() {
		return tipoTeleTaxi;
	}

	public void setTipoTeleTaxi(Integer tipoTeleTaxi) {
		this.tipoTeleTaxi = tipoTeleTaxi;
	}
	
	public String getNomePassageiro() {
		return nomePassageiro;
	}

	public void setNomePassageiro(String nomePassageiro) {
		this.nomePassageiro = nomePassageiro;
	}
	
	public Integer getIndicadorCadeirinha() {
		return indicadorCadeirinha;
	}

	public void setIndicadorCadeirinha(Integer indicadorCadeirinha) {
		this.indicadorCadeirinha = indicadorCadeirinha;
	}

	public String getCelularPassageiro() {
		return celularPassageiro;
	}

	public void setCelularPassageiro(String celularPassageiro) {
		this.celularPassageiro = celularPassageiro;
	}
	public String getVlMinimoCorridaPadrao() {
		return vlMinimoCorridaPadrao;
	}
	public void setVlMinimoCorridaPadrao(String vlMinimoCorridaPadrao) {
		this.vlMinimoCorridaPadrao = vlMinimoCorridaPadrao;
	}

	public String getVlMaximoCorridaPadrao() {
		return vlMaximoCorridaPadrao;
	}

	public void setVlMaximoCorridaPadrao(String vlMaximoCorridaPadrao) {
		this.vlMaximoCorridaPadrao = vlMaximoCorridaPadrao;
	}
	
	public BigDecimal getDescontoCorrida() {
		return descontoCorrida;
	}

	public void setDescontoCorrida(BigDecimal descontoCorrida) {
		this.descontoCorrida = descontoCorrida;
	}

	public Localidade getPontoApoio1() {
		return pontoApoio1;
	}

	public void setPontoApoio1(Localidade pontoApoio1) {
		this.pontoApoio1 = pontoApoio1;
	}

	public Localidade getPontoApoio2() {
		return pontoApoio2;
	}

	public void setPontoApoio2(Localidade pontoApoio2) {
		this.pontoApoio2 = pontoApoio2;
	}

	public Localidade getPontoApoio3() {
		return pontoApoio3;
	}

	public void setPontoApoio3(Localidade pontoApoio3) {
		this.pontoApoio3 = pontoApoio3;
	}
	
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public Integer getCheguei() {
		return cheguei;
	}

	public void setCheguei(Integer cheguei) {
		this.cheguei = cheguei;
	}
	
	public Long getIdEmpresaConveniada() {
		return idEmpresaConveniada;
	}

	public void setIdEmpresaConveniada(Long idEmpresaConveniada) {
		this.idEmpresaConveniada = idEmpresaConveniada;
	}
	
	public Long getIdVoucher() {
		return idVoucher;
	}

	public void setIdVoucher(Long idVoucher) {
		this.idVoucher = idVoucher;
	}

	public EmpresaConveniada getEmpresaConveniada() {
		return empresaConveniada;
	}

	public void setEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		this.empresaConveniada = empresaConveniada;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public String getPrevisaoCorridaFormatada() {
		
		String previsaoCorridaFormatada = "";
		if (previsaoCorrida != null) {
			Double minutos = (Double.valueOf(previsaoCorrida) / 60);
			Double resultado = (minutos + (1 - 0.60));
			previsaoCorridaFormatada = resultado.toString().replace(".", ":")  + " minutos";
		}
		return previsaoCorridaFormatada;
	}

	public String getTempoCorridaFormatada() {
		
		String tempoCorridaFormatada = "";
		if (tempoCorrida != null) {
			long diferencaSegundos = (getDataFinalizacao().getTime() - getDataInicio().getTime());
		    float minDiferenca = Float.valueOf(diferencaSegundos) / (1000*60);
			tempoCorridaFormatada = minDiferenca  + " minutos";
		}
		return tempoCorridaFormatada;
	}
	
	public String getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(String mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public boolean isCorridaVoucher() {
		return corridaVoucher;
	}

	public void setCorridaVoucher(boolean corridaVoucher) {
		this.corridaVoucher = corridaVoucher;
	}
	
	public String getPontosApoio() {
		return pontosApoio;
	}

	public void setPontosApoio(String pontosApoio) {
		this.pontosApoio = pontosApoio;
	}
	
	public Localidade getPontoApoio4() {
		return pontoApoio4;
	}

	public void setPontoApoio4(Localidade pontoApoio4) {
		this.pontoApoio4 = pontoApoio4;
	}
	
	public Integer getIndicadorVoucherPapel() {
		return indicadorVoucherPapel;
	}

	public void setIndicadorVoucherPapel(Integer indicadorVoucherPapel) {
		this.indicadorVoucherPapel = indicadorVoucherPapel;
	}

	public String getUnidadeMotorista() {
		return unidadeMotorista;
	}

	public void setUnidadeMotorista(String unidadeMotorista) {
		this.unidadeMotorista = unidadeMotorista;
	}
	
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public Long getIdResponsavelCancelamento() {
		return idResponsavelCancelamento;
	}

	public void setIdResponsavelCancelamento(Long idResponsavelCancelamento) {
		this.idResponsavelCancelamento = idResponsavelCancelamento;
	}
	
	public Date getDataRecuperacao() {
		return dataRecuperacao;
	}

	public void setDataRecuperacao(Date dataRecuperacao) {
		this.dataRecuperacao = dataRecuperacao;
	}

	public Long getIdMotoristaRecuperacao() {
		return idMotoristaRecuperacao;
	}

	public void setIdMotoristaRecuperacao(Long idMotoristaRecuperacao) {
		this.idMotoristaRecuperacao = idMotoristaRecuperacao;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public String getMotivoRecuperacao() {
		return motivoRecuperacao;
	}

	public void setMotivoRecuperacao(String motivoRecuperacao) {
		this.motivoRecuperacao = motivoRecuperacao;
	}
	
	public String getObservacaoTele() {
		return observacaoTele;
	}

	public void setObservacaoTele(String observacaoTele) {
		this.observacaoTele = observacaoTele;
	}
	
	public Integer getIndicadorPossuiMsgChat() {
		return indicadorPossuiMsgChat;
	}

	public void setIndicadorPossuiMsgChat(Integer indicadorPossuiMsgChat) {
		this.indicadorPossuiMsgChat = indicadorPossuiMsgChat;
	}

	public List<CarrosVO> getCarrosProximos() {
		return carrosProximos;
	}

	public void setCarrosProximos(List<CarrosVO> carrosProximos) {
		this.carrosProximos = carrosProximos;
	}
	
	public List<CarrosVO> getCarrosRecusados() {
		return carrosRecusados;
	}

	public void setCarrosRecusados(List<CarrosVO> carrosRecusados) {
		this.carrosRecusados = carrosRecusados;
	}
	
	public MotoristaLigue getMotoristaLigue() {
		return motoristaLigue;
	}

	public void setMotoristaLigue(MotoristaLigue motoristaLigue) {
		this.motoristaLigue = motoristaLigue;
	}
	
	public VeiculoLigue getVeiculoLigue() {
		return veiculoLigue;
	}

	public void setVeiculoLigue(VeiculoLigue veiculoLigue) {
		this.veiculoLigue = veiculoLigue;
	}
	
	public Date getDataInicioPeriodo() {
		return dataInicioPeriodo;
	}

	public void setDataInicioPeriodo(Date dataInicioPeriodo) {
		this.dataInicioPeriodo = dataInicioPeriodo;
	}

	public Date getDataFimPeriodo() {
		return dataFimPeriodo;
	}

	public void setDataFimPeriodo(Date dataFimPeriodo) {
		this.dataFimPeriodo = dataFimPeriodo;
	}
	
	public BigDecimal getHoraParada1() {
		return horaParada1;
	}

	public void setHoraParada1(BigDecimal horaParada1) {
		this.horaParada1 = horaParada1;
	}

	public BigDecimal getHoraParada2() {
		return horaParada2;
	}

	public void setHoraParada2(BigDecimal horaParada2) {
		this.horaParada2 = horaParada2;
	}

	public BigDecimal getHoraParada3() {
		return horaParada3;
	}

	public void setHoraParada3(BigDecimal horaParada3) {
		this.horaParada3 = horaParada3;
	}

	public BigDecimal getHoraParada4() {
		return horaParada4;
	}

	public void setHoraParada4(BigDecimal horaParada4) {
		this.horaParada4 = horaParada4;
	}

	public String getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(String dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public String getHoraAgendamento() {
		return horaAgendamento;
	}

	public void setHoraAgendamento(String horaAgendamento) {
		this.horaAgendamento = horaAgendamento;
	}

	public String getHistoricoCancelamentos() {
		return historicoCancelamentos;
	}

	public void setHistoricoCancelamentos(String historicoCancelamentos) {
		this.historicoCancelamentos = historicoCancelamentos;
	}
	
	public String getVlMedioCorridaPadrao() {
		return vlMedioCorridaPadrao;
	}

	public void setVlMedioCorridaPadrao(String vlMedioCorridaPadrao) {
		this.vlMedioCorridaPadrao = vlMedioCorridaPadrao;
	}
	
	public Date getDataAgendamentoFinal() {
		return dataAgendamentoFinal;
	}

	public void setDataAgendamentoFinal(Date dataAgendamentoFinal) {
		this.dataAgendamentoFinal = dataAgendamentoFinal;
	}
	
	public Integer getQtdTrechos() {
		return qtdTrechos;
	}

	public void setQtdTrechos(Integer qtdTrechos) {
		this.qtdTrechos = qtdTrechos;
	}
	
	public String getDestino2() {
		return destino2;
	}

	public void setDestino2(String destino2) {
		this.destino2 = destino2;
	}

	public String getDestinoEndereco2() {
		return destinoEndereco2;
	}

	public void setDestinoEndereco2(String destinoEndereco2) {
		this.destinoEndereco2 = destinoEndereco2;
	}

	public String getDestinoLatitude2() {
		return destinoLatitude2;
	}

	public void setDestinoLatitude2(String destinoLatitude2) {
		this.destinoLatitude2 = destinoLatitude2;
	}

	public String getDestinoLongitude2() {
		return destinoLongitude2;
	}

	public void setDestinoLongitude2(String destinoLongitude2) {
		this.destinoLongitude2 = destinoLongitude2;
	}

	public String getDestino3() {
		return destino3;
	}

	public void setDestino3(String destino3) {
		this.destino3 = destino3;
	}

	public String getDestinoEndereco3() {
		return destinoEndereco3;
	}

	public void setDestinoEndereco3(String destinoEndereco3) {
		this.destinoEndereco3 = destinoEndereco3;
	}

	public String getDestinoLatitude3() {
		return destinoLatitude3;
	}

	public void setDestinoLatitude3(String destinoLatitude3) {
		this.destinoLatitude3 = destinoLatitude3;
	}

	public String getDestinoLongitude3() {
		return destinoLongitude3;
	}

	public void setDestinoLongitude3(String destinoLongitude3) {
		this.destinoLongitude3 = destinoLongitude3;
	}

	public String getDestino4() {
		return destino4;
	}

	public void setDestino4(String destino4) {
		this.destino4 = destino4;
	}

	public String getDestinoEndereco4() {
		return destinoEndereco4;
	}

	public void setDestinoEndereco4(String destinoEndereco4) {
		this.destinoEndereco4 = destinoEndereco4;
	}

	public String getDestinoLatitude4() {
		return destinoLatitude4;
	}

	public void setDestinoLatitude4(String destinoLatitude4) {
		this.destinoLatitude4 = destinoLatitude4;
	}

	public String getDestinoLongitude4() {
		return destinoLongitude4;
	}

	public void setDestinoLongitude4(String destinoLongitude4) {
		this.destinoLongitude4 = destinoLongitude4;
	}
	
	public String getOrigem2() {
		return origem2;
	}

	public void setOrigem2(String origem2) {
		this.origem2 = origem2;
	}

	public String getOrigemEndereco2() {
		return origemEndereco2;
	}

	public void setOrigemEndereco2(String origemEndereco2) {
		this.origemEndereco2 = origemEndereco2;
	}

	public String getOrigemLatitude2() {
		return origemLatitude2;
	}

	public void setOrigemLatitude2(String origemLatitude2) {
		this.origemLatitude2 = origemLatitude2;
	}

	public String getOrigemLongitude2() {
		return origemLongitude2;
	}

	public void setOrigemLongitude2(String origemLongitude2) {
		this.origemLongitude2 = origemLongitude2;
	}

	public String getDistanciaKm2() {
		return distanciaKm2;
	}

	public void setDistanciaKm2(String distanciaKm2) {
		this.distanciaKm2 = distanciaKm2;
	}

	public Long getPrevisaoCorrida2() {
		return previsaoCorrida2;
	}

	public void setPrevisaoCorrida2(Long previsaoCorrida2) {
		this.previsaoCorrida2 = previsaoCorrida2;
	}

	public String getOrigem3() {
		return origem3;
	}

	public void setOrigem3(String origem3) {
		this.origem3 = origem3;
	}

	public String getOrigemEndereco3() {
		return origemEndereco3;
	}

	public void setOrigemEndereco3(String origemEndereco3) {
		this.origemEndereco3 = origemEndereco3;
	}

	public String getOrigemLatitude3() {
		return origemLatitude3;
	}

	public void setOrigemLatitude3(String origemLatitude3) {
		this.origemLatitude3 = origemLatitude3;
	}

	public String getOrigemLongitude3() {
		return origemLongitude3;
	}

	public void setOrigemLongitude3(String origemLongitude3) {
		this.origemLongitude3 = origemLongitude3;
	}

	public String getDistanciaKm3() {
		return distanciaKm3;
	}

	public void setDistanciaKm3(String distanciaKm3) {
		this.distanciaKm3 = distanciaKm3;
	}

	public Long getPrevisaoCorrida3() {
		return previsaoCorrida3;
	}

	public void setPrevisaoCorrida3(Long previsaoCorrida3) {
		this.previsaoCorrida3 = previsaoCorrida3;
	}

	public String getOrigem4() {
		return origem4;
	}

	public void setOrigem4(String origem4) {
		this.origem4 = origem4;
	}

	public String getOrigemEndereco4() {
		return origemEndereco4;
	}

	public void setOrigemEndereco4(String origemEndereco4) {
		this.origemEndereco4 = origemEndereco4;
	}

	public String getOrigemLatitude4() {
		return origemLatitude4;
	}

	public void setOrigemLatitude4(String origemLatitude4) {
		this.origemLatitude4 = origemLatitude4;
	}

	public String getOrigemLongitude4() {
		return origemLongitude4;
	}

	public void setOrigemLongitude4(String origemLongitude4) {
		this.origemLongitude4 = origemLongitude4;
	}

	public String getDistanciaKm4() {
		return distanciaKm4;
	}

	public void setDistanciaKm4(String distanciaKm4) {
		this.distanciaKm4 = distanciaKm4;
	}

	public Long getPrevisaoCorrida4() {
		return previsaoCorrida4;
	}

	public void setPrevisaoCorrida4(Long previsaoCorrida4) {
		this.previsaoCorrida4 = previsaoCorrida4;
	}
	
	public Date getDataFinalizacao1() {
		return dataFinalizacao1;
	}

	public void setDataFinalizacao1(Date dataFinalizacao1) {
		this.dataFinalizacao1 = dataFinalizacao1;
	}

	public Date getDataFinalizacao2() {
		return dataFinalizacao2;
	}

	public void setDataFinalizacao2(Date dataFinalizacao2) {
		this.dataFinalizacao2 = dataFinalizacao2;
	}

	public Date getDataFinalizacao3() {
		return dataFinalizacao3;
	}

	public void setDataFinalizacao3(Date dataFinalizacao3) {
		this.dataFinalizacao3 = dataFinalizacao3;
	}

	public Date getDataFinalizacao4() {
		return dataFinalizacao4;
	}

	public void setDataFinalizacao4(Date dataFinalizacao4) {
		this.dataFinalizacao4 = dataFinalizacao4;
	}
	
	public Integer getIndicadorCarrosSelecionados() {
		return indicadorCarrosSelecionados;
	}

	public void setIndicadorCarrosSelecionados(Integer indicadorCarrosSelecionados) {
		this.indicadorCarrosSelecionados = indicadorCarrosSelecionados;
	}
	
	public List<CarrosVO> getCarrosSelecionados() {
		return carrosSelecionados;
	}

	public void setCarrosSelecionados(List<CarrosVO> carrosSelecionados) {
		this.carrosSelecionados = carrosSelecionados;
	}
	
	public List<CarrosVO> getCarrosSelecionadosLivres() {
		return carrosSelecionadosLivres;
	}

	public void setCarrosSelecionadosLivres(List<CarrosVO> carrosSelecionadosLivres) {
		this.carrosSelecionadosLivres = carrosSelecionadosLivres;
	}
	
	public Long getIdCentroCusto() {
		return idCentroCusto;
	}

	public void setIdCentroCusto(Long idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}
	
	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}
	
	public Integer getIndicadorSemDesconto() {
		return indicadorSemDesconto;
	}

	public void setIndicadorSemDesconto(Integer indicadorSemDesconto) {
		this.indicadorSemDesconto = indicadorSemDesconto;
	}

	public Long getPrevisaoCorridaTotal() {
		Long previsao = getPrevisaoCorrida();
		if (previsao != null) {
			if (getPrevisaoCorrida2() != null) {
				previsao = previsao + getPrevisaoCorrida2();
			}
			
			if (getPrevisaoCorrida3() != null) {
				previsao = previsao + getPrevisaoCorrida3();
			}
			
			if (getPrevisaoCorrida4() != null) {
				previsao = previsao + getPrevisaoCorrida4();
			}
		}
		return previsao;
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
		Corrida other = (Corrida) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}