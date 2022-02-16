/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNotificacao;
import br.com.seg.econotaxi.service.NotificacaoService;
import br.com.seg.econotaxi.service.UsuarioNotificacaoService;
import br.com.seg.econotaxi.util.MapaMensagensUtil;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/notificacoes")
public class NotificacoesResource {

	@Autowired
	private NotificacaoService notificacaoService;
	@Autowired
	private UsuarioNotificacaoService usuarioNotificacaoService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<UsuarioNotificacao> listarNotificacoes(@RequestBody Usuario usuario) {
		
		return notificacaoService.recuperarUltimasNotificacoes(usuario);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/naoVistas", method = RequestMethod.POST)
	public List<UsuarioNotificacao> recuperarNotificacoesNaoVistas(@RequestBody Usuario usuario) {
		
		return MapaMensagensUtil.recuperarNotificacoesUsuario(usuario);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public Notificacao buscarNotificacao(@RequestBody Notificacao notificacao) {
		
		return notificacaoService.recuperarNotificacaoPorChave(notificacao.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/marcarVista", method = RequestMethod.POST)
	public void marcarNotificacaoVista(@RequestBody Usuario usuario) {
		
		List<UsuarioNotificacao> notificacoes = usuarioNotificacaoService.recuperarNotificacoesUsuario(usuario);
		for (UsuarioNotificacao notificacao : notificacoes) {
			notificacao.setIndicadorVisto(1);
			notificacao.setDataVisto(new Date());
			usuarioNotificacaoService.salvar(notificacao);
			MapaMensagensUtil.removerNotificacao(notificacao);
		}
	}
	
}