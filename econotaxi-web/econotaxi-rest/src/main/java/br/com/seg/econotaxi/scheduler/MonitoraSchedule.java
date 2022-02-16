package br.com.seg.econotaxi.scheduler;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.seg.econotaxi.enums.FormaPagamentoEnum;
import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.StatusTransacaoEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Transacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.model.UsuarioNovidade;
import br.com.seg.econotaxi.model.UsuarioNovidadePk;
import br.com.seg.econotaxi.repository.ParametrosRepository;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.NotificacaoService;
import br.com.seg.econotaxi.service.NovidadeService;
import br.com.seg.econotaxi.service.TransacaoService;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;
import br.com.seg.econotaxi.service.UsuarioNovidadeService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.util.EmailUtil;
import br.com.seg.econotaxi.util.MapaCarroUtil;
import br.com.seg.econotaxi.util.MapaCidadeUtil;
import br.com.seg.econotaxi.util.MapaCorridaUtil;
import br.com.seg.econotaxi.util.MapaEntregaUtil;
import br.com.seg.econotaxi.util.MapaMensagensUtil;
import br.com.seg.econotaxi.vo.CarrosVO;
import br.com.seg.econotaxi.vo.EmailVO;

@Component
@Configuration
@EnableScheduling
public class MonitoraSchedule implements Serializable {

	// Constantes
	private static final long serialVersionUID = -481564721005030107L;
	
	// Atributos
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private TransacaoService transacaoService;
	@Autowired
    private ParametrosRepository parametrosRepository;
	@Autowired
	private NotificacaoService notificacaoService;
	@Autowired
	private NovidadeService novidadeService;
	@Autowired
	private UsuarioNotificacaoService usuarioNotificacaoService;
	@Autowired
	private UsuarioNovidadeService usuarioNovidadeService;
	@Autowired
	private UsuarioService usuarioService;
	//@Autowired
	//private SimpMessagingTemplate simpMessagingTemplate;
	
	/*@Scheduled(fixedRate = 10000)
	public void enviaMensagemCorridaAndamento() {
		Set<Long> idCidades = MapaCidadeUtil.getInstance().getMapaCidade().keySet();
		for (Long idCidade : idCidades) {
			List<Corrida> corridas = MapaCorridaUtil.recuperarCorridasDisponiveis(idCidade);
			if (corridas != null) {
				for (Corrida corrida : corridas) {
					
					if (corrida.getCarrosProximos() != null) {
						StringBuilder carros = new StringBuilder();
						for (CarrosVO carro : corrida.getCarrosProximos()) {
							carros.append("#" + carro.getIdMotorista() + "-");
						}
						this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
								"solicitar-corrida-id-" + corrida.getId() + "-carros-" + carros.toString());
					}
				}
			}
		}
	}*/
	
	@Scheduled(fixedRate = 600000000)
	public void startMensagens() {
		MapaMensagensUtil.start();
	}
	
