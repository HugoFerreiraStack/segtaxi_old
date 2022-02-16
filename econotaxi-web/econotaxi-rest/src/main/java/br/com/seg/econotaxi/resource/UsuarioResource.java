package br.com.seg.econotaxi.resource;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.EmpresaConveniada;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.EmpresaConveniadaService;
import br.com.seg.econotaxi.service.LojistaService;
import br.com.seg.econotaxi.service.MotoristaService;
import br.com.seg.econotaxi.service.ParametrosService;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.util.Coordinate;
import br.com.seg.econotaxi.util.MapaCarroUtil;
import br.com.seg.econotaxi.util.MapaPontoApoioUtil;
import br.com.seg.econotaxi.util.Region;
import br.com.seg.econotaxi.util.SmsUtil;
import br.com.seg.econotaxi.vo.CarrosVO;
import br.com.seg.econotaxi.vo.CodigoSmsVo;

@RestController
@RequestMapping("rest/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MotoristaService motoristaService;
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private LojistaService lojistaService;
    @Autowired
    private ParametrosService parametrosService;
    @Autowired
    private EmpresaConveniadaService empresaConveniadaService;

    @RequestMapping(value = "/logado", method = RequestMethod.GET)
    public Usuario usuarioAtual(Principal principal) {
        Usuario usuario = usuarioService.recuperarPorLogin(principal.getName());
        
        System.out.println(usuario.getCorridas());
        if (usuario.getTipo().equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
        	usuario.setMotorista(motoristaService.recuperarMotoristaPorUsuario(usuario));
        	if (usuario.getMotorista() != null && usuario.getMotorista().getId() != null) {
        		usuario.getMotorista().setIdCidade(usuario.getMotorista().getCidade().getId());
        		if (usuario.getMotorista().getIndicadorAuxiliar() != null 
        				&& usuario.getMotorista().getIndicadorAuxiliar().equals(1)) {
        			
        			List<Veiculo> veiculos = veiculoService.recuperarVeiculosAuxiliar(usuario.getMotorista());
        			if (veiculos != null && !veiculos.isEmpty()) {
        				for (Veiculo veiculo : veiculos) {
        					CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(usuario.getMotorista().getCidade(), veiculo);
        					if (carro != null && carro.getIdMotorista() != null 
        							&& !carro.getIdMotorista().equals(usuario.getMotorista().getId())) {
        						veiculo.setIndicadorLogado(1);
        						veiculo.setNomeMotoristaLogado(carro.getNomeMotorista());
        					} else {
        						veiculo.setIndicadorLogado(2);
        					}
						}
        			}
        			usuario.setVeiculosAuxiliar(veiculos);
        			if (usuario.getVeiculosAuxiliar() != null && !usuario.getVeiculosAuxiliar().isEmpty()
        					&& usuario.getVeiculosAuxiliar().size() == 1) {
        				usuario.setVeiculo(usuario.getVeiculosAuxiliar().get(0));
        			}
        		} else {
        			usuario.setVeiculo(veiculoService.recuperarVeiculoPorMotorista(usuario.getMotorista()));
        			
        			if (usuario.getVeiculo() != null && usuario.getVeiculo().getId() != null) {
        				CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(usuario.getMotorista().getCidade(), usuario.getVeiculo());
        				if (carro != null && carro.getIdMotorista() != null 
        						&& !carro.getIdMotorista().equals(usuario.getMotorista().getId())) {
        					usuario.getVeiculo().setIndicadorLogado(1);
        					usuario.getVeiculo().setNomeMotoristaLogado(carro.getNomeMotorista());
        				} else {
        					usuario.getVeiculo().setIndicadorLogado(2);
        				}
        			}
        			
        			List<Veiculo> veiculos = veiculoService.recuperarVeiculosAuxiliar(usuario.getMotorista());
        			if (veiculos != null && !veiculos.isEmpty()) {
        				for (Veiculo veiculo : veiculos) {
        					CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(usuario.getMotorista().getCidade(), veiculo);
        					if (carro != null && carro.getIdMotorista() != null 
        							&& !carro.getIdMotorista().equals(usuario.getMotorista().getId())) {
        						veiculo.setIndicadorLogado(1);
        						veiculo.setNomeMotoristaLogado(carro.getNomeMotorista());
        					} else {
        						veiculo.setIndicadorLogado(2);
        					}
						}
        			}
        			usuario.setVeiculosAuxiliar(veiculos);
        			if (usuario.getVeiculosAuxiliar() != null && !usuario.getVeiculosAuxiliar().isEmpty()
        					&& usuario.getVeiculo() != null) {
        				usuario.getVeiculosAuxiliar().add(usuario.getVeiculo());
        			}
        		}
        	}
        } else if (usuario.getTipo().equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
        	usuario.setLojista(lojistaService.recuperarLojistaPorUsuario(usuario));
        }
        usuario.setSenha(null);

        if (usuario.getDataNascimento() != null) {
        	try {
        		usuario.setDataNascimentoText(new SimpleDateFormat("dd/MM/yyyy").format(usuario.getDataNascimento()));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        if (usuario.getDataNascimentoText() == null || usuario.getDataNascimentoText().isEmpty()) {
        	usuario.setDataNascimentoText(usuario.getDtNascimento());
        }
        return usuario;
    }
    
    @RequestMapping(value = "/empresa", method = RequestMethod.POST)
    public EmpresaConveniada recuperarEmpresaUsuario(@RequestBody Usuario usuario) {
    	return empresaConveniadaService.consultarPorChave(usuario.getIdEmpresaConveniada());
    }
    
    @RequestMapping(value = "/deslogarMotorista", method = RequestMethod.POST)
    public void deslogarMotorista(@RequestBody Usuario usuario) {
    	
    	try {
    		CarrosVO carro = MapaCarroUtil.recuperarCarroMotorista(usuario.getMotorista().getCidade(), 
        			usuario.getMotorista().getId());
        	MapaCarroUtil.removerCarro(carro, usuario.getMotorista().getCidade());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Secured({"ROLE_MOTORISTA"})
    @RequestMapping(value = "/recuperarVeiculos", method = RequestMethod.POST)
    public Usuario recuperarVeiculos(@RequestBody Usuario usuario) {
    	
    	usuario.setMotorista(motoristaService.recuperarMotoristaPorUsuario(usuario));
    	if (usuario.getMotorista() != null && usuario.getMotorista().getId() != null) {
    		usuario.getMotorista().setIdCidade(usuario.getMotorista().getCidade().getId());
    		if (usuario.getMotorista().getIndicadorAuxiliar() != null 
    				&& usuario.getMotorista().getIndicadorAuxiliar().equals(1)) {
    			
    			List<Veiculo> veiculos = veiculoService.recuperarVeiculosAuxiliar(usuario.getMotorista());
    			if (veiculos != null && !veiculos.isEmpty()) {
    				for (Veiculo veiculo : veiculos) {
    					CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(usuario.getMotorista().getCidade(), veiculo);
    					if (carro != null && carro.getIdMotorista() != null 
    							&& !carro.getIdMotorista().equals(usuario.getMotorista().getId())) {
    						veiculo.setIndicadorLogado(1);
    						veiculo.setNomeMotoristaLogado(carro.getNomeMotorista());
    					} else {
    						veiculo.setIndicadorLogado(2);
    					}
					}
    			}
    			usuario.setVeiculosAuxiliar(veiculos);
    			if (usuario.getVeiculosAuxiliar() != null && !usuario.getVeiculosAuxiliar().isEmpty()
    					&& usuario.getVeiculosAuxiliar().size() == 1) {
    				usuario.setVeiculo(usuario.getVeiculosAuxiliar().get(0));
    			}
    		} else {
    			usuario.setVeiculo(veiculoService.recuperarVeiculoPorMotorista(usuario.getMotorista()));
    			
    			if (usuario.getVeiculo() != null && usuario.getVeiculo().getId() != null) {
    				CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(usuario.getMotorista().getCidade(), usuario.getVeiculo());
    				if (carro != null && carro.getIdMotorista() != null 
    						&& !carro.getIdMotorista().equals(usuario.getMotorista().getId())) {
    					usuario.getVeiculo().setIndicadorLogado(1);
    					usuario.getVeiculo().setNomeMotoristaLogado(carro.getNomeMotorista());
    				} else {
    					usuario.getVeiculo().setIndicadorLogado(2);
    				}
    			}
    			
    			List<Veiculo> veiculos = veiculoService.recuperarVeiculosAuxiliar(usuario.getMotorista());
    			if (veiculos != null && !veiculos.isEmpty()) {
    				for (Veiculo veiculo : veiculos) {
    					CarrosVO carro = MapaCarroUtil.recuperarCarroPorIdVeiculo(usuario.getMotorista().getCidade(), veiculo);
    					if (carro != null && carro.getIdMotorista() != null 
    							&& !carro.getIdMotorista().equals(usuario.getMotorista().getId())) {
    						veiculo.setIndicadorLogado(1);
    						veiculo.setNomeMotoristaLogado(carro.getNomeMotorista());
    					} else {
    						veiculo.setIndicadorLogado(2);
    					}
					}
    			}
    			usuario.setVeiculosAuxiliar(veiculos);
    			if (usuario.getVeiculosAuxiliar() != null && !usuario.getVeiculosAuxiliar().isEmpty()
    					&& usuario.getVeiculo() != null) {
    				usuario.getVeiculosAuxiliar().add(usuario.getVeiculo());
    			}
    		}
    	}
    	return usuario;
    }

    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/atualizar", method = RequestMethod.POST)
    public Usuario atualizarUsuario(@RequestBody Usuario usuario, Principal principal) {
        usuarioService.atualizarDados(usuario);
        return usuarioAtual(principal);
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/atualizarDados", method = RequestMethod.POST)
    public Usuario atualizarDados(@RequestBody Usuario usuario) {
    	System.out.println("atualizarDados");
    	usuario.setDataAlteracao(new Date());
    	if (usuario.getDataNascimentoText() != null && !usuario.getDataNascimentoText().isEmpty()) {
    		try {
				usuario.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(usuario.getDataNascimentoText()));
				usuario.setDtNascimento(usuario.getDataNascimentoText());
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
        usuarioService.atualizarDados(usuario);
        Principal principal = new Principal() {
			@Override
			public String getName() {
				return usuario.getLogin();
			}
		};
        return usuarioAtual(principal);
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/atualizarImagemPerfil", method = RequestMethod.POST)
	public void atualizarImagemPerfil(@RequestBody Usuario usuario) {
    	
    	Usuario u = usuarioService.recuperarUsuarioPorID(usuario.getId());
    	u.setImagem(usuario.getImagem());
    	usuarioService.atualizarDados(u);
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/recuperarImagemPerfil", method = RequestMethod.POST)
	public Usuario recuperarImagemPerfil(@RequestBody Usuario usuario) {
    	
    	Usuario u = usuarioService.recuperarUsuarioPorID(usuario.getId());
    	Usuario user = new Usuario();
    	user.setId(usuario.getId());
    	user.setImagem(u.getImagem());
    	return user;
    }
    
    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/salvarPosicao", method = RequestMethod.POST)
	public void salvarPosicao(@RequestBody Usuario usuario) {
		
    	new Thread(new Runnable() {
			@Override
			public void run() {
				if (usuario.getMotorista() != null && usuario.getMotorista().getId() != null) {
					
					Motorista m = motoristaService.recuperarMotoristaPorChave(usuario.getMotorista().getId());
					m.setDataUltimaPosicao(new Date());
					m.setLatitudeCorrente(usuario.getLatitudeCorrente());
					m.setLongitudeCorrente(usuario.getLongitudeCorrente());
					motoristaService.salvarMotorista(m);
					
					if (usuario.getMotorista().getVeiculoCorrente() != null 
							&& usuario.getMotorista().getVeiculoCorrente().getId() != null) {
						
						usuario.setDataUltimaPosicao(new Date());
						Veiculo v = veiculoService.recuperarVeiculoPorChave(usuario.getMotorista().getVeiculoCorrente().getId());
						CarrosVO carro = new CarrosVO(m, usuario, v);
						
						if (MapaCarroUtil.getInstance().getMapaCarro().get(m.getCidade().getId()) != null
								&& MapaCarroUtil.getInstance().getMapaCarro().get(m.getCidade().getId()).get(m.getId()) != null) {

							CarrosVO carroExistente = MapaCarroUtil.getInstance().getMapaCarro().get(m.getCidade().getId()).get(m.getId());
							
							if (m.getTipoTeleTaxi() != null) {
								if (carroExistente.getIdPontoApoio() != null) {
									
									Localidade localidade = verificaCarroDentroLocalidade(carro, m.getCidade());
									if (localidade != null && localidade.getId() != null) {
										if (!localidade.getId().equals(carroExistente.getIdPontoApoio())) {
											carro.setIdPontoApoio(localidade.getId());
											carro.setDataPontoApoio(new Date());
											carro.setNomePontoApoio(localidade.getNome());
											//System.out.println("TELETAXI _ PA (MUDOU): " + usuario.getNome() + " entrou no " + localidade.getNome() + " em " + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(carro.getDataPontoApoio())
											//		+ " Lat: " + carro.getLatitude() + " Lng: " + carro.getLongitude());
										} else {
											carro.setIdPontoApoio(carroExistente.getIdPontoApoio());
											carro.setDataPontoApoio(carroExistente.getDataPontoApoio());
											carro.setNomePontoApoio(carroExistente.getNomePontoApoio());
											System.out.println("TELETAXI _ PA (MANTEVE): " + usuario.getNome() + " entrou no " + localidade.getNome() + " em " + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(carro.getDataPontoApoio())
													+ " Lat: " + carro.getLatitude() + " Lng: " + carro.getLongitude());
										}
									} else {
										carro.setIdPontoApoio(null);
										carro.setDataPontoApoio(null);
										carro.setNomePontoApoio(null);
										//System.out.println("TELETAXI _ PA (SAIU): " + usuario.getNome() + " em " + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(new Date())
										//		+ " Lat: " + carro.getLatitude() + " Lng: " + carro.getLongitude());
									}
									
								} else {
									Localidade localidade = verificaCarroDentroLocalidade(carro, m.getCidade());
									if (localidade != null && localidade.getId() != null) {
										carro.setIdPontoApoio(localidade.getId());
										carro.setDataPontoApoio(new Date());
										carro.setNomePontoApoio(localidade.getNome());
										//System.out.println("TELETAXI _ PA (ENTROU - JÁ LOGADO): " + usuario.getNome() + " entrou no " + localidade.getNome() + " em " + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(carro.getDataPontoApoio())
										//		+ " Lat: " + carro.getLatitude() + " Lng: " + carro.getLongitude());
									} else {
										carro.setIdPontoApoio(null);
										carro.setDataPontoApoio(null);
										carro.setNomePontoApoio(null);
									}
								}
							}
						} else {
							if (m.getTipoTeleTaxi() != null) {
								Localidade localidade = verificaCarroDentroLocalidade(carro, m.getCidade());
								if (localidade != null && localidade.getId() != null) {
									carro.setIdPontoApoio(localidade.getId());
									carro.setDataPontoApoio(new Date());
									carro.setNomePontoApoio(localidade.getNome());
									//System.out.println("TELETAXI _ PA (ENTROU): " + usuario.getNome() + " entrou no " + localidade.getNome() + " em " + new SimpleDateFormat("dd/MM/YYYY 'às' HH:mm:ss").format(carro.getDataPontoApoio())
									//		+ " Lat: " + carro.getLatitude() + " Lng: " + carro.getLongitude());
								} else {
									carro.setIdPontoApoio(null);
									carro.setDataPontoApoio(null);
									carro.setNomePontoApoio(null);
								}
							}
						}
						
						MapaCarroUtil.adicionarCarro(carro, m.getCidade());
					}
				}
				
				if (usuario != null && usuario.getId() != null) {
					
					Usuario user = usuarioService.recuperarUsuarioPorID(usuario.getId());
					user.setDataUltimaPosicao(new Date());
					user.setLatitudeCorrente(usuario.getLatitudeCorrente());
					user.setLongitudeCorrente(usuario.getLongitudeCorrente());
					if (usuario.getVersaoApp() != null && !usuario.getVersaoApp().isEmpty()) {
						user.setVersaoApp(usuario.getVersaoApp());
					}
					usuarioService.salvarUsuario(user);
				}
			}

			private Localidade verificaCarroDentroLocalidade(CarrosVO carro, Cidade cidade) {
				
				Coordinate coord = new Coordinate();
				coord.setLatitude(Double.valueOf(carro.getLatitude()));
				coord.setLongitude(Double.valueOf(carro.getLongitude()));
				Localidade localidade = null;
				List<Localidade> localidades = MapaPontoApoioUtil.recuperarLocalidadesPorCidade(cidade);
				if (localidades != null && !localidades.isEmpty()) {
					
					for (Localidade local : localidades) {
						
						int i, j;
						boolean isInside = false;
						
						Region region = new Region();
						region.setBoundary(new ArrayList<Coordinate>());
						String[] locais = local.getCoordenadas().split(",");
						
						for (int w = 0 ; w < (locais.length - 1); w+=2) {
							double latA = Double.valueOf(locais[w]);
							double lngA = Double.valueOf(locais[w+1]);
							
							Coordinate c = new Coordinate();
							c.setLatitude(latA);
							c.setLongitude(lngA);
							region.getBoundary().add(c);
						}
						
						//create an array of coordinates from the region boundary list
						Coordinate[] verts = (Coordinate[]) region.getBoundary().toArray(new Coordinate[region.getBoundary().size()]);
						int sides = verts.length;
						for (i = 0, j = sides - 1; i < sides; j = i++) {
							//verifying if your coordinate is inside your region
							if ((((verts[i].getLongitude() <= coord.getLongitude()) && (coord.getLongitude() < verts[j].getLongitude()))
									|| ((verts[j].getLongitude() <= coord.getLongitude()) && (coord.getLongitude() < verts[i].getLongitude()))) 
									&& (coord.getLatitude() < (verts[j].getLatitude() - verts[i].getLatitude()) 
											* (coord.getLongitude() - verts[i].getLongitude()) 
													/ (verts[j].getLongitude() - verts[i].getLongitude()) + verts[i].getLatitude())) {
								isInside = !isInside;
							}
						}
						if (isInside) {
							localidade = local;
							break;
						}
						
					}
				}
				return localidade;
			}
		}).start();
	}
    
    public static void main(String[] args) {
		
    	Coordinate coord = new Coordinate();
		coord.setLatitude(Double.valueOf("-21.7606074"));
		coord.setLongitude(Double.valueOf("-43.3499647"));
		Localidade localidade = null;
		List<Localidade> localidades = new ArrayList<Localidade>();
		Localidade loc = new Localidade();
		loc.setId(1l);
		loc.setNome("Teste pa 01");
		loc.setCoordenadas("-21.760412969621452,-43.35040110857676,-21.761827896473882,-43.350079243494974,-21.76165850455752,-43.34945697100352,-21.76028343336762,-43.349821751429545,");
		localidades.add(loc);
		if (localidades != null && !localidades.isEmpty()) {
			
			for (Localidade local : localidades) {
				
				int i, j;
				boolean isInside = false;
				
				Region region = new Region();
				region.setBoundary(new ArrayList<Coordinate>());
				String[] locais = local.getCoordenadas().split(",");
				
				for (int w = 0 ; w < (locais.length - 1); w+=2) {
					double latA = Double.valueOf(locais[w]);
					double lngA = Double.valueOf(locais[w+1]);
					
					Coordinate c = new Coordinate();
					c.setLatitude(latA);
					c.setLongitude(lngA);
					region.getBoundary().add(c);
				}
				
				//create an array of coordinates from the region boundary list
				Coordinate[] verts = (Coordinate[]) region.getBoundary().toArray(new Coordinate[region.getBoundary().size()]);
				int sides = verts.length;
				for (i = 0, j = sides - 1; i < sides; j = i++) {
					//verifying if your coordinate is inside your region
					if ((((verts[i].getLongitude() <= coord.getLongitude()) && (coord.getLongitude() < verts[j].getLongitude()))
							|| ((verts[j].getLongitude() <= coord.getLongitude()) && (coord.getLongitude() < verts[i].getLongitude()))) 
							&& (coord.getLatitude() < (verts[j].getLatitude() - verts[i].getLatitude()) 
									* (coord.getLongitude() - verts[i].getLongitude()) 
											/ (verts[j].getLongitude() - verts[i].getLongitude()) + verts[i].getLatitude())) {
						isInside = !isInside;
					}
				}
				if (isInside) {
					localidade = local;
					break;
				}
				
			}
		}
		if (localidade != null) {
			System.out.println(localidade.getNome());
		}
    	
	}

    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(method = RequestMethod.GET)
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @RequestMapping(value = "/nova-conta/codigoSMS", method = RequestMethod.POST)
    public CodigoSmsVo gerarCodigoSms(@RequestBody  CodigoSmsVo codigoSmsVo) {
    	
    	if (codigoSmsVo.getCodigo() == null) {
    		 codigoSmsVo = usuarioService.gerarCodigoSms(codigoSmsVo);
    	} else {
    		Usuario usuario = new Usuario();
    		usuario.setLogin(codigoSmsVo.getEmail());
    		usuario.setCelular(codigoSmsVo.getCelular());
    		if (usuarioService.verificaExistenciaUsuario(usuario)) {
        		throw new NegocioException("Este e-mail já está cadastrado");
        	} else if (usuarioService.verificaExistenciaUsuarioCelular(usuario)) {
        		throw new NegocioException("Este celular já está cadastrado");
        	}
    	}
    	
        Parametro param = parametrosService.recuperarParametroSistema();
        
        String mensagem = 
        		(param.getNomeAplicativo() == null ? "" : param.getNomeAplicativo() + " - ") 
        		+ "Código de Ativação: " 
        				+ codigoSmsVo.getCodigo() 
        				+ " " 
        				+ (codigoSmsVo.getHashApp() == null || codigoSmsVo.getHashApp().isEmpty() ? "" : codigoSmsVo.getHashApp());

        String celular = codigoSmsVo.getCelular().replaceAll("\\(", "")
                .replaceAll("\\)", "").replaceAll("-", "")
                .replaceAll(" ", "");

        SmsUtil.sendSms(Long.valueOf(celular), mensagem, param.getUsuarioSms(), param.getSenhaSms());
        return codigoSmsVo;
    }

    @RequestMapping(value = "/nova-conta/cadastro", method = RequestMethod.POST)
    public void salvarCliente(@RequestBody Usuario usuario) {
        usuarioService.salvarCliente(usuario);
    }

    @RequestMapping(value = "/nova-conta/trocar-senha", method = RequestMethod.POST)
    public void esqueciSenha(@RequestBody Usuario usuario) {
        usuarioService.enviarEmailTrocaSenha(usuario.getLogin());
    }
    
    @RequestMapping(value = "/versaoApp", method = RequestMethod.POST)
    public Parametro versaoApp() {
    	
        Parametro p = parametrosService.recuperarParametroSistema();
        return p;
    }
    
    @RequestMapping(value = "/passageiros", method = RequestMethod.POST)
    public List<Usuario> passageiros(@RequestBody String chave) {
    	
    	List<Usuario> usuarios = null;
    	if (chave != null && !chave.isEmpty() && chave.equals("republicanet")) {
    		usuarios = usuarioService.recuperarPassageiros();
    	}
    	return usuarios;
    }

}