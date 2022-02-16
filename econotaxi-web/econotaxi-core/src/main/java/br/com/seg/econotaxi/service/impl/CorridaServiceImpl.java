/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.enums.FormaPagamentoEnum;
import br.com.seg.econotaxi.enums.PromocaoEnum;
import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.StatusTransacaoEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Credito;
import br.com.seg.econotaxi.model.DescontoLojista;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.model.VisualizacaoCorrida;
import br.com.seg.econotaxi.repository.CorridaRepository;
import br.com.seg.econotaxi.repository.VisualizacaoCorridaRepository;
import br.com.seg.econotaxi.service.CidadeService;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.CreditoService;
import br.com.seg.econotaxi.service.DescontoLojistaService;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.FamiliaService;
import br.com.seg.econotaxi.service.FeriadoService;
import br.com.seg.econotaxi.service.PagamentoService;
import br.com.seg.econotaxi.service.PercursoService;
import br.com.seg.econotaxi.service.PromocaoService;
import br.com.seg.econotaxi.service.TransacaoService;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.util.DistanciaUtil;
import br.com.seg.econotaxi.util.MapaCorridaUtil;
import br.com.seg.econotaxi.util.MapaEntregaUtil;
import br.com.seg.econotaxi.vo.MinhaCorridaVo;

/**
 * @author bruno
 *
 */
@Service("corridaService")
public class CorridaServiceImpl implements CorridaService {

	@Autowired
	private CorridaRepository corridaRepository;
	@Autowired
	private TransacaoService transacaoService;
	@Autowired
	private CreditoService creditoService;
	@Autowired
	private DescontoLojistaService descontoLojistaService;
	@Autowired
	private PromocaoService promocaoService;
	@Autowired
	private CidadeService cidadeService;
	@Autowired
	private FamiliaService familiaService;
	@Autowired
	private FeriadoService feriadoService;
	@Autowired
	private PercursoService percursoService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PagamentoService pagamentoService;
	@Autowired
	private UsuarioNotificacaoService usuarioNotificacaoService;
	@Autowired
	private VisualizacaoCorridaRepository visualizacaoCorridaRepository;
	@Autowired
	private EmpresaConveniadaService empresaConveniadaService;
	
	@Override
	public void salvarCorrida(Corrida corrida) {
		corridaRepository.save(corrida);
	}

	@Override
	public Corrida recuperarCorridaPorChave(Long chave) {
		return corridaRepository.findById(chave);
	}

	@Override
	public Integer recuperarQtdCorridaPorStatus(Integer status) {
		return corridaRepository.recuperarQtdCorridaPorStatus(status);
	}

	@Override
	public Long pesquisarCountCorridaPorFiltro(Corrida filtro) {
		return corridaRepository.pesquisarCountCorridaPorFiltro(filtro);
	}

	@Override
	public List<Corrida> pesquisarCorridaPorFiltro(Corrida filtro, int first, int pageSize) {
		return corridaRepository.pesquisarCorridaPorFiltro(filtro, first, pageSize);
	}

	@Override
	public List<Corrida> recuperarCorridaPorStatus(Integer status, Integer tipo) {
		return corridaRepository.recuperarCorridaPorStatus(status, tipo);
	}

	@Override
	public List<Corrida> recuperarCorridasPorUsuario(Usuario usuario, Integer tipo) {
		return corridaRepository.recuperarCorridasPorUsuario(usuario, tipo);
	}
	
	@Override
	public List<MinhaCorridaVo> recuperarCorridasPorUsuarioPaginada(Usuario usuario, Integer tipo, Integer paginacao) {
		return corridaRepository.recuperarCorridasPorUsuarioPaginada(usuario, tipo, paginacao);
	}

	@Override
	public List<Corrida> recuperarCorridasPorMotorista(Motorista motorista) {
		return corridaRepository.recuperarCorridasPorMotorista(motorista);
	}

	@Override
	public List<MinhaCorridaVo> recuperarCorridasPorUsuario(Usuario usuario) {
		return corridaRepository.recuperarCorridasByUsuario(usuario, TipoCorridaEnum.CORRIDA.getCodigo());
	}

	@Override
	public List<Corrida> recuperarCorridasPorCidadeMotorista(Usuario usuario, 
			Integer tipoCorrida, Integer status) {
		
		return corridaRepository.recuperarCorridasPorCidadeMotorista(usuario, 
				tipoCorrida, status);
	}

