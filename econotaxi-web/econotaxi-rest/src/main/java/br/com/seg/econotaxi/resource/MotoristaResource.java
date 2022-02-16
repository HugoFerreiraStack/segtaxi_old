/**
 * 
 */
package br.com.seg.econotaxi.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.enums.TipoVeiculoEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.util.MapaCarroUtil;
import br.com.seg.econotaxi.util.MapaPontoApoioUtil;
import br.com.seg.econotaxi.vo.CarrosVO;
import br.com.seg.econotaxi.vo.PontoApoioVO;

/**
 * @author bruno
 *
 */
@RestController
@RequestMapping("rest/motorista")
public class MotoristaResource {

	@Autowired
	private CorridaService corridaService;
	@Autowired
	private MotoristaService motoristaService;
	@Autowired
	private VeiculoService veiculoService;
	@Autowired
	private UsuarioService usuarioService;
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/minhasCorridas", method = RequestMethod.POST)
	public List<Corrida> listarMinhasCorridas(@RequestBody Motorista motorista) {
		
		return corridaService.recuperarCorridasPorMotorista(motorista);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public synchronized void salvar(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		
		try {
			Motorista mot = motoristaService.recuperarMotoristaPorUsuario(user);
			if (mot.getId() != null) {
				usuario.getMotorista().setId(mot.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Motorista motoristaVeiculo = null;
		Motorista mPersistente = null;
		if (usuario.getMotorista().getId() == null) {
			usuario.getMotorista().setDataCadastro(new Date());
			usuario.getMotorista().setStatus(StatusMotoristaEnum.PENDENTE.getStatus());
			usuario.getMotorista().setIdUsuario(usuario.getId());
			usuario.getMotorista().setIndicadorOnline(1);
			usuario.getMotorista().setCidade(new Cidade());
			usuario.getMotorista().getCidade().setId(usuario.getMotorista().getIdCidade());
			if (usuario.getMotorista().getTipoTeleTaxi() != null 
					&& usuario.getMotorista().getTipoTeleTaxi().equals(0)) {
				usuario.getMotorista().setTipoTeleTaxi(null);
				usuario.getMotorista().setIndicadorAceitaVoucher(null);
			}
			motoristaService.salvarMotorista(usuario.getMotorista());
			motoristaVeiculo = usuario.getMotorista();
			
			if (!user.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
				user.setTipo(TipoUsuarioEnum.MOTORISTA.getCodigo());
				user.setImagem(usuario.getImagem());
				usuarioService.atualizarDados(user);
			}
		} else {
			mPersistente = motoristaService.recuperarMotoristaPorChave(usuario.getMotorista().getId());
			mPersistente.setIdCidade(mPersistente.getCidade().getId());
			if (mPersistente.getCidade().getId() != usuario.getMotorista().getCidade().getId()
					|| !mPersistente.getCidadeMotorista().equals(usuario.getMotorista().getCidadeMotorista())
					|| !mPersistente.getSiglaUf().equals(usuario.getMotorista().getSiglaUf())
					|| !mPersistente.getDataNascimento().equals(usuario.getMotorista().getDataNascimento())
					|| !mPersistente.getCpf().equals(usuario.getMotorista().getCpf())
					|| !mPersistente.getCarteira().equals(usuario.getMotorista().getCarteira())
					|| !mPersistente.getIdCidade().equals(usuario.getMotorista().getIdCidade())
					|| !mPersistente.getImagemCarteira().equals(usuario.getMotorista().getImagemCarteira())
					|| (usuario.getMotorista().getIndicadorPermissionario() != null 
							&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
							&& !mPersistente.getImagemCarteiraTaxista().equals(usuario.getMotorista().getImagemCarteiraTaxista()))
					
					|| (usuario.getMotorista().getIndicadorPermissionario() != null 
							&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
							&& !mPersistente.getCarteiraTaxista().equals(usuario.getMotorista().getCarteiraTaxista()))
					
					|| (usuario.getMotorista().getIndicadorPermissionario() != null 
							&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
							&& !mPersistente.getDataVencimentoMotorista().equals(usuario.getMotorista().getDataVencimentoMotorista()))
					
					|| (usuario.getMotorista().getIndicadorPermissionario() != null 
							&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
							&& !mPersistente.getDataVencimentoTaxi().equals(usuario.getMotorista().getDataVencimentoTaxi()))
					
					|| (usuario.getMotorista().getIndicadorPermissionario() != null 
							&& usuario.getMotorista().getIndicadorPermissionario().equals(1) 
							&& (mPersistente.getTipoTeleTaxi() == null
							&& !mPersistente.getTipoTeleTaxi().equals(usuario.getMotorista().getTipoTeleTaxi())))
					
					|| (usuario.getMotorista().getIndicadorAceitaVoucher() != null
						&& (mPersistente.getIndicadorAceitaVoucher() == null 
							|| !mPersistente.getIndicadorAceitaVoucher().equals(usuario.getMotorista().getIndicadorAceitaVoucher())))
					
					|| ((usuario.getMotorista().getIndicadorParticular() == null 
							|| !usuario.getMotorista().getIndicadorParticular().equals(1)) 
							&& usuario.getMotorista().getTipoTeleTaxi() != null 
							&& (mPersistente.getTipoTeleTaxi() == null 
							|| !mPersistente.getTipoTeleTaxi().equals(usuario.getMotorista().getTipoTeleTaxi())))
					
					|| (usuario.getMotorista().getFormaRecebimento() != null
						&& (usuario.getMotorista().getIndicadorPermissionario() != null 
						&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
						&& (mPersistente.getFormaRecebimento() == null 
							|| !mPersistente.getFormaRecebimento().equals(usuario.getMotorista().getFormaRecebimento()))))
					
					|| (usuario.getMotorista().getIndicadorMaquinaDebito() != null
							&& (mPersistente.getIndicadorMaquinaDebito() == null 
								|| !mPersistente.getIndicadorMaquinaDebito().equals(usuario.getMotorista().getIndicadorMaquinaDebito())))) {
				
				mPersistente.setCidadeMotorista(usuario.getMotorista().getCidadeMotorista());
				mPersistente.setCarteiraTaxista(usuario.getMotorista().getCarteiraTaxista());
				mPersistente.setDataVencimentoMotorista(usuario.getMotorista().getDataVencimentoMotorista());
				mPersistente.setDataVencimentoTaxi(usuario.getMotorista().getDataVencimentoTaxi());
				mPersistente.setSiglaUf(usuario.getMotorista().getSiglaUf());
				mPersistente.setDataNascimento(usuario.getMotorista().getDataNascimento());
				mPersistente.setCpf(usuario.getMotorista().getCpf());
				mPersistente.setCarteira(usuario.getMotorista().getCarteira());
				mPersistente.setCidade(new Cidade());
				mPersistente.getCidade().setId(usuario.getMotorista().getIdCidade());
				mPersistente.setIndicadorOnline(1);
				mPersistente.setStatus(StatusMotoristaEnum.PENDENTE.getStatus());
				mPersistente.setIndicadorParticular(usuario.getMotorista().getIndicadorParticular());
				mPersistente.setIndicadorAuxiliar(usuario.getMotorista().getIndicadorAuxiliar());
				mPersistente.setIndicadorPermissionario(usuario.getMotorista().getIndicadorPermissionario());
				mPersistente.setImagemCarteira(usuario.getMotorista().getImagemCarteira());
				mPersistente.setImagemCarteiraTaxista(usuario.getMotorista().getImagemCarteiraTaxista());
				mPersistente.setTipoTeleTaxi(usuario.getMotorista().getTipoTeleTaxi());
				mPersistente.setIndicadorMaquinaDebito(usuario.getMotorista().getIndicadorMaquinaDebito());
				mPersistente.setFormaRecebimento(usuario.getMotorista().getFormaRecebimento());
				if (mPersistente.getTipoTeleTaxi() != null 
						&& mPersistente.getTipoTeleTaxi().equals(0)) {
					mPersistente.setTipoTeleTaxi(null);
					mPersistente.setIndicadorAceitaVoucher(null);
				}
				mPersistente.setIndicadorAceitaVoucher(usuario.getMotorista().getIndicadorAceitaVoucher());
				mPersistente.setBanco(usuario.getMotorista().getBanco());
				mPersistente.setAgencia(usuario.getMotorista().getAgencia());
				mPersistente.setContaCorrente(usuario.getMotorista().getContaCorrente());
				mPersistente.setOperacao(usuario.getMotorista().getOperacao());
				motoristaService.salvarMotorista(mPersistente);
				
				if (!user.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
					user.setTipo(TipoUsuarioEnum.MOTORISTA.getCodigo());
					user.setImagem(usuario.getImagem());
					usuarioService.salvarUsuario(user);
				}
			}
			motoristaVeiculo = mPersistente;
		}
		
		Veiculo vPersistente = null;
		
		try {
			if (usuario.getMotorista().getId() != null && usuario.getMotorista().getIndicadorPermissionario() != null 
					&& (usuario.getMotorista().getIndicadorPermissionario().equals(1) 
							|| usuario.getMotorista().getIndicadorParticular().equals(1))) {
				
				Veiculo v = veiculoService.recuperarVeiculoPorMotorista(usuario.getMotorista());
				if (v != null && v.getId() != null) {
					usuario.getVeiculo().setId(v.getId());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if ((usuario.getMotorista().getIndicadorAuxiliar() == null 
				|| usuario.getMotorista().getIndicadorAuxiliar() != 1) 
				&& usuario.getVeiculo().getId() == null) {
			
			if (usuario.getMotorista().getIndicadorPermissionario() != null 
					&& (usuario.getMotorista().getIndicadorPermissionario().equals(1) 
							|| usuario.getMotorista().getIndicadorParticular().equals(1))) {
				
				usuario.getVeiculo().setDataSolicitacao(new Date());
				usuario.getVeiculo().setTipo(TipoVeiculoEnum.PADRAO.getCodigo());
				usuario.getVeiculo().setStatus(StatusVeiculoEnum.PENDENTE.getStatus());
				usuario.getVeiculo().setMotorista(motoristaVeiculo);
				veiculoService.salvarVeiculo(usuario.getVeiculo());
				
				// Nao é mais necessário verificar o rastreador
				/*new Thread(new Runnable() {
					@Override
					public void run() {
						
						RestTemplate rest = new RestTemplate();
						
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
						
						MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
						map.add("chave", "9179b41f6ded470b82e33912b71c3b3c");
						map.add("placa", usuario.getVeiculo().getPlaca().toUpperCase());
						
						HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
						
						ResponseEntity<String> response = rest.postForEntity("http://sistema.segrastreadores.com.br/webhook/status_taxi", request , String.class );
						JsonParser parser = new JsonParser();
						JsonObject o = parser.parse(response.getBody()).getAsJsonObject();
						
						VeiculoSegVO veic = null;
						if (o.get("status") != null && o.get("codigo") != null 
								&& o.get("retorno") != null) {
							veic = new VeiculoSegVO();
							veic.setCodigo(o.get("codigo").getAsString());
							veic.setStatus(o.get("status").getAsString());
							veic.setRetorno(o.get("retorno").getAsString());
						}
						if (veic != null && veic.getCodigo() != null
								&& !veic.getCodigo().isEmpty()) {
							
							if (veic.getCodigo().equals(StatusVeiculoSegEnum.PLACA_NAO_EXISTENTE.getStatus())
									|| veic.getCodigo().equals(StatusVeiculoSegEnum.PLACA_ENCONTRADA_POSICAO_DESATUALIZADA.getStatus())
									|| veic.getCodigo().equals(StatusVeiculoSegEnum.PLACA_ENCONTRADA_POSICAO_NAO.getStatus())
									|| veic.getCodigo().equals(StatusVeiculoSegEnum.PROBLEMA_FINANCEIRO.getStatus())) {
								
								StatusVeiculoSegEnum enumVeic = StatusVeiculoSegEnum.valueOfStatus(veic.getCodigo());
								if (veic.getCodigo().equals(StatusVeiculoSegEnum.PROBLEMA_FINANCEIRO.getStatus())) {
									usuario.getVeiculo().setMsgErroRastreador(enumVeic.getDescricao() + veic.getRetorno());
								} else {
									usuario.getVeiculo().setMsgErroRastreador(enumVeic.getDescricao());
								}
								usuario.getVeiculo().setIndicadorRastreadorSeg(2);
								veiculoService.salvarVeiculo(usuario.getVeiculo());
							} else {
								usuario.getVeiculo().setMsgErroRastreador(null);
								usuario.getVeiculo().setIndicadorRastreadorSeg(1);
								veiculoService.salvarVeiculo(usuario.getVeiculo());
							}
						}
					}
				}).start();*/
				
			}
		} else if ((usuario.getMotorista().getIndicadorAuxiliar() == null 
				|| usuario.getMotorista().getIndicadorAuxiliar() != 1) 
				&& usuario.getVeiculo().getId() != null) {
			
			if (usuario.getMotorista().getIndicadorPermissionario() != null 
					&& (usuario.getMotorista().getIndicadorPermissionario().equals(1) 
							|| usuario.getMotorista().getIndicadorParticular().equals(1))) {
				
				vPersistente = veiculoService.recuperarVeiculoPorChave(usuario.getVeiculo().getId());
				if (!vPersistente.getPlaca().equals(usuario.getVeiculo().getPlaca())
						|| !vPersistente.getRenavan().equals(usuario.getVeiculo().getRenavan())
						|| !vPersistente.getCidade().equals(usuario.getVeiculo().getCidade())
						|| !vPersistente.getSiglaUf().equals(usuario.getVeiculo().getSiglaUf())
						|| (usuario.getMotorista().getIndicadorPermissionario() != null 
								&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
								&& !vPersistente.getIndicadorAdaptado().equals(usuario.getVeiculo().getIndicadorAdaptado()))
						//|| !vPersistente.getTipo().equals(usuario.getVeiculo().getTipo())
						/*|| (usuario.getMotorista().getIndicadorPermissionario() != null 
								&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
								&& !vPersistente.getImagemCiv().equals(usuario.getVeiculo().getImagemCiv()))*/
						|| !vPersistente.getImagemCrlv().equals(usuario.getVeiculo().getImagemCrlv())
						|| !vPersistente.getCor().equals(usuario.getVeiculo().getCor())
						|| !vPersistente.getModelo().equals(usuario.getVeiculo().getModelo())
						|| !vPersistente.getMarca().equals(usuario.getVeiculo().getMarca())
						|| (usuario.getMotorista().getIndicadorPermissionario() != null 
								&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
								&& !vPersistente.getIndicadorBicicleta().equals(usuario.getVeiculo().getIndicadorBicicleta()))
						|| (usuario.getVeiculo().getIndicadorCadeirinha() != null 
								&& (usuario.getMotorista().getIndicadorPermissionario() != null 
								&& usuario.getMotorista().getIndicadorPermissionario().equals(1)
								&& (vPersistente.getIndicadorCadeirinha() == null 
									|| !vPersistente.getIndicadorCadeirinha().equals(usuario.getVeiculo().getIndicadorCadeirinha()))))) {
					
					vPersistente.setTipo(usuario.getVeiculo().getTipo());
					vPersistente.setModelo(usuario.getVeiculo().getModelo());
					vPersistente.setMarca(usuario.getVeiculo().getMarca());
					vPersistente.setCor(usuario.getVeiculo().getCor());
					vPersistente.setPlaca(usuario.getVeiculo().getPlaca());
					vPersistente.setRenavan(usuario.getVeiculo().getRenavan());
					vPersistente.setAnoFabricacao(usuario.getVeiculo().getAnoFabricacao());
					vPersistente.setCidade(usuario.getVeiculo().getCidade());
					vPersistente.setSiglaUf(usuario.getVeiculo().getSiglaUf());
					vPersistente.setIndicadorAdaptado(usuario.getVeiculo().getIndicadorAdaptado());
					vPersistente.setIndicadorBicicleta(usuario.getVeiculo().getIndicadorBicicleta());
					vPersistente.setIndicadorCadeirinha(usuario.getVeiculo().getIndicadorCadeirinha());
					vPersistente.setImagemCiv(usuario.getVeiculo().getImagemCiv());
					vPersistente.setImagemCrlv(usuario.getVeiculo().getImagemCrlv());
					vPersistente.setDataSolicitacao(new Date());
					vPersistente.setStatus(StatusVeiculoEnum.PENDENTE.getStatus());
					veiculoService.salvarVeiculo(vPersistente);
				}
			}
			
		}
		
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/reautorizar", method = RequestMethod.POST)
	public void reautorizar(@RequestBody Motorista motorista, Usuario usuario) {
		
		Motorista m = motoristaService.recuperarMotoristaPorChave(motorista.getId());
		m.setDataCadastro(new Date());
		m.setStatus(StatusMotoristaEnum.PENDENTE.getStatus());
		motoristaService.salvarMotorista(m);
	}
	
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/usuario", method = RequestMethod.POST)
	public Usuario recuperarMotoristaPorUsuario(@RequestBody Usuario usuario) {
		
		Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
		Motorista motorista = null;
		try {
			motorista = motoristaService.recuperarMotoristaPorUsuario(user);
			user.setMotorista(motorista);
			if (user.getMotorista() != null && user.getMotorista().getId() != null) {
				user.getMotorista().setIdCidade(user.getMotorista().getCidade().getId());
        		if (user.getMotorista().getIndicadorAuxiliar() != null 
        				&& user.getMotorista().getIndicadorAuxiliar().equals(1)) {
        			
        			List<Veiculo> veiculos = veiculoService.recuperarVeiculosAuxiliar(user.getMotorista());
        			if (veiculos != null && !veiculos.isEmpty()) {
        				for (Veiculo veiculo : veiculos) {
        					CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(user.getMotorista().getCidade(), veiculo);
        					if (carro != null && carro.getIdMotorista() != null 
        							&& !carro.getIdMotorista().equals(user.getMotorista().getId())) {
        						veiculo.setIndicadorLogado(1);
        						veiculo.setNomeMotoristaLogado(carro.getNomeMotorista());
        					} else {
        						veiculo.setIndicadorLogado(2);
        					}
						}
        			}
        			user.setVeiculosAuxiliar(veiculos);
        			if (user.getVeiculosAuxiliar() != null && !user.getVeiculosAuxiliar().isEmpty()
        					&& user.getVeiculosAuxiliar().size() == 1) {
        				user.setVeiculo(user.getVeiculosAuxiliar().get(0));
        			}
        		} else {
        			user.setVeiculo(veiculoService.recuperarVeiculoPorMotorista(user.getMotorista()));
        			
        			if (user.getVeiculo() != null && user.getVeiculo().getId() != null) {
        				CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(user.getMotorista().getCidade(), user.getVeiculo());
        				if (carro != null && carro.getIdMotorista() != null 
        						&& !carro.getIdMotorista().equals(user.getMotorista().getId())) {
        					user.getVeiculo().setIndicadorLogado(1);
        					user.getVeiculo().setNomeMotoristaLogado(carro.getNomeMotorista());
        				} else {
        					user.getVeiculo().setIndicadorLogado(2);
        				}
        			}
        			
        			List<Veiculo> veiculos = veiculoService.recuperarVeiculosAuxiliar(user.getMotorista());
        			if (veiculos != null && !veiculos.isEmpty()) {
        				for (Veiculo veiculo : veiculos) {
        					CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(user.getMotorista().getCidade(), veiculo);
        					if (carro != null && carro.getIdMotorista() != null 
        							&& !carro.getIdMotorista().equals(user.getMotorista().getId())) {
        						veiculo.setIndicadorLogado(1);
        						veiculo.setNomeMotoristaLogado(carro.getNomeMotorista());
        					} else {
        						veiculo.setIndicadorLogado(2);
        					}
						}
        			}
        			user.setVeiculosAuxiliar(veiculos);
        			if (user.getVeiculosAuxiliar() != null && !user.getVeiculosAuxiliar().isEmpty()
        					&& user.getVeiculo() != null) {
        				user.getVeiculosAuxiliar().add(user.getVeiculo());
        			}
        		}
        	}
		} catch (Exception e) {}
		return user;
	}
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/meusPAs", method = RequestMethod.POST)
    public List<CarrosVO> meusPAs(@RequestBody Motorista motorista) {
		
		List<CarrosVO> carros = null;
		CarrosVO carro = MapaCarroUtil.recuperarCarroMotorista(motorista.getCidade(), motorista.getId());
		if (carro != null && carro.getIdMotorista() != null
				&& carro.getIdPontoApoio() != null) {
			
			carros = MapaCarroUtil.recuperarCarrosLocalidade(motorista.getCidade(), 
					carro.getIdPontoApoio());
			
		}
        return carros;
    }
	
	@Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/consultarPAs", method = RequestMethod.POST)
    public List<PontoApoioVO> consultarPAs(@RequestBody Motorista motorista) {
		
		List<PontoApoioVO> pas = null;
		List<Localidade> locais = MapaPontoApoioUtil.recuperarLocalidadesPorCidade(motorista.getCidade());
		if (locais != null) {
			pas = new ArrayList<PontoApoioVO>();
			for (Localidade localidade : locais) {
				PontoApoioVO pa = new PontoApoioVO();
				pa.setPontoApoio(localidade.getNome());
				pa.setCarros(MapaCarroUtil.recuperarCarrosLocalidade(motorista.getCidade(), 
					localidade.getId()));
				pas.add(pa);
			}
		}
		if (pas != null) {
			
			Collections.sort(pas, new Comparator<PontoApoioVO>() {
				@Override
				public int compare(PontoApoioVO o1, PontoApoioVO o2) {
					return o1.getPontoApoio().compareTo(o2.getPontoApoio());
				}
			});
		}
        return pas;
    }
	
}