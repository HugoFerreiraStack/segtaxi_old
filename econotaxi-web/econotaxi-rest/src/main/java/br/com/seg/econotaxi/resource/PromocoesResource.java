/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.PromocaoService;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/promocoes")
public class PromocoesResource {

	@Autowired
	private PromocaoService promocaoService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<Promocao> listarPromocoes(@RequestBody Usuario usuario) {
		
		return promocaoService.recuperarUltimasPromocoes();
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public Promocao buscarPromocao(@RequestBody Promocao promocao) {
		
		return promocaoService.recuperaPromocaoPorChave(promocao.getId());
	}
	
}