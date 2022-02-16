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

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.LocalPacote;
import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.LocalPacoteService;
import br.com.seg.econotaxi.service.PacoteService;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/pacote")
public class PacoteResource {

	@Autowired
	private PacoteService pacoteService;
	@Autowired
	private CorridaService corridaService;
	@Autowired
	private LocalPacoteService localPacoteService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/listar", method = RequestMethod.POST)
	public List<Pacote> listarPacotes(@RequestBody Cidade cidade) {
		
		return pacoteService.recuperarPacotesComLocaisPorCidade(cidade);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/buscar", method = RequestMethod.POST)
	public Pacote buscarPacote(@RequestBody Pacote pacote) {
		
		Pacote pac = pacoteService.recuperarPacotePorChave(pacote.getId());
		pac.setLocais(localPacoteService.recuperarLocaisPorPacote(pac));
		return pac;
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/gerarCorrida", method = RequestMethod.POST)
	public Corrida gerarCorrida(@RequestBody Pacote pacote) {
		
		Pacote pac = pacoteService.recuperarPacotePorChave(pacote.getId());
		Corrida corrida = new Corrida();
		corrida.setOrigemLatitude(pacote.getLatitudeOrigem());
		corrida.setOrigemLongitude(pacote.getLongitudeOrigem());
		LocalPacote ultimoLocal = localPacoteService.recuperarUltimoLocalPacote(pac);
		corrida.setDestinoLatitude(ultimoLocal.getLatitude());
		corrida.setDestinoLongitude(ultimoLocal.getLongitude());
		corrida.setValorCorridaPacote(pacote.getPreco());
		corrida.setIndicadorPacote(1);
		corrida.setUsuario(pacote.getUsuario());
		corrida.setCidade(pacote.getCidade());
		corrida.setStatus(StatusCorridaEnum.SOLICITADO.getStatus());
		corrida.setDataSolicitacao(new Date());
		corrida.setOrigem(pacote.getOrigem());
		corrida.setOrigemEndereco(pacote.getOrigemEndereco());
		corrida.setDestino(ultimoLocal.getNome());
		corridaService.salvarCorrida(corrida);
		return corrida;
	}
	
}