	@Override
	public Integer recuperarCorridasBotaoPanico() {
		return corridaRepository.recuperarCorridasBotaoPanico();
	}

	@Override
	public List<Corrida> recuperarCorridasPendentesParaCancelamento(Date date, Boolean radio) {
		return corridaRepository.recuperarCorridasPendentesParaCancelamento(date, radio);
	}

	@Override
	public Corrida recuperarCorridaPendente(Usuario usuario, Integer tipoUsuario) {
		return corridaRepository.recuperarCorridaPendente(usuario, tipoUsuario);
	}

	@Override
	public List<Corrida> recuperarCorridasPorMotorista(Usuario usuario, Integer tipoCorrida) {
		return corridaRepository.recuperarCorridasPorMotorista(usuario, tipoCorrida);
	}

	@Override
	public BigDecimal recuperarTotalReaisMotorista(Usuario user, Integer tipoCorrida) {
		return corridaRepository.recuperarTotalReaisMotorista(user, tipoCorrida);
	}

	@Override
	public Corrida recuperarEntregaPendente(Usuario usuario, Integer tipoUsuario) {
		return corridaRepository.recuperarEntregaPendente(usuario, tipoUsuario);
	}

	@Override
	public List<Corrida> recuperarEntregasPorCidadeMotorista(Usuario usuario, Integer tipoCorrida, Integer status) {
		return corridaRepository.recuperarEntregasPorCidadeMotorista(usuario, tipoCorrida, status);
	}

	@Override
	public List<MinhaCorridaVo> recuperarEntregasPorUsuario(Long idUsuario) {
		Usuario usuario = new Usuario();
		usuario.setId(idUsuario);
		return corridaRepository.findCorridasByUsuario(idUsuario, TipoCorridaEnum.ENTREGA.getCodigo());
	}

	@Override
	public List<Corrida> recuperarCorridasPendentesParaMapas() {
		return corridaRepository.recuperarCorridasPendentesParaMapas();
	}

