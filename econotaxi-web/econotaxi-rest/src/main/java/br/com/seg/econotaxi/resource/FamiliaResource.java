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

import br.com.seg.econotaxi.enums.StatusFamiliaEnum;
import br.com.seg.econotaxi.model.Familia;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.FamiliaService;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/familia")
public class FamiliaResource {

	// Atributos
	@Autowired
	private FamiliaService familiaService; 
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<Familia> listarFamiliares(@RequestBody Usuario usuario) {
		
		return familiaService.recuperarFamiliaresUsuario(usuario);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listarSolicitacoes", method = RequestMethod.POST)
	public List<Familia> listarFamiliaresSolicitacoes(@RequestBody Usuario usuario) {
		
		return familiaService.recuperarFamiliaresSolicitacoesUsuario(usuario);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public Familia salvar(@RequestBody Familia familia) {
		
		familia.setStatus(StatusFamiliaEnum.PENDENTE.getStatus());
		familia.setDataPedido(new Date());
		familiaService.salvar(familia);
		return familia;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
	public void excluir(@RequestBody Familia familia) {
		
		familiaService.excluir(familia);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/autorizar", method = RequestMethod.POST)
	public Familia autorizar(@RequestBody Familia familia) {
		
		Familia f = familiaService.recuperarFamiliaPorChave(familia.getId());
		f.setStatus(StatusFamiliaEnum.AUTORIZADO.getStatus());
		f.setDataAutorizado(new Date());
		familiaService.salvar(f);
		return f;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/rejeitar", method = RequestMethod.POST)
	public Familia rejeitar(@RequestBody Familia familia) {
		
		Familia f = familiaService.recuperarFamiliaPorChave(familia.getId());
		f.setStatus(StatusFamiliaEnum.REJEITADO.getStatus());
		f.setDataAutorizado(null);
		familiaService.salvar(f);
		return f;
	}
	
}