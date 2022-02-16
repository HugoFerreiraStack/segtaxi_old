/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.enums.FormaPagamentoEnum;
import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.ClienteTeleTaxi;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.LocalFavorito;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;
import br.com.seg.econotaxi.model.Voucher;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.DescontoLojistaService;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.FamiliaService;
import br.com.seg.econotaxi.service.LocalFavoritoService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.PercursoService;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.util.MapaCarroUtil;
import br.com.seg.econotaxi.util.MapaCidadeUtil;
import br.com.seg.econotaxi.util.MapaCorridaChatUtil;
import br.com.seg.econotaxi.util.MapaCorridaUtil;
import br.com.seg.econotaxi.util.MapaEntregaUtil;
import br.com.seg.econotaxi.util.MapaMensagensUtil;
import br.com.seg.econotaxi.util.MapaMotoristaBloqueadoUtil;
import br.com.seg.econotaxi.util.MapaPromocaoUtil;
import br.com.seg.econotaxi.util.SpringContextUtil;
import br.com.seg.econotaxi.vo.CarrosVO;
import br.com.seg.econotaxi.vo.CorridaCompletaVO;
import br.com.seg.econotaxi.vo.CorridaVO;
import br.com.seg.econotaxi.vo.MensagemCorridaVO;
import br.com.seg.econotaxi.vo.MinhaCorridaVo;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/corrida")
public class CorridaResource {

