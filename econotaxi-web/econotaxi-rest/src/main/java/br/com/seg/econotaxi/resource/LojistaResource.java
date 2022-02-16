/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.DescontoLojista;
import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.DescontoLojistaService;
import br.com.seg.econotaxi.service.LojistaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.util.HashUtil;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/lojista")
public class LojistaResource {

	@Autowired
	private LojistaService lojistaService;
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DescontoLojistaService descontoLojistaService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public Lojista salvar(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		Lojista lPersistente = null;
		if (usuario.getLojista().getId() == null) {
			usuario.getLojista().setDataCadastro(new Date());
			usuario.getLojista().setIdUsuario(usuario.getId());
			usuario.getLojista().setStatus(StatusMotoristaEnum.PENDENTE.getStatus());
			lojistaService.salvar(usuario.getLojista());
			
			if (!user.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
				user.setTipo(TipoUsuarioEnum.LOJISTA.getCodigo());
				if (user.getImagem() == null || !user.getImagem().equals(usuario.getImagem())) {
					user.setImagem(usuario.getImagem());
				}
				usuarioService.salvarUsuario(user);
			}
		} else {
			lPersistente = lojistaService.consultarLojistaPorChave(usuario.getLojista().getId());
			if (lPersistente.getIdCidade() != usuario.getLojista().getIdCidade()
					|| !lPersistente.getNomeLoja().equals(usuario.getLojista().getNomeLoja())
					|| !lPersistente.getCnpj().equals(usuario.getLojista().getCnpj())
					|| !lPersistente.getEndereco().equals(usuario.getLojista().getEndereco())
					|| !lPersistente.getTelefone().equals(usuario.getLojista().getTelefone())
					|| !lPersistente.getCep().equals(usuario.getLojista().getCep())) {
				
				
				lPersistente.setIdCidade(usuario.getLojista().getIdCidade());
				lPersistente.setNomeLoja(usuario.getLojista().getNomeLoja());
				lPersistente.setCnpj(usuario.getLojista().getCnpj());
				lPersistente.setEndereco(usuario.getLojista().getEndereco());
				lPersistente.setTelefone(usuario.getLojista().getTelefone());
				lPersistente.setCep(usuario.getLojista().getCep());
				lPersistente.setStatus(StatusMotoristaEnum.PENDENTE.getStatus());
				lojistaService.salvar(lPersistente);
				
				user.setTipo(TipoUsuarioEnum.LOJISTA.getCodigo());
				user.setImagem(usuario.getImagem());
				usuarioService.salvarUsuario(user);
			}
		}
		return lPersistente;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/obterDesconto", method = RequestMethod.POST)
	public DescontoLojista obterDesconto(@RequestBody DescontoLojista descontoLojista) {
		
		String retorno = "";
		DescontoLojista desconto = descontoLojistaService.recuperarDescontoPorHash(descontoLojista.getHash());
		
		if (descontoLojistaService.countDescontosDisponiveisPorUsuario(descontoLojista.getIdUsuarioCliente()) < 5) {
			if (desconto != null && desconto.getIdUsuarioCliente() == null) {
				
				desconto.setIdUsuarioCliente(descontoLojista.getIdUsuarioCliente());
				descontoLojistaService.salvar(desconto);
				retorno = "Parabéns! O Desconto foi armazenado em sua conta e será utilizado em sua próxima corrida! "
						+ "Lembrete: os descontos não são acumulativos, será utilizado um desconto a cada corrida.";
			} else {
				retorno = "Esse QR Code não pode ser mais utilizado. Solicite a geração de um novo ao Lojista.";
			}
		} else {
			retorno = "Não é possível acumular mais do que 5 descontos sem utilizar em corridas.";
		}
		desconto.setMensagemRetorno(retorno);
		return desconto;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/hash", method = RequestMethod.POST)
	public DescontoLojista gerarHash(@RequestBody DescontoLojista descontoLojista) {
		
		descontoLojista.setDataCadastro(new Date());
		String hash;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		
		Lojista l = lojistaService.consultarLojistaPorChave(descontoLojista.getIdLojista());
		hash = HashUtil.stringHexa(HashUtil.gerarHash(l.getCnpj() 
				+ descontoLojista.getIdLojista() + Calendar.getInstance() + c.getTime(), "MD5"));
		descontoLojista.setHash(hash);
		descontoLojistaService.salvar(descontoLojista);
		return descontoLojista;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/lojistaUsuario", method = RequestMethod.POST)
	public Lojista recuperarLojistaPorUsuario(@RequestBody Usuario usuario) {
		
		return lojistaService.recuperarLojistaPorUsuario(usuario);
	}
	
}