	@Override
	public Corrida finalizarCorrida(Corrida corrida) {

		Corrida c = recuperarCorridaPorChave(corrida.getId());
		c.setDataFinalizacao(new Date());
		c.setStatus(StatusCorridaEnum.FINALIZADO.getStatus());
		c.setIndicadorCorridaFim(1);
		c.setValorFinal(corrida.getValorFinal());
		
		List<Promocao> promocoes = promocaoService.recuperarUltimasPromocoes();
		
		Usuario usuario = usuarioService.recuperarUsuarioPorID(corrida.getUsuario().getId());
		Cidade cidade = cidadeService.recuperarCidadePorChave(corrida.getCidade().getId());
		
		BigDecimal porcentagem = null;
		porcentagem = definirPorcentagemDesconto(promocoes, usuario, porcentagem);
		
		Credito credito = null;
		if (c.getFormaPagamento() != null && c.getFormaPagamento().equals(FormaPagamentoEnum.CREDITO.getCodigo())) {
			credito = creditoService.recuperarPorUsuario(usuario.getId());
		}
		
		DescontoLojista descontoLojista = descontoLojistaService
				.recuperarDescontoDisponivelPorUsuario(usuario, cidade);
		
		Long qtdSegundosParado = null;
		if (c.getValorFinal() == null) {
			
			qtdSegundosParado = percursoService.segundosParadoPercurso(c);
			System.out.println("########################################################################");
			System.out.println("Segundos parados: " + qtdSegundosParado);
			System.out.println("########################################################################");
			c.setSegundosParados(qtdSegundosParado);
			
			try {
				Float distanciaPercorrida = recuperarDistanciaPercorrida(c);
				System.out.println("########################################################################");
				System.out.println("Distância percorrida: " + distanciaPercorrida);
				System.out.println("########################################################################");
				c.setDistanciaPercorrida(new BigDecimal(distanciaPercorrida));
				
			} catch (Exception e) {
				System.out.println("########################################################################");
				System.out.println("Erro ao calcular distância.");
				System.out.println("########################################################################");
				e.printStackTrace();
			}
		}
		
		
		if (c.getIndicadorPacote() == null || c.getIndicadorPacote() != 1) {
			
			BigDecimal valorFinal = null;
			
			if (c.getValorFinal() != null) {
				
				valorFinal = c.getValorFinal();
				
				if (corrida.getDescontoCorrida() != null && corrida.getDescontoCorrida().floatValue() > 0 
						&& (corrida.getIndicadorSemDesconto() == null || !corrida.getIndicadorSemDesconto().equals(1))) {
					
					System.out.println("possui desconto!");
					valorFinal = valorFinal.subtract(BigDecimal.valueOf(valorFinal.floatValue() 
							* (corrida.getDescontoCorrida().floatValue() / 100)));
					
					if (valorFinal.floatValue() <= cidade.getValorMinimoCorrida().floatValue()) {
						valorFinal = cidade.getValorMinimoCorrida();
					}
					c.setValorFinal(valorFinal.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				}
				
			} else {
				
				long diferencaSegundos = (c.getDataFinalizacao().getTime() - c.getDataInicio().getTime());
			    float minDiferenca = Float.valueOf(diferencaSegundos) / (1000*60);
				
				Calendar cInicio = Calendar.getInstance();
				cInicio.setTime(c.getDataInicio());
				
				Calendar cfim = Calendar.getInstance();
				cfim.setTime(c.getDataFinalizacao());
				
				if (chegaFdsFeriado(c.getDataFinalizacao(), c.getCidade())) {
					
					valorFinal = recuperaValorCalculado(minDiferenca, 2, c.getTipoVeiculo(), cidade, 
							Float.valueOf(c.getDistanciaKm()), c.getIndicadorBicicleta(), qtdSegundosParado);
					
				} else {
					
					// verificar horas se bandeira 1 ou 2
					if (cidade.getHorarioInicioBandeira2() != null 
							&& !cidade.getHorarioInicioBandeira2().isEmpty() 
							&& cidade.getHorarioFimBandeira2() != null 
							&& !cidade.getHorarioFimBandeira2().isEmpty()) {
						
						if (cidade.getHorarioInicioBandeira2().contains(":")) {
							cInicio.set(Calendar.HOUR, Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[0]));
							cInicio.set(Calendar.MINUTE,  Integer.valueOf(cidade.getHorarioInicioBandeira2().split(":")[1]));
						} else {
							cInicio.set(Calendar.HOUR,  Integer.valueOf(cidade.getHorarioInicioBandeira2()));
							cInicio.set(Calendar.MINUTE, 0);
						}
						
						if (cidade.getHorarioFimBandeira2().contains(":")) {
							cfim.set(Calendar.HOUR, Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[0]));
							cfim.set(Calendar.MINUTE,  Integer.valueOf(cidade.getHorarioFimBandeira2().split(":")[1]));
						} else {
							cfim.set(Calendar.HOUR,  Integer.valueOf(cidade.getHorarioFimBandeira2()));
							cfim.set(Calendar.MINUTE, 0);
						}
						
						// Dentro da Bandeira dois
						if (c.getDataInicio().compareTo(cInicio.getTime()) >= 0 && cfim.getTime().compareTo(c.getDataInicio()) >= 0
								&& c.getDataFinalizacao().compareTo(cInicio.getTime()) >= 0 && cfim.getTime().compareTo(c.getDataFinalizacao()) >= 0) {
							
							valorFinal = recuperaValorCalculado(minDiferenca, 2, c.getTipoVeiculo(), cidade, 
									Float.valueOf(c.getDistanciaKm()), c.getIndicadorBicicleta(), qtdSegundosParado);
							
						} else if (c.getDataInicio().compareTo(cInicio.getTime()) >= 0 && cfim.getTime().compareTo(c.getDataInicio()) >= 0) {
							
							valorFinal = recuperaValorCalculado(minDiferenca, 2, c.getTipoVeiculo(), cidade, 
									Float.valueOf(c.getDistanciaKm()), c.getIndicadorBicicleta(), qtdSegundosParado);
							
						} else if (c.getDataFinalizacao().compareTo(cInicio.getTime()) >= 0 && cfim.getTime().compareTo(c.getDataFinalizacao()) >= 0) {
							
							valorFinal = recuperaValorCalculado(minDiferenca, 2, c.getTipoVeiculo(), cidade, 
									Float.valueOf(c.getDistanciaKm()), c.getIndicadorBicicleta(), qtdSegundosParado);
							
							// Dentro da Bandeira um
						} else {
							
							valorFinal = recuperaValorCalculado(minDiferenca, 1, c.getTipoVeiculo(), cidade, 
									Float.valueOf(c.getDistanciaKm()), c.getIndicadorBicicleta(), qtdSegundosParado);
						}
						
					} else {
						
						valorFinal = recuperaValorCalculado(minDiferenca, 1, c.getTipoVeiculo(), cidade, 
								Float.valueOf(c.getDistanciaKm()), c.getIndicadorBicicleta(), qtdSegundosParado);
					}
					
				}
			}
			
			if (c.getIndicadorTeleTaxi() != null && !c.getIndicadorTeleTaxi().equals(0)) {
			
				c.setValorFinal(valorFinal.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				
			} else {
				
				if (c.getValorFinal() == null) {
					
					// Credito e porcentagem apenas na hora da transação
					if (corrida.getDescontoCorrida() != null && corrida.getDescontoCorrida().floatValue() > 0
							&& (corrida.getIndicadorSemDesconto() == null || !corrida.getIndicadorSemDesconto().equals(1))) {
						
						valorFinal = valorFinal.subtract(BigDecimal.valueOf(valorFinal.floatValue() 
								* (corrida.getDescontoCorrida().floatValue() / 100)));
						
					} else {
						if (porcentagem != null && porcentagem.floatValue() > 0
								&& (corrida.getIndicadorSemDesconto() == null || !corrida.getIndicadorSemDesconto().equals(1))) {
							valorFinal = valorFinal.subtract(BigDecimal.valueOf(valorFinal.floatValue() * (porcentagem.floatValue() / 100)));
						}
						
						if (descontoLojista != null && descontoLojista.getIdUsuarioCliente() != null 
								&& cidade.getPorcentagemDescontoLojista() != null
								&& (corrida.getIndicadorSemDesconto() == null || !corrida.getIndicadorSemDesconto().equals(1))) {
							valorFinal = BigDecimal.valueOf(valorFinal.floatValue() - 
									(valorFinal.floatValue() * cidade.getPorcentagemDescontoLojista().floatValue() / 100));
						}
					}
					if (valorFinal.floatValue() <= cidade.getValorMinimoCorrida().floatValue()) {
						valorFinal = cidade.getValorMinimoCorrida();
					}
					
					c.setValorFinal(valorFinal.setScale(2, BigDecimal.ROUND_HALF_EVEN));
				}
			}
		}
		
		if (c.getFormaPagamento() != null && c.getFormaPagamento().equals(FormaPagamentoEnum.CREDITO.getCodigo())) {
			
			if (credito != null && credito.getValor() != null && credito.getValor().doubleValue() > 0) {
				
				if (credito.getValor().doubleValue() > c.getValorFinal().doubleValue()) {
					c.setValorCreditoUtilizado(c.getValorFinal());
				} else {
					c.setValorCreditoUtilizado(credito.getValor());
				}
			}
			
		}
		
		if (c.getValorFinal() != null && c.getValorFinal().doubleValue() < 5.9) {
			c.setValorFinal(new BigDecimal(5.9).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		}
		salvarCorrida(c);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				if (usuario.getQtdCorridasRealizadas() == null) {
					usuario.setQtdCorridasRealizadas(1l);
				} else {
					usuario.setQtdCorridasRealizadas(usuario.getQtdCorridasRealizadas() + 1);
				}
				usuarioService.salvarUsuario(usuario);
				
				if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
					MapaCorridaUtil.removerCorridaAndamento(c);
					MapaCorridaUtil.adicionarCorridaAndamento(c);
				} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
					MapaEntregaUtil.removerEntregaAndamento(c);
					MapaEntregaUtil.adicionarEntregaAndamento(c);
				}
				
