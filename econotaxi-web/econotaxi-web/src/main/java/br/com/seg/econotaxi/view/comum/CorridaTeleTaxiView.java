package br.com.seg.econotaxi.view.comum;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Named;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import br.com.seg.econotaxi.enums.MenuEnum;
import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.enums.TipoVeiculoEnum;
import br.com.seg.econotaxi.model.CentroCusto;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.ClienteTeleTaxi;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;
import br.com.seg.econotaxi.model.Voucher;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.ClienteTeleTaxiService;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.LocalidadeService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.ParametrosService;
import br.com.seg.econotaxi.service.PercursoService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.view.BaseView;
import br.com.seg.econotaxi.view.paginator.CorridaTeleDataModel;
import br.com.seg.econotaxi.vo.MensagemCorridaVO;

@Named
@Scope("view")
@ManagedBean(name = "corridaTeleTaxiView")
public class CorridaTeleTaxiView extends BaseView {

    // Constantes
	private static final long serialVersionUID = 1584745532035822519L;
	private static char[] letras = "0123456789".toCharArray();
	private static Random rand = new Random();
	
	// Atributos
	@Autowired
	private LocalidadeService localidadeService;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private MotoristaService motoristaService;
	@Autowired
	private PercursoService percursoService;
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	@Autowired
	private ClienteTeleTaxiService clienteTeleTaxiService;
	@Autowired
	private ParametrosService parametrosService;
	private Corrida corrida;
	private Corrida corridaNova;
	private Corrida filtro;
	private CorridaTeleDataModel corridaDataModel;
	private List<Cidade> listaCidade;
	private List<Percurso> listaPercurso;
	private Usuario usuarioTele;
	private List<EmpresaConveniada> empresas;
	private List<CentroCusto> centros;
	private EmpresaConveniada empresaConveniada;
	private String imagemQrCode;
	private ClienteTeleTaxi cliente;
	private String cidadeMapa;
	private List<MensagemCorridaVO> mensagensChat;
	private String mensagemChat;
	private Integer qtdCorridasMes;
	private Integer qtdCorridas24Horas;
	private Integer qtdCorridasHoje;
	private Integer qtdCorridasManha;
	private Integer qtdCorridasTarde;
	private Integer qtdCorridasNoite;
	private Integer qtdCorridasMadrugada;
	private Integer qtdCorridasCanceladasHoje;
	private Integer qtdCorridasSolicitadas;
	private Integer qtdCorridasAndamento;
	private Integer tipoCorrida;
	private List<VisualizacaoCorrida> visualizacoes;
	private List<Localidade> localidades;
	private Localidade regiao;
	private String motivoCancelamento;
	
