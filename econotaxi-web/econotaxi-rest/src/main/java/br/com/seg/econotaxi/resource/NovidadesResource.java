/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.UsuarioNovidade;
import br.com.seg.econotaxi.service.NovidadeService;
import br.com.seg.econotaxi.service.UsuarioNovidadeService;
import br.com.seg.econotaxi.util.MapaMensagensUtil;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/novidades")
public class NovidadesResource {

	@Autowired
	private NovidadeService novidadeService;
	@Autowired
	private UsuarioNovidadeService usuarioNovidadeService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<Novidade> listarNovidades(@RequestBody Usuario usuario) {
		
		return novidadeService.recuperarUltimasNovidades(usuario);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/naoVistas", method = RequestMethod.POST)
	public List<Novidade> recuperarNovidadesNaoVistas(@RequestBody Usuario usuario) {
		
		List<Novidade> news = null;
		List<UsuarioNovidade> novidades = MapaMensagensUtil.recuperarNovidadesUsuario(usuario);
		if (novidades != null) {
			news = new ArrayList<Novidade>();
			news.add(new Novidade());
			news.get(0).setId(1l);
		}
		return news;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public Novidade buscarNovidade(@RequestBody Novidade novidade) {
		
		return novidadeService.recuperaNovidadePorChave(novidade.getId());
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/marcarVista", method = RequestMethod.POST)
	public void marcarNovidadeVista(@RequestBody Usuario usuario) {
		
		List<UsuarioNovidade> novidades = usuarioNovidadeService.recuperarNovidadesUsuario(usuario);
		for (UsuarioNovidade usuarioNovidade : novidades) {
			usuarioNovidade.setIndicadorVisto(1);
			usuarioNovidade.setDataVisto(new Date());
			usuarioNovidadeService.salvar(usuarioNovidade);
			MapaMensagensUtil.removerNovidade(usuarioNovidade);
		}
	}
	
}