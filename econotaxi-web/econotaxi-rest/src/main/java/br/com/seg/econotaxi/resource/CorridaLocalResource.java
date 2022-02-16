/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.util.MapaCarroUtil;
import br.com.seg.econotaxi.util.MapaCidadeUtil;
import br.com.seg.econotaxi.util.MapaCorridaChatUtil;
import br.com.seg.econotaxi.util.MapaCorridaUtil;
import br.com.seg.econotaxi.util.MapaEntregaUtil;
import br.com.seg.econotaxi.util.MapaMensagensUtil;
import br.com.seg.econotaxi.util.MapaPontoApoioUtil;
import br.com.seg.econotaxi.util.MapaPromocaoUtil;
import br.com.seg.econotaxi.util.MapaRegiaoUtil;
import br.com.seg.econotaxi.vo.CarrosVO;
import br.com.seg.econotaxi.vo.MensagemCorridaVO;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/corridaLocal")
public class CorridaLocalResource {
	
	//@Autowired
	//private SimpMessagingTemplate simpMessagingTemplate;

	@RequestMapping(value = "/adicionarCorridaLocal", method = RequestMethod.POST)
	public void adicionarCorridaLocal(@RequestBody Corrida corrida) {
		
		if (corrida.getStatus().equals(StatusCorridaEnum.AGENDADA.getStatus())) {
			
			if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
				MapaCorridaUtil.adicionarCorridaAgendamento(corrida);
			} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
				MapaEntregaUtil.adicionarEntregaAgendamento(corrida);
			}
			
		} else {
			if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
				MapaCorridaUtil.adicionarCorrida(corrida);
				MapaCorridaUtil.adicionarCorridaAndamento(corrida);
				
			} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
				MapaEntregaUtil.adicionarEntrega(corrida);
				MapaEntregaUtil.adicionarEntregaAndamento(corrida);
				//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
			}
		}
		
	}
	
	@RequestMapping(value = "/removerCorridaLocal", method = RequestMethod.POST)
	public void removerCorridaLocal(@RequestBody Corrida corrida) {
		
		if (corrida.getStatus().equals(StatusCorridaEnum.AGENDADA.getStatus())) {
			
			if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
				MapaCorridaUtil.removerCorridaAgendamento(corrida);
			} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
				MapaEntregaUtil.removerEntregaAgendamento(corrida);
			}
			
		} else {
			
			if (corrida.getTipo().equals(TipoCorridaEnum.CORRIDA.getCodigo())) {
				
				Corrida cMapa = MapaCorridaUtil.recuperarCorridaPorId(corrida.getCidade().getId(), corrida.getId());

				MapaCorridaUtil.removerCorrida(corrida);
				MapaCorridaUtil.removerCorridaAndamento(corrida);
				
				if (cMapa.getCarrosProximos() != null) {
					StringBuilder carros = new StringBuilder();
					for (CarrosVO carro : cMapa.getCarrosProximos()) {
						carros.append("#" + carro.getIdMotorista() + "-");
					}
					//this.simpMessagingTemplate.convertAndSend("/topic/notificar-solicitacao-corrida", 
						//	"solicitar-corrida-id-" + cMapa.getId() + "-carros-" + carros.toString());
				}
				
			} else if (corrida.getTipo().equals(TipoCorridaEnum.ENTREGA.getCodigo())) {
				MapaEntregaUtil.removerEntrega(corrida);
				MapaEntregaUtil.removerEntregaAndamento(corrida);
			}
		}
		
	}
	
	@RequestMapping(value = "/adicionarCidadeLocal", method = RequestMethod.POST)
	public void adicionarCidadeLocal(@RequestBody Cidade cidade) {
		
		MapaCidadeUtil.adicionarCidade(cidade);
	}
	
	@RequestMapping(value = "/adicionarPontoApoioLocal", method = RequestMethod.POST)
	public void adicionarPontoApoioLocal(@RequestBody Localidade localidade) {
		
		MapaPontoApoioUtil.adicionarLocalidade(localidade, localidade.getCidade());
	}
	
	@RequestMapping(value = "/adicionarRegiaoLocal", method = RequestMethod.POST)
	public void adicionarRegiaoLocal(@RequestBody Localidade localidade) {
		
		MapaRegiaoUtil.adicionarLocalidade(localidade, localidade.getCidade());
	}
	
	@RequestMapping(value = "/removerPontoApoioLocal", method = RequestMethod.POST)
	public void removerPontoApoioLocal(@RequestBody Localidade localidade) {
		
		MapaPontoApoioUtil.removerLocalidade(localidade, localidade.getCidade());
	}
	
	@RequestMapping(value = "/removerRegiaoLocal", method = RequestMethod.POST)
	public void removerRegiaoLocal(@RequestBody Localidade localidade) {
		
		MapaRegiaoUtil.removerLocalidade(localidade, localidade.getCidade());
	}
	
	@RequestMapping(value = "/carrosOnline", method = RequestMethod.POST)
	public List<CarrosVO> recuperarCarrosOnline(@RequestBody Cidade cidade) {
		return MapaCarroUtil.recuperarCarrosPorCidade(cidade);
	}
	
	@RequestMapping(value = "/localidades", method = RequestMethod.POST)
	public List<Localidade> recuperarLocalidades(@RequestBody Cidade cidade) {
		return MapaPontoApoioUtil.recuperarLocalidadesPorCidade(cidade);
	}
	
	@RequestMapping(value = "/regioes", method = RequestMethod.POST)
	public List<Localidade> recuperarRegioes(@RequestBody Cidade cidade) {
		return MapaRegiaoUtil.recuperarLocalidadesPorCidade(cidade, 3l);
	}
	
	@RequestMapping(value = "/mensagens", method = RequestMethod.POST)
	public List<MensagemCorridaVO> recuperarLocalidades(@RequestBody Corrida corrida) {
		return MapaCorridaChatUtil.recuperarMensagens(corrida.getId());
	}
	
	@RequestMapping(value = "/verificaMensagem", method = RequestMethod.POST)
	public @ResponseBody Usuario verificaMensagemChat(@RequestBody Usuario usuario) {
		
		return MapaCorridaChatUtil.verificaMensagemNova(usuario.getId());
	}
	
    @RequestMapping(value = "/visualizouMensagem", method = RequestMethod.POST)
	public void visualizarMensagem(@RequestBody Usuario usuario) {
		
		MapaCorridaChatUtil.retirarMensagemNova(usuario.getId());
	}
	
	@RequestMapping(value = "/adicionarMensagem", method = RequestMethod.POST)
	public void adicionarMensagem(@RequestBody MensagemCorridaVO mensagem) {
		MapaCorridaChatUtil.adicionarMensagem(mensagem);
		MapaCorridaChatUtil.definirMensagemNova(mensagem.getIdMotorista());
	}
	
	@RequestMapping(value = "/corridasAgora", method = RequestMethod.POST)
	public List<Corrida> recuperarCorridasRest(@RequestBody Cidade cidade) {
		return MapaCorridaUtil.recuperarCorridasDisponiveis(cidade.getId());
	}
	
	@RequestMapping(value = "/adicionarPromocao", method = RequestMethod.POST)
	public void adicionarPromocao(@RequestBody Promocao promocao) {
		MapaPromocaoUtil.adicionarPromocao(promocao);
	}
	
	@RequestMapping(value = "/removerPromocao", method = RequestMethod.POST)
	public void removerPromocao(@RequestBody Promocao promocao) {
		MapaPromocaoUtil.removerPromocao(promocao);
	}
	
	@RequestMapping(value = "/mudarPosicaoMotoristaLocal", method = RequestMethod.POST)
	public void mudarPosicaoMotoristaLocal(@RequestBody CarrosVO motorista) {
		
		Cidade cidade = new Cidade();
		cidade.setId(motorista.getIdCidade());
		MapaCarroUtil.adicionarCarro(motorista, cidade);
		System.out.println(motorista.getIdMotorista());
	}
	
	@RequestMapping(value = "/adicionarNotificacao", method = RequestMethod.POST)
	public void adicionarNotificacao(@RequestBody UsuarioNotificacao usuarioNotificacao) {
		MapaMensagensUtil.adicionarNotificacao(usuarioNotificacao);
	}
	
}