	/**
	 * Método responsável pela inicialização de informações na tela.
	 */
	@PostConstruct
	public void init() {
		
		setUsuarioTele(recuperarUsuarioSessao());
		setListaCidade(cidadeService.recuperarTodasCidades());
		setEmpresas(empresaConveniadaService.recuperarEmpresas());
		getFiltro().setMotorista(new Motorista());
		getFiltro().setUsuario(new Usuario());
		Integer status = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("status");
		if (status != null) {
			getFiltro().setStatus(status);
		}
		Long idMotorista = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idMotorista");
		if (idMotorista != null) {
			getFiltro().getMotorista().setId(idMotorista);
		}
		Long idPassageiro = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idPassageiro");
		if (idPassageiro != null) {
			getFiltro().getUsuario().setId(idPassageiro);
		}
		Integer indicadorPanico = (Integer) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("indicadorPanico");
		if (indicadorPanico != null) {
			getFiltro().setIndicadorPanico(indicadorPanico);
		}
		Long idCorrida = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idCorrida");
		if (idCorrida != null) {
			getFiltro().setId(idCorrida);
			carregarCorrida(corridaService.recuperarCorridaPorChave(idCorrida));
		}
		getFiltro().setTipo(TipoCorridaEnum.CORRIDA.getCodigo());
		getFiltro().setIndicadorTeleTaxi(1);
		if (getUsuarioTele() != null && getUsuarioTele().getTipoTeleTaxi() != null) {
			getFiltro().setTipoTeleTaxi(getUsuarioTele().getTipoTeleTaxi());
		}
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			EmpresaConveniada ec = empresaConveniadaService.consultarPorChave(getUsuarioTele().getIdEmpresaConveniada());
			setEmpresaConveniada(ec);
			setEmpresas(new ArrayList<EmpresaConveniada>());
			getEmpresas().add(ec);
			getFiltro().setEmpresaConveniada(ec);
			getFiltro().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
			carregarCentrosCusto();
			if (getUsuarioTele() != null && getUsuarioTele().getIdCentroCusto() != null) {
				getFiltro().setIdCentroCusto(getUsuarioTele().getIdCentroCusto());
				getCorrida().setIdCentroCusto(getUsuarioTele().getIdCentroCusto());
			}
		}
		getCorrida().setMotorista(new Motorista());
		getCorrida().setUsuarioMotorista(new Usuario());
		setCorridaDataModel(new CorridaTeleDataModel(corridaService, getFiltro()));
		carregarContadores();
	}

	public void carregarContadoresAutomaticamente() {
		
		carregarContadores();
	}
	
	private void carregarContadores() {
		
		setQtdCorridasMes(corridaService.recuperarQtdCorridaTelePorTipo(1));
		setQtdCorridas24Horas(corridaService.recuperarQtdCorridaTelePorTipo(2));
		setQtdCorridasHoje(corridaService.recuperarQtdCorridaTelePorTipo(3));
		setQtdCorridasManha(corridaService.recuperarQtdCorridaTelePorTipo(4));
		setQtdCorridasTarde(corridaService.recuperarQtdCorridaTelePorTipo(5));
		setQtdCorridasNoite(corridaService.recuperarQtdCorridaTelePorTipo(6));
		setQtdCorridasMadrugada(corridaService.recuperarQtdCorridaTelePorTipo(7));
		setQtdCorridasCanceladasHoje(corridaService.recuperarQtdCorridaCanceladaTelePorTipo(3));
		setQtdCorridasSolicitadas(corridaService.recuperarQtdCorridaTelePorStatus(StatusCorridaEnum.SOLICITADO.getStatus()));
		setQtdCorridasAndamento(corridaService.recuperarQtdCorridaTelePorStatus(StatusCorridaEnum.CORRENTE.getStatus()));
	}
	
	public void carregarNovaCorrida() {
		getCorridaNova().setDistanciaKm(null);
		getCorridaNova().setDistanciaKm2(null);
		getCorridaNova().setDistanciaKm3(null);
		getCorridaNova().setDistanciaKm4(null);
		
		getCorridaNova().setValorFinal(null);
		
		getCorridaNova().setOrigemEndereco(null);
		getCorridaNova().setOrigem(null);
		getCorridaNova().setOrigemEndereco2(null);
		getCorridaNova().setOrigem2(null);
		getCorridaNova().setOrigemEndereco3(null);
		getCorridaNova().setOrigem3(null);
		getCorridaNova().setOrigemEndereco4(null);
		getCorridaNova().setOrigem4(null);
		
		getCorridaNova().setDestinoEndereco(null);
		getCorridaNova().setDestino(null);
		getCorridaNova().setDestinoEndereco2(null);
		getCorridaNova().setDestino2(null);
		getCorridaNova().setDestinoEndereco3(null);
		getCorridaNova().setDestino3(null);
		getCorridaNova().setDestinoEndereco4(null);
		getCorridaNova().setDestino4(null);
		
		getCorridaNova().setOrigemLatitude(null);
		getCorridaNova().setOrigemLongitude(null);
		getCorridaNova().setOrigemLatitude2(null);
		getCorridaNova().setOrigemLongitude2(null);
		getCorridaNova().setOrigemLatitude3(null);
		getCorridaNova().setOrigemLongitude3(null);
		getCorridaNova().setOrigemLatitude4(null);
		getCorridaNova().setOrigemLongitude4(null);
		
		getCorridaNova().setDestinoLatitude(null);
		getCorridaNova().setDestinoLongitude(null);
		getCorridaNova().setDestinoLatitude2(null);
		getCorridaNova().setDestinoLongitude2(null);
		getCorridaNova().setDestinoLatitude3(null);
		getCorridaNova().setDestinoLongitude3(null);
		getCorridaNova().setDestinoLatitude4(null);
		getCorridaNova().setDestinoLongitude4(null);
		
		getCorridaNova().setIdCentroCusto(null);
		
		if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			EmpresaConveniada ec = empresaConveniadaService.consultarPorChave(getUsuarioTele().getIdEmpresaConveniada());
			setEmpresaConveniada(ec);
			setEmpresas(new ArrayList<EmpresaConveniada>());
			getEmpresas().add(ec);
			gerarVoucher();
			if (getUsuarioTele() != null && getUsuarioTele().getIdCentroCusto() != null) {
				getCorridaNova().setIdCentroCusto(getUsuarioTele().getIdCentroCusto());
			}
		} else {
			getFiltro().setEmpresaConveniada(null);
		}
	}
	
	public void carregarCorridaRefazer(Corrida corrida) {
		
		if (corrida.getCelularPassageiro() != null && !corrida.getCelularPassageiro().isEmpty()) {
			ClienteTeleTaxi c = clienteTeleTaxiService.recuperarClientePorCelular(
					corrida.getCelularPassageiro());
			if (c != null) {
				getCorridaNova().setEmail(c.getEmail());
			}
		}
		getCorridaNova().setNomePassageiro(corrida.getNomePassageiro());
		getCorridaNova().setCelularPassageiro(corrida.getCelularPassageiro());
		getCorridaNova().setComplemento(corrida.getComplemento());
		getCorridaNova().setCidade(corrida.getCidade());
		getCorridaNova().setVoucher(corrida.getVoucher());
		getCorridaNova().setUsuario(corrida.getUsuario());
		getCorridaNova().setTipoVeiculo(corrida.getTipoVeiculo());
		getCorridaNova().setTipo(corrida.getTipo());
		getCorridaNova().setTipoTeleTaxi(corrida.getTipoTeleTaxi());
		getCorridaNova().setTempoCorrida(corrida.getTempoCorrida());
		getCorridaNova().setPrevisaoCorrida(corrida.getPrevisaoCorrida());
		getCorridaNova().setOrigemLatitude(corrida.getOrigemLatitude());
		getCorridaNova().setOrigemLongitude(corrida.getOrigemLongitude());
		getCorridaNova().setOrigem(corrida.getOrigem());
		getCorridaNova().setDestinoLatitude(corrida.getDestinoLatitude());
		getCorridaNova().setDestinoLongitude(corrida.getDestinoLongitude());
		getCorridaNova().setDestino(corrida.getDestino());
		getCorridaNova().setDestinoEndereco(corrida.getDestinoEndereco());
		getCorridaNova().setOrigemEndereco(corrida.getOrigemEndereco());
		getCorridaNova().setIdEmpresaConveniada(corrida.getIdEmpresaConveniada());
		getCorridaNova().setDistanciaKm(corrida.getDistanciaKm());
		getCorridaNova().setObservacaoTele(corrida.getObservacaoTele());
		getCorridaNova().setValorFinal(corrida.getValorFinal());
		setCidadeMapa(corrida.getCidade().getNome());
		
		getCorridaNova().setDestinoEndereco2(null);
		getCorridaNova().setDestino2(null);
		getCorridaNova().setDestinoEndereco3(null);
		getCorridaNova().setDestino3(null);
		getCorridaNova().setDestinoEndereco4(null);
		getCorridaNova().setDestino4(null);
		
		getCorridaNova().setOrigemLatitude2(null);
		getCorridaNova().setOrigemLongitude2(null);
		getCorridaNova().setOrigemLatitude3(null);
		getCorridaNova().setOrigemLongitude3(null);
		getCorridaNova().setOrigemLatitude4(null);
		getCorridaNova().setOrigemLongitude4(null);
		
		getCorridaNova().setDestinoLatitude2(null);
		getCorridaNova().setDestinoLongitude2(null);
		getCorridaNova().setDestinoLatitude3(null);
		getCorridaNova().setDestinoLongitude3(null);
		getCorridaNova().setDestinoLatitude4(null);
		getCorridaNova().setDestinoLongitude4(null);
		
		getCorridaNova().setQtdTrechos(1);
		
		getCorridaNova().setIdCentroCusto(null);
		
		if (getCorridaNova().getOrigemLatitude() == null || getCorridaNova().getOrigemLatitude().isEmpty()
				|| getCorridaNova().getOrigemLongitude() == null || getCorridaNova().getOrigemLongitude().isEmpty()) {
			setTipoCorrida(2);
		} else {
			setTipoCorrida(1);
		}
		if (corrida.getIdEmpresaConveniada() != null) {
			setEmpresaConveniada(empresaConveniadaService.consultarPorChave(corrida.getIdEmpresaConveniada()));
			if (getUsuarioTele() != null && getUsuarioTele().getIdCentroCusto() != null) {
				getCorridaNova().setIdCentroCusto(getUsuarioTele().getIdCentroCusto());
			}
		}
		getCorridaNova().setQtdTrechos(null);
	}
	
	public void chamarModal() {
		if (getCorrida().getId() != null) {
			execJavaScript("exibirDetalhesCorrida()");
		}
	}
	
	public void definirNomeCidade() {
		
		if (getCorridaNova().getCidade() != null) {
			setCidadeMapa(getCorridaNova().getCidade().getNome());
			
			if (getTipoCorrida().equals(2)) {
				setLocalidades(localidadeService.recuperarTodasLocalidadesPorTipo(3, getCorridaNova().getCidade().getId()));
			}
		}
	}
	
	public void calcularNovaCorrida() {
		
		Boolean contemErro = Boolean.FALSE;
		if (corridaNova.getCidade() == null || corridaNova.getCidade().getId() == null) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Cidade' é obrigatório.");
		}
		if (corridaNova.getNomePassageiro() == null || corridaNova.getNomePassageiro().isEmpty()) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Nome do Passageiro' é obrigatório.");
		}
		if (corridaNova.getTipoTeleTaxi() == null) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Empresa' é obrigatório.");
		}
		
		if (corridaNova.getOrigemEndereco() == null || corridaNova.getOrigemEndereco().isEmpty()) {
			contemErro = Boolean.TRUE;
			addMsgErro("Não foi selecionado uma Origem Válida.");
		}
		
		if ((corridaNova.getDestinoEndereco() == null || corridaNova.getDestinoEndereco().isEmpty())
				&& getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null 
				&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
			contemErro = Boolean.TRUE;
			addMsgErro("Não foi selecionado um Destino Válido.");
		}
		
		if (corridaNova.getOrigem() == null || corridaNova.getOrigem().isEmpty()) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Origem' é obrigatório.");
		}
		
		if ((corridaNova.getDestino() == null || corridaNova.getDestino().isEmpty())
				&& getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null 
				&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Destino' é obrigatório.");
		}
		
		if ((corridaNova.getDistanciaKm() == null || corridaNova.getDistanciaKm().isEmpty())
				&& getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null 
				&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
			contemErro = Boolean.TRUE;
			addMsgErro("Não foi possível calcular a distância entre Origem e Destino.");
		}
		
		if (!contemErro) {
			Cidade cidade = corridaNova.getCidade();
			if (getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
				
				BigDecimal vlMaximoCorridaPadrao = new BigDecimal(0);
				BigDecimal distancia = new BigDecimal(corridaNova.getDistanciaKm().replace(",", "."));
				BigDecimal distancia2 = null;
				BigDecimal distancia3 = null;
				BigDecimal distancia4 = null;
				
				if (corridaNova.getDistanciaKm2() != null && !corridaNova.getDistanciaKm2().isEmpty()) {
					distancia2 = new BigDecimal(corridaNova.getDistanciaKm2().replace(",", "."));
				}
				
				if (corridaNova.getDistanciaKm3() != null && !corridaNova.getDistanciaKm3().isEmpty()) {
					distancia3 = new BigDecimal(corridaNova.getDistanciaKm3().replace(",", "."));
				}
				
				if (corridaNova.getDistanciaKm4() != null && !corridaNova.getDistanciaKm4().isEmpty()) {
					distancia4 = new BigDecimal(corridaNova.getDistanciaKm4().replace(",", "."));
				}
				
				if (cidade.getKmForaCidade() != null && cidade.getValorKmForaCidade() != null && cidade.getKmForaCidade().floatValue() < distancia.floatValue()) {
					
					vlMaximoCorridaPadrao = cidade.getValorBandeiradaRadio() 
							.add(cidade.getValorKmForaCidade().multiply(distancia)) 
							.add(new BigDecimal((corridaNova.getPrevisaoCorrida()/60)).multiply(cidade.getValorHoraParadaRadio()));
					
					if (distancia2 != null) {
						BigDecimal vlMaximoCorridaPadrao2 = cidade.getValorKmForaCidade().multiply(distancia2) 
								.add(new BigDecimal((corridaNova.getPrevisaoCorrida2()/60)).multiply(cidade.getValorHoraParadaRadio()));
						vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao2);
					}
					
					if (distancia3 != null) {
						BigDecimal vlMaximoCorridaPadrao3 = cidade.getValorKmForaCidade().multiply(distancia3)
								.add(new BigDecimal((corridaNova.getPrevisaoCorrida3()/60)).multiply(cidade.getValorHoraParadaRadio()));
						vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao3);
					}
					
					if (distancia4 != null) {
						BigDecimal vlMaximoCorridaPadrao4 = cidade.getValorKmForaCidade().multiply(distancia4)
								.add(new BigDecimal((corridaNova.getPrevisaoCorrida4()/60)).multiply(cidade.getValorHoraParadaRadio()));
						vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao4);
					}
					
				} else {
					
					int bandeira = 1;
					if (cidade.getHorarioInicioBandeira2() != null && cidade.getHorarioFimBandeira2() != null) {
						
						Date dataAtual = new Date();
						Date dataInicio = new Date();
						Date dataFim = new Date();
						Calendar cAtual = Calendar.getInstance();
						cAtual.setTime(dataAtual);
						Calendar cInicio = Calendar.getInstance();
						cInicio.setTime(dataInicio);
						Calendar cFim = Calendar.getInstance();
						cFim.setTime(dataFim);
						cFim.add(Calendar.DAY_OF_MONTH, 1);
						if (cidade.getHorarioInicioBandeira2().indexOf(':') > -1) {
							cInicio.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[0]));
							cInicio.set(Calendar.MINUTE, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[1]));
						} else {
							cInicio.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[0]));
							cInicio.set(Calendar.MINUTE, 0);
						}
						
						if (cidade.getHorarioFimBandeira2().indexOf(':') > -1) {
							cFim.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[0]));
							cFim.set(Calendar.MINUTE, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[1]));
						} else {
							cFim.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[0]));
							cFim.set(Calendar.MINUTE, 0);
						}
						
						int day = cAtual.get(Calendar.DAY_OF_WEEK);
						boolean isWeekend = (day == 6) || (day == 0);
						if (isWeekend) {
							bandeira = 2;
						} else if (dataAtual.getTime() < cFim.getTime().getTime()) {
							bandeira = 2;
						} else if (dataAtual.getTime() > cInicio.getTime().getTime()) {
							bandeira = 2;
						} else {
							bandeira = 1;
						}
						
					}
					
					if (bandeira == 1) {
						
						vlMaximoCorridaPadrao = cidade.getValorBandeiradaRadio() 
								.add(cidade.getValorKmBandeira1Radio().multiply(distancia)) 
								.add(new BigDecimal((corridaNova.getPrevisaoCorrida()/60)).multiply(cidade.getValorHoraParadaRadio()));
						
						if (distancia2 != null) {
							BigDecimal vlMaximoCorridaPadrao2 = cidade.getValorKmBandeira1Radio().multiply(distancia2) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida2()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao2);
						}
						
						if (distancia3 != null) {
							BigDecimal vlMaximoCorridaPadrao3 = cidade.getValorKmBandeira1Radio().multiply(distancia3) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida3()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao3);
						}
						
						if (distancia4 != null) {
							BigDecimal vlMaximoCorridaPadrao4 = cidade.getValorKmBandeira1Radio().multiply(distancia4) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida4()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao4);
						}
						
					} else {
						
						vlMaximoCorridaPadrao = cidade.getValorBandeiradaRadio().add(cidade.getValorKmBandeira2Radio().multiply(distancia)) 
								.add(new BigDecimal((corridaNova.getPrevisaoCorrida()/60)).multiply(cidade.getValorHoraParadaRadio()));
						
						if (distancia2 != null) {
							BigDecimal vlMaximoCorridaPadrao2 = cidade.getValorKmBandeira2Radio().multiply(distancia2) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida2()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao2);
						}
						
						if (distancia3 != null) {
							BigDecimal vlMaximoCorridaPadrao3 = cidade.getValorKmBandeira2Radio().multiply(distancia3) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida3()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao3);
						}
						
						if (distancia4 != null) {
							BigDecimal vlMaximoCorridaPadrao4 = cidade.getValorKmBandeira2Radio().multiply(distancia4) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida4()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao4);
						}
					}
				}
				
				if (cidade.getValorMinimoCorrida() != null 
						&& vlMaximoCorridaPadrao.floatValue() < cidade.getValorMinimoCorrida().floatValue()) {
					vlMaximoCorridaPadrao = cidade.getValorMinimoCorrida();
				}
				
				if (getEmpresaConveniada().getDescontoPorcentagem() != null) {
					BigDecimal valorSubtrair = vlMaximoCorridaPadrao.multiply(
							getEmpresaConveniada().getDescontoPorcentagem().divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));
					corridaNova.setValorFinal(vlMaximoCorridaPadrao.add(valorSubtrair.multiply(new BigDecimal(-1))));
				} else {
					corridaNova.setValorFinal(vlMaximoCorridaPadrao);
				}
				corridaNova.setValorFinal(corridaNova.getValorFinal().setScale(2, 
						BigDecimal.ROUND_HALF_EVEN));
			}
		}
		
	}
	
	public void solicitarNovaCorrida() throws Exception {
		
		Boolean contemErro = Boolean.FALSE;
		if (corridaNova.getCidade() == null || corridaNova.getCidade().getId() == null) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Cidade' é obrigatório.");
		}
		if (corridaNova.getNomePassageiro() == null || corridaNova.getNomePassageiro().isEmpty()) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Nome do Passageiro' é obrigatório.");
		}
		if (corridaNova.getTipoTeleTaxi() == null) {
			contemErro = Boolean.TRUE;
			addMsgErro("O campo 'Empresa' é obrigatório.");
		}
		
		if ((corridaNova.getDataAgendamento() == null || corridaNova.getDataAgendamento().isEmpty()) 
				&& (corridaNova.getHoraAgendamento() != null && !corridaNova.getHoraAgendamento().isEmpty())) {
			contemErro = Boolean.TRUE;
			addMsgErro("Para o agendamento de uma corrida, tanto a data como o horário devem ser preenchidos");
		}
		
		if ((corridaNova.getDataAgendamento() != null && !corridaNova.getDataAgendamento().isEmpty()) 
				&& (corridaNova.getHoraAgendamento() == null || corridaNova.getHoraAgendamento().isEmpty())) {
			contemErro = Boolean.TRUE;
			addMsgErro("Para o agendamento de uma corrida, tanto a data como o horário devem ser preenchidos");
		}
		
		if ((corridaNova.getDataAgendamento() != null && !corridaNova.getDataAgendamento().isEmpty())
				&& (corridaNova.getHoraAgendamento() != null && !corridaNova.getHoraAgendamento().isEmpty())) {
			String data =  corridaNova.getDataAgendamento() + corridaNova.getHoraAgendamento();
			try {
				Date date = new SimpleDateFormat("dd/MM/yyyyHH:mm").parse(data);
				if (date.getTime() < new Date().getTime()) {
					contemErro = Boolean.TRUE;
					addMsgErro("A Data de Agendamento e seu Horário devem ser maiores que a data atual.");
				} else {
					corridaNova.setDataAgendamentoFinal(date);
				}
			} catch (Exception e) {
				contemErro = Boolean.TRUE;
				addMsgErro("A Data de Agendamento e seu Horário não estão no padrão dd/MM/YYYY HH:mm:ss.");
			}
		}
		
		if (corridaNova.getOrigemEndereco() == null || corridaNova.getOrigemEndereco().isEmpty()) {
			contemErro = Boolean.TRUE;
			addMsgErro("Não foi selecionado uma Origem Válida.");
		}
		
		if ((corridaNova.getDestinoEndereco() == null || corridaNova.getDestinoEndereco().isEmpty())
				&& getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null 
				&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
			contemErro = Boolean.TRUE;
			addMsgErro("Não foi selecionado um Destino Válido.");
		}
		
		if (getTipoCorrida().equals(1)) {
			
			if (corridaNova.getOrigem() == null || corridaNova.getOrigem().isEmpty()) {
				contemErro = Boolean.TRUE;
				addMsgErro("O campo 'Origem' é obrigatório.");
			}
			
			if ((corridaNova.getDestino() == null || corridaNova.getDestino().isEmpty())
					&& getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null 
					&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
				contemErro = Boolean.TRUE;
				addMsgErro("O campo 'Destino' é obrigatório.");
			}
			
			if ((corridaNova.getDistanciaKm() == null || corridaNova.getDistanciaKm().isEmpty())
					&& getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null 
					&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
				contemErro = Boolean.TRUE;
				addMsgErro("Não foi possível calcular a distância entre Origem e Destino.");
			}
		} else if (getTipoCorrida().equals(2)) {
			corridaNova.setDestino(corridaNova.getDestinoEndereco());
			corridaNova.setOrigem(corridaNova.getOrigemEndereco());
			
			if (getRegiao() == null || getRegiao().getId() == null) {
				contemErro = Boolean.TRUE;
				addMsgErro("O Campo 'Região' é obrigatório.");
			} else {
				if (getRegiao().getIdPa1() == null || getRegiao().getIdPa2() == null) {
					contemErro = Boolean.TRUE;
					addMsgErro("A Região escolhida não possui Pontos de Apoio configurados.");
				} else {
					corridaNova.setPontoApoio1(localidadeService.recuperarPorChave(getRegiao().getIdPa1()));
					corridaNova.setPontoApoio2(localidadeService.recuperarPorChave(getRegiao().getIdPa2()));
					if (getRegiao().getIdPa3() != null) {
						corridaNova.setPontoApoio3(localidadeService.recuperarPorChave(getRegiao().getIdPa3()));
					}
					if (getRegiao().getIdPa4() != null) {
						corridaNova.setPontoApoio4(localidadeService.recuperarPorChave(getRegiao().getIdPa4()));
					}
				}
			}
		}
		
		
		if (getEmpresaConveniada().getId() != null) {
			
			if (getCorridaNova().getVoucher() == null || getCorridaNova().getVoucher().isEmpty()) {
				contemErro = Boolean.TRUE;
				addMsgErro("O Voucher é obrigatório para corridas de Empresas Conveniadas.");
			} else {
				getCorridaNova().setVoucher(getCorridaNova().getVoucher().replaceAll(" ", ""));
			}
			
		}
		
		if (!contemErro) {
			corridaNova.setUsuario(recuperarUsuarioSessao());
			corridaNova.setTipo(TipoCorridaEnum.CORRIDA.getCodigo());
			corridaNova.setIndicadorTeleTaxi(1);
			if (corridaNova.getTipoVeiculo().equals(TipoVeiculoEnum.ADAPTADO_PADRAO.getCodigo())) {
				corridaNova.setTipo(TipoVeiculoEnum.PADRAO.getCodigo());
				corridaNova.setIndicadorAdaptado(1);
			}
			corridaNova.setDataSolicitacao(new Date());
			if (corridaNova.getDataAgendamento() != null && !corridaNova.getDataAgendamento().isEmpty()) {
				corridaNova.setStatus(StatusCorridaEnum.AGENDADA.getStatus());
			} else {
				corridaNova.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
			}
			corridaNova.setIndicadorCorridaFim(0);
			corridaNova.setIndicadorCorridaInicio(0);
			corridaNova.setIdEmpresaConveniada(getEmpresaConveniada().getId());
			String celularPassageiro = corridaNova.getCelularPassageiro();
			if (corridaNova.getVoucher() != null && !corridaNova.getVoucher().isEmpty()) {
				corridaNova.setVoucher(corridaNova.getVoucher().trim());
			}
			if (corridaNova.getIdEmpresaConveniada() != null) {
				Cidade cidade = corridaNova.getCidade();
				corridaNova.setIndicadorCarrosSelecionados(getEmpresaConveniada().getIndicadorCarrosSelecionados());
				if (getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
					
					BigDecimal vlMaximoCorridaPadrao = new BigDecimal(0);
					BigDecimal distancia = new BigDecimal(corridaNova.getDistanciaKm().replace(",", "."));
					BigDecimal distancia2 = null;
					BigDecimal distancia3 = null;
					BigDecimal distancia4 = null;
					
					if (corridaNova.getDistanciaKm2() != null && !corridaNova.getDistanciaKm2().isEmpty()) {
						distancia2 = new BigDecimal(corridaNova.getDistanciaKm2().replace(",", "."));
					}
					
					if (corridaNova.getDistanciaKm3() != null && !corridaNova.getDistanciaKm3().isEmpty()) {
						distancia3 = new BigDecimal(corridaNova.getDistanciaKm3().replace(",", "."));
					}
					
					if (corridaNova.getDistanciaKm4() != null && !corridaNova.getDistanciaKm4().isEmpty()) {
						distancia4 = new BigDecimal(corridaNova.getDistanciaKm4().replace(",", "."));
					}
					
					if (distancia4 != null) {
						corridaNova.setNomePassageiro("Grupo: (" + corridaNova.getNomePassageiro() + " + 3)");
					} else if (distancia3 != null) {
						corridaNova.setNomePassageiro("Grupo: (" + corridaNova.getNomePassageiro() + " + 2)");
					} else if (distancia2 != null) {
						corridaNova.setNomePassageiro("Grupo: (" + corridaNova.getNomePassageiro() + " + 1)");
					}
					
					if (cidade.getKmForaCidade() != null && cidade.getValorKmForaCidade() != null && cidade.getKmForaCidade().floatValue() < distancia.floatValue()) {
						
						vlMaximoCorridaPadrao = cidade.getValorBandeiradaRadio() 
								.add(cidade.getValorKmForaCidade().multiply(distancia)) 
								.add(new BigDecimal((corridaNova.getPrevisaoCorrida()/60)).multiply(cidade.getValorHoraParadaRadio()));
						
						if (distancia2 != null) {
							BigDecimal vlMaximoCorridaPadrao2 = cidade.getValorKmForaCidade().multiply(distancia2) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida2()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao2);
						}
						
						if (distancia3 != null) {
							BigDecimal vlMaximoCorridaPadrao3 = cidade.getValorKmForaCidade().multiply(distancia3)
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida3()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao3);
						}
						
						if (distancia4 != null) {
							BigDecimal vlMaximoCorridaPadrao4 = cidade.getValorKmForaCidade().multiply(distancia4)
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida4()/60)).multiply(cidade.getValorHoraParadaRadio()));
							vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao4);
						}
						
					} else {
						
						int bandeira = 1;
						if (cidade.getHorarioInicioBandeira2() != null && cidade.getHorarioFimBandeira2() != null) {
							
							Date dataAtual = new Date();
							Date dataInicio = new Date();
							Date dataFim = new Date();
							Calendar cAtual = Calendar.getInstance();
							cAtual.setTime(dataAtual);
							Calendar cInicio = Calendar.getInstance();
							cInicio.setTime(dataInicio);
							Calendar cFim = Calendar.getInstance();
							cFim.setTime(dataFim);
							cFim.add(Calendar.DAY_OF_MONTH, 1);
							if (cidade.getHorarioInicioBandeira2().indexOf(':') > -1) {
								cInicio.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[0]));
								cInicio.set(Calendar.MINUTE, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[1]));
							} else {
								cInicio.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[0]));
								cInicio.set(Calendar.MINUTE, 0);
							}
							
							if (cidade.getHorarioFimBandeira2().indexOf(':') > -1) {
								cFim.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[0]));
								cFim.set(Calendar.MINUTE, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[1]));
							} else {
								cFim.set(Calendar.HOUR_OF_DAY, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[0]));
								cFim.set(Calendar.MINUTE, 0);
							}
							
							int day = cAtual.get(Calendar.DAY_OF_WEEK);
							boolean isWeekend = (day == 6) || (day == 0);
							if (isWeekend) {
								bandeira = 2;
							} else if (dataAtual.getTime() < cFim.getTime().getTime()) {
								bandeira = 2;
							} else if (dataAtual.getTime() > cInicio.getTime().getTime()) {
								bandeira = 2;
							} else {
								bandeira = 1;
							}
							
						}
						
						if (bandeira == 1) {
							
							vlMaximoCorridaPadrao = cidade.getValorBandeiradaRadio() 
									.add(cidade.getValorKmBandeira1Radio().multiply(distancia)) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida()/60)).multiply(cidade.getValorHoraParadaRadio()));
							
							if (distancia2 != null) {
								BigDecimal vlMaximoCorridaPadrao2 = cidade.getValorKmBandeira1Radio().multiply(distancia2)
										.add(new BigDecimal((corridaNova.getPrevisaoCorrida2()/60)).multiply(cidade.getValorHoraParadaRadio()));
								vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao2);
							}
							
							if (distancia3 != null) {
								BigDecimal vlMaximoCorridaPadrao3 = cidade.getValorKmBandeira1Radio().multiply(distancia3)
										.add(new BigDecimal((corridaNova.getPrevisaoCorrida3()/60)).multiply(cidade.getValorHoraParadaRadio()));
								vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao3);
							}
							
							if (distancia4 != null) {
								BigDecimal vlMaximoCorridaPadrao4 = cidade.getValorKmBandeira1Radio().multiply(distancia4)
										.add(new BigDecimal((corridaNova.getPrevisaoCorrida4()/60)).multiply(cidade.getValorHoraParadaRadio()));
								vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao4);
							}
							
						} else {
							
							vlMaximoCorridaPadrao = cidade.getValorBandeiradaRadio().add(cidade.getValorKmBandeira2Radio().multiply(distancia)) 
									.add(new BigDecimal((corridaNova.getPrevisaoCorrida()/60)).multiply(cidade.getValorHoraParadaRadio()));
							
							if (distancia2 != null) {
								BigDecimal vlMaximoCorridaPadrao2 = cidade.getValorKmBandeira2Radio().multiply(distancia2)
										.add(new BigDecimal((corridaNova.getPrevisaoCorrida2()/60)).multiply(cidade.getValorHoraParadaRadio()));
								vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao2);
							}
							
							if (distancia3 != null) {
								BigDecimal vlMaximoCorridaPadrao3 = cidade.getValorKmBandeira2Radio().multiply(distancia3) 
										.add(new BigDecimal((corridaNova.getPrevisaoCorrida3()/60)).multiply(cidade.getValorHoraParadaRadio()));
								vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao3);
							}
							
							if (distancia4 != null) {
								BigDecimal vlMaximoCorridaPadrao4 = cidade.getValorKmBandeira2Radio().multiply(distancia4)
										.add(new BigDecimal((corridaNova.getPrevisaoCorrida4()/60)).multiply(cidade.getValorHoraParadaRadio()));
								vlMaximoCorridaPadrao = vlMaximoCorridaPadrao.add(vlMaximoCorridaPadrao4);
							}
						}
						
					}
					
					if (cidade.getValorMinimoCorrida() != null 
							&& vlMaximoCorridaPadrao.floatValue() < cidade.getValorMinimoCorrida().floatValue()) {
						vlMaximoCorridaPadrao = cidade.getValorMinimoCorrida();
					}
					
					if (getEmpresaConveniada().getDescontoPorcentagem() != null) {
						BigDecimal valorSubtrair = vlMaximoCorridaPadrao.multiply(
								getEmpresaConveniada().getDescontoPorcentagem().divide(new BigDecimal(100), 2, BigDecimal.ROUND_FLOOR));
						corridaNova.setValorFinal(vlMaximoCorridaPadrao.add(valorSubtrair.multiply(new BigDecimal(-1))));
					} else {
						corridaNova.setValorFinal(vlMaximoCorridaPadrao);
					}
					corridaNova.setValorFinal(corridaNova.getValorFinal().setScale(2, 
							BigDecimal.ROUND_HALF_EVEN));
				} else {
					corridaNova.setValorFinal(null);
				}
			}
			corridaService.salvarCorrida(corridaNova);
			corridaNova.setCelularPassageiro(celularPassageiro);
			if (corridaNova.getIdEmpresaConveniada() != null) {
				Voucher voucher = new Voucher();
				voucher.setDataGeracao(new Date());
				voucher.setIdCorrida(corridaNova.getId());
				voucher.setVoucher(corridaNova.getVoucher());
				voucher.setIdEmpresaConveniada(corridaNova.getIdEmpresaConveniada());
				empresaConveniadaService.salvarVoucher(voucher);
				
				if (getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null
						&& getEmpresaConveniada().getIndicadorVoucherEletronico().equals(2)) {
					corridaNova.setIndicadorVoucherPapel(1);
				}
			}
			
			RestTemplate rest = new RestTemplate();
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarCorridaLocal", 
					corridaNova, Void.class);
			
			Corrida cEmail = new Corrida();
			cEmail.setId(getCorridaNova().getId());
			cEmail.setOrigemEndereco(getCorridaNova().getOrigemEndereco());
			cEmail.setOrigem(getCorridaNova().getOrigem());
			cEmail.setOrigemLatitude(getCorridaNova().getOrigemLatitude());
			cEmail.setOrigemLongitude(getCorridaNova().getOrigemLongitude());
			cEmail.setDestinoEndereco(getCorridaNova().getDestinoEndereco());
			cEmail.setDestino(getCorridaNova().getDestino());
			cEmail.setDestinoLatitude(getCorridaNova().getDestinoLatitude());
			cEmail.setDestinoLongitude(getCorridaNova().getDestinoLongitude());
			cEmail.setVoucher(getCorridaNova().getVoucher());
			cEmail.setUsuario(new Usuario());
			cEmail.setNomePassageiro(getCorridaNova().getNomePassageiro());
			cEmail.setDataSolicitacao(getCorridaNova().getDataSolicitacao());
			cEmail.setUsuario(getCorridaNova().getUsuario());
			cEmail.setCelularPassageiro(getCorridaNova().getCelularPassageiro());
			cEmail.setEmail(getCorridaNova().getEmail());
			cEmail.setTipoTeleTaxi(getCorridaNova().getTipoTeleTaxi());
			cEmail.setComplemento(getCorridaNova().getComplemento());
			cEmail.setTempoCorrida(getCorridaNova().getTempoCorrida());
			cEmail.setPrevisaoCorrida(getCorridaNova().getPrevisaoCorrida());
			cEmail.setDistanciaKm(getCorridaNova().getDistanciaKm());
			cEmail.setObservacaoTele(getCorridaNova().getObservacaoTele());
			cEmail.setValorFinal(getCorridaNova().getValorFinal());
			cEmail.setDataAgendamento(getCorridaNova().getDataAgendamento());
			cEmail.setHoraAgendamento(getCorridaNova().getHoraAgendamento());
			
			EmpresaConveniada eEmail = new EmpresaConveniada();
			eEmail.setId(getEmpresaConveniada().getId());
			eEmail.setNome(getEmpresaConveniada().getNome());
			eEmail.setEmail(getEmpresaConveniada().getEmail());
			eEmail.setDescontoPorcentagem(getEmpresaConveniada().getDescontoPorcentagem());
			eEmail.setIndicadorVoucherEletronico(getEmpresaConveniada().getIndicadorVoucherEletronico());
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					if (eEmail.getId() != null) {
						empresaConveniadaService.enviarEmailVoucher(cEmail, eEmail);
					}
					if (cliente == null || cliente.getId() == null) {
						ClienteTeleTaxi clienteTeleTaxi = new ClienteTeleTaxi();
						clienteTeleTaxi.setNome(cEmail.getNomePassageiro());
						clienteTeleTaxi.setDataCadastro(new Date());
						clienteTeleTaxi.setEmail(cEmail.getEmail());
						clienteTeleTaxi.setCelular(cEmail.getCelularPassageiro());
						clienteTeleTaxi.setEmpresa(cEmail.getTipoTeleTaxi());
						
						if (cEmail.getOrigemLatitude() != null && !cEmail.getOrigemLatitude().isEmpty()
								&& cEmail.getOrigemLongitude() != null && !cEmail.getOrigemLongitude().isEmpty()) {
							clienteTeleTaxi.setOrigem(cEmail.getOrigem());
							clienteTeleTaxi.setOrigemEndereco(cEmail.getOrigemEndereco());
							clienteTeleTaxi.setLatitudeOrigem(cEmail.getOrigemLatitude());
							clienteTeleTaxi.setLongitudeOrigem(cEmail.getOrigemLongitude());
						}
						
						if (cEmail.getDestinoLatitude() != null && !cEmail.getDestinoLatitude().isEmpty()
								&& cEmail.getDestinoLongitude() != null && !cEmail.getDestinoLongitude().isEmpty()) {
							clienteTeleTaxi.setDestino(cEmail.getDestino());
							clienteTeleTaxi.setDestinoEndereco(cEmail.getDestinoEndereco());
							clienteTeleTaxi.setLatitudeDestino(cEmail.getDestinoLatitude());
							clienteTeleTaxi.setLongitudeDestino(cEmail.getDestinoLongitude());
						}
						
						clienteTeleTaxi.setQtdCorridas(1l);
						clienteTeleTaxi.setComplemento(cEmail.getComplemento());
						clienteTeleTaxi.setTempoCorrida(cEmail.getTempoCorrida());
						clienteTeleTaxi.setPrevisaoCorrida(cEmail.getPrevisaoCorrida());
						clienteTeleTaxi.setDistanciaKm(cEmail.getDistanciaKm());
						clienteTeleTaxi.setObservacaoTele(cEmail.getObservacaoTele());
						
						clienteTeleTaxiService.salvar(clienteTeleTaxi);
					} else {
						ClienteTeleTaxi clienteTeleTaxi = clienteTeleTaxiService.recuperarPorChave(cliente.getId());
						if (clienteTeleTaxi.getQtdCorridas() != null) {
							clienteTeleTaxi.setQtdCorridas(clienteTeleTaxi.getQtdCorridas() + 1l);
						} else {
							clienteTeleTaxi.setQtdCorridas(1l);
						}
						if (clienteTeleTaxi.getOrigem() == null || clienteTeleTaxi.getOrigem().isEmpty()) {
							
							if (cEmail.getOrigemLatitude() != null && !cEmail.getOrigemLatitude().isEmpty()
									&& cEmail.getOrigemLongitude() != null && !cEmail.getOrigemLongitude().isEmpty()) {
								clienteTeleTaxi.setOrigem(cEmail.getOrigem());
								clienteTeleTaxi.setOrigemEndereco(cEmail.getOrigemEndereco());
								clienteTeleTaxi.setLatitudeOrigem(cEmail.getOrigemLatitude());
								clienteTeleTaxi.setLongitudeOrigem(cEmail.getOrigemLongitude());
							}
							
							if (cEmail.getDestinoLatitude() != null && !cEmail.getDestinoLatitude().isEmpty()
									&& cEmail.getDestinoLongitude() != null && !cEmail.getDestinoLongitude().isEmpty()) {
								clienteTeleTaxi.setDestino(cEmail.getDestino());
								clienteTeleTaxi.setDestinoEndereco(cEmail.getDestinoEndereco());
								clienteTeleTaxi.setLatitudeDestino(cEmail.getDestinoLatitude());
								clienteTeleTaxi.setLongitudeDestino(cEmail.getDestinoLongitude());
							}
							
						}
						clienteTeleTaxiService.salvar(clienteTeleTaxi);
					}
					
				}
			}).start();
			
			setCorridaNova(null);
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
			setEmpresaConveniada(null);
			addMsgSuccess("Corrida solicitada com sucesso.");
			execJavaScript("fecharModalNovaCorrida()");
		}
		
	}
	
	public void removerTrecho(Integer trecho) {
		
		if (getCorridaNova().getQtdTrechos() != null) {
			getCorridaNova().setQtdTrechos(trecho - 1);
			if (trecho.equals(2)) {
				getCorridaNova().setOrigemEndereco2(null);
				getCorridaNova().setOrigemLatitude2(null);
				getCorridaNova().setOrigemLongitude2(null);
				getCorridaNova().setDestino2(null);
				getCorridaNova().setDestinoEndereco2(null);
				getCorridaNova().setDestinoLatitude2(null);
				getCorridaNova().setDestinoLongitude2(null);
			} else if (trecho.equals(3)) {
				getCorridaNova().setOrigemEndereco3(null);
				getCorridaNova().setOrigemLatitude3(null);
				getCorridaNova().setOrigemLongitude3(null);
				getCorridaNova().setDestino3(null);
				getCorridaNova().setDestinoEndereco3(null);
				getCorridaNova().setDestinoLatitude3(null);
				getCorridaNova().setDestinoLongitude3(null);
			} else if (trecho.equals(4)) {
				getCorridaNova().setOrigemEndereco4(null);
				getCorridaNova().setOrigemLatitude4(null);
				getCorridaNova().setOrigemLongitude4(null);
				getCorridaNova().setDestino4(null);
				getCorridaNova().setDestinoEndereco4(null);
				getCorridaNova().setDestinoLatitude4(null);
				getCorridaNova().setDestinoLongitude4(null);
			}
		}
	}
	
	public void adicionarTrecho() {
		
		if (getCorridaNova().getQtdTrechos() == null) {
			getCorridaNova().setQtdTrechos(1);
		}
		
		if (getCorridaNova().getQtdTrechos().equals(1)) {
			
			if (getCorridaNova().getOrigemEndereco() != null 
					&& getCorridaNova().getDestinoEndereco() != null
					&& !getCorridaNova().getOrigemEndereco().isEmpty()
					&& !getCorridaNova().getDestinoEndereco().isEmpty()
					&& getCorridaNova().getDestinoLatitude() != null
					&& !getCorridaNova().getDestinoLatitude().isEmpty()
					&& getCorridaNova().getDestinoLongitude() != null
					&& !getCorridaNova().getDestinoLongitude().isEmpty()
					&& getCorridaNova().getDistanciaKm() != null) {
				getCorridaNova().setOrigemEndereco2(getCorridaNova().getDestinoEndereco());
				getCorridaNova().setOrigemLatitude2(getCorridaNova().getDestinoLatitude());
				getCorridaNova().setOrigemLongitude2(getCorridaNova().getDestinoLongitude());
				getCorridaNova().setQtdTrechos(getCorridaNova().getQtdTrechos() + 1);
			}
			
		} else if (getCorridaNova().getQtdTrechos().equals(2)) {
			
			if (getCorridaNova().getOrigemEndereco2() != null 
					&& getCorridaNova().getDestinoEndereco2() != null
					&& !getCorridaNova().getOrigemEndereco2().isEmpty()
					&& !getCorridaNova().getDestinoEndereco2().isEmpty()
					&& getCorridaNova().getDestinoLatitude2() != null
					&& !getCorridaNova().getDestinoLatitude2().isEmpty()
					&& getCorridaNova().getDestinoLongitude2() != null
					&& !getCorridaNova().getDestinoLongitude2().isEmpty()
					&& getCorridaNova().getDistanciaKm2() != null) {
				getCorridaNova().setOrigemEndereco3(getCorridaNova().getDestinoEndereco2());
				getCorridaNova().setOrigemLatitude3(getCorridaNova().getDestinoLatitude2());
				getCorridaNova().setOrigemLongitude3(getCorridaNova().getDestinoLongitude2());
				getCorridaNova().setQtdTrechos(getCorridaNova().getQtdTrechos() + 1);
			}
			
		} else if (getCorridaNova().getQtdTrechos().equals(3)) {
			
			if (getCorridaNova().getOrigemEndereco3() != null 
					&& getCorridaNova().getDestinoEndereco3() != null
					&& !getCorridaNova().getOrigemEndereco3().isEmpty()
					&& !getCorridaNova().getDestinoEndereco3().isEmpty()
					&& getCorridaNova().getDestinoLatitude3() != null
					&& !getCorridaNova().getDestinoLatitude3().isEmpty()
					&& getCorridaNova().getDestinoLongitude3() != null
					&& !getCorridaNova().getDestinoLongitude3().isEmpty()
					&& getCorridaNova().getDistanciaKm3() != null) {
				getCorridaNova().setOrigemEndereco4(getCorridaNova().getDestinoEndereco3());
				getCorridaNova().setOrigemLatitude4(getCorridaNova().getDestinoLatitude3());
				getCorridaNova().setOrigemLongitude4(getCorridaNova().getDestinoLongitude3());
				getCorridaNova().setQtdTrechos(getCorridaNova().getQtdTrechos() + 1);
			}
		}
		
	}
	
	public void limparDataAgendada() {
		
		getCorridaNova().setDataAgendamento(null);
		getCorridaNova().setDataAgendamentoFinal(null);
		getCorridaNova().setHoraAgendamento(null);
	}
	
	public void carregarCentrosCusto() {
		if (getEmpresaConveniada() != null && getEmpresaConveniada().getId() != null) {
			setCentros(empresaConveniadaService.recuperarCentroCustos(getEmpresaConveniada().getId()));
		} else {
			setCentros(null);
		}
	}
	
	public void gerarVoucher() {
		
		if (getEmpresaConveniada() != null && getEmpresaConveniada().getId() != null) {
			setCentros(empresaConveniadaService.recuperarCentroCustos(getEmpresaConveniada().getId()));
		} else {
			setCentros(null);
		}
		
		if (getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null
				&& (getEmpresaConveniada().getIndicadorVoucherEletronico().equals(1)
				|| getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6))) {
			String voucher = "";
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 7; i++) {
				int ch = rand.nextInt(letras.length);
				sb.append(letras[ch]);
			}    
			voucher = sb.toString();
			while (empresaConveniadaService.verificaExistenciaVoucher(voucher)) {
				for (int i = 0; i < 7; i++) {
					int ch = rand.nextInt(letras.length);
					sb.append(letras[ch]);
				}    
				voucher = sb.toString();
			}
			getCorridaNova().setVoucher(voucher);
			
			if (getEmpresaConveniada().getIndicadorVoucherEletronico().equals(6)) {
				getCorridaNova().setOrigem(null);
				getCorridaNova().setOrigemEndereco(null);
				getCorridaNova().setOrigemLatitude(null);
				getCorridaNova().setOrigemLongitude(null);
			}
		} else if (getEmpresaConveniada() != null && getEmpresaConveniada().getIndicadorVoucherEletronico() != null
				&& getEmpresaConveniada().getIndicadorVoucherEletronico() > 2) {
			getCorridaNova().setVoucher(getEmpresaConveniada().getNumeroInicioVoucher());
		} else {
			getCorridaNova().setVoucher(null);
		}
	}
	
	public void recuperarDadosPassageiro() {
		if (getCorridaNova().getCelularPassageiro() != null && !getCorridaNova().getCelularPassageiro().isEmpty()) {
			ClienteTeleTaxi cliente = clienteTeleTaxiService.recuperarClientePorCelular(getCorridaNova().getCelularPassageiro());
			if (cliente != null) {
				getCorridaNova().setNomePassageiro(cliente.getNome());
				getCorridaNova().setEmail(cliente.getEmail());
				if (cliente.getLatitudeOrigem() != null && !cliente.getLatitudeOrigem().isEmpty()
						&& cliente.getLongitudeOrigem() != null && !cliente.getLongitudeOrigem().isEmpty()
						&& getUsuarioTele().getIdEmpresaConveniada() == null) {
					
					getCorridaNova().setOrigem(cliente.getOrigem());
					getCorridaNova().setOrigemEndereco(cliente.getOrigemEndereco());
					getCorridaNova().setOrigemLatitude(cliente.getLatitudeOrigem());
					getCorridaNova().setOrigemLongitude(cliente.getLongitudeOrigem());
					
					if (cliente.getLatitudeDestino() != null && !cliente.getLatitudeDestino().isEmpty()
							&& cliente.getLongitudeDestino() != null && !cliente.getLongitudeDestino().isEmpty()) {
						//getCorridaNova().setDestinoLatitude(cliente.getLatitudeDestino());
						//getCorridaNova().setDestinoLongitude(cliente.getLongitudeDestino());
						//getCorridaNova().setDestino(cliente.getDestino());
						//getCorridaNova().setDestinoEndereco(cliente.getDestinoEndereco());
						//getCorridaNova().setDistanciaKm(cliente.getDistanciaKm());
						//getCorridaNova().setPrevisaoCorrida(cliente.getPrevisaoCorrida());
						//getCorridaNova().setTempoCorrida(cliente.getTempoCorrida());
					}
					getCorridaNova().setComplemento(cliente.getComplemento());
					getCorridaNova().setObservacaoTele(cliente.getObservacaoTele());
				}
				setCliente(cliente);
			}
		}
	}
	
	public void cancelarCorrida() {
		
		if (getMotivoCancelamento() == null || getMotivoCancelamento().isEmpty()) {
			addMsgErro("O Motivo do Cancelamento é obrigatório.");
		} else {
			Corrida c = corridaService.recuperarCorridaPorChave(getCorrida().getId());
			c.setDataFinalizacao(new Date());
			c.setStatus(StatusCorridaEnum.CANCELADA.getStatus());
			c.setMotivoCancelamento(getMotivoCancelamento());
			corridaService.salvarCorrida(c);
			
			RestTemplate rest = new RestTemplate();
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/removerCorridaLocal", 
					getCorrida(), Void.class);
			
			setCorridaNova(null);
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
			setMotivoCancelamento(null);
			addMsgSuccess("Corrida cancelada com sucesso.");
		}
		
	}
	
	public String detalharPassageiro() {
		
		definirMenu(MenuEnum.PASSAGEIROS.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idUsuario", 
				getCorrida().getUsuario().getId());
		return "passageiro";
	}
	
	public String detalharMotorista() {
		
		definirMenu(MenuEnum.MOTORISTA.getMenu());
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("idMotorista", 
				getCorrida().getMotorista().getId());
		return "motorista";
	}
	
	public String acompanharPercurso() {
		
		return "corrida";
	}
	
	public void carregarCorrida(Corrida corrida) {
		
		setEmpresaConveniada(null);
		setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
		if (getCorrida().getIdEmpresaConveniada() != null) {
			setEmpresaConveniada(empresaConveniadaService.consultarPorChave(getCorrida().getIdEmpresaConveniada()));
		}
		if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
			getCorrida().setMotorista(motoristaService.recuperarMotoristaPorChave(getCorrida().getMotorista().getId()));
			getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
			setListaPercurso(percursoService.recuperarPercursosCorrida(corrida));
			for (Percurso percurso : listaPercurso) {
				if (getCorrida().getLatitudesPercurso() == null || getCorrida().getLatitudesPercurso().isEmpty()) {
					getCorrida().setLatitudesPercurso(percurso.getLatitude());
					getCorrida().setLongitudesPercurso(percurso.getLongitude());
				} else {
					getCorrida().setLatitudesPercurso(getCorrida().getLatitudesPercurso() + ";" + percurso.getLatitude());
					getCorrida().setLongitudesPercurso(getCorrida().getLongitudesPercurso() + ";" + percurso.getLongitude());
				}
			}
		} else {
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
		}
	}
	
	public void carregarCorridaVisualizacoes(Corrida corrida) {
		
		setEmpresaConveniada(null);
		setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
		if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
			getCorrida().setMotorista(motoristaService.recuperarMotoristaPorChave(getCorrida().getMotorista().getId()));
			getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
		} else {
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
		}
		setVisualizacoes(corridaService.recuperarVisualizacoesCorrida(corrida));
	}
	
	public void carregarChatCorrida(Corrida corrida) {

		setMensagensChat(null);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		MediaType[] mdArray = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML };
		List<MediaType> medias = Arrays.asList(mdArray);
		converter.setSupportedMediaTypes(medias);     
		messageConverters.add(converter);
		RestTemplate rest = new RestTemplate();
		rest.setMessageConverters(messageConverters);
		
		if (corrida != null && corrida.getId() != null && corrida.getStatus() != null 
				&& (corrida.getStatus().equals(StatusCorridaEnum.A_CAMINHO.getStatus())
						|| corrida.getStatus().equals(StatusCorridaEnum.CORRENTE.getStatus()))) {
			
			Parametro parametro = parametrosService.recuperarParametroSistema();
			setEmpresaConveniada(null);
			setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
			if (getCorrida().getIdEmpresaConveniada() != null) {
				setEmpresaConveniada(empresaConveniadaService.consultarPorChave(getCorrida().getIdEmpresaConveniada()));
			}
			if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
				getCorrida().setMotorista(motoristaService.recuperarMotoristaPorChave(getCorrida().getMotorista().getId()));
				getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
			} else {
				getCorrida().setMotorista(new Motorista());
				getCorrida().setUsuarioMotorista(new Usuario());
			}
			
			MensagemCorridaVO[] mensagens = rest.postForObject(
					parametro.getHost() + "/econotaxi-rest/rest/corridaLocal/mensagens", 
					getCorrida(), MensagemCorridaVO[].class);
			if (mensagens != null && mensagens.length > 0) {
				setMensagensChat(Arrays.asList(mensagens));
			}
		} else {
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
		}
		
	}
	
	public void carregarChatCorrida() {

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		MediaType[] mdArray = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML };
		List<MediaType> medias = Arrays.asList(mdArray);
		converter.setSupportedMediaTypes(medias);     
		messageConverters.add(converter);
		RestTemplate rest = new RestTemplate();
		rest.setMessageConverters(messageConverters);
		
		if (corrida != null && corrida.getId() != null && corrida.getStatus() != null 
				&& (corrida.getStatus().equals(StatusCorridaEnum.A_CAMINHO.getStatus())
						|| corrida.getStatus().equals(StatusCorridaEnum.CORRENTE.getStatus()))) {
			
			setEmpresaConveniada(null);
			setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
			if (getCorrida().getIdEmpresaConveniada() != null) {
				setEmpresaConveniada(empresaConveniadaService.consultarPorChave(getCorrida().getIdEmpresaConveniada()));
			}
			if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
				getCorrida().setMotorista(motoristaService.recuperarMotoristaPorChave(getCorrida().getMotorista().getId()));
				getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
			} else {
				getCorrida().setMotorista(new Motorista());
				getCorrida().setUsuarioMotorista(new Usuario());
			}
			
			MensagemCorridaVO[] mensagens = rest.postForObject(
					Corrida.URL + "econotaxi-rest/rest/corridaLocal/mensagens", 
					getCorrida(), MensagemCorridaVO[].class);
			if (mensagens != null && mensagens.length > 0) {
				setMensagensChat(Arrays.asList(mensagens));
			}
		} else {
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
		}
	}
	
	public void verificaMensagemChatCorrida() {

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		MediaType[] mdArray = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED,
				MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML };
		List<MediaType> medias = Arrays.asList(mdArray);
		converter.setSupportedMediaTypes(medias);     
		messageConverters.add(converter);
		RestTemplate rest = new RestTemplate();
		rest.setMessageConverters(messageConverters);
		
		if (getUsuarioTele() != null && getUsuarioTele().getId() != null) {
			
			try {
				Usuario usuario = rest.postForObject(
						Corrida.URL + "econotaxi-rest/rest/corridaLocal/verificaMensagem", 
						getUsuarioTele(), Usuario.class);
				
				if (usuario != null && usuario.getId() != null) {
					RestTemplate restVisualizou = new RestTemplate();
					restVisualizou.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/visualizouMensagem", 
							getUsuarioTele(), Void.class);
					if (usuario.getNomeMotorista() != null && !usuario.getNomeMotorista().isEmpty()) {
						String identificacao = usuario.getNomeMotorista();
						if (usuario.getPlacaMotorista() != null && !usuario.getPlacaMotorista().isEmpty()) {
							identificacao = identificacao + " (Placa: " + usuario.getPlacaMotorista();
						}
						if (usuario.getUnidadeMotorista() != null && !usuario.getUnidadeMotorista().isEmpty()) {
							identificacao = identificacao + " / Unidade: " + usuario.getUnidadeMotorista() + ")";
						} else {
							identificacao = identificacao + ")";
						}
						execJavaScript("alert('Atenção: existe mensagem de chat do motorista: " + identificacao + " não lida: " 
								+ usuario.getMensagemEnviada() + "')");
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void adicionarMensagem() {
		
		if (getMensagemChat() != null && !getMensagemChat().isEmpty() 
				&& getCorrida() != null && getCorrida().getId() != null
				&& getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
			
			MensagemCorridaVO msg = new MensagemCorridaVO();
			msg.setDataMensagem(new Date());
			msg.setMensagem(mensagemChat);
			msg.setNome("Atendente: " + getUsuarioTele().getNome());
			msg.setOrigemPassageiro(Boolean.TRUE);
			msg.setIdCorrida(getCorrida().getId());
			msg.setIdMotorista(getCorrida().getUsuarioMotorista().getId());
			RestTemplate rest = new RestTemplate();
			rest.postForEntity(Corrida.URL + "econotaxi-rest/rest/corridaLocal/adicionarMensagem", 
					msg, Void.class);
			
			setMensagemChat(null);
			carregarChatCorrida();
		}
	}
	
	public void carregarCorrida() {
		
		if (corrida != null && corrida.getId() != null && corrida.getStatus() != null 
				&& (corrida.getStatus().equals(StatusCorridaEnum.A_CAMINHO.getStatus()) 
				|| corrida.getStatus().equals(StatusCorridaEnum.CORRENTE.getStatus()))) {
			setEmpresaConveniada(null);
			setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
			if (getCorrida().getIdEmpresaConveniada() != null) {
				setEmpresaConveniada(empresaConveniadaService.consultarPorChave(getCorrida().getIdEmpresaConveniada()));
			}
			if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
				getCorrida().setMotorista(motoristaService.recuperarMotoristaPorChave(getCorrida().getMotorista().getId()));
				getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
				setListaPercurso(percursoService.recuperarPercursosCorrida(corrida));
				for (Percurso percurso : listaPercurso) {
					if (getCorrida().getLatitudesPercurso() == null || getCorrida().getLatitudesPercurso().isEmpty()) {
						getCorrida().setLatitudesPercurso(percurso.getLatitude());
						getCorrida().setLongitudesPercurso(percurso.getLongitude());
					} else {
						getCorrida().setLatitudesPercurso(getCorrida().getLatitudesPercurso() + ";" + percurso.getLatitude());
						getCorrida().setLongitudesPercurso(getCorrida().getLongitudesPercurso() + ";" + percurso.getLongitude());
					}
				}
			} else {
				getCorrida().setMotorista(new Motorista());
				getCorrida().setUsuarioMotorista(new Usuario());
			}
		} else {
			getCorrida().setMotorista(new Motorista());
			getCorrida().setUsuarioMotorista(new Usuario());
		}
	}
	
	public void carregarCorridaVoucher(Corrida corrida) throws WriterException, IOException {
		
		setEmpresaConveniada(null);
		setCorrida(corridaService.recuperarCorridaPorChave(corrida.getId()));
		if (getCorrida().getIdEmpresaConveniada() != null) {
			setEmpresaConveniada(empresaConveniadaService.consultarPorChave(getCorrida().getIdEmpresaConveniada()));
		}
		if (getCorrida().getUsuarioMotorista() == null || getCorrida().getUsuarioMotorista().getId() == null) {
			if (getCorrida().getMotorista() != null && getCorrida().getMotorista().getId() != null) {
				getCorrida().setUsuarioMotorista(usuarioService.recuperarUsuarioPorID(getCorrida().getMotorista().getIdUsuario()));
			} else {
				getCorrida().setMotorista(new Motorista());
				if (getCorrida().getUsuarioMotorista() == null) {
					getCorrida().setUsuarioMotorista(new Usuario());
				}
			}
		}
		Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
		hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		
		// Now with zxing version 3.2.1 you could change border size (white border size to just 1)
		hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(corrida.getId() 
				+ "###FROTA###" + corrida.getUsuario().getId() 
				+ "###FROTA###" + corrida.getVoucher(), BarcodeFormat.QR_CODE, 250,
				250, hintMap);
		
		BufferedImage image = toBufferedImage(byteMatrix);
		
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "png", os);
		setImagemQrCode(Base64.getEncoder().encodeToString(os.toByteArray()));
	}
	
	private BufferedImage toBufferedImage(BitMatrix matrix) {

		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int onColor = 0xFF000000;
		int offColor = 0xFFFFFFFF;
		int[] rowPixels = new int[width];
		BitArray row = new BitArray(width);
		for (int y = 0; y < height; y++) {
			row = matrix.getRow(y, row);
			for (int x = 0; x < width; x++) {
				rowPixels[x] = row.get(x) ? onColor : offColor;
			}
			image.setRGB(0, y, width, 1, rowPixels, 0, width);
		}
		return image;
	}
	
    public void pesquisarCorridaPorFiltro() {
    	
    	getFiltro().setTipo(TipoCorridaEnum.CORRIDA.getCodigo());
    	getFiltro().setIndicadorTeleTaxi(1);
    	if (getUsuarioTele() != null && getUsuarioTele().getTipoTeleTaxi() != null) {
			getFiltro().setTipoTeleTaxi(getUsuarioTele().getTipoTeleTaxi());
		}
    	if (getUsuarioTele() != null && getUsuarioTele().getIdEmpresaConveniada() != null) {
			EmpresaConveniada ec = empresaConveniadaService.consultarPorChave(getUsuarioTele().getIdEmpresaConveniada());
			setEmpresaConveniada(ec);
			setEmpresas(new ArrayList<EmpresaConveniada>());
			getEmpresas().add(ec);
			getFiltro().setEmpresaConveniada(ec);
			getFiltro().setIdEmpresaConveniada(getUsuarioTele().getIdEmpresaConveniada());
		}
    	setCorridaDataModel(new CorridaTeleDataModel(corridaService, filtro));
    }
    
    /**
     * Método responsável por limpar formulário e resultados da pesquisa.
     */
    public void limparFormulario() {
    	
    	setFiltro(null);
    	setCorridaDataModel(null);
    }

    // Métodos get/set
	public Corrida getCorrida() {
		if (corrida == null) {
			corrida = new Corrida();
		}
		return corrida;
	}

	public void setCorrida(Corrida corrida) {
		this.corrida = corrida;
	}
	public Corrida getCorridaNova() {
		if (corridaNova == null) {
			corridaNova = new Corrida();
		}
		return corridaNova;
	}

	public void setCorridaNova(Corrida corridaNova) {
		this.corridaNova = corridaNova;
	}

	public Corrida getFiltro() {
		if (filtro == null) {
			filtro = new Corrida();
			filtro.setMotorista(new Motorista());
			filtro.setUsuario(new Usuario());
		}
		return filtro;
	}

	public void setFiltro(Corrida filtro) {
		this.filtro = filtro;
	}

	public CorridaTeleDataModel getCorridaDataModel() {
		return corridaDataModel;
	}

	public void setCorridaDataModel(CorridaTeleDataModel corridaDataModel) {
		this.corridaDataModel = corridaDataModel;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}
	
	public List<StatusCorridaEnum> getListaStatusCorrida() {
		return Arrays.asList(StatusCorridaEnum.values());
	}

	public List<Percurso> getListaPercurso() {
		return listaPercurso;
	}

	public void setListaPercurso(List<Percurso> listaPercurso) {
		this.listaPercurso = listaPercurso;
	}

	public Usuario getUsuarioTele() {
		return usuarioTele;
	}

	public void setUsuarioTele(Usuario usuarioTele) {
		this.usuarioTele = usuarioTele;
	}

	public List<EmpresaConveniada> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<EmpresaConveniada> empresas) {
		this.empresas = empresas;
	}

	public EmpresaConveniada getEmpresaConveniada() {
		if (empresaConveniada == null) {
			empresaConveniada = new EmpresaConveniada();
		}
		return empresaConveniada;
	}

	public void setEmpresaConveniada(EmpresaConveniada empresaConveniada) {
		this.empresaConveniada = empresaConveniada;
	}
	public String getImagemQrCode() {
		return imagemQrCode;
	}
	public void setImagemQrCode(String imagemQrCode) {
		this.imagemQrCode = imagemQrCode;
	}
	public ClienteTeleTaxi getCliente() {
		return cliente;
	}
	public void setCliente(ClienteTeleTaxi cliente) {
		this.cliente = cliente;
	}
	public String getCidadeMapa() {
		return cidadeMapa;
	}
	public void setCidadeMapa(String cidadeMapa) {
		this.cidadeMapa = cidadeMapa;
	}
	public List<MensagemCorridaVO> getMensagensChat() {
		return mensagensChat;
	}
	public void setMensagensChat(List<MensagemCorridaVO> mensagensChat) {
		this.mensagensChat = mensagensChat;
	}
	public String getMensagemChat() {
		return mensagemChat;
	}
	public void setMensagemChat(String mensagemChat) {
		this.mensagemChat = mensagemChat;
	}
	public Integer getQtdCorridasMes() {
		return qtdCorridasMes;
	}
	public void setQtdCorridasMes(Integer qtdCorridasMes) {
		this.qtdCorridasMes = qtdCorridasMes;
	}
	public Integer getQtdCorridas24Horas() {
		return qtdCorridas24Horas;
	}
	public void setQtdCorridas24Horas(Integer qtdCorridas24Horas) {
		this.qtdCorridas24Horas = qtdCorridas24Horas;
	}
	public Integer getQtdCorridasHoje() {
		return qtdCorridasHoje;
	}
	public void setQtdCorridasHoje(Integer qtdCorridasHoje) {
		this.qtdCorridasHoje = qtdCorridasHoje;
	}
	public Integer getQtdCorridasManha() {
		return qtdCorridasManha;
	}
	public void setQtdCorridasManha(Integer qtdCorridasManha) {
		this.qtdCorridasManha = qtdCorridasManha;
	}
	public Integer getQtdCorridasTarde() {
		return qtdCorridasTarde;
	}
	public void setQtdCorridasTarde(Integer qtdCorridasTarde) {
		this.qtdCorridasTarde = qtdCorridasTarde;
	}
	public Integer getQtdCorridasNoite() {
		return qtdCorridasNoite;
	}
	public void setQtdCorridasNoite(Integer qtdCorridasNoite) {
		this.qtdCorridasNoite = qtdCorridasNoite;
	}
	public List<VisualizacaoCorrida> getVisualizacoes() {
		return visualizacoes;
	}
	public void setVisualizacoes(List<VisualizacaoCorrida> visualizacoes) {
		this.visualizacoes = visualizacoes;
	}
	public Integer getQtdCorridasMadrugada() {
		return qtdCorridasMadrugada;
	}
	public void setQtdCorridasMadrugada(Integer qtdCorridasMadrugada) {
		this.qtdCorridasMadrugada = qtdCorridasMadrugada;
	}
	public Integer getQtdCorridasCanceladasHoje() {
		return qtdCorridasCanceladasHoje;
	}
	public void setQtdCorridasCanceladasHoje(Integer qtdCorridasCanceladasHoje) {
		this.qtdCorridasCanceladasHoje = qtdCorridasCanceladasHoje;
	}
	public Integer getQtdCorridasSolicitadas() {
		return qtdCorridasSolicitadas;
	}
	public void setQtdCorridasSolicitadas(Integer qtdCorridasSolicitadas) {
		this.qtdCorridasSolicitadas = qtdCorridasSolicitadas;
	}
	public Integer getQtdCorridasAndamento() {
		return qtdCorridasAndamento;
	}
	public void setQtdCorridasAndamento(Integer qtdCorridasAndamento) {
		this.qtdCorridasAndamento = qtdCorridasAndamento;
	}
	public Integer getTipoCorrida() {
		return tipoCorrida;
	}
	public void setTipoCorrida(Integer tipoCorrida) {
		this.tipoCorrida = tipoCorrida;
	}
	public List<Localidade> getLocalidades() {
		return localidades;
	}
	public void setLocalidades(List<Localidade> localidades) {
		this.localidades = localidades;
	}
	public Localidade getRegiao() {
		return regiao;
	}
	public void setRegiao(Localidade regiao) {
		this.regiao = regiao;
	}
	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}
	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
	public List<CentroCusto> getCentros() {
		return centros;
	}
	public void setCentros(List<CentroCusto> centros) {
		this.centros = centros;
	}
	
}