	@Scheduled(cron="0 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58 * * * ?")
	public void verificarCorridasPendentesParaCancelamentoRadio() {

		Date data = new Date();
		try {
			
			List<Corrida> corridas = corridaService.recuperarCorridasPendentesParaCancelamento(data, Boolean.TRUE);
			for (Corrida corrida : corridas) {
				if (corrida.getDataSolicitacao().getTime() < recuperarData10Minutos(data).getTime()) {
					corrida.setStatus(StatusCorridaEnum.CANCELADA_TEMPO.getStatus());
					corrida.setDataFinalizacao(new Date());
					corridaService.salvarCorrida(corrida);
					if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
						
						Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
						
						MapaCorridaUtil.removerCorrida(corrida);
						MapaCorridaUtil.removerCorridaAndamento(corrida);
						
						if (cMapa.getCarrosProximos() != null) {
							StringBuilder carros2 = new StringBuilder();
							for (CarrosVO car : cMapa.getCarrosProximos()) {
								carros2.append("#" + car.getIdMotorista() + "-");
							}
							//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
								//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros2.toString());
						}
						
					} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
						MapaEntregaUtil.removerEntrega(corrida);
						MapaEntregaUtil.removerEntregaAndamento(corrida);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Corrida> corridasPendentes = corridaService.recuperarCorridasPendentesParaCancelamento(data, Boolean.FALSE);
			for (Corrida corrida : corridasPendentes) {
				if (corrida.getDataSolicitacao().getTime() < recuperarData2Minutos(data).getTime()) {
					corrida.setStatus(StatusCorridaEnum.CANCELADA_TEMPO.getStatus());
					corrida.setDataFinalizacao(new Date());
					corridaService.salvarCorrida(corrida);
					if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
						
						Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
						
						MapaCorridaUtil.removerCorrida(corrida);
						MapaCorridaUtil.removerCorridaAndamento(corrida);
						
						if (cMapa.getCarrosProximos() != null) {
							StringBuilder carros2 = new StringBuilder();
							for (CarrosVO car : cMapa.getCarrosProximos()) {
								carros2.append("#" + car.getIdMotorista() + "-");
							}
							//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
								//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros2.toString());
						}
						
					} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
						MapaEntregaUtil.removerEntrega(corrida);
						MapaEntregaUtil.removerEntregaAndamento(corrida);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Set<Long> idCidades = MapaCidadeUtil.getInstance().getMapaCidade().keySet();
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.add(Calendar.SECOND, 90);
			for (Long idCidade : idCidades) {
				List<Corrida> corridas = MapaCorridaUtil.recuperarCorridasAgendadas(idCidade);
				if (corridas != null && !corridas.isEmpty()) {
					for (Corrida corrida : corridas) {
						if (corrida.getDataAgendamentoFinal() != null) {
							if (cal.getTime().getTime() >= corrida.getDataAgendamentoFinal().getTime()) {
								Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
								c.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
								corrida.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
								c.setDataSolicitacao(new Date());
								corrida.setDataSolicitacao(c.getDataSolicitacao());
								corridaService.alterar(c);
								
								if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
									MapaCorridaUtil.removerCorridaAgendamento(corrida);
									MapaCorridaUtil.adicionarCorrida(corrida);
									MapaCorridaUtil.adicionarCorridaAndamento(corrida);
									
									if (corrida.getCarrosProximos() != null) {
										StringBuilder carros2 = new StringBuilder();
										for (CarrosVO car : corrida.getCarrosProximos()) {
											carros2.append("#" + car.getIdMotorista() + "-");
										}
										//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
											//	"solicitar-corrida-id-" + corrida.getId() + "-carros-" + carros2.toString());
									}
									
								} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
									MapaEntregaUtil.removerEntregaAgendamento(corrida);
									MapaEntregaUtil.adicionarEntrega(corrida);
									MapaEntregaUtil.adicionarEntregaAndamento(corrida);
									//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
								}
								
							}
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0,10,20,30,40,50 * * * * ?")
	public void verificarCorridasCanceladas() {

		try {
			Set<Long> idCidades = MapaCidadeUtil.getInstance().getMapaCidade().keySet();
			for (Long idCidade : idCidades) {
				List<Corrida> corridas = MapaCorridaUtil.recuperarCorridasDisponiveis(idCidade);
				if (corridas != null && !corridas.isEmpty()) {
					for (Corrida corrida : corridas) {
						Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
						
						if (c.getStatus().equals(StatusCorridaEnum.CANCELADA.getStatus()) 
								|| c.getStatus().equals(StatusCorridaEnum.CANCELADA_TEMPO.getStatus())) {
							
							System.out.println("Existe corrida cancelada q n saiu do mapa: " 
									+ c.getNomePassageiro() + " status: " + c.getStatus());
							
							if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
								Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());

								MapaCorridaUtil.removerCorrida(corrida);
								MapaCorridaUtil.removerCorridaAndamento(corrida);
								
								if (cMapa.getCarrosProximos() != null) {
									StringBuilder carros2 = new StringBuilder();
									for (CarrosVO car : cMapa.getCarrosProximos()) {
										carros2.append("#" + car.getIdMotorista() + "-");
									}
									//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
										//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros2.toString());
								}
								
							} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
								MapaEntregaUtil.removerEntrega(corrida);
								MapaEntregaUtil.removerEntregaAndamento(corrida);
							}
							
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Novidade> novidades = novidadeService.recuperarNovidades2Dias();
			for (Novidade novidade : novidades) {
				int qtd = usuarioService.quantidadeUsuariosSemNovidade(novidade);
				while (qtd > 0) {
					List<Usuario> usuarios  = usuarioService.recuperarUsuariosSemNovidade(novidade);
					for (Usuario usuario : usuarios) {
						UsuarioNovidade usuarioNovidade = new UsuarioNovidade();
						UsuarioNovidadePk pk = new UsuarioNovidadePk();
						pk.setIdNovidade(novidade.getId());
						pk.setIdUsuario(usuario.getId());
						usuarioNovidade.setId(pk);
						usuarioNovidadeService.salvar(usuarioNovidade);
						MapaMensagensUtil.adicionarNovidade(usuarioNovidade);
					}
					qtd = usuarioService.quantidadeUsuariosSemNovidade(novidade);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?")
	public void verificarTransacoesPendentesEnvio() {
		
		try {
			List<Corrida> corridas = corridaService.recuperarCorridasPendentesParaMapas();
			for (Corrida corrida : corridas) {
				if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
					
					if (MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId()) == null) {
						if (corrida.getStatus().equals(StatusCorridaEnum.SOLICITADO.getStatus())) {
							MapaCorridaUtil.adicionarCorrida(corrida);
						}
						MapaCorridaUtil.adicionarCorridaAndamento(corrida);
					}
					
				} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
					
					if (MapaEntregaUtil.recuperarEntregaPorId(corrida.getCidade().getId(), corrida.getId()) == null) {
						if (corrida.getStatus().equals(StatusCorridaEnum.SOLICITADO.getStatus())) {
							MapaEntregaUtil.adicionarEntrega(corrida);
						}
						MapaEntregaUtil.adicionarEntregaAndamento(corrida);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Corrida> corridas = corridaService.recuperarCorridasAgendadasParaMapas();
			for (Corrida corrida : corridas) {
				if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
					
					if (MapaCorridaUtil.recuperarCorridaAgendadaPorId(corrida.getCidade().getId(), corrida.getId()) == null) {
						Date date = new SimpleDateFormat("dd/MM/yyyyHH:mm").parse(corrida.getDataAgendamento()+corrida.getHoraAgendamento());
						corrida.setDataAgendamentoFinal(date);
						MapaCorridaUtil.adicionarCorridaAgendamento(corrida);
					}
					
				} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
					
					if (MapaEntregaUtil.recuperarEntregaAgendadaPorId(corrida.getCidade().getId(), corrida.getId()) == null) {
						Date date = new SimpleDateFormat("dd/MM/yyyyHH:mm").parse(corrida.getDataAgendamento()+corrida.getHoraAgendamento());
						corrida.setDataAgendamentoFinal(date);
						MapaEntregaUtil.adicionarEntregaAgendamento(corrida);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Corrida> corridas = corridaService.recuperarCorridasCorrentesNaoFinalizadas();
			if (corridas != null) {
				for (Corrida corrida : corridas) {
					corridaService.finalizarCorrida(corrida);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Notificacao> notificacoes = notificacaoService.recuperarNotificacoes2Dias();
			for (Notificacao notificacao : notificacoes) {
				Integer qtd = usuarioService.quantidadeUsuariosSemNotificacao(notificacao);
				while (qtd > 0) {
					List<Usuario> usuarios  = usuarioService.recuperarUsuariosSemNotificacao(notificacao);
					for (Usuario usuario : usuarios) {
						if (!usuarioNotificacaoService.verificaExistenciaNotificacaoUsuario(usuario, notificacao)) {
							UsuarioNotificacao usuarioNotificacao = new UsuarioNotificacao();
							usuarioNotificacao.setIdNotificacao(notificacao.getId());
							usuarioNotificacao.setIdUsuario(usuario.getId());
							usuarioNotificacao.setTexto(notificacao.getTexto());
							usuarioNotificacao.setDataNotificacao(new Date());
							usuarioNotificacaoService.salvar(usuarioNotificacao);
							MapaMensagensUtil.adicionarNotificacao(usuarioNotificacao);
						}
					}
					qtd = usuarioService.quantidadeUsuariosSemNotificacao(notificacao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Cidade> cidades = new ArrayList<Cidade>();
			List<Corrida> corridasExcluidas = new ArrayList<Corrida>();
			cidades.addAll(MapaCidadeUtil.getInstance().getMapaCidade().values());
			for (Cidade cidade : cidades) {
				corridasExcluidas.addAll(MapaCorridaUtil.recuperarCorridasFinalizadas(cidade.getId()));
				
				for (Corrida corrida : corridasExcluidas) {
					Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());
					
					MapaCorridaUtil.removerCorrida(corrida);
					MapaCorridaUtil.removerCorridaAndamento(corrida);
					
					if (cMapa != null && cMapa.getCarrosProximos() != null) {
						StringBuilder carros2 = new StringBuilder();
						for (CarrosVO car : cMapa.getCarrosProximos()) {
							carros2.append("#" + car.getIdMotorista() + "-");
						}
						//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
							//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros2.toString());
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Cidade> cidades = new ArrayList<Cidade>();
			List<Corrida> corridasExcluidas = new ArrayList<Corrida>();
			cidades.addAll(MapaCidadeUtil.getInstance().getMapaCidade().values());
			for (Cidade cidade : cidades) {
				corridasExcluidas.addAll(MapaEntregaUtil.recuperarEntregasFinalizadas(cidade.getId()));
				
				for (Corrida corrida : corridasExcluidas) {
					MapaEntregaUtil.removerEntrega(corrida);
					MapaEntregaUtil.removerEntregaAndamento(corrida);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			List<Transacao> transacoes = transacaoService.recuperarTransacoesPendentesEnvio();
			if (transacoes != null && !transacoes.isEmpty()) {
				
				Parametro parametro = parametrosRepository.recuperarParametro();
				for (Transacao transacao : transacoes) {
					
					try {
						EmailVO email = new EmailVO();
						StringBuilder mensagem = new StringBuilder();
						email.setAssunto(parametro.getNomeAplicativo() + " - Corrida Finalizada");
						mensagem.append("Este é um e-mail confirmando a finalização da sua corrida, iniciada em ");
						mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(transacao.getCorrida().getDataInicio()));
						mensagem.append(" e finalizada em ");
						mensagem.append(new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(transacao.getCorrida().getDataFinalizacao()) + ".");
						mensagem.append("<br /><br /> ");
						if (transacao.getCorrida().getNomePassageiro() != null && !transacao.getCorrida().getNomePassageiro().isEmpty()) {
							mensagem.append("<strong>Nome do Passageiro:</strong> " + transacao.getCorrida().getNomePassageiro());
							mensagem.append("<br /> ");
						}
						mensagem.append("<strong>Origem:</strong> " + transacao.getCorrida().getOrigem());
						mensagem.append("<br /> ");
						mensagem.append("<strong>Destino:</strong> " + transacao.getCorrida().getDestino());
						mensagem.append("<br /> ");
						mensagem.append("<strong>Motorista:</strong> " + transacao.getNome());
						mensagem.append("<br /> ");
						mensagem.append("<strong>Veículo:</strong> " + transacao.getCorrida().getVeiculo().getMarca()); 
						mensagem.append(", " + transacao.getCorrida().getVeiculo().getModelo() + ". Placa: " + transacao.getCorrida().getVeiculo().getPlaca());
						mensagem.append("<br /><br /> ");
						DecimalFormat decFormat = new DecimalFormat("'R$ ' 0.##");
						mensagem.append("<strong>Valor final da corrida</strong>: " + decFormat.format(transacao.getValor()));
						mensagem.append("<br /> ");
						if (transacao.getCorrida().getFormaPagamento() != null && transacao.getCorrida().getFormaPagamento().equals(FormaPagamentoEnum.CREDITO.getCodigo())) {
							mensagem.append("<strong>Status do Pagamento</strong>: Pagamento realizado com cartão de crédito (via Aplicativo) ou crédito disponivel no próprio aplicativo.<br /> ");
							mensagem.append("<strong>Status do Pagamento</strong>: " + StatusTransacaoEnum.valueOfStatus(transacao.getStatus()).getDescricao());
						} else if (transacao.getCorrida().getFormaPagamento() != null && transacao.getCorrida().getFormaPagamento().equals(FormaPagamentoEnum.DEBITO.getCodigo())) {
							mensagem.append("<strong>Status do Pagamento</strong>: Pagamento realizado com cartão de débito através de Máquina de Cartão do próprio motorista.<br /> ");
						} else if (transacao.getCorrida().getFormaPagamento() != null && transacao.getCorrida().getFormaPagamento().equals(FormaPagamentoEnum.DINHEIRO.getCodigo())) {
							mensagem.append("<strong>Status do Pagamento</strong>: Pagamento realizado em dinheiro diretamente ao Motorista.<br /> ");
						}
						mensagem.append("<br /><br /> ");
						if (transacao.getStatus().equals(StatusTransacaoEnum.PAGAMENTO_REJEITADO.getStatus())) {
							mensagem.append("<strong>ATENÇÃO:</strong> Como seu pagamento foi rejeitado, seu saldo no aplicativo ");
							mensagem.append("ficará negativo. Até a quitação do débito, não será possível realizar novas corridas. ");
							mensagem.append("O aplicativo se reserva o direito de tentar realizar a cobrança a qualquer momento. ");
							mensagem.append("É possível quitar seu débito no próprio aplicativo na funcionalidade 'Pagamento', ");
							mensagem.append("no menu superior 'Comprar Créditos'. Lembramos que a não quitação dos débitos poderá acarretar ");
							mensagem.append("na negativação do Nome nas Entidades competentes. ");
							mensagem.append("<br /><br /> ");
						}
						mensagem.append("Caso você não reconheça essa corrida, entre em contato pelo e-mail " + parametro.getEmailCorridasTele());
						mensagem.append("<br /><br /> ");
						mensagem.append("Atenciosamente,<br /> ");
						mensagem.append("<strong>Equipe " + parametro.getNomeAplicativo() + "</strong>");
						email.setMensagem(mensagem.toString());
						email.setNomeRemetente(parametro.getUsuarioEmail());
						if (transacao.getCorrida().getIndicadorTeleTaxi() != null && transacao.getCorrida().getIndicadorTeleTaxi().equals(1)) {
							email.setDestinatarios(parametro.getEmailCorridasTele());
						} else {
							email.setDestinatarios(transacao.getCorrida().getUsuario().getLogin());
						}
						email.setServidorSMTP(parametro.getUrlSmtp());
						email.setUsuarioEmail(parametro.getUsuarioEmail());
						email.setSenhaEmail(parametro.getSenhaEmail());
						email.setPortaServidorSMTP(parametro.getPortaSmtp());
						email.setTitulo(parametro.getNomeAplicativo() + " - Notifica!");
					
						EmailUtil.sendEmail(email);
						transacao.setIndicadorEmailEnviado(1);
						transacaoService.salvarTransacao(transacao);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="0 * * * * ?")
	public void removerCarrosOffline() {
		
		Date dataCorrente = new Date();
		List<Cidade> cidades = new ArrayList<Cidade>();
		List<CarrosVO> carrosExcluidos = new ArrayList<CarrosVO>();
		cidades.addAll(MapaCidadeUtil.getInstance().getMapaCidade().values());
		for (Cidade cidade : cidades) {
			List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosPorCidade(cidade);
			if (carros != null && !carros.isEmpty()) {
				for (CarrosVO carrosVO : carros) {
					try {
						if (carrosVO.getDataUltimaPosicao().getTime() < recuperaData1Minuto(dataCorrente).getTime()) {
							carrosExcluidos.add(carrosVO);
						}
					} catch (Exception e) {
						//System.out.println("Carro n removido motorista: " + carrosVO.getNomeMotorista());
						//System.out.println("Carro n removido motorista: " + carrosVO.getPlaca());
						//System.out.println("Problema ao adicionar na lista de excluidos");
						e.printStackTrace();
					}
				}
				for (CarrosVO carrosVO : carrosExcluidos) {
					try {
						MapaCarroUtil.removerCarro(carrosVO, cidade);
						//System.out.println("Carro removido motorista: " + carrosVO.getNomeMotorista());
						//System.out.println("Carro removido motorista: " + carrosVO.getPlaca());
					} catch (Exception e) {
						//System.out.println("Carro n removido motorista: " + carrosVO.getNomeMotorista());
						//System.out.println("Carro n removido motorista: " + carrosVO.getPlaca());
						//System.out.println("Problema ao remover carro");
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private Date recuperaData1Minuto(Date dataCorrente) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -1);
		return c.getTime();
	}
	
	private Date recuperarData10Minutos(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -10);
		return c.getTime();
	}
	
	private Date recuperarData2Minutos(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, -2);
		return c.getTime();
	}
	
}