	private static final String CORRIDA_JOGADA_LIVRE = "Corrida jogada no livre.";
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private PercursoService percursoService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MotoristaService motoristaService;
	@Autowired
	private LocalFavoritoService localFavoritoService;
	@Autowired
	private FamiliaService familiaService;
	@Autowired
	private UsuarioNotificacaoService usuarioNotificacaoService;
	@Autowired
	private VeiculoService veiculoService;
	@Autowired
	private DescontoLojistaService descontoLojistaService;
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	
	//@Autowired
	//private SimpMessagingTemplate simpMessagingTemplate;

	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/minhasCorridas", method = RequestMethod.POST)
	public List<Corrida> listarMinhasCorridas(@RequestBody Usuario usuario) {
		
		return corridaService.recuperarCorridasPorUsuario(usuario, TipoCorridaEnum.CORRIDA.getCodigo());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/minhasCorridasPaginadas", method = RequestMethod.POST)
	public List<MinhaCorridaVo> listarMinhasCorridasPaganidas(@RequestBody Usuario usuario) {
		
		return corridaService.recuperarCorridasPorUsuarioPaginada(usuario, TipoCorridaEnum.CORRIDA.getCodigo(), usuario.getPaginacao());
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/historicoMotorista", method = RequestMethod.POST)
	public List<Corrida> listarCorridasMotorista(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		if (usuario.getMotorista() == null || usuario.getMotorista().getId() == null) {
			user.setMotorista(motoristaService.recuperarMotoristaPorUsuario(user));
		} else {
			user.setMotorista(usuario.getMotorista());
		}
		user.setMesAno(usuario.getMesAno());
		return corridaService.recuperarCorridasPorMotorista(user, TipoCorridaEnum.CORRIDA.getCodigo());
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/historicoMotoristaEntrega", method = RequestMethod.POST)
	public List<Corrida> listarEntregasMotorista(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		if (usuario.getMotorista() == null || usuario.getMotorista().getId() == null) {
			user.setMotorista(motoristaService.recuperarMotoristaPorUsuario(user));
		} else {
			user.setMotorista(usuario.getMotorista());
		}
		user.setMesAno(usuario.getMesAno());
		return corridaService.recuperarCorridasPorMotorista(user, TipoCorridaEnum.ENTREGA.getCodigo());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/solicitadas", method = RequestMethod.POST)
	public List<CorridaCompletaVO> listarSolicitadas(@RequestBody Motorista motorista) {
		
		List<Corrida> listaCorridas = new ArrayList<Corrida>();
		Corrida corridaCorrente = MapaCorridaUtil.recuperarCorridaPendenteMotorista(motorista.getId(), 
				motorista.getCidade().getId());
		
		if (corridaCorrente != null && corridaCorrente.getId() != null 
				&& !corridaCorrente.getStatus().equals(StatusCorridaEnum.FINALIZADO.getStatus())) {
			//System.out.println("corrida em andamento motorista (id) : " + motorista.getId());
		} else {
			
			List<Corrida> corridasSelecionadas = null;
			List<Corrida> corridasSelecionadasTele = null;
			if (motorista.getStatus().equals(StatusMotoristaEnum.ATIVO.getStatus()) 
					&& motorista.getVeiculoCorrente() != null
					&& motorista.getVeiculoCorrente().getStatus().equals(StatusVeiculoEnum.ATIVO.getStatus())) {
				
				List<Corrida> corridas = MapaCorridaUtil.recuperarCorridasDisponiveis(motorista.getCidade().getId());
				corridasSelecionadas = new ArrayList<Corrida>();
				
				// Corridas do App
				corridasSelecionadas = corridasAppTaxi(motorista, corridasSelecionadas, corridas);
				
				// Corridas Tele-Táxi e Ligue-Táxi
				corridasSelecionadasTele = corridasTeleTaxi(motorista, corridasSelecionadasTele, corridas);
				
			}
			
			if (corridasSelecionadasTele != null && !corridasSelecionadasTele.isEmpty()) {
				listaCorridas.addAll(corridasSelecionadasTele);
			}
			if (corridasSelecionadas != null && !corridasSelecionadas.isEmpty()) {
				listaCorridas.addAll(corridasSelecionadas);
			}
		}
		
		List<CorridaCompletaVO> corridasVO = null;
		if (listaCorridas != null && !listaCorridas.isEmpty()) {
			corridasVO = new ArrayList<CorridaCompletaVO>();
			for (Corrida corrida : listaCorridas) {
				CorridaCompletaVO corridaVO = new CorridaCompletaVO();
				BeanUtils.copyProperties(corrida, corridaVO, "veiculo", "cidade", "usuario");
				if (corrida.getCidade() != null && corrida.getCidade().getId() != null) {
					corridaVO.setCidade(new Cidade());
					corridaVO.getCidade().setId(corrida.getCidade().getId());
					corridaVO.getCidade().setNome(corrida.getCidade().getNome());
				}
				if (corrida.getUsuario() != null && corrida.getUsuario().getId() != null) {
					corridaVO.setUsuario(new Usuario());
					corridaVO.getUsuario().setId(corrida.getUsuario().getId());
					corridaVO.getUsuario().setNome(corrida.getUsuario().getNome());
				}
				corridasVO.add(corridaVO);
			}
		}
		return corridasVO;
	}

	/**
	 * Corridas Seg App
	 * 
	 * @param motorista
	 * @param corridasSelecionadas
	 * @param corridas
	 * @return
	 */
	private List<Corrida> corridasAppTaxi(Motorista motorista, List<Corrida> corridasSelecionadas, List<Corrida> corridas) {
		
		if (corridas != null && !corridas.isEmpty()) {
		
			CarrosVO carro2 = MapaCarroUtil.recuperarCarroMotorista(motorista.getCidade(), motorista.getId());
			
			List<Corrida> cList = corridas.stream()
					.filter(corrida -> corrida != null)
					.filter(corrida -> !corrida.getUsuario().getId().equals(motorista.getIdUsuario()))
					.filter(corrida -> corrida.getTipo().equals(motorista.getVeiculoCorrente().getTipo()))
					.filter(corrida -> (corrida.getIndicadorTeleTaxi() == null || !corrida.getIndicadorTeleTaxi().equals(1)))
					.filter(corrida -> 
					((corrida.getCarrosRecusados() == null || corrida.getCarrosRecusados().isEmpty())
							|| !corrida.getCarrosRecusados().contains(carro2)))
					.collect(Collectors.toList());
			
			corridasSelecionadas = cList;
			
			if (motorista.getVeiculoCorrente().getIndicadorBicicleta() == null
					|| !motorista.getVeiculoCorrente().getIndicadorBicicleta().equals(1)) {
				
				List<Corrida> cListNaoBike = corridasSelecionadas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getIndicadorBicicleta() == null || !corrida.getIndicadorBicicleta().equals(1))
						.collect(Collectors.toList());
				
				corridasSelecionadas = cListNaoBike;
			}
			
			if (motorista.getVeiculoCorrente().getIndicadorCadeirinha() == null
					|| !motorista.getVeiculoCorrente().getIndicadorCadeirinha().equals(1)) {
				
				List<Corrida> cListNaoCadeirinha = corridasSelecionadas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getIndicadorCadeirinha() == null || !corrida.getIndicadorCadeirinha().equals(1))
						.collect(Collectors.toList());
				
				corridasSelecionadas = cListNaoCadeirinha;
			}
			
			if (motorista.getIndicadorMaquinaDebito() == null
					|| !motorista.getIndicadorMaquinaDebito().equals(1)) {
				
				List<Corrida> cListNaoDebito = corridasSelecionadas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getFormaPagamento() == null || !corrida.getFormaPagamento().equals(
								FormaPagamentoEnum.DEBITO.getCodigo()))
						.collect(Collectors.toList());
				
				corridasSelecionadas = cListNaoDebito;
			}
			
			if (motorista.getVeiculoCorrente().getIndicadorAdaptado() == null
					|| !motorista.getVeiculoCorrente().getIndicadorAdaptado().equals(1)) {
				
				List<Corrida> cListNaoAdaptados = corridasSelecionadas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getIndicadorAdaptado() == null || !corrida.getIndicadorAdaptado().equals(1))
						.collect(Collectors.toList());
				
				corridasSelecionadas = cListNaoAdaptados;
				
			}
			
			if (motorista.getSexo() == null || (motorista.getSexo() != null && motorista.getSexo().equals("M"))) {
				
				List<Corrida> cListNaoRosa = corridasSelecionadas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getSexoMotorista() == null || corrida.getSexoMotorista().isEmpty())
						.collect(Collectors.toList());
				
				corridasSelecionadas = cListNaoRosa;
			}
			
			
			List<Corrida> corridasProximas = new ArrayList<Corrida>();
			for (Corrida cProxima : corridasSelecionadas) {
				
				Calendar cAtual = Calendar.getInstance();
				cAtual.setTime(new Date());
				Calendar cCorrida = Calendar.getInstance();
				if (cProxima.getDataRecuperacao() != null) {
					cCorrida.setTime(cProxima.getDataRecuperacao());
				} else {
					cCorrida.setTime(cProxima.getDataSolicitacao());
				}
				long segundos = ((cAtual.getTimeInMillis() - cCorrida.getTimeInMillis())) / 1000;
				
				int segCarrosProximos = ((cProxima.getCarrosProximos() != null ? cProxima.getCarrosProximos().size() : 0) * 20);
				
				if (segundos > (segCarrosProximos + 10)) {
					
					cProxima.setObservacoes(CORRIDA_JOGADA_LIVRE);
					corridasProximas.add(cProxima);
					visualizouCorrida(cProxima, carro2, motorista, Boolean.FALSE);
					
				} else {
					
					List<CarrosVO> carros = cProxima.getCarrosProximos();
					
					if (carros != null && !carros.isEmpty()) {
						for (int i = 0; i < carros.size(); i++) {
							int segCarro = (i * 20);
							if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
									&& carros.get(i).getIdMotorista().equals(motorista.getId())) {
								cProxima.setObservacoes("Corrida exibida dentro do raio.");
								corridasProximas.add(cProxima);
								visualizouCorrida(cProxima, carros.get(i), motorista, Boolean.FALSE);
								break;
							}
						}
					}
					
				}
				
			}
			corridasSelecionadas = corridasProximas;
		}
		return corridasSelecionadas;
	}

	/**
	 * Corridas tele-táxi e ligue-táxi
	 * 
	 * @param motorista
	 * @param corridasSelecionadasTele
	 * @param corridas
	 * @return
	 */
	private List<Corrida> corridasTeleTaxi(Motorista motorista, List<Corrida> corridasSelecionadasTele, List<Corrida> corridas) {
		
		if (motorista.getTipoTeleTaxi() != null && !motorista.getTipoTeleTaxi().equals(0)) {
			
			if (corridas != null && !corridas.isEmpty()) {
				
				CarrosVO carro = MapaCarroUtil.recuperarCarroMotorista(motorista.getCidade(), motorista.getId());
				
				corridasSelecionadasTele = new ArrayList<Corrida>();
				List<Corrida> cList = corridas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> !corrida.getUsuario().getId().equals(motorista.getIdUsuario()))
						.filter(corrida -> corrida.getTipo().equals(motorista.getVeiculoCorrente().getTipo()))
						.filter(corrida -> (corrida.getIndicadorTeleTaxi() != null && corrida.getIndicadorTeleTaxi().equals(1)))
						.filter(corrida -> 
							((corrida.getCarrosRecusados() == null || corrida.getCarrosRecusados().isEmpty())
							|| !corrida.getCarrosRecusados().contains(carro)))
						.collect(Collectors.toList());
				
				if (cList != null && !cList.isEmpty()) {
					
					corridasSelecionadasTele = cList;
					List<Corrida> cRadio = cList.stream()
							.filter(corrida -> corrida != null)
							.filter(corrida -> corrida.getTipoTeleTaxi() != null 
								&& (corrida.getTipoTeleTaxi().equals(motorista.getTipoTeleTaxi()) || motorista.getTipoTeleTaxi().equals(3)))
							.collect(Collectors.toList());
					corridasSelecionadasTele = cRadio;
					
					List<Corrida> cRadioApp = cList.stream()
							.filter(corrida -> corrida != null)
							.filter(corrida -> corrida.getTipoTeleTaxi() != null && corrida.getTipoTeleTaxi().equals(99))
							.collect(Collectors.toList());
					
					if (cRadioApp != null && !cRadioApp.isEmpty()) {
						if (corridasSelecionadasTele != null && !corridasSelecionadasTele.isEmpty()) {
							if (cRadioApp != null && !cRadioApp.isEmpty()) {
								corridasSelecionadasTele.addAll(cRadioApp);
							}
						} else {
							corridasSelecionadasTele = cRadioApp;
						}
					}
					
				}
				
				List<Corrida> corridasPA = new ArrayList<Corrida>();
				if (corridasSelecionadasTele != null && !corridasSelecionadasTele.isEmpty()
						&& corridasSelecionadasTele.size() > 0) {
					
					if (carro != null && carro.getDataUltimaPosicao().getTime() > recuperaData3Minutos(new Date()).getTime()) {
						if (motorista.getIndicadorMaquinaDebito() == null
								|| !motorista.getIndicadorMaquinaDebito().equals(1)) {
							
							List<Corrida> cListNaoDebito = corridasSelecionadasTele.stream()
									.filter(corrida -> corrida != null)
									.filter(corrida -> corrida.getFormaPagamento() == null || !corrida.getFormaPagamento().equals(
											FormaPagamentoEnum.DEBITO.getCodigo()))
									.collect(Collectors.toList());
							corridasSelecionadasTele = cListNaoDebito;
						}
						
						if (motorista.getVeiculoCorrente().getIndicadorAdaptado() == null
								|| !motorista.getVeiculoCorrente().getIndicadorAdaptado().equals(1)) {
							
							List<Corrida> cListNaoAdaptados = corridasSelecionadasTele.stream()
									.filter(corrida -> corrida != null)
									.filter(corrida -> corrida.getIndicadorAdaptado() == null || !corrida.getIndicadorAdaptado().equals(1))
									.collect(Collectors.toList());
							
							corridasSelecionadasTele = cListNaoAdaptados;
						}
						
						if (motorista.getIndicadorAceitaVoucher() == null
								|| !motorista.getIndicadorAceitaVoucher().equals(1)) {
							
							List<Corrida> cListVoucher = corridasSelecionadasTele.stream()
									.filter(corrida -> corrida != null)
									.filter(corrida -> corrida.getVoucher() == null || corrida.getVoucher().isEmpty())
									.collect(Collectors.toList());
							corridasSelecionadasTele = cListVoucher;
						}
						
						for (Corrida cTeleFinal : corridasSelecionadasTele) {
							
							if (cTeleFinal.getIdMotoristaRecuperacao() == null 
									|| !cTeleFinal.getIdMotoristaRecuperacao().equals(motorista.getId())) {
								
								Calendar cAtual = Calendar.getInstance();
								cAtual.setTime(new Date());
								Calendar cCorrida = Calendar.getInstance();
								if (cTeleFinal.getDataRecuperacao() != null) {
									cCorrida.setTime(cTeleFinal.getDataRecuperacao());
								} else {
									cCorrida.setTime(cTeleFinal.getDataSolicitacao());
								}
								long segundos = ((cAtual.getTimeInMillis() - cCorrida.getTimeInMillis())) / 1000;
								if (cTeleFinal.getIndicadorCarrosSelecionados() != null && cTeleFinal.getIndicadorCarrosSelecionados().equals(1)
										&& ((cTeleFinal.getCarrosSelecionados() != null && !cTeleFinal.getCarrosSelecionados().isEmpty())
										|| (cTeleFinal.getCarrosSelecionadosLivres() != null && !cTeleFinal.getCarrosSelecionadosLivres().isEmpty()))) {
									System.out.println("vai verificar corrida apenas selecionados!!!!");
									verificarCorridaComSelecionadas(motorista, carro, corridasPA, cTeleFinal, segundos);
								} else {
									verificarCorridaSemSelecionadas(motorista, carro, corridasPA, cTeleFinal, segundos);
								}
								
							}
							corridasSelecionadasTele = corridasPA;
						}
							
					} else {
						corridasSelecionadasTele = null;
					}
				}
			}
		}
		return corridasSelecionadasTele;
	}

	private void verificarCorridaComSelecionadas(Motorista motorista, CarrosVO carro, List<Corrida> corridasPA, Corrida cTeleFinal,
			long segundos) {
		
		List<CarrosVO> cars = new ArrayList<CarrosVO>();
		List<CarrosVO> carsPA = new ArrayList<CarrosVO>();
		if (cTeleFinal.getPontoApoio1() != null && cTeleFinal.getPontoApoio1().getCarros() != null
				&& !cTeleFinal.getPontoApoio1().getCarros().isEmpty()) {
			System.out.println("adicionou carro selecionado pa 1!!!! fora do for");
			if (cTeleFinal.getCarrosSelecionados() != null && !cTeleFinal.getCarrosSelecionados().isEmpty()) {
				for (CarrosVO car : cTeleFinal.getCarrosSelecionados()) {
					Optional<CarrosVO> carP = cTeleFinal.getPontoApoio1().getCarros().stream().filter(
							c -> c.getIdMotorista().equals(car.getIdMotorista())).findFirst();
					if (carP != null && carP.isPresent()) {
						System.out.println("adicionou carro selecionado pa 1!!!!");
						cars.add(carP.get());
					}
				}
			}
			carsPA.addAll(cTeleFinal.getPontoApoio1().getCarros());
		}
		if (cTeleFinal.getPontoApoio2() != null && cTeleFinal.getPontoApoio2().getCarros() != null
				&& !cTeleFinal.getPontoApoio2().getCarros().isEmpty()) {
			System.out.println("adicionou carro selecionado pa 2!!!! fora do for");
			if (cTeleFinal.getCarrosSelecionados() != null && !cTeleFinal.getCarrosSelecionados().isEmpty()) {
				for (CarrosVO car : cTeleFinal.getCarrosSelecionados()) {
					Optional<CarrosVO> carP = cTeleFinal.getPontoApoio2().getCarros().stream().filter(
							c -> c.getIdMotorista().equals(car.getIdMotorista())).findFirst();
					if (carP != null && carP.isPresent()) {
						System.out.println("adicionou carro selecionado pa 2!!!!");
						cars.add(carP.get());
					}
				}
			}
			carsPA.addAll(cTeleFinal.getPontoApoio2().getCarros());
		}
		if (cTeleFinal.getPontoApoio3() != null && cTeleFinal.getPontoApoio3().getCarros() != null
				&& !cTeleFinal.getPontoApoio3().getCarros().isEmpty()) {
			System.out.println("adicionou carro selecionado pa 3!!!! fora do for");
			if (cTeleFinal.getCarrosSelecionados() != null && !cTeleFinal.getCarrosSelecionados().isEmpty()) {
				for (CarrosVO car : cTeleFinal.getCarrosSelecionados()) {
					Optional<CarrosVO> carP = cTeleFinal.getPontoApoio3().getCarros().stream().filter(
							c -> c.getIdMotorista().equals(car.getIdMotorista())).findFirst();
					if (carP != null && carP.isPresent()) {
						System.out.println("adicionou carro selecionado pa 3!!!!");
						cars.add(carP.get());
					}
				}
			}
			carsPA.addAll(cTeleFinal.getPontoApoio3().getCarros());
		}
		if (cTeleFinal.getPontoApoio4() != null && cTeleFinal.getPontoApoio4().getCarros() != null
				&& !cTeleFinal.getPontoApoio4().getCarros().isEmpty()) {
			System.out.println("adicionou carro selecionado pa 4!!!! fora do for");
			if (cTeleFinal.getCarrosSelecionados() != null && !cTeleFinal.getCarrosSelecionados().isEmpty()) {
				for (CarrosVO car : cTeleFinal.getCarrosSelecionados()) {
					Optional<CarrosVO> carP = cTeleFinal.getPontoApoio4().getCarros().stream().filter(
							c -> c.getIdMotorista().equals(car.getIdMotorista())).findFirst();
					if (carP != null && carP.isPresent()) {
						System.out.println("adicionou carro selecionado pa 4!!!!");
						cars.add(carP.get());
					}
				}
			}
			carsPA.addAll(cTeleFinal.getPontoApoio4().getCarros());
		}
		
		int segCarrosSelecionadosPA = ((cars != null ? cars.size() : 0) * 20);
		System.out.println("segCarrosSelecionadosPA: " + segCarrosSelecionadosPA);
		int segCarrosSelecionadosProximos = 
				((cTeleFinal.getCarrosSelecionados() != null ? cTeleFinal.getCarrosSelecionados().size() : 0) * 20) + (segCarrosSelecionadosPA > 0 ? segCarrosSelecionadosPA + 10 : segCarrosSelecionadosPA);
		System.out.println("segCarrosSelecionadosProximos: " + segCarrosSelecionadosProximos);
		int segCarrosSelecionadosLivre = segCarrosSelecionadosProximos + 20;
		System.out.println("segCarrosSelecionadosLivre: " + segCarrosSelecionadosLivre);
		int segCarrosPA = ((carsPA != null ? carsPA.size() : 0) * 20) + segCarrosSelecionadosLivre;
		System.out.println("segCarrosPA: " + segCarrosPA);
		int segCarrosProximos = 
				((cTeleFinal.getCarrosProximos() != null ? cTeleFinal.getCarrosProximos().size() : 0) * 20) + segCarrosPA;
		System.out.println("segCarrosProximos: " + segCarrosProximos);
		
		if (cars != null && !cars.isEmpty()) {
			for (CarrosVO carrosVO : cars) {
				System.out.println("carro selecionado pa: " + carrosVO.getPlaca());
			}
		}
		
		if (cTeleFinal.getCarrosSelecionados() != null && !cTeleFinal.getCarrosSelecionados().isEmpty()) {
			for (CarrosVO carrosVO : cTeleFinal.getCarrosSelecionados()) {
				System.out.println("carro selecionado: " + carrosVO.getPlaca());
			}
		}
		
		if (cTeleFinal.getCarrosSelecionadosLivres() != null && !cTeleFinal.getCarrosSelecionadosLivres().isEmpty()) {
			for (CarrosVO carrosVO : cTeleFinal.getCarrosSelecionadosLivres()) {
				System.out.println("carro selecionado livre: " + carrosVO.getPlaca());
			}
		}
		
		System.out.println("vai começar a verificar tempo. segundos: " + segundos);
		if (segundos > segCarrosProximos) {
			System.out.println("achou carro!");
			System.out.println(CORRIDA_JOGADA_LIVRE);
			cTeleFinal.setObservacoes(CORRIDA_JOGADA_LIVRE);
			corridasPA.add(cTeleFinal);
			visualizouCorrida(cTeleFinal, carro, motorista, Boolean.FALSE);
			
		} else if (segundos > segCarrosPA) {
			
			System.out.println("Corrida exibida dentro do raio. Ninguém pegou no PA. fora do for");
			List<CarrosVO> carros = cTeleFinal.getCarrosProximos();
			
			if (carros != null && !carros.isEmpty()) {
				for (int i = 0; i < carros.size(); i++) {
					int segCarro = (i * 20) + segCarrosPA;
					if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
							&& carros.get(i).getIdMotorista().equals(motorista.getId())) {
						System.out.println("achou carro!");
						System.out.println("Corrida exibida dentro do raio. Ninguém pegou no PA.");
						cTeleFinal.setObservacoes("Corrida exibida dentro do raio. Ninguém pegou no PA.");
						corridasPA.add(cTeleFinal);
						visualizouCorrida(cTeleFinal, carros.get(i), motorista, Boolean.FALSE);
						break;
					}
				}
			}
			
		} else if (segundos > segCarrosSelecionadosLivre) {
			
			System.out.println("Corrida exibida dentro do PA. fora do for");
			for (int i = 0; i < carsPA.size(); i++) {
				int segCarro = (i * 20) + segCarrosSelecionadosLivre;
				System.out.println("segundos dentro: " + segCarro);
				if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
						&& carsPA.get(i).getIdMotorista().equals(motorista.getId())) {
					System.out.println("Corrida exibida dentro do PA.");
					cTeleFinal.setObservacoes("Corrida exibida dentro do PA.");
					System.out.println("achou carro!");
					corridasPA.add(cTeleFinal);
					visualizouCorrida(cTeleFinal, carsPA.get(i), motorista, Boolean.TRUE);
					break;
				}
			}
			
		} else if (segundos > segCarrosSelecionadosProximos) {
			
			System.out.println("Corrida jogada no livre (!).");
			cTeleFinal.setObservacoes("Corrida jogada no livre (!).");
			List<CarrosVO> carros = cTeleFinal.getCarrosSelecionadosLivres();
			if (carros != null && !carros.isEmpty()) {
				if (carros.contains(carro)) {
					System.out.println("achou carro!");
					corridasPA.add(cTeleFinal);
					visualizouCorrida(cTeleFinal, carro, motorista, Boolean.FALSE);
				}
			}
			
		} else if (segundos > segCarrosSelecionadosPA) {
			
			System.out.println("Corrida exibida dentro do raio (!). Ninguém pegou no PA. fora do for");
			List<CarrosVO> carros = cTeleFinal.getCarrosSelecionados();
			
			if (carros != null && !carros.isEmpty()) {
				for (int i = 0; i < carros.size(); i++) {
					int segCarro = (i * 20) + segCarrosSelecionadosPA;
					System.out.println("segundos dentro: " + segCarro);
					if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
							&& carros.get(i).getIdMotorista().equals(motorista.getId())) {
						System.out.println("Corrida exibida dentro do raio (!). Ninguém pegou no PA.");
						cTeleFinal.setObservacoes("Corrida exibida dentro do raio (!). Ninguém pegou no PA.");
						System.out.println("achou carro!");
						corridasPA.add(cTeleFinal);
						visualizouCorrida(cTeleFinal, carros.get(i), motorista, Boolean.FALSE);
						break;
					}
				}
			}
			
		} else {
			System.out.println("Corrida exibida dentro do PA (!) fora do for.");
			for (int i = 0; i < cars.size(); i++) {
				int segCarro = i * 20;
				System.out.println("segundos dentro: " + segCarro);
				if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
						&& cars.get(i).getIdMotorista().equals(motorista.getId())) {
					System.out.println("Corrida exibida dentro do PA (!).");
					cTeleFinal.setObservacoes("Corrida exibida dentro do PA (!).");
					System.out.println("achou carro!");
					corridasPA.add(cTeleFinal);
					visualizouCorrida(cTeleFinal, cars.get(i), motorista, Boolean.TRUE);
					break;
				}
			}
		}
	}

	private void verificarCorridaSemSelecionadas(Motorista motorista, CarrosVO carro, List<Corrida> corridasPA, Corrida cTeleFinal,
			long segundos) {
		
		List<CarrosVO> cars = new ArrayList<CarrosVO>();
		if (cTeleFinal.getPontoApoio1() != null && cTeleFinal.getPontoApoio1().getCarros() != null
				&& !cTeleFinal.getPontoApoio1().getCarros().isEmpty()) {
			cars.addAll(cTeleFinal.getPontoApoio1().getCarros());
		}
		if (cTeleFinal.getPontoApoio2() != null && cTeleFinal.getPontoApoio2().getCarros() != null
				&& !cTeleFinal.getPontoApoio2().getCarros().isEmpty()) {
			cars.addAll(cTeleFinal.getPontoApoio2().getCarros());
		}
		if (cTeleFinal.getPontoApoio3() != null && cTeleFinal.getPontoApoio3().getCarros() != null
				&& !cTeleFinal.getPontoApoio3().getCarros().isEmpty()) {
			cars.addAll(cTeleFinal.getPontoApoio3().getCarros());
		}
		if (cTeleFinal.getPontoApoio4() != null && cTeleFinal.getPontoApoio4().getCarros() != null
				&& !cTeleFinal.getPontoApoio4().getCarros().isEmpty()) {
			cars.addAll(cTeleFinal.getPontoApoio4().getCarros());
		}
		
		int segCarrosPA = ((cars != null ? cars.size() : 0) * 20);
		int segCarrosProximos = ((cTeleFinal.getCarrosProximos() != null ? cTeleFinal.getCarrosProximos().size() : 0) * 20);
		
		if (segundos > (segCarrosPA + segCarrosProximos + 10)) {
			
			cTeleFinal.setObservacoes(CORRIDA_JOGADA_LIVRE);
			corridasPA.add(cTeleFinal);
			visualizouCorrida(cTeleFinal, carro, motorista, Boolean.FALSE);
			
		} else if (segundos > segCarrosPA) {
			
			List<CarrosVO> carros = cTeleFinal.getCarrosProximos();
			
			if (carros != null && !carros.isEmpty()) {
				for (int i = 0; i < carros.size(); i++) {
					int segCarro = (i * 20) + segCarrosPA;
					if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
							&& carros.get(i).getIdMotorista().equals(motorista.getId())) {
						cTeleFinal.setObservacoes("Corrida exibida dentro do raio. Ninguém pegou no PA.");
						corridasPA.add(cTeleFinal);
						visualizouCorrida(cTeleFinal, carros.get(i), motorista, Boolean.FALSE);
						break;
					}
				}
			}
			
		} else {
			
			for (int i = 0; i < cars.size(); i++) {
				int segCarro = i * 20;
				if (segundos >= (segCarro == 0 ? segCarro : segCarro + 10) && segundos <= (segCarro + 20)
						&& cars.get(i).getIdMotorista().equals(motorista.getId())) {
					cTeleFinal.setObservacoes("Corrida exibida dentro do PA.");
					corridasPA.add(cTeleFinal);
					visualizouCorrida(cTeleFinal, cars.get(i), motorista, Boolean.TRUE);
					break;
				}
			}
			
		}
	}
	
	private Date recuperaData3Minutos(Date dataCorrente) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -3);
		return c.getTime();
	}
	
	private void visualizouCorrida(Corrida corrida, CarrosVO carro, Motorista motorista, Boolean dentroPA) {
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				VisualizacaoCorrida visualizacaoCorrida = new VisualizacaoCorrida();
				if (carro.getIdPontoApoio() != null) {
					
					if (dentroPA) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(motorista.getCidade(), carro.getIdPontoApoio());
						if (carros != null && !carros.isEmpty()) {
							for (int i = 0; i < carros.size(); i++) {
								if (motorista.getId().equals(carros.get(i).getIdMotorista())) {
									int posicao = i + 1;
									visualizacaoCorrida.setPontoApoio(carro.getNomePontoApoio() + " (" + posicao + "º) ");
								}
							}
						}
					} else {
						visualizacaoCorrida.setPontoApoio(carro.getNomePontoApoio());
					}
					
					
				}
				visualizacaoCorrida.setIdCorrida(corrida.getId());
				visualizacaoCorrida.setIdMotorista(motorista.getId());
				visualizacaoCorrida.setNomeMotorista(carro.getNomeMotorista());
				visualizacaoCorrida.setPlacaVeiculo(carro.getPlaca());
				visualizacaoCorrida.setUnidadeVeiculo(carro.getUnidade());
				visualizacaoCorrida.setDataVisualizacao(new Date());
				visualizacaoCorrida.setLatitude(carro.getLatitude());
				visualizacaoCorrida.setLongitude(carro.getLongitude());
				visualizacaoCorrida.setDataEntrouPA(carro.getDataPontoApoio());
				visualizacaoCorrida.setDataUltimaPosicao(carro.getDataUltimaPosicao());
				corridaService.salvarVisualizacao(visualizacaoCorrida);
			}
		}).start();
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/solicitadasEntregas", method = RequestMethod.POST)
	public List<CorridaCompletaVO> listarSolicitadasEntregas(@RequestBody Motorista motorista) {
		
		List<Corrida> corridasSelecionadas = null;
		
		if (motorista.getStatus().equals(StatusMotoristaEnum.ATIVO.getStatus()) 
				&& motorista.getVeiculoCorrente() != null 
				&& motorista.getVeiculoCorrente().getStatus() != null
				&& motorista.getVeiculoCorrente().getStatus().equals(StatusVeiculoEnum.ATIVO.getStatus())) {
			
			List<Corrida> corridas = MapaEntregaUtil.recuperarEntregasDisponiveis(motorista.getCidade().getId());
			corridasSelecionadas = new ArrayList<Corrida>();
			if (corridas != null && !corridas.isEmpty()) {
				
				List<Corrida> cList = corridas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getTipo().equals(motorista.getVeiculoCorrente().getTipo()))
						.collect(Collectors.toList());
				
				corridasSelecionadas = cList;
			}
		}
		
		List<CorridaCompletaVO> corridasVO = null;
		if (corridasSelecionadas != null && !corridasSelecionadas.isEmpty()) {
			corridasVO = new ArrayList<CorridaCompletaVO>();
			for (Corrida corrida : corridasSelecionadas) {
				CorridaCompletaVO corridaVO = new CorridaCompletaVO();
				BeanUtils.copyProperties(corrida, corridaVO, "veiculo", "cidade");
				if (corrida.getCidade() != null && corrida.getCidade().getId() != null) {
					corridaVO.setCidade(new Cidade());
					corridaVO.getCidade().setId(corrida.getCidade().getId());
					corridaVO.getCidade().setNome(corrida.getCidade().getNome());
				}
				if (corrida.getUsuario() != null && corrida.getUsuario().getId() != null) {
					corridaVO.setUsuario(new Usuario());
					corridaVO.getUsuario().setId(corrida.getUsuario().getId());
					corridaVO.getUsuario().setNome(corrida.getUsuario().getNome());
				}
				corridasVO.add(corridaVO);
			}
		}
		return corridasVO;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/solicitarEmpresarial", method = RequestMethod.POST)
	public Corrida solicitarCorridaEmpresarial(@RequestBody Corrida corrida) throws Exception {
		
		Usuario usuario = usuarioService.recuperarUsuarioPorID(corrida.getUsuario().getId());
		if (usuario.getIndicadorBloqueio() != null && usuario.getIndicadorBloqueio().equals(1)) {
			
			throw new Exception("Você está bloqueado para realização de solicitações de corrida. Motivo: " + usuario.getMotivoBloqueio());
			
		} else {
			if (!MapaCorridaUtil.verificaExistenciaCorrida(corrida.getUsuario(), corrida.getCidade())) {
				
				if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
					
					String observacoes = corrida.getObservacoes();
					String observacaoTele = corrida.getObservacaoTele();
					corrida.setIndicadorTeleTaxi(1);
					corrida.setDataSolicitacao(new Date());
					corrida.setIndicadorCorridaFim(0);
					corrida.setIndicadorCorridaInicio(0);
					EmpresaConveniada ec = empresaConveniadaService.consultarPorChave(corrida.getIdEmpresaConveniada());
					corrida.setIndicadorCarrosSelecionados(ec.getIndicadorCarrosSelecionados());
					if (corrida.getUsuario() != null && corrida.getUsuario().getId() != null) {
						Usuario u = usuarioService.recuperarUsuarioPorID(corrida.getUsuario().getId());
						if (u != null && u.getIdCentroCusto() != null) {
							corrida.setIdCentroCusto(u.getIdCentroCusto());
						}
					}
					
					corridaService.salvarCorrida(corrida);
					
					Voucher voucher = new Voucher();
					voucher.setDataGeracao(new Date());
					voucher.setIdCorrida(corrida.getId());
					voucher.setVoucher(corrida.getVoucher());
					voucher.setIdEmpresaConveniada(corrida.getIdEmpresaConveniada());
					empresaConveniadaService.salvarVoucher(voucher);
					
					corrida.setObservacoes(observacoes);
					corrida.setObservacaoTele(observacaoTele);
					
					MapaCorridaUtil.adicionarCorrida(corrida);
					MapaCorridaUtil.adicionarCorridaAndamento(corrida);
					
					Corrida corridaEmail = corrida;
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							ClienteTeleTaxi clienteTeleTaxi = new ClienteTeleTaxi();
							clienteTeleTaxi.setNome(corridaEmail.getNomePassageiro());
							clienteTeleTaxi.setDataCadastro(new Date());
							clienteTeleTaxi.setEmail(ec.getEmail());
							clienteTeleTaxi.setCelular(corridaEmail.getCelularPassageiro());
							clienteTeleTaxi.setEmpresa(corridaEmail.getTipoTeleTaxi());
							
							empresaConveniadaService.enviarEmailVoucher(corridaEmail, ec);
						}
					}).start();
					
				} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
					MapaEntregaUtil.adicionarEntrega(corrida);
					MapaEntregaUtil.adicionarEntregaAndamento(corrida);
					//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
				}
				
			} else {
				corrida = null;
			}
			return corrida;
		}
		
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/solicitar", method = RequestMethod.POST)
	public Corrida solicitarCorrida(@RequestBody Corrida corrida) throws Exception {
		
		Usuario usuario = usuarioService.recuperarUsuarioPorID(corrida.getUsuario().getId());
		if (usuario.getIndicadorBloqueio() != null && usuario.getIndicadorBloqueio().equals(1)) {
			
			throw new Exception("Você está bloqueado para realização de solicitações de corrida. Motivo: " + usuario.getMotivoBloqueio());
			
		} else {
			if (!MapaCorridaUtil.verificaExistenciaCorrida(corrida.getUsuario(), corrida.getCidade())) {
				
				BigDecimal descontoCorrida = corrida.getDescontoCorrida();
				if (descontoCorrida == null) {
					descontoCorrida = new BigDecimal(0);
				}
				
				corrida.getUsuario().setQtdDescontoLojista(
						descontoLojistaService.countDescontosDisponiveisPorUsuario(corrida.getUsuario().getId()));
				BigDecimal desconto = MapaCidadeUtil.getInstance().getMapaCidade().get(corrida.getCidade().getId()).getPorcentagemDescontoLojista();
				if (desconto == null) {
					desconto = new BigDecimal(0);
				}
				BigDecimal descontoAplicado = new BigDecimal(0);
				if (corrida.getUsuario().getQtdDescontoLojista() > 0) {
					descontoAplicado = desconto;
					descontoCorrida = descontoCorrida.add(desconto.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				}
				
				descontoAplicado = descontoAplicado.add(MapaPromocaoUtil.definirPorcentagemDesconto(
						corrida.getUsuario())).setScale(2, BigDecimal.ROUND_HALF_EVEN);
				
				if (descontoCorrida.floatValue() > descontoAplicado.floatValue()) {
					descontoAplicado = descontoCorrida;
				}
				
				System.out.println("desconto solicitacao: " + descontoAplicado);
				corrida.setDescontoCorrida(descontoAplicado);
				corrida.setDataSolicitacao(new Date());
				corrida.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
				corrida.setIndicadorCorridaFim(0);
				corrida.setIndicadorCorridaInicio(0);
				if (corrida.getFormaPagamento() == null) {
					corrida.setFormaPagamento(FormaPagamentoEnum.CREDITO.getCodigo());
				}
				/*Cidade cidade = MapaCidadeUtil.getInstance().getMapaCidade().get(corrida.getCidade().getId());
				if (corrida.getVlMedioCorridaPadrao() != null 
						&& !corrida.getVlMedioCorridaPadrao().isEmpty() 
						&& cidade != null 
						&& cidade.getIndicadorPossuiTaximetro() != null 
						&& cidade.getIndicadorPossuiTaximetro().equals(2)) {
					corrida.setValorFinal(
							new BigDecimal(corrida.getVlMedioCorridaPadrao().replace(".", "").replace(",", "."))
								.setScale(2, BigDecimal.ROUND_FLOOR));
				}*/
				corridaService.salvarCorrida(corrida);
				
				if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
					MapaCorridaUtil.adicionarCorrida(corrida);
					MapaCorridaUtil.adicionarCorridaAndamento(corrida);
				} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
					MapaEntregaUtil.adicionarEntrega(corrida);
					MapaEntregaUtil.adicionarEntregaAndamento(corrida);
				}
				
			} else {
				corrida = null;
			}
			return corrida;
		}
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public CorridaCompletaVO buscarCorrida(@RequestBody Corrida corrida) {
		
		CorridaCompletaVO corridaVO = null;
		Corrida c = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
		if (c != null && c.getMotorista() != null) {
			Usuario usuario = usuarioService.recuperarUsuarioPorID(c.getMotorista().getIdUsuario());
			c.getMotorista().setNome(usuario.getNome());
			c.getMotorista().setSexo(usuario.getSexo());
			c.getMotorista().setSelfie(usuario.getImagem());
			c.getMotorista().setCelular(usuario.getCelular());
			c.getMotorista().setLatitudeCorrente(usuario.getLatitudeCorrente());
			c.getMotorista().setLongitudeCorrente(usuario.getLongitudeCorrente());
			//c.setVeiculo(veiculoService.recuperarVeiculoPorMotorista(c.getMotorista()));
		}
		if (c != null && c.getId() != null) {
			corridaVO = new CorridaCompletaVO();
			BeanUtils.copyProperties(c, corridaVO, "veiculo", "cidade");
			if (c.getVeiculo() != null && c.getVeiculo().getId() != null) {
				corridaVO.setVeiculo(new Veiculo());
				corridaVO.getVeiculo().setPlaca(c.getVeiculo().getPlaca());
				corridaVO.getVeiculo().setModelo(c.getVeiculo().getModelo());
				corridaVO.getVeiculo().setMarca(c.getVeiculo().getMarca());
				corridaVO.getVeiculo().setCor(c.getVeiculo().getCor());
				corridaVO.getVeiculo().setTipo(c.getVeiculo().getTipo());
				corridaVO.getVeiculo().setIndicadorAdaptado(c.getVeiculo().getIndicadorAdaptado());
				corridaVO.getVeiculo().setIndicadorBicicleta(c.getVeiculo().getIndicadorBicicleta());
				corridaVO.getVeiculo().setIndicadorCadeirinha(c.getVeiculo().getIndicadorCadeirinha());
			}
			if (c.getCidade() != null && c.getCidade().getId() != null) {
				corridaVO.setCidade(new Cidade());
				corridaVO.getCidade().setId(c.getCidade().getId());
				corridaVO.getCidade().setNome(c.getCidade().getNome());
			}
			if (c.getUsuario() != null && c.getUsuario().getId() != null) {
				corridaVO.setUsuario(new Usuario());
				corridaVO.getUsuario().setId(c.getUsuario().getId());
				corridaVO.getUsuario().setNome(c.getUsuario().getNome());
			}
		}
		return corridaVO;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/verificarStatus", method = RequestMethod.POST)
	public CorridaVO verificarStatus(@RequestBody Corrida corrida) {
		
		CorridaVO corridaVO = null;
		Corrida c = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
		if (c != null && c.getId() != null) {
			corridaVO = new CorridaVO();
			corridaVO.setId(c.getId());
			corridaVO.setStatus(c.getStatus());
			corridaVO.setCheguei(c.getCheguei());
			if (c.getMotorista() != null) {
				CarrosVO carro = MapaCarroUtil.recuperarCarroMotorista(c.getCidade(), c.getMotorista().getId());
				if (carro != null) {
					corridaVO.setLatitudeCorrente(carro.getLatitude());
					corridaVO.setLongitudeCorrente(carro.getLongitude());
				}
			}
		}
		return corridaVO;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/alertaCheguei", method = RequestMethod.POST)
	public void alertaCheguei(@RequestBody Corrida corrida) {
		
		Corrida c = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
		c.setCheguei(1);
		MapaCorridaUtil.adicionarCorridaAndamento(c);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscarFinalizado", method = RequestMethod.POST)
	public Corrida buscarCorridaFinalizada(@RequestBody Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		if (c != null && c.getMotorista() != null) {
			Usuario usuario = usuarioService.recuperarUsuarioPorID(c.getMotorista().getIdUsuario());
			c.getMotorista().setNome(usuario.getNome());
			c.getMotorista().setSexo(usuario.getSexo());
			c.getMotorista().setSelfie(usuario.getImagem());
			if (c.getMotorista().getLatitudeCorrente() == null ||
					c.getMotorista().getLatitudeCorrente().isEmpty()) {
				c.getMotorista().setLatitudeCorrente(usuario.getLatitudeCorrente());
				c.getMotorista().setLongitudeCorrente(usuario.getLongitudeCorrente());
			}
			//c.setVeiculo(veiculoService.recuperarVeiculoPorMotorista(c.getMotorista()));
		}
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public void cancelarCorrida(@RequestBody Corrida corrida) throws Exception {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		c.setDataFinalizacao(new Date());
		if (c.getStatus().equals(StatusCorridaEnum.SOLICITADO.getStatus())
				|| c.getStatus().equals(StatusCorridaEnum.A_CAMINHO.getStatus())) {
			if (corrida.getMotivoCancelamento() != null && !corrida.getMotivoCancelamento().isEmpty()) {
				c.setMotivoCancelamento(corrida.getMotivoCancelamento());
			}
			c.setStatus(StatusCorridaEnum.CANCELADA.getStatus());
			corridaService.salvarCorrida(c);
			
			if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
				
				Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(c.getCidade().getId(), c.getId());
				
				MapaCorridaUtil.removerCorrida(c);
				MapaCorridaUtil.removerCorridaAndamento(c);
				
				if (cMapa != null && cMapa.getCarrosProximos() != null) {
					StringBuilder carros = new StringBuilder();
					for (CarrosVO carro : cMapa.getCarrosProximos()) {
						carros.append("#" + carro.getIdMotorista() + "-");
					}
					//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
						//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros.toString());
				}
				
			} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
				MapaEntregaUtil.removerEntrega(c);
				MapaEntregaUtil.removerEntregaAndamento(c);
				
				//SpringContextUtil.getBean(SimpMessagingTemplate.class).convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
			}
		} else if (!c.getStatus().equals(StatusCorridaEnum.CANCELADA.getStatus())
				&& !c.getStatus().equals(StatusCorridaEnum.CANCELADA_TEMPO.getStatus())) {
			throw new Exception("Sua solicitação de corrida acabou de ser atendida, aguarde para visualizar as informações do motorista.");
		}
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/recuperar", method = RequestMethod.POST)
	public void recuperarCorrida(@RequestBody Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		CarrosVO carro = MapaCarroUtil.getInstance().getMapaCarro().get(c.getCidade().getId()).get(c.getMotorista().getId());
		if (carro != null && carro.getIdMotorista() != null) {
			carro.setIdPontoApoio(null);
			carro.setDataPontoApoio(null);
			carro.setNomePontoApoio(null);
			MapaCarroUtil.adicionarCarro(carro, c.getCidade());
		}
		if (c.getMotorista() != null) {
			c.setIdMotoristaRecuperacao(c.getMotorista().getId());
		}
		if (corrida.getMotivoRecuperacao() != null && !corrida.getMotivoRecuperacao().isEmpty()) {
			c.setMotivoRecuperacao(corrida.getMotivoRecuperacao());
		}
		c.setMotorista(null);
		c.setVeiculo(null);
		c.setDataRecuperacao(new Date());
		c.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
		corridaService.salvarCorrida(c);
		c.setCelularPassageiro(corrida.getCelularPassageiro());
		
		if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
			MapaCorridaUtil.removerCorrida(c);
			MapaCorridaUtil.removerCorridaAndamento(c);
			
			MapaCorridaUtil.adicionarCorrida(c);
			MapaCorridaUtil.adicionarCorridaAndamento(c);
			
			Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(c.getCidade().getId(), c.getId());
			
			if (cMapa.getCarrosProximos() != null) {
				StringBuilder carros = new StringBuilder();
				for (CarrosVO car : cMapa.getCarrosProximos()) {
					carros.append("#" + car.getIdMotorista() + "-");
				}
				carros.append("#" + carro.getIdMotorista() + "-");
				//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
					//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros.toString());
			}
			
		} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
			MapaEntregaUtil.removerEntrega(c);
			MapaEntregaUtil.removerEntregaAndamento(c);
			
			MapaEntregaUtil.adicionarEntrega(c);
			MapaEntregaUtil.adicionarEntregaAndamento(c);
			
			SpringContextUtil.getBean(SimpMessagingTemplate.class).convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
		}
		
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/cancelarTempo", method = RequestMethod.POST)
	public void cancelarCorridaTempo(@RequestBody Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		c.setDataFinalizacao(new Date());
		c.setStatus(StatusCorridaEnum.CANCELADA_TEMPO.getStatus());
		corridaService.salvarCorrida(c);
		CarrosVO carro = MapaCarroUtil.getInstance().getMapaCarro().get(c.getCidade().getId()).get(c.getMotorista().getId());
		if (carro != null && carro.getIdMotorista() != null) {
			carro.setIdPontoApoio(null);
			carro.setDataPontoApoio(null);
			carro.setNomePontoApoio(null);
			MapaCarroUtil.adicionarCarro(carro, c.getCidade());
		}
		
		//SimpMessagingTemplate smt = this.simpMessagingTemplate;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
					
					Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(c.getCidade().getId(), c.getId());
					
					MapaCorridaUtil.removerCorrida(c);
					MapaCorridaUtil.removerCorridaAndamento(c);
					
					if (cMapa.getCarrosProximos() != null) {
						StringBuilder carros = new StringBuilder();
						for (CarrosVO car : cMapa.getCarrosProximos()) {
							carros.append("#" + car.getIdMotorista() + "-");
						}
						//smt.convertAndSend("/topic/notificar-solicitacao-corrida", 
							//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros.toString());
					}
					
				} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
					MapaEntregaUtil.removerEntrega(c);
					MapaEntregaUtil.removerEntregaAndamento(c);
					
					SpringContextUtil.getBean(SimpMessagingTemplate.class).convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
				}
			}
		}).start();
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/recusar", method = RequestMethod.POST)
	public synchronized Corrida recusarCorrida(@RequestBody Corrida corrida) {
		
		Corrida c = null;
		Corrida corridaMapa = MapaCorridaUtil.getInstance().getMapaCorridaCidade().get(
				corrida.getCidade().getId()).get(corrida.getId());
		if (corridaMapa != null && !corridaMapa.getStatus().equals(StatusCorridaEnum.CANCELADA.getStatus())
				&& !corridaMapa.getStatus().equals(StatusCorridaEnum.CANCELADA_TEMPO.getStatus())) {
			
			CarrosVO motorista = new CarrosVO();
			motorista.setIdMotorista(corrida.getIdUsuarioMotorista());
			if (corridaMapa.getCarrosRecusados() != null) {
				corridaMapa.getCarrosRecusados().add(motorista);
			} else {
				corridaMapa.setCarrosRecusados(new ArrayList<CarrosVO>());
				corridaMapa.getCarrosRecusados().add(motorista);
			}
			
		}
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/pegar", method = RequestMethod.POST)
	public synchronized Corrida pegarCorrida(@RequestBody Corrida corrida) {
		
		Corrida c = null;
		Corrida corridaMapa = MapaCorridaUtil.getInstance().getMapaCorridaCidade().get(
				corrida.getCidade().getId()).get(corrida.getId());
		if (corridaMapa == null || (corridaMapa.getStatus().equals(StatusCorridaEnum.CANCELADA.getStatus())
				|| corridaMapa.getStatus().equals(StatusCorridaEnum.CANCELADA_TEMPO.getStatus()))) {
			c = corridaMapa;
		} else {
			c = corridaService.recuperarCorridaPorChave(corrida.getId());
			Usuario usuario = null;
			if (c.getStatus().equals(StatusCorridaEnum.SOLICITADO.getStatus())) {
				//System.out.println("Pegando corrida Origem: " + c.getOrigem());
				//System.out.println("Pegando corrida Destino: " + c.getDestino());
				if (corrida.getVeiculo() != null && corrida.getVeiculo().getPlaca() != null && !corrida.getVeiculo().getPlaca().isEmpty()) {
					//System.out.println("Placa do veiculo: " + corrida.getVeiculo());
				}
				usuario = new Usuario();
				usuario.setId(corrida.getIdUsuarioMotorista());
				Motorista m = motoristaService.recuperarMotoristaPorUsuario(usuario);
				
				CarrosVO carro = MapaCarroUtil.recuperarCarroMotorista(m.getCidade(), m.getId());
				
				if (carro != null && carro.getNomeMotorista() != null && !carro.getNomeMotorista().isEmpty()) {
					//System.out.println("Carro do motorista pegando corrida: " + carro.getNomeMotorista());
				} else {
					//System.out.println("Motorista não está online (id): " + m.getId());
				}
				
				if (carro != null && carro.getDataUltimaPosicao().getTime() > recuperaData3Minutos(new Date()).getTime()) {
					
					if (c.getIndicadorTeleTaxi() != null && c.getIndicadorTeleTaxi().equals(1)) {
						
						List<CarrosVO> carros = new ArrayList<CarrosVO>();
						if (corridaMapa != null && corridaMapa.getPontoApoio1() != null 
								&& corridaMapa.getPontoApoio1().getCarros() != null
								&& !corridaMapa.getPontoApoio1().getCarros().isEmpty()) {
							carros.addAll(corridaMapa.getPontoApoio1().getCarros());
						}
						
						if (corridaMapa != null && corridaMapa.getPontoApoio2() != null 
								&& corridaMapa.getPontoApoio2().getCarros() != null
								&& !corridaMapa.getPontoApoio2().getCarros().isEmpty()) {
							carros.addAll(corridaMapa.getPontoApoio2().getCarros());
						}
						
						if (corridaMapa != null && corridaMapa.getPontoApoio3() != null 
								&& corridaMapa.getPontoApoio3().getCarros() != null
								&& !corridaMapa.getPontoApoio3().getCarros().isEmpty()) {
							carros.addAll(corridaMapa.getPontoApoio3().getCarros());
						}
						
						if (corridaMapa != null && corridaMapa.getPontoApoio4() != null 
								&& corridaMapa.getPontoApoio4().getCarros() != null
								&& !corridaMapa.getPontoApoio4().getCarros().isEmpty()) {
							carros.addAll(corridaMapa.getPontoApoio4().getCarros());
						}
						
						if (corridaMapa.getCarrosProximos() != null && !corridaMapa.getCarrosProximos().isEmpty()) {
							carros.addAll(corridaMapa.getCarrosProximos());
						}
						
						if (carros != null && !carros.isEmpty() && (
								(corridaMapa.getObservacoes() == null || corridaMapa.getObservacoes().isEmpty()) ||
								!corridaMapa.getObservacoes().equals(CORRIDA_JOGADA_LIVRE))) {
							
							List<CarrosVO> cCarro = carros.stream()
									.filter(cCar -> cCar != null)
									.filter(cCar -> cCar.getIdMotorista() != null)
									.filter(cCar -> cCar.getIdMotorista().equals(m.getId()))
									.collect(Collectors.toList());
							
							if (cCarro != null && !cCarro.isEmpty() && cCarro.size() > 0) {
								
								//System.out.println("Pegou corrida e estava dentro do proximo.");
								c.setMotorista(m);
								c.setStatus(StatusCorridaEnum.A_CAMINHO.getStatus());
								c.setVeiculo(corrida.getVeiculo());
								corridaService.salvarCorrida(c);
								c.setCelularPassageiro(corrida.getCelularPassageiro());
								c.setDescontoCorrida(corrida.getDescontoCorrida());
								c.setIndicadorVoucherPapel(corrida.getIndicadorVoucherPapel());
								
								if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
									
									Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
									
									if (corridaMapa != null && corridaMapa.getDescontoCorrida() != null) {
										System.out.println("desconto pegar: " + corridaMapa.getDescontoCorrida());
										c.setDescontoCorrida(corridaMapa.getDescontoCorrida());
									}
									MapaCorridaUtil.removerCorrida(c);
									MapaCorridaUtil.adicionarCorridaAndamento(c);
									
									if (cMapa.getCarrosProximos() != null) {
										StringBuilder carros2 = new StringBuilder();
										for (CarrosVO car : cMapa.getCarrosProximos()) {
											carros2.append("#" + car.getIdMotorista() + "-");
										}
										//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
											//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros2.toString());
									}
									
								} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
									MapaEntregaUtil.removerEntrega(c);
									MapaEntregaUtil.adicionarEntregaAndamento(c);
								}
								carro.setIdPontoApoio(null);
								carro.setDataPontoApoio(null);
								carro.setNomePontoApoio(null);
								MapaCarroUtil.adicionarCarro(carro, c.getCidade());
								
							} else {
								c = null;
								//System.out.println("Não pegou corrida e n estava dentro dos proximos.");
							}
							
						} else {
							// caiu no livre
							
							c.setMotorista(m);
							c.setStatus(StatusCorridaEnum.A_CAMINHO.getStatus());
							c.setVeiculo(corrida.getVeiculo());
							corridaService.salvarCorrida(c);
							c.setCelularPassageiro(corrida.getCelularPassageiro());
							c.setDescontoCorrida(corrida.getDescontoCorrida());
							c.setIndicadorVoucherPapel(corrida.getIndicadorVoucherPapel());
							
							if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
								
								Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
								
								if (cMapa != null && cMapa.getDescontoCorrida() != null) {
									c.setDescontoCorrida(cMapa.getDescontoCorrida());
								}
								MapaCorridaUtil.removerCorrida(c);
								MapaCorridaUtil.adicionarCorridaAndamento(c);
								
								if (cMapa.getCarrosProximos() != null) {
									StringBuilder carros2 = new StringBuilder();
									for (CarrosVO car : cMapa.getCarrosProximos()) {
										carros2.append("#" + car.getIdMotorista() + "-");
									}
									//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
										//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros2.toString());
								}
								
							} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
								MapaEntregaUtil.removerEntrega(c);
								MapaEntregaUtil.adicionarEntregaAndamento(c);
							}
							carro.setIdPontoApoio(null);
							carro.setDataPontoApoio(null);
							carro.setNomePontoApoio(null);
							MapaCarroUtil.adicionarCarro(carro, c.getCidade());
						}
						
					} else {
						
						c.setMotorista(m);
						c.setStatus(StatusCorridaEnum.A_CAMINHO.getStatus());
						c.setVeiculo(corrida.getVeiculo());
						corridaService.salvarCorrida(c);
						c.setCelularPassageiro(corrida.getCelularPassageiro());
						c.setDescontoCorrida(corrida.getDescontoCorrida());
						c.setIndicadorVoucherPapel(corrida.getIndicadorVoucherPapel());
						
						if (c.getDescontoCorrida() == null && corridaMapa != null && corridaMapa.getDescontoCorrida() != null) {
							c.setDescontoCorrida(corridaMapa.getDescontoCorrida());
						}
						if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
							MapaCorridaUtil.removerCorrida(c);
							MapaCorridaUtil.adicionarCorridaAndamento(c);
						} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
							MapaEntregaUtil.removerEntrega(c);
							MapaEntregaUtil.adicionarEntregaAndamento(c);
						}
						carro.setIdPontoApoio(null);
						carro.setDataPontoApoio(null);
						carro.setNomePontoApoio(null);
						MapaCarroUtil.adicionarCarro(carro, c.getCidade());
					}
					
				} else {
					c = null;
				}
			}
		}
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/panico", method = RequestMethod.POST)
	public Corrida panicoCorrida(@RequestBody Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		c.setIndicadorPanico(1);
		corridaService.salvarCorrida(c);
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/iniciar", method = RequestMethod.POST)
	public Corrida iniciarCorrida(@RequestBody Corrida corrida) {
		
		Corrida corridaRetorno = null;
		Corrida corridaMapa = MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(
				corrida.getCidade().getId()).get(corrida.getId());
		
		if (corridaMapa == null || (corridaMapa.getStatus().equals(StatusCorridaEnum.CANCELADA.getStatus())
				|| corridaMapa.getStatus().equals(StatusCorridaEnum.CANCELADA_TEMPO.getStatus()))) {
			corridaRetorno = corridaMapa;
			
		} else {
			Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
			c.setDataInicio(new Date());
			c.setStatus(StatusCorridaEnum.CORRENTE.getStatus());
			c.setIndicadorCorridaInicio(1);
			corridaService.salvarCorrida(c);
			c.setDescontoCorrida(corrida.getDescontoCorrida());
			
			corridaRetorno = c;
			
			if (c.getDescontoCorrida() == null && corridaMapa != null && corridaMapa.getDescontoCorrida() != null) {
				System.out.println("desconto inicio: " + corridaMapa.getDescontoCorrida());
				c.setDescontoCorrida(corridaMapa.getDescontoCorrida());
			}
			if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
				MapaCorridaUtil.adicionarCorridaAndamento(c);
			} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
				MapaEntregaUtil.adicionarEntregaAndamento(c);
			}
			
			CarrosVO carro = MapaCarroUtil.getInstance().getMapaCarro().get(c.getCidade().getId()).get(c.getMotorista().getId());
			if (carro != null && carro.getIdMotorista() != null) {
				carro.setIdPontoApoio(null);
				carro.setDataPontoApoio(null);
				carro.setNomePontoApoio(null);
				MapaCarroUtil.adicionarCarro(carro, c.getCidade());
			}
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					UsuarioNotificacao usuarioNotificacao = new UsuarioNotificacao();
					usuarioNotificacao.setIdNotificacao(new Long(1l));
					usuarioNotificacao.setIdUsuario(c.getUsuario().getId());
					usuarioNotificacao.setTexto("Sua corrida para " 
							+ c.getDestino() + " foi iniciada em " 
							+ new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(c.getDataInicio()));
					usuarioNotificacao.setDataNotificacao(new Date());
					usuarioNotificacaoService.salvar(usuarioNotificacao);
					MapaMensagensUtil.adicionarNotificacao(usuarioNotificacao);
					
					List<Familia> familiares = familiaService.recuperarFamiliaresUsuario(corrida.getUsuario());
					for (Familia familia : familiares) {
						
						try {
							
							Usuario usuario = usuarioService.recuperarPorLogin(familia.getEmail());
							UsuarioNotificacao usuarioNotificacao2 = new UsuarioNotificacao();
							usuarioNotificacao2.setIdNotificacao(new Long(1l));
							usuarioNotificacao2.setIdUsuario(usuario.getId());
							usuarioNotificacao2.setTexto("Seu familiar cadastrado " 
									+ c.getUsuario().getNome() + " iniciou uma corrida para " 
									+ c.getDestino() + " em " 
									+ new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(c.getDataInicio()));
							usuarioNotificacao2.setDataNotificacao(new Date());
							usuarioNotificacaoService.salvar(usuarioNotificacao2);
						} catch (Exception e) {}
					}
					
				}
			}).start();
		}
		return corridaRetorno;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public Corrida finalizarCorrida(@RequestBody Corrida corrida) {
		
	
		Corrida cor = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
		if (cor != null) {
			System.out.println("desconto: " + cor.getDescontoCorrida());
			corrida.setDescontoCorrida(cor.getDescontoCorrida());
		}
		Corrida c = corridaService.finalizarCorrida(corrida);
		CarrosVO carro = MapaCarroUtil.getInstance().getMapaCarro().get(c.getCidade().getId()).get(c.getMotorista().getId());
		if (carro != null && carro.getIdMotorista() != null) {
			carro.setIdPontoApoio(null);
			carro.setDataPontoApoio(null);
			carro.setNomePontoApoio(null);
			MapaCarroUtil.adicionarCarro(carro, c.getCidade());
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				MapaCorridaChatUtil.apagarMensagens(corrida.getId());
				if ((c.getIndicadorTeleTaxi() == null || !c.getIndicadorTeleTaxi().equals(1)) 
						&& MapaMotoristaBloqueadoUtil.recuperarVeiculo(c.getVeiculo().getId()) != null) {
					Veiculo vet = MapaMotoristaBloqueadoUtil.recuperarVeiculo(c.getVeiculo().getId());
					Veiculo v = veiculoService.recuperarVeiculoPorChave(c.getVeiculo().getId());
					v.setStatus(vet.getStatus());
					v.setMotivoBloqueio(vet.getMotivoBloqueio());
					veiculoService.salvarVeiculo(v);
					MapaMotoristaBloqueadoUtil.removerVeiculo(c.getVeiculo());
				}
				
			}
		}).start();
		
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/finalizarRota", method = RequestMethod.POST)
	public Corrida finalizarCorridaRota(@RequestBody Corrida corrida) {
		
		Corrida c = null;
		Corrida cRota = corridaService.recuperarCorridaPorChave(corrida.getId());
		Boolean finaliza = Boolean.FALSE;
		if (cRota.getDataFinalizacao1() == null
				&& cRota.getDestino() != null && cRota.getDestinoEndereco() != null
				&& !cRota.getDestino().isEmpty() && !cRota.getDestinoEndereco().isEmpty()) {
			cRota.setDataFinalizacao1(new Date());
			corridaService.alterar(cRota);
			c = cRota;
		} else if (cRota.getDataFinalizacao2() == null
				&& cRota.getDestino2() != null && cRota.getDestinoEndereco2() != null
				&& !cRota.getDestino2().isEmpty() && !cRota.getDestinoEndereco2().isEmpty()) {
			cRota.setDataFinalizacao2(new Date());
			corridaService.alterar(cRota);
			c = cRota;
			if (cRota.getDestino3() == null || cRota.getDestino3().isEmpty()) {
				finaliza = true;
			}
		} else if (cRota.getDataFinalizacao3() == null
				&& cRota.getDestino3() != null && cRota.getDestinoEndereco3() != null
				&& !cRota.getDestino3().isEmpty() && !cRota.getDestinoEndereco3().isEmpty()) {
			cRota.setDataFinalizacao3(new Date());
			corridaService.alterar(cRota);
			c = cRota;
			if (cRota.getDestino4() == null || cRota.getDestino4().isEmpty()) {
				finaliza = true;
			}
		} else if (cRota.getDataFinalizacao4() == null 
				&& cRota.getDestino4() != null && cRota.getDestinoEndereco4() != null
				&& !cRota.getDestino4().isEmpty() && !cRota.getDestinoEndereco4().isEmpty()) {
			cRota.setDataFinalizacao4(new Date());
			corridaService.alterar(cRota);
			c = cRota;
			finaliza = true;
		}
		
		if (finaliza) {
			c = corridaService.finalizarCorrida(corrida);
			CarrosVO carro = MapaCarroUtil.getInstance().getMapaCarro().get(c.getCidade().getId()).get(c.getMotorista().getId());
			if (carro != null && carro.getIdMotorista() != null) {
				carro.setIdPontoApoio(null);
				carro.setDataPontoApoio(null);
				carro.setNomePontoApoio(null);
				MapaCarroUtil.adicionarCarro(carro, c.getCidade());
			}
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					
					MapaCorridaChatUtil.apagarMensagens(corrida.getId());
					if ((cRota.getIndicadorTeleTaxi() == null || !cRota.getIndicadorTeleTaxi().equals(1)) 
							&& MapaMotoristaBloqueadoUtil.recuperarVeiculo(cRota.getVeiculo().getId()) != null) {
						Veiculo vet = MapaMotoristaBloqueadoUtil.recuperarVeiculo(cRota.getVeiculo().getId());
						Veiculo v = veiculoService.recuperarVeiculoPorChave(cRota.getVeiculo().getId());
						v.setStatus(vet.getStatus());
						v.setMotivoBloqueio(vet.getMotivoBloqueio());
						veiculoService.salvarVeiculo(v);
						MapaMotoristaBloqueadoUtil.removerVeiculo(cRota.getVeiculo());
					}
					
				}
			}).start();
		}
		
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/percurso", method = RequestMethod.POST)
	public List<Percurso> recuperarPercurso(@RequestBody Corrida corrida, Motorista motorista) {
		
		return percursoService.recuperarPercursosCorrida(corrida);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/acompanhar", method = RequestMethod.POST)
	public Corrida acompanharCorrida(@RequestBody Corrida corrida) {
		
		return corridaService.recuperarCorridaPorChave(corrida.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
	@RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<MinhaCorridaVo> listarCorridasPorUsuario(@RequestBody Usuario usuario) {
		
		List<MinhaCorridaVo> corridas = corridaService.recuperarCorridasPorUsuario(usuario);

		corridas.forEach(c -> {
			String nomeMotorista = usuarioService.recuperarNomePorId(c.getIdMotoristaUsuario());
			c.setMotora(nomeMotorista);
		});

		return corridas;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
	@RequestMapping(value = "/listarEntregas/{idUsuario}", method = RequestMethod.GET)
	public List<MinhaCorridaVo> listarEntregasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
		List<MinhaCorridaVo> corridas = corridaService.recuperarEntregasPorUsuario(idUsuario);

		corridas.forEach(c -> {
			String nomeMotorista = usuarioService.recuperarNomePorId(c.getIdMotoristaUsuario());
			c.setMotora(nomeMotorista);
		});

		return corridas;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/classificar", method = RequestMethod.POST)
	public Corrida classificarCorrida(@RequestBody Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		Motorista m = motoristaService.recuperarMotoristaPorChave(c.getMotorista().getId());
		c.setClassificacaoCorrida(corrida.getClassificacaoCorrida());
		c.setClassificacaoDescricao(corrida.getClassificacaoDescricao());
		
		if (c.getClassificacaoCorrida() != null) {
			if (m.getClassificacao() == null) {
				m.setClassificacao(c.getClassificacaoCorrida());
				m.setQtdClassificacao(1);
			} else {
				m.setClassificacao(m.getClassificacao() + c.getClassificacaoCorrida());
				m.setQtdClassificacao(m.getQtdClassificacao() + 1);
			}
		}
		corridaService.salvarCorrida(c);
		motoristaService.salvarMotorista(m);
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/classificarVeiculo", method = RequestMethod.POST)
	public Corrida classificarCorridaVeiculo(@RequestBody Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		Veiculo v = veiculoService.recuperarVeiculoPorChave(c.getVeiculo().getId());
		c.setClassificacaoVeiculo(corrida.getClassificacaoVeiculo());
		
		if (v != null && c.getClassificacaoVeiculo() != null) {
			if (v.getClassificacao() == null) {
				v.setClassificacao(c.getClassificacaoVeiculo());
				v.setQtdClassificacao(1);
			} else {
				v.setClassificacao(v.getClassificacao() + c.getClassificacaoVeiculo());
				v.setQtdClassificacao(v.getQtdClassificacao() + 1);
			}
		}
		corridaService.salvarCorrida(c);
		veiculoService.salvarVeiculo(v);
		return c;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvarPercurso", method = RequestMethod.POST)
	public void salvarPercurso(@RequestBody Percurso percurso) {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Percurso p = new Percurso();
				p.setData(new Date());
				p.setCorrida(percurso.getCorrida());
				p.setLatitude(percurso.getLatitude());
				p.setLongitude(percurso.getLongitude());
				percursoService.salvarPercurso(p);
			}
		}).start();
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/pendente", method = RequestMethod.POST)
	public Corrida recuperarCorridaPendente(@RequestBody Usuario usuario) {
		
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			usuario.setMotorista(motoristaService.recuperarMotoristaPorUsuario(usuario));
		}
		
		Corrida corrida = null;
		Corrida corridaUsuario = MapaCorridaUtil.recuperarCorridaPendenteUsuario(
				usuario.getId(), usuario.getIdCidade());
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			Corrida corridaMotorista = MapaCorridaUtil.recuperarCorridaPendenteMotorista(
					usuario.getMotorista().getId(), usuario.getIdCidade());
			if (corridaMotorista != null && corridaMotorista.getId() != null) {
				corrida = corridaMotorista;
			} else if (corridaUsuario != null && corridaUsuario.getId() != null) {
				corrida = corridaUsuario;
			}
		} else if (usuario.getTipo().equals(TipoUsuarioEnum.CLIENTE.getCodigo())
				|| usuario.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			corrida = corridaUsuario;
			if (corrida != null && corrida.getMotorista() != null && corrida.getMotorista().getIdUsuario() != null) {
				try {
					Usuario usuarioMotorista = usuarioService.recuperarUsuarioPorID(corrida.getMotorista().getIdUsuario());
					corrida.getMotorista().setNome(usuarioMotorista.getNome());
					corrida.getMotorista().setSexo(usuarioMotorista.getSexo());
					corrida.getMotorista().setSelfie(usuarioMotorista.getImagem());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		/*Corrida corrida = null;
		Corrida corridaUsuario = null;
		Corrida corridaMotorista = null;
		
		corridaUsuario = corridaService.recuperarCorridaPendente(usuario, 
				TipoUsuarioEnum.CLIENTE.getCodigo());
		
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			corridaMotorista = corridaService.recuperarCorridaPendente(usuario, 
					TipoUsuarioEnum.MOTORISTA.getCodigo());
		}
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			if (corridaMotorista != null && corridaMotorista.getId() != null) {
				corrida = corridaMotorista;
			} else if (corridaUsuario != null && corridaUsuario.getId() != null) {
				corrida = corridaUsuario;
			}
		} else if (usuario.getTipo().equals(TipoUsuarioEnum.CLIENTE.getCodigo())
				|| usuario.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			corrida = corridaUsuario;
		}*/
		return corrida;
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/totalReais", method = RequestMethod.POST)
	public BigDecimal recuperarTotalReaisMotorista(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		if (usuario.getMotorista() == null || usuario.getMotorista().getId() == null) {
			user.setMotorista(motoristaService.recuperarMotoristaPorUsuario(user));
		} else {
			user.setMotorista(usuario.getMotorista());
		}
		user.setMesAno(usuario.getMesAno());
		return corridaService.recuperarTotalReaisMotorista(user, TipoCorridaEnum.CORRIDA.getCodigo());
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/totalReaisEntrega", method = RequestMethod.POST)
	public BigDecimal recuperarTotalReaisMotoristaEntrega(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		if (usuario.getMotorista() == null || usuario.getMotorista().getId() == null) {
			user.setMotorista(motoristaService.recuperarMotoristaPorUsuario(user));
		} else {
			user.setMotorista(usuario.getMotorista());
		}
		user.setMesAno(usuario.getMesAno());
		return corridaService.recuperarTotalReaisMotorista(user, TipoCorridaEnum.ENTREGA.getCodigo());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listarPercursos", method = RequestMethod.POST)
	public List<Percurso> listarPercursos(@RequestBody Corrida corrida) {
		
		return percursoService.recuperarPercursosCorrida(corrida);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/entregaPendente", method = RequestMethod.POST)
	public Corrida recuperarEntregaPendente(@RequestBody Usuario usuario) {
		
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			usuario.setMotorista(motoristaService.recuperarMotoristaPorUsuario(usuario));
		}
		
		Corrida corrida = null;
		Corrida corridaUsuario = MapaEntregaUtil.recuperarEntregaPendenteUsuario(
				usuario.getId(), usuario.getIdCidade());
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			Corrida corridaMotorista = MapaEntregaUtil.recuperarEntregaPendenteMotorista(
					usuario.getMotorista().getId(), usuario.getIdCidade());
			if (corridaMotorista != null && corridaMotorista.getId() != null) {
				corrida = corridaMotorista;
			} else if (corridaUsuario != null && corridaUsuario.getId() != null) {
				corrida = corridaUsuario;
			}
		} else if (usuario.getTipo().equals(TipoUsuarioEnum.CLIENTE.getCodigo())
				|| usuario.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			corrida = corridaUsuario;
		}
		/*Corrida corridaUsuario = corridaService.recuperarEntregaPendente(usuario, 
				TipoUsuarioEnum.CLIENTE.getCodigo());
		Corrida corridaMotorista = corridaService.recuperarEntregaPendente(usuario, 
				TipoUsuarioEnum.MOTORISTA.getCodigo());
		if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			if (corridaMotorista != null && corridaMotorista.getId() != null) {
				corrida = corridaMotorista;
			} else if (corridaUsuario != null && corridaUsuario.getId() != null) {
				corrida = corridaUsuario;
			}
		} else if (usuario.getTipo().equals(TipoUsuarioEnum.CLIENTE.getCodigo())
				|| usuario.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			corrida = corridaUsuario;
		}*/
		return corrida;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/mensagens", method = RequestMethod.POST)
	public List<MensagemCorridaVO> recuperarMensagens(@RequestBody Corrida corrida) {
		
		return MapaCorridaChatUtil.recuperarMensagens(corrida.getId()); 
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/visualizouMensagem", method = RequestMethod.POST)
	public void visualizarMensagem(@RequestBody Usuario usuario) {
		
		MapaCorridaChatUtil.retirarMensagemNova(usuario.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/verificaMensagem", method = RequestMethod.POST)
	public Usuario verificaMensagemChat(@RequestBody Usuario usuario) {
		
		return MapaCorridaChatUtil.verificaMensagemNova(usuario.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/enviarMensagem", method = RequestMethod.POST)
	public void enviarMensagem(@RequestBody MensagemCorridaVO mensagem) {
		
		mensagem.setDataMensagem(new Date());
		MapaCorridaChatUtil.adicionarMensagem(mensagem);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				Corrida corrida = corridaService.recuperarCorridaPorChave(mensagem.getIdCorrida());
				
				if (mensagem.getOrigemPassageiro()) {
					MapaCorridaChatUtil.definirMensagemNova(corrida.getMotorista().getIdUsuario());
				} else {
					if (corrida.getMotorista() != null) {
						corrida.getMotorista().setMensagemEnviada(mensagem.getMensagem());
					}
					MapaCorridaChatUtil.definirMensagemNova(corrida.getUsuario().getId(), corrida.getMotorista());
				}
			}
		}).start();
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/excluirLocal", method = RequestMethod.POST)
    public void removerLocal(@RequestBody LocalFavorito localFavorito) {
        localFavoritoService.remover(localFavorito);
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvarLocal", method = RequestMethod.POST)
    public LocalFavorito salvarLocal(@RequestBody LocalFavorito localFavorito) {
        localFavoritoService.salvarLocal(localFavorito);
        return localFavorito;
    }
	
}