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
import br.com.seg.econotaxi.model.Auxiliar;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.AuxiliarService;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/auxiliar")
public class AuxiliarResource {

	// Atributos
	@Autowired
	private AuxiliarService auxiliarService; 
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<Auxiliar> listarAuxiliares(@RequestBody Usuario usuario) {
		
		return auxiliarService.recuperarAuxiliaresUsuario(usuario);
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public Auxiliar salvar(@RequestBody Auxiliar auxiliar) throws Exception {
		
		if (!auxiliarService.verificaExistenciaAuxiliar(auxiliar)) {
			auxiliar.setStatus(StatusFamiliaEnum.AUTORIZADO.getStatus());
			auxiliar.setDataPedido(new Date());
			auxiliarService.salvar(auxiliar);
		} else {
			auxiliar = null;
		}
		return auxiliar;
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/excluir", method = RequestMethod.POST)
	public void excluir(@RequestBody Auxiliar auxiliar) {
		
		auxiliarService.excluir(auxiliar);
	}
	
}