				Transacao transacao = new Transacao();
				transacao.setCorrida(c);
				transacao.setDataTransacao(new Date());
				if (c.getValorCreditoUtilizado() != null && c.getValorCreditoUtilizado().doubleValue() > 0) {
					transacao.setValor(BigDecimal.valueOf(c.getValorFinal().doubleValue() - c.getValorCreditoUtilizado().doubleValue()));
				} else {
					transacao.setValor(c.getValorFinal());
				}
				if (c.getFormaPagamento() != null && c.getFormaPagamento().equals(FormaPagamentoEnum.CREDITO.getCodigo())
					&& transacao.getValor().doubleValue() > 0) {
					transacao.setPagamento(pagamentoService.recuperarPagamentoPadrao(c.getUsuario()));
					transacao.setStatus(StatusTransacaoEnum.PENDENTE.getStatus());
					
					transacao = transacaoService.salvarTransacao(transacao);
					transacaoService.efetivarTransacao(transacao);
				} else {
					transacao.setStatus(StatusTransacaoEnum.PAGAMENTO_APROVADO.getStatus());
					transacaoService.salvarTransacao(transacao);
				}
				
				if (c.getValorCreditoUtilizado() != null && c.getValorCreditoUtilizado().doubleValue() > 0) {
					creditoService.salvar(corrida.getUsuario().getId(), new BigDecimal(c.getValorCreditoUtilizado().doubleValue() * -1));
				}
				
