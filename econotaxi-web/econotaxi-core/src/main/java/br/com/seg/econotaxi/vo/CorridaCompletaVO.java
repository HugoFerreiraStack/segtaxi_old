/**
 * 
 */
package br.com.seg.econotaxi.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;

/**
 * @author bruno
 *
 */
public class CorridaCompletaVO implements Serializable {

	// Constantes
	private static final long serialVersionUID = 1977581947751587304L;

    private Long id;
	private String origem;
	private String origemEndereco;
	private String destino;
	private String destinoEndereco;
	private String origemLatitude;
	private String origemLongitude;
	private String destinoLatitude;
	private String destinoLongitude;
	private Date dataSolicitacao;
	private Date dataFinalizacao;
	private Long previsaoCorrida;
	private Long tempoCorrida;
	private Double classificacaoCorrida;
	private String classificacaoDescricao;
	private String latitudeCorrente;
	private String longitudeCorrente;
	private Integer indicadorCorridaInicio;
	private Integer indicadorCorridaFim;
	private Integer tipo;
	private Integer tipoEntrega;
	private Double larguraItem;
	private Double alturaItem;
	private Double profundidadeItem;
	private Double pesoItem;
	private Integer tipoDenuncia;
	private String denuncia;
	private Integer status;
	private Integer tipoVeiculo;
	private Date dataInicio;
	private Integer indicadorPanico;
	private BigDecimal valorFinal;
	private Integer indicadorBicicleta;
	private Integer indicadorAdaptado;
	private String sexoMotorista;
	
	private Integer indicadorPossuiPromocao;
	private BigDecimal porcentagemPromocao;
	private String motivoPromocao;
	private Long idPromocao;
	
	private Integer indicadorPacote;
	private BigDecimal valorCorridaPacote;
	
	private String descricaoPacote;
	private String tituloPacote;
	private String duracaoHorasPacote;
	
	private String distanciaKm;
	
	private BigDecimal valorCreditoUtilizado;
	
	private Integer formaPagamento;
	
	private Long segundosParados;
	
	private BigDecimal distanciaPercorrida;
	
	private Double classificacaoVeiculo;
	private String classificacaoDescricaoVeiculo;
	
	private String complemento;
	
	private Integer indicadorTeleTaxi;
	
	private Integer tipoTeleTaxi;
	
	private String nomePassageiro;
	
	private Integer indicadorCadeirinha;
	
	private String latitudesPercurso;
	private String longitudesPercurso;
	private Long idUsuarioMotorista;
	private String celularPassageiro;
	private String vlMinimoCorridaPadrao;
	private String vlMaximoCorridaPadrao;
	private BigDecimal descontoCorrida;
	private Integer indicadorVoucherPapel;
	private String pontosApoio;
	private String voucher;
	private Long idEmpresaConveniada;
	private String observacoes;
	private Date dataRecuperacao;
	private Long idMotoristaRecuperacao;
	private String motivoCancelamento;
	private String motivoRecuperacao;
	private String observacaoTele;
	private Integer indicadorPossuiMsgChat;
	
	private Motorista motorista;
	
	private Veiculo veiculo;
	
	private Cidade cidade;
	
	private Usuario usuario;
	
	private List<CarrosVO> carrosProximos;
	
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
	public BigDecimal getValorCreditoUtilizado() {
		return valorCreditoUtilizado;
	}
	public void setValorCreditoUtilizado(BigDecimal valorCreditoUtilizado) {
		this.valorCreditoUtilizado = valorCreditoUtilizado;
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
	
	public Cidade getCidade() {
		return cidade;
	}
	
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Integer getIndicadorVoucherPapel() {
		return indicadorVoucherPapel;
	}
	public void setIndicadorVoucherPapel(Integer indicadorVoucherPapel) {
		this.indicadorVoucherPapel = indicadorVoucherPapel;
	}
	public String getPontosApoio() {
		return pontosApoio;
	}
	public void setPontosApoio(String pontosApoio) {
		this.pontosApoio = pontosApoio;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public Long getIdEmpresaConveniada() {
		return idEmpresaConveniada;
	}
	public void setIdEmpresaConveniada(Long idEmpresaConveniada) {
		this.idEmpresaConveniada = idEmpresaConveniada;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
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
	
	public String getTempoCorridaFormatada() {
		
		String tempoCorridaFormatada = "";
		if (tempoCorrida != null) {
			long diferencaSegundos = (getDataFinalizacao().getTime() - getDataInicio().getTime());
		    float minDiferenca = Float.valueOf(diferencaSegundos) / (1000*60);
			tempoCorridaFormatada = minDiferenca  + " minutos";
		}
		return tempoCorridaFormatada;
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
		CorridaCompletaVO other = (CorridaCompletaVO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}