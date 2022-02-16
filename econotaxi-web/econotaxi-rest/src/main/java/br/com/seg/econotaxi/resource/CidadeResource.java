/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.util.MapaCarroUtil;
import br.com.seg.econotaxi.util.MapaCidadeUtil;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/cidade")
public class CidadeResource {

	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<Cidade> listarCidades(@RequestBody Usuario usuario) {
		
		List<Cidade> cidades = new ArrayList<Cidade>();
		cidades.addAll(MapaCidadeUtil.getInstance().getMapaCidade().values());
		return cidades;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public Cidade buscarCidade(@RequestBody Cidade cidade) {
		
		return MapaCidadeUtil.getInstance().getMapaCidade().get(cidade.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/proxima", method = RequestMethod.POST)
	public Cidade recuperarCidadeProxima(@RequestBody Usuario usuario) {
		
		/*Cidade cidade = cidadeService.recuperarCidadeUsuarioApp(usuario.getLatitudeCorrente(), 
				usuario.getLongitudeCorrente());
		if (cidade != null && cidade.getId() != null) {
			List<CarrosVO> carros = motoristaService.recuperarMotoristasCidadeOnline(cidade);
			cidade.setCarros(carros);
		}
		return cidade;*/
		Cidade cidade = MapaCidadeUtil.recuperarCidadeMaisProxima(usuario);
		if (cidade != null && cidade.getId() != null) {
			cidade.setCarros(MapaCarroUtil.recuperarCarrosPorCidade(cidade));
		}
		return cidade;
	}
	
}