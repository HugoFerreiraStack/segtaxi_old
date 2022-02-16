/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.TipoCorridaEnum;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Percurso;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.PercursoService;
import br.com.seg.econotaxi.util.SpringContextUtil;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/entregas")
public class EntregasResource {

	@Autowired
	private CorridaService corridaService;
	@Autowired
	private PercursoService percursoService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/minhasEntregas", method = RequestMethod.POST)
	public List<Corrida> listarMinhasEntregas(@RequestBody Usuario usuario) {
		
		return corridaService.recuperarCorridasPorUsuario(usuario, TipoCorridaEnum.ENTREGA.getCodigo());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/solicitar", method = RequestMethod.POST)
	public void solicitarEntrega(@RequestBody Usuario usuario, Corrida corrida) {
		
		corrida.setTipo(TipoCorridaEnum.ENTREGA.getCodigo());
		corrida.setUsuario(usuario);
		corrida.setDataSolicitacao(new Date());
		corrida.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
		corridaService.salvarCorrida(corrida);
		
		SpringContextUtil.getBean(SimpMessagingTemplate.class).convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	public void cancelarEntrega(@RequestBody Usuario usuario, Corrida corrida) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		c.setDataFinalizacao(new Date());
		c.setStatus(StatusCorridaEnum.CANCELADA.getStatus());
		corridaService.salvarCorrida(c);
		
		SpringContextUtil.getBean(SimpMessagingTemplate.class).convertAndSend("/topic/notificar-solicitacao-corrida", "solicitar-entrega");
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/pegar", method = RequestMethod.POST)
	public void pegarEntrega(@RequestBody Corrida corrida, Motorista motorista) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		c.setMotorista(motorista);
		c.setStatus(StatusCorridaEnum.A_CAMINHO.getStatus());
		corridaService.salvarCorrida(c);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public void finalizarEntrega(@RequestBody Corrida corrida, Motorista motorista) {
		
		Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
		c.setDataFinalizacao(new Date());
		c.setStatus(StatusCorridaEnum.FINALIZADO.getStatus());
		corridaService.salvarCorrida(corrida);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/percurso", method = RequestMethod.POST)
	public List<Percurso> recuperarPercurso(@RequestBody Corrida corrida, Motorista motorista) {
		
		return percursoService.recuperarPercursosCorrida(corrida);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/acompanhar", method = RequestMethod.POST)
	public Corrida acompanharEntrega(@RequestBody Corrida corrida) {
		
		return corridaService.recuperarCorridaPorChave(corrida.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/solicitadas", method = RequestMethod.POST)
	public List<Corrida> listarEntregasSolicitadas() {
		return corridaService.recuperarCorridaPorStatus(StatusCorridaEnum.SOLICITADO.getStatus(), 
				TipoCorridaEnum.ENTREGA.getCodigo());
	}
	
}