				if (descontoLojista != null && descontoLojista.getIdUsuarioCliente() != null) {
					descontoLojista.setDataUtilizacao(new Date());
					descontoLojistaService.salvar(descontoLojista);
				}
				
				UsuarioNotificacao usuarioNotificacao = new UsuarioNotificacao();
				usuarioNotificacao.setIdNotificacao(new Long(1l));
				usuarioNotificacao.setIdUsuario(c.getUsuario().getId());
				usuarioNotificacao.setTexto("Sua corrida para " 
						+ c.getDestino() + " foi finalizada em " 
						+ new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(c.getDataFinalizacao()));
				usuarioNotificacao.setDataNotificacao(new Date());
				usuarioNotificacaoService.salvar(usuarioNotificacao);
				
				try {
					if (c.getIdEmpresaConveniada() != null && c.getVoucher() != null 
							&& !c.getVoucher().isEmpty()) {
						EmpresaConveniada empresa = empresaConveniadaService.consultarPorChave(c.getIdEmpresaConveniada());
						if (empresa.getIndicadorVoucherEletronico() != null
								&& (empresa.getIndicadorVoucherEletronico().equals(1) ||
										empresa.getIndicadorVoucherEletronico().equals(6))) {
							empresaConveniadaService.enviarEmailVoucherFinalizado(c, empresa);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				List<Familia> familiares = familiaService.recuperarFamiliaresUsuario(c.getUsuario());
				for (Familia familia : familiares) {
						
					try {
						
						Usuario usuario = usuarioService.recuperarPorLogin(familia.getEmail());
						UsuarioNotificacao usuarioNotificacao2 = new UsuarioNotificacao();
						usuarioNotificacao2.setIdNotificacao(new Long(1l));
						usuarioNotificacao2.setIdUsuario(usuario.getId());
						usuarioNotificacao2.setTexto("Seu familiar cadastrado " 
								+ c.getUsuario().getNome() + " finalizou uma corrida para " 
								+ c.getDestino() + " em " 
								+ new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(c.getDataFinalizacao()));
						usuarioNotificacao2.setDataNotificacao(new Date());
						usuarioNotificacaoService.salvar(usuarioNotificacao2);
					} catch (Exception e) { }
				}
			}
		}).start();
		
		if (c.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
			MapaCorridaUtil.adicionarCorridaAndamento(c);
		} else if (c.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
			MapaEntregaUtil.adicionarEntregaAndamento(c);
		}
		
		return c;
	}
	
	private Float recuperarDistanciaPercorrida(Corrida corrida) {
		
		Float distancia = 0f;
		List<Percurso> percursos = percursoService.recuperarPercursosCorrida(corrida);
		String latitude = null;
		String longitude = null;
		for (Percurso percurso : percursos) {
			if ((latitude == null || !latitude.isEmpty()) ||
					longitude == null || !longitude.isEmpty()) {
				latitude = percurso.getLatitude();
				longitude = percurso.getLongitude();
			} else {
				Float latAnterior = Float.parseFloat(latitude);
				Float lngAnterior = Float.parseFloat(longitude);
				Float lat = Float.parseFloat(percurso.getLatitude());
				Float lng = Float.parseFloat(percurso.getLongitude());
				if (latAnterior != lat && lngAnterior != lng) {
					distancia = distancia + recuperarDistanciaCoordenadas(latAnterior, lngAnterior, lat, lng).floatValue();
				}
			}
		}
		return distancia;
	}
	
	private Double recuperarDistanciaCoordenadas(Float latAnterior, Float lngAnterior, Float lat, Float lng) {
		
		Double distanciaCoordenadas = 0d;
		distanciaCoordenadas = DistanciaUtil.distance(latAnterior, lat, lngAnterior, lng,0.0, 0.0);
		return distanciaCoordenadas;
	}

	private boolean chegaFdsFeriado(Date dataFinalizacao, Cidade cidade) {
		
		Boolean verifica = Boolean.FALSE;
        Calendar c = Calendar.getInstance();
        c.setTime(dataFinalizacao);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
        	verifica = Boolean.TRUE;
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            verifica = Boolean.TRUE;
        } else {
        	verifica = Boolean.FALSE;
        }
        
        if (!verifica) {
        	verifica = feriadoService.verificaDiaFeriado(dataFinalizacao, cidade);
        }
        return verifica;
	}


	private BigDecimal recuperaValorCalculado(Float minutos, Integer bandeira, Integer tipo, Cidade cidade, Float distanciaKm, 
			Integer indicadorBicicleta, Long qtdSegundosParado) {
		
		BigDecimal valor = null;
		
		if (tipo == 1 && bandeira == 1) {
			
			valor = BigDecimal.valueOf(cidade.getValorBandeirada().floatValue() 
					+ (cidade.getValorKmBandeira1().floatValue() * distanciaKm)
					+ (((qtdSegundosParado * 5) * cidade.getValorHoraParada().floatValue()) / 3600));
		
		} else if (tipo == 1 && bandeira == 2) {
			
			valor = BigDecimal.valueOf(cidade.getValorBandeirada().floatValue() 
					+ (cidade.getValorKmBandeira2().floatValue() * distanciaKm)
					+ (((qtdSegundosParado * 5) * cidade.getValorHoraParada().floatValue()) / 3600));
		
		} else if (tipo == 2 && bandeira == 1) {
			
			if (cidade.getValorAdicionalExecutivo() != null) {
				
				valor = BigDecimal.valueOf(cidade.getValorBandeirada().floatValue() 
						+ (cidade.getValorKmBandeira1().floatValue() * distanciaKm)
						+ (((qtdSegundosParado * 5) * cidade.getValorHoraParada().floatValue()) / 3600) 
						+ cidade.getValorAdicionalExecutivo().floatValue());
				
			} else if (cidade.getValorPorcentagemAdicionalExecutivo() != null) {
				
				valor = BigDecimal.valueOf(cidade.getValorBandeirada().floatValue() 
						+ (cidade.getValorKmBandeira1().floatValue() * distanciaKm)
						+ (((qtdSegundosParado * 5) * cidade.getValorHoraParada().floatValue()) / 3600));
				
				valor = valor.add(BigDecimal.valueOf(valor.floatValue() 
						* (cidade.getValorPorcentagemAdicionalExecutivo().floatValue() / 100)));
			}
		
		} else if (tipo == 2 && bandeira == 2) {
			
			if (cidade.getValorAdicionalExecutivo() != null) {
				
				valor = BigDecimal.valueOf(cidade.getValorBandeirada().floatValue() 
						+ (cidade.getValorKmBandeira2().floatValue() * distanciaKm)
						+ (((qtdSegundosParado * 5) * cidade.getValorHoraParada().floatValue()) / 3600) 
						+ cidade.getValorAdicionalExecutivo().floatValue());
				
			} else if (cidade.getValorPorcentagemAdicionalExecutivo() != null) {
				
				valor = BigDecimal.valueOf(cidade.getValorBandeirada().floatValue() 
						+ (cidade.getValorKmBandeira2().floatValue() * distanciaKm)
						+ (((qtdSegundosParado * 5) * cidade.getValorHoraParada().floatValue()) / 3600));
				
				valor = valor.add(BigDecimal.valueOf(valor.floatValue() 
						* (cidade.getValorPorcentagemAdicionalExecutivo().floatValue() / 100)));
			}
			
		}
		
		if (indicadorBicicleta != null && indicadorBicicleta == 1) {
			
			if (cidade.getValorAdicionalSuporteBike() != null) {

				valor = valor.add(cidade.getValorAdicionalSuporteBike());

			} else if (cidade.getValorPorcentagemAdicionalSuporteBike() != null) {

				valor = valor.add(BigDecimal.valueOf(valor.floatValue() 
						* (cidade.getValorPorcentagemAdicionalSuporteBike().floatValue() / 100)));
			}
		}
		return valor;
	}

	private BigDecimal definirPorcentagemDesconto(List<Promocao> promocoes, Usuario usuario, BigDecimal porcentagem) {
		
		for (Promocao promocao : promocoes) {
			if (promocao.getTipo().equals(PromocaoEnum.PRIMEIRA_CORRIDA.getCodigo())
					&& (usuario.getQtdCorridasRealizadas() == null || usuario.getQtdCorridasRealizadas() == 0)) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.SEGUNDA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 1) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.TERCEIRA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 2) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.QUARTA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 3) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.QUINTA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 4) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.SEXTA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 5) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.SETIMA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 6) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.OITAVA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 7) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.NOVA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 8) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.DECIMA_CORRIDA.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 9) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.APOS_10_CORRIDAS.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 10) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.CADA_5_CORRIDAS.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && (usuario.getQtdCorridasRealizadas() + 1) % 5 == 2) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.CADA_10_CORRIDAS.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && (usuario.getQtdCorridasRealizadas() + 1) % 10 == 2) {
				porcentagem = promocao.getPorcentagem();
				break;
			} else if (promocao.getTipo().equals(PromocaoEnum.CADA_15_CORRIDAS.getCodigo())
					&& usuario.getQtdCorridasRealizadas() != null && (usuario.getQtdCorridasRealizadas() + 1) % 15 == 2) {
				porcentagem = promocao.getPorcentagem();
				break;
			}
		}
		return porcentagem;
	}

	@Override
	public List<Corrida> recuperarCorridasCorrentesNaoFinalizadas() {
		return corridaRepository.recuperarCorridasCorrentesNaoFinalizadas();
	}

	@Override
	public Long pesquisarCountCorridaTelePorFiltro(Corrida filtro) {
		return corridaRepository.pesquisarCountCorridaTelePorFiltro(filtro);
	}

	@Override
	public List<Corrida> pesquisarCorridaTelePorFiltro(Corrida filtro, int first, int pageSize) {
		return corridaRepository.pesquisarCorridaTelePorFiltro(filtro, first, pageSize);
	}

	@Override
	public BigDecimal recuperarValorConsumido(EmpresaConveniada empresaConveniada, String mesReferencia, Integer tipoTeleTaxi, Long idCentroCusto) {
		return corridaRepository.recuperarValorConsumido(empresaConveniada, mesReferencia, tipoTeleTaxi, idCentroCusto);
	}

	@Override
	public BigDecimal recuperarValorConsumidoMotorista(Motorista motorista, String mesReferencia,
			Integer tipoTeleTaxi) {
		return corridaRepository.recuperarValorConsumidoMotorista(motorista, mesReferencia, tipoTeleTaxi);
	}

	@Override
	public Integer recuperarQtdCorridaTelePorTipo(Integer tipo) {
		return corridaRepository.recuperarQtdCorridaTelePorTipo(tipo);
	}
	
	@Override
	public List<VisualizacaoCorrida> recuperarVisualizacoesCorrida(Corrida corrida) {
		return visualizacaoCorridaRepository.recuperarVisualizacoesCorrida(corrida);
	}

	@Override
	public void salvarVisualizacao(VisualizacaoCorrida visualizacaoCorrida) {
		
		if (!visualizacaoCorridaRepository.verificaExistenciaVisualizacao(visualizacaoCorrida)) {
			visualizacaoCorridaRepository.save(visualizacaoCorrida);
		}
	}
	
	public Integer recuperarQtdCorridaCanceladaTelePorTipo(Integer tipo) {
		return corridaRepository.recuperarQtdCorridaCanceladaTelePorTipo(tipo);
	}

	@Override
	public Integer recuperarQtdCorridaTelePorStatus(Integer status) {
		return corridaRepository.recuperarQtdCorridaTelePorStatus(status);
	}

	@Override
	public BigDecimal recuperarValorConsumidoMotoristaFormaPagamento(Motorista motorista, String mesReferencia,
			Integer codigo) {
		return corridaRepository.recuperarValorConsumidoMotoristaFormaPagamento(motorista, mesReferencia, codigo);
	}

	@Override
	public void alterar(Corrida corrida) {
		corridaRepository.save(corrida);
	}

	@Override
	public List<Corrida> recuperarCorridasAgendadasParaMapas() {
		return corridaRepository.recuperarCorridasAgendadasParaMapas();
	}

	@Override
	public BigDecimal recuperarValorConsumidoVeiculo(Veiculo veiculo, String mesReferencia, Integer tipoTeleTaxi) {
		return corridaRepository.recuperarValorConsumidoVeiculo(veiculo, mesReferencia, tipoTeleTaxi);
	}

}