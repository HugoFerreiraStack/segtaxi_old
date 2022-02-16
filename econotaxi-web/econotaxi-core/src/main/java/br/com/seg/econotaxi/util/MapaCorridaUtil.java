/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.model.Parametro;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.service.CorridaService;
import br.com.seg.econotaxi.service.LocalidadeService;
import br.com.seg.econotaxi.service.ParametrosService;
import br.com.seg.econotaxi.service.VeiculoService;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public class MapaCorridaUtil {

	private static final int KM_RAIO_CARROS_PROXIMOS = 4000;

	private static MapaCorridaUtil instance = new MapaCorridaUtil();
	
	private Map<Long, Map<Long, Corrida>> mapaCorridaCidade = new HashMap<Long, Map<Long, Corrida>>();
	private Map<Long, Map<Long, Corrida>> mapaCorridaCidadeAndamento = new HashMap<Long, Map<Long, Corrida>>();
	private Map<Long, Map<Long, Corrida>> mapaCorridaCidadeAgendamento = new HashMap<Long, Map<Long, Corrida>>();
	
	static {
		
		Collection<Cidade> cidades = MapaCidadeUtil.getInstance().getMapaCidade().values();
		for (Cidade cidade : cidades) {
			instance.getMapaCorridaCidade().put(cidade.getId(), new HashMap<Long, Corrida>());
			instance.getMapaCorridaCidadeAndamento().put(cidade.getId(), new HashMap<Long, Corrida>());
			instance.getMapaCorridaCidadeAgendamento().put(cidade.getId(), new HashMap<Long, Corrida>());
		}
	}
	
	public static void adicionarCorrida(Corrida corrida) {
		
		if (corrida != null && corrida.getUsuario() != null
				&& corrida.getUsuario().getVeiculo() != null) {
			corrida.getUsuario().setVeiculo(null);
		}
		if (corrida != null && corrida.getUsuario() != null
				&& corrida.getUsuario().getVeiculosAuxiliar() != null) {
			corrida.getUsuario().setVeiculosAuxiliar(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getMotorista().getImagemCarteira() != null 
				&& !corrida.getMotorista().getImagemCarteira().isEmpty()) {
			corrida.getMotorista().setImagemCarteira(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getMotorista().getImagemCarteiraTaxista() != null 
				&& !corrida.getMotorista().getImagemCarteiraTaxista().isEmpty()) {
			corrida.getMotorista().setImagemCarteiraTaxista(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getMotorista().getVeiculoCorrente() != null
				&& corrida.getMotorista().getVeiculoCorrente().getImagemCrlv() != null 
				&& !corrida.getMotorista().getVeiculoCorrente().getImagemCrlv().isEmpty()) {
			corrida.getMotorista().getVeiculoCorrente().setImagemCrlv(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getVeiculo().getMotorista() != null
				&& corrida.getVeiculo().getMotorista().getVeiculoCorrente() != null
				&& corrida.getVeiculo().getMotorista().getVeiculoCorrente() != null
				&& corrida.getVeiculo().getMotorista().getVeiculoCorrente().getImagemCrlv() != null 
				&& !corrida.getVeiculo().getMotorista().getVeiculoCorrente().getImagemCrlv().isEmpty()) {
			corrida.getVeiculo().getMotorista().getVeiculoCorrente().setImagemCrlv(null);
		}
		if (corrida != null && corrida.getVeiculo() != null
				&& corrida.getVeiculo().getMotorista() != null
				&& corrida.getVeiculo().getMotorista().getImagemCarteira() != null
				&& !corrida.getVeiculo().getMotorista().getImagemCarteira().isEmpty()) {
			corrida.getVeiculo().getMotorista().setImagemCarteira(null);
		}
		if (corrida != null && corrida.getVeiculo() != null
				&& corrida.getVeiculo().getMotorista() != null
				&& corrida.getVeiculo().getMotorista().getImagemCarteiraTaxista() != null
				&& !corrida.getVeiculo().getMotorista().getImagemCarteiraTaxista().isEmpty()) {
			corrida.getVeiculo().getMotorista().setImagemCarteiraTaxista(null);
		}
		if (corrida != null && corrida.getVeiculo() != null
				&& corrida.getVeiculo().getImagemCrlv() != null 
				&& !corrida.getVeiculo().getImagemCrlv().isEmpty()) {
			corrida.getVeiculo().setImagemCrlv(null);
		}
		
		if (corrida.getIndicadorTeleTaxi() != null && corrida.getIndicadorTeleTaxi().equals(1)) {
			
			//CidadeService cidadeService = (CidadeService) SpringContextUtil.getBean("cidadeService");
			//Cidade cidade = cidadeService.recuperarCidadePorChave(corrida.getCidade().getId());
			Parametro param = null;
			try {
				ParametrosService parametrosService = (ParametrosService) SpringContextUtil.getBean("parametrosService");
				param = parametrosService.recuperarParametroSistema();
			} catch (Exception e) {
			}
			//corrida.setDescontoCorrida(new BigDecimal(0));
			
			Localidade localidade = null;
			Coordinate coord = new Coordinate();
			
			if (corrida.getOrigemLatitude() != null && !corrida.getOrigemLatitude().isEmpty()) {
				coord.setLatitude(Double.valueOf(corrida.getOrigemLatitude()));
			}
			if (corrida.getOrigemLongitude() != null && !corrida.getOrigemLongitude().isEmpty()) {
				coord.setLongitude(Double.valueOf(corrida.getOrigemLongitude()));
			}
			if (corrida.getOrigemLongitude() != null && !corrida.getOrigemLongitude().isEmpty()
					&& corrida.getOrigemLatitude() != null && !corrida.getOrigemLatitude().isEmpty()
					&& param != null && param.getIndicadorTipoCorridaTele() != null && param.getIndicadorTipoCorridaTele().equals(2)) {
				
				List<Localidade> regioes = MapaRegiaoUtil.recuperarLocalidadesPorCidade(corrida.getCidade(), 3l);
				if (regioes != null && !regioes.isEmpty()) {
					
					for (Localidade local : regioes) {
						
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
				
				if (localidade != null && localidade.getId() != null && localidade.getIdPa1() != null
						&& localidade.getIdPa2() != null) {
					
					LocalidadeService localidadeService = (LocalidadeService) SpringContextUtil.getBean("localidadeService");
					corrida.setPontoApoio1(localidadeService.recuperarPorChave(localidade.getIdPa1()));
					corrida.setPontoApoio2(localidadeService.recuperarPorChave(localidade.getIdPa2()));
					if (localidade.getIdPa3() != null) {
						corrida.setPontoApoio3(localidadeService.recuperarPorChave(localidade.getIdPa3()));
					}
					if (localidade.getIdPa4() != null) {
						corrida.setPontoApoio4(localidadeService.recuperarPorChave(localidade.getIdPa4()));
					}
					
					if (corrida.getPontoApoio1() != null && corrida.getPontoApoio1().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio1().getId());
						corrida.getPontoApoio1().setCarros(carros);
					}
					
					if (corrida.getPontoApoio2() != null && corrida.getPontoApoio2().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio2().getId());
						corrida.getPontoApoio2().setCarros(carros);
					}
					
					if (corrida.getPontoApoio3() != null && corrida.getPontoApoio3().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio3().getId());
						corrida.getPontoApoio3().setCarros(carros);
					}
					
					if (corrida.getPontoApoio4() != null && corrida.getPontoApoio4().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio4().getId());
						corrida.getPontoApoio4().setCarros(carros);
					}
					
					List<CarrosVO> carrosList = MapaCarroUtil.recuperarCarrosPorCidade(corrida.getCidade());
					List<CarrosVO> carros = new ArrayList<CarrosVO>();
					if (carrosList != null) {
						for (CarrosVO carrosVO : carrosList) {
							if (carrosVO != null && carrosVO.getDataUltimaPosicao().getTime() > recuperaData3Minutos(new Date()).getTime()) {
								carros.add(carrosVO);
							}
						}
					}
					
					
					Double distanciaCoordenadas = null;
					List<CarrosVO> carrosProximos = new ArrayList<CarrosVO>();
					
					if (carros != null && !carros.isEmpty()) {
						
						for (CarrosVO carroProximo : carros) {
							distanciaCoordenadas = DistanciaUtil.distance(
									Double.valueOf(corrida.getOrigemLatitude()), 
									Double.valueOf(carroProximo.getLatitude()), 
									Double.valueOf(corrida.getOrigemLongitude()), 
									Double.valueOf(carroProximo.getLongitude()), 0.0, 0.0);
							carroProximo.setDistancia(distanciaCoordenadas);
						}
						
						Collections.sort(carros, new Comparator<CarrosVO>() {
							@Override
							public int compare(CarrosVO o1, CarrosVO o2) {
								return o1.getDistancia().compareTo(o2.getDistancia());
							}
						});
						
						if (carros != null && !carros.isEmpty()) {
							for (CarrosVO carrosVO : carros) {
								if (carrosVO.getDistancia() != null && carrosVO.getDistancia() <= KM_RAIO_CARROS_PROXIMOS) {
									System.out.println("Adicionando carro proximo. 0 " + carrosVO.getPlaca());
									carrosProximos.add(carrosVO);
								}
							}
						}
						
					}
					corrida.setCarrosProximos(carrosProximos);
					
					recuperarCarrosSelecionados(corrida, carrosProximos, carros);
					
				} else {
					
					if (corrida.getOrigemLatitude() != null && !corrida.getOrigemLatitude().isEmpty()
							&& corrida.getOrigemLongitude() != null && !corrida.getOrigemLongitude().isEmpty()) {
						
						List<Localidade> pontosApoio = MapaPontoApoioUtil.recuperarLocalidadesPorCidade(corrida.getCidade());
						if (pontosApoio != null && !pontosApoio.isEmpty()) {
							
							Double distanciaCoordenadas = null;
							for (Localidade pa : pontosApoio) {
								if (pa.getCoordenadas() != null && !pa.getCoordenadas().isEmpty()) {
									String[] coordenadas = pa.getCoordenadas().split(",");
									if (coordenadas != null && coordenadas.length > 1) {
										distanciaCoordenadas = DistanciaUtil.distance(
												Double.valueOf(corrida.getOrigemLatitude()), 
												Double.valueOf(coordenadas[0]), 
												Double.valueOf(corrida.getOrigemLongitude()), 
												Double.valueOf(coordenadas[1]), 0.0, 0.0);
										pa.setDistancia(distanciaCoordenadas);
										//System.out.println(pa.getNome());
										//System.out.println(distanciaCoordenadas);
									}
								}
							}
							Collections.sort(pontosApoio, new Comparator<Localidade>() {
								@Override
								public int compare(Localidade o1, Localidade o2) {
									return o1.getDistancia().compareTo(o2.getDistancia());
								}
							});
							
							if (pontosApoio.size() >= 4) {
								corrida.setPontoApoio1(pontosApoio.get(0));
								corrida.setPontoApoio2(pontosApoio.get(1));
								corrida.setPontoApoio3(pontosApoio.get(2));
								corrida.setPontoApoio4(pontosApoio.get(3));
							} else if (pontosApoio.size() >= 3) {
								corrida.setPontoApoio1(pontosApoio.get(0));
								corrida.setPontoApoio2(pontosApoio.get(1));
								corrida.setPontoApoio3(pontosApoio.get(2));
							} else if (pontosApoio.size() == 2) {
								corrida.setPontoApoio1(pontosApoio.get(0));
								corrida.setPontoApoio2(pontosApoio.get(1));
							} else if (pontosApoio.size() == 1) {
								corrida.setPontoApoio1(pontosApoio.get(0));
							}
						}
						
					}
					
					if (corrida.getPontoApoio1() != null && corrida.getPontoApoio1().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio1().getId());
						corrida.getPontoApoio1().setCarros(carros);
					}
					
					if (corrida.getPontoApoio2() != null && corrida.getPontoApoio2().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio2().getId());
						corrida.getPontoApoio2().setCarros(carros);
					}
					
					if (corrida.getPontoApoio3() != null && corrida.getPontoApoio3().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio3().getId());
						corrida.getPontoApoio3().setCarros(carros);
					}
					
					if (corrida.getPontoApoio4() != null && corrida.getPontoApoio4().getId() != null) {
						List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
								corrida.getCidade(), corrida.getPontoApoio4().getId());
						corrida.getPontoApoio4().setCarros(carros);
					}
					
					List<CarrosVO> carrosList = MapaCarroUtil.recuperarCarrosPorCidade(corrida.getCidade());
					List<CarrosVO> carros = new ArrayList<CarrosVO>();
					if (carrosList != null) {
						for (CarrosVO carrosVO : carrosList) {
							if (carrosVO != null && carrosVO.getDataUltimaPosicao().getTime() > recuperaData3Minutos(new Date()).getTime()) {
								carros.add(carrosVO);
							}
						}
					}
					
					Double distanciaCoordenadas = null;
					List<CarrosVO> carrosProximos = new ArrayList<CarrosVO>();
					
					if (carros != null && !carros.isEmpty()) {
						
						for (CarrosVO carroProximo : carros) {
							distanciaCoordenadas = DistanciaUtil.distance(
									Double.valueOf(corrida.getOrigemLatitude()), 
									Double.valueOf(carroProximo.getLatitude()), 
									Double.valueOf(corrida.getOrigemLongitude()), 
									Double.valueOf(carroProximo.getLongitude()), 0.0, 0.0);
							carroProximo.setDistancia(distanciaCoordenadas);
						}
						
						Collections.sort(carros, new Comparator<CarrosVO>() {
							@Override
							public int compare(CarrosVO o1, CarrosVO o2) {
								return o1.getDistancia().compareTo(o2.getDistancia());
							}
						});
						
						if (carros != null && !carros.isEmpty()) {
							for (CarrosVO carrosVO : carros) {
								if (carrosVO.getDistancia() != null && carrosVO.getDistancia() <= KM_RAIO_CARROS_PROXIMOS) {
									System.out.println("Adicionando carro proximo. 1 " + carrosVO.getPlaca());
									carrosProximos.add(carrosVO);
								}
							}
						}
						
					}
					corrida.setCarrosProximos(carrosProximos);
					recuperarCarrosSelecionados(corrida, carrosProximos, carros);
					
				}
			} else {
				
				if (corrida.getPontoApoio1() != null && corrida.getPontoApoio1().getId() != null) {
					List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
							corrida.getCidade(), corrida.getPontoApoio1().getId());
					corrida.getPontoApoio1().setCarros(carros);
				}
				
				if (corrida.getPontoApoio2() != null && corrida.getPontoApoio2().getId() != null) {
					List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
							corrida.getCidade(), corrida.getPontoApoio2().getId());
					corrida.getPontoApoio2().setCarros(carros);
				}
				
				if (corrida.getPontoApoio3() != null && corrida.getPontoApoio3().getId() != null) {
					List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
							corrida.getCidade(), corrida.getPontoApoio3().getId());
					corrida.getPontoApoio3().setCarros(carros);
				}
				
				if (corrida.getPontoApoio4() != null && corrida.getPontoApoio4().getId() != null) {
					List<CarrosVO> carros = MapaCarroUtil.recuperarCarrosLocalidade(
							corrida.getCidade(), corrida.getPontoApoio4().getId());
					corrida.getPontoApoio4().setCarros(carros);
				}
				
				List<CarrosVO> carrosList = MapaCarroUtil.recuperarCarrosPorCidade(corrida.getCidade());
				
				List<CarrosVO> carros = new ArrayList<CarrosVO>();
				if (carrosList != null) {
					for (CarrosVO carrosVO : carrosList) {
						if (carrosVO != null && carrosVO.getDataUltimaPosicao().getTime() > recuperaData3Minutos(new Date()).getTime()) {
							carros.add(carrosVO);
						}
					}
				}
				
				Double distanciaCoordenadas = null;
				List<CarrosVO> carrosProximos = new ArrayList<CarrosVO>();
				
				if (corrida.getOrigemLatitude() != null && corrida.getOrigemLongitude() != null) {
					if (carros != null && !carros.isEmpty()) {
						
						for (CarrosVO carroProximo : carros) {
							distanciaCoordenadas = DistanciaUtil.distance(
									Double.valueOf(corrida.getOrigemLatitude()), 
									Double.valueOf(carroProximo.getLatitude()), 
									Double.valueOf(corrida.getOrigemLongitude()), 
									Double.valueOf(carroProximo.getLongitude()), 0.0, 0.0);
							carroProximo.setDistancia(distanciaCoordenadas);
							System.out.println(distanciaCoordenadas);
						}
						
						Collections.sort(carros, new Comparator<CarrosVO>() {
							@Override
							public int compare(CarrosVO o1, CarrosVO o2) {
								return o1.getDistancia().compareTo(o2.getDistancia());
							}
						});
						
						if (carros != null && !carros.isEmpty()) {
							for (CarrosVO carrosVO : carros) {
								if (carrosVO.getDistancia() != null && carrosVO.getDistancia() <= KM_RAIO_CARROS_PROXIMOS) {
									System.out.println("Adicionando carro proximo. 2 " + carrosVO.getPlaca());
									carrosProximos.add(carrosVO);
								}
							}
						}
						
					}
					corrida.setCarrosProximos(carrosProximos);
				}
				recuperarCarrosSelecionados(corrida, carrosProximos, carros);
			}
			
			CorridaService corridaService = (CorridaService) SpringContextUtil.getBean("corridaService");
			if (corridaService != null && corrida.getId() != null) {
				Corrida c = corridaService.recuperarCorridaPorChave(corrida.getId());
				if (localidade != null && localidade.getId() != null) {
					c.setPontosApoio("Região Selecionada: " + localidade.getNome() + ". PAs: ");
					c.setPontosApoio(c.getPontosApoio().concat(corrida.getPontoApoio1().getNome()));
				} else {
					if (corrida.getPontoApoio1() != null) {
						c.setPontosApoio(corrida.getPontoApoio1().getNome());
					}
				}
				if (corrida.getPontoApoio2() != null) {
					c.setPontosApoio(c.getPontosApoio().concat(", " + corrida.getPontoApoio2().getNome()));
				}
				if (corrida.getPontoApoio3() != null) {
					c.setPontosApoio(c.getPontosApoio().concat(", " + corrida.getPontoApoio3().getNome()));
				}
				if (corrida.getPontoApoio4() != null) {
					c.setPontosApoio(c.getPontosApoio().concat(", " + corrida.getPontoApoio4().getNome()));
				}
				corridaService.salvarCorrida(c);
			}
			
		} else {
			
			List<CarrosVO> carrosList = MapaCarroUtil.recuperarCarrosPorCidade(corrida.getCidade());
			List<CarrosVO> carros = new ArrayList<CarrosVO>();
			if (carrosList != null) {
				for (CarrosVO carrosVO : carrosList) {
					if (carrosVO != null && carrosVO.getDataUltimaPosicao().getTime() > recuperaData3Minutos(new Date()).getTime()) {
						carros.add(carrosVO);
					}
				}
			}
			
			Double distanciaCoordenadas = null;
			List<CarrosVO> carrosProximos = new ArrayList<CarrosVO>();
			
			if (carros != null && !carros.isEmpty()) {
				
				for (CarrosVO carroProximo : carros) {
					distanciaCoordenadas = DistanciaUtil.distance(
							Double.valueOf(corrida.getOrigemLatitude()), 
							Double.valueOf(carroProximo.getLatitude()), 
							Double.valueOf(corrida.getOrigemLongitude()), 
							Double.valueOf(carroProximo.getLongitude()), 0.0, 0.0);
					carroProximo.setDistancia(distanciaCoordenadas);
					System.out.println(distanciaCoordenadas);
				}
				
				Collections.sort(carros, new Comparator<CarrosVO>() {
					@Override
					public int compare(CarrosVO o1, CarrosVO o2) {
						return o1.getDistancia().compareTo(o2.getDistancia());
					}
				});
				
				if (carros != null && !carros.isEmpty()) {
					for (CarrosVO carrosVO : carros) {
						if (carrosVO.getDistancia() != null && carrosVO.getDistancia() <= KM_RAIO_CARROS_PROXIMOS) {
							System.out.println("Adicionando carro proximo. 2 " + carrosVO.getPlaca());
							carrosProximos.add(carrosVO);
						}
					}
				}
				
			}
			corrida.setCarrosProximos(carrosProximos);
			
			recuperarCarrosSelecionados(corrida, carrosProximos, carros);
		}
		
		MapaCorridaUtil.getInstance().getMapaCorridaCidade().get(corrida.getCidade().getId()).put(corrida.getId(), corrida);
	}

	private static void recuperarCarrosSelecionados(Corrida corrida, List<CarrosVO> carrosProximos, List<CarrosVO> carrosOnline) {
		List<Veiculo> veics = null;
		
		if (corrida.getIndicadorCarrosSelecionados() != null && corrida.getIndicadorCarrosSelecionados().equals(1)
				&& corrida.getIdEmpresaConveniada() != null) {
			VeiculoService veiculoService = (VeiculoService) SpringContextUtil.getBean("veiculoService");
			System.out.println("veiculoService.recuperarVeiculosEmpresa");
			veics = veiculoService.recuperarVeiculosEmpresa(
					corrida.getIdEmpresaConveniada(), corrida.getCidade().getId());
		}
		
		if (corrida.getIndicadorCarrosSelecionados() != null && corrida.getIndicadorCarrosSelecionados().equals(1)
				&& corrida.getIdEmpresaConveniada() != null && carrosOnline != null && !carrosOnline.isEmpty()) {
			
			if (veics != null && !veics.isEmpty()) {
				System.out.println("possui veiculos selecionados " + veics.size());
				List<CarrosVO> carrosSelecionados = new ArrayList<CarrosVO>();
				for (CarrosVO car : carrosOnline) {
					Optional<Veiculo> v = veics.stream().filter(vei -> vei != null && vei.getId().equals(car.getIdVeiculo())).findFirst();
					if (v != null && v.isPresent()) {
						System.out.println("possui veiculos selecionados online carro 1 " + car.getPlaca());
						carrosSelecionados.add(car);
					}
				}
				corrida.setCarrosSelecionadosLivres(carrosSelecionados);
			}
		}
		
		if (corrida.getIndicadorCarrosSelecionados() != null && corrida.getIndicadorCarrosSelecionados().equals(1)
				&& corrida.getIdEmpresaConveniada() != null && carrosProximos != null && !carrosProximos.isEmpty()) {
			
			if (veics != null && !veics.isEmpty()) {
				System.out.println("possui veiculos selecionados " + veics.size());
				List<CarrosVO> carrosSelecionados = new ArrayList<CarrosVO>();
				for (CarrosVO car : carrosProximos) {
					Optional<Veiculo> v = veics.stream().filter(vei -> vei != null && vei.getId().equals(car.getIdVeiculo())).findFirst();
					if (v != null && v.isPresent()) {
						System.out.println("possui veiculos selecionados online carro 2 " + car.getPlaca());
						carrosSelecionados.add(car);
					}
				}
				corrida.setCarrosSelecionados(carrosSelecionados);
			}
		}
	}
	
	private static Date recuperaData3Minutos(Date dataCorrente) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -3);
		return c.getTime();
	}
	
	public static void removerCorrida(Corrida corrida) {
		MapaCorridaUtil.getInstance().getMapaCorridaCidade().get(corrida.getCidade().getId()).remove(corrida.getId());
	}
	
	@SuppressWarnings("all")
	public static Boolean verificaExistenciaCorrida(Usuario usuario, Cidade cidade) {
		
		Boolean verifica = Boolean.FALSE;
		if (usuario != null && usuario.getId() != null && cidade != null && cidade.getId() != null) {
			
			List<Corrida> corridas = new ArrayList<Corrida>();
			List<Corrida> corridasSolicitadas = recuperarCorridasDisponiveis(cidade.getId());
			List<Corrida> corridasAndamento = new ArrayList(
					MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(cidade.getId()).values());
			if (corridasSolicitadas != null && !corridasSolicitadas.isEmpty()) {
				corridas.addAll(corridasSolicitadas);
			}
			if (corridasAndamento != null && !corridasAndamento.isEmpty()) {
				corridas.addAll(corridasAndamento);
			}
			if (corridas != null && !corridas.isEmpty()) {
				
				List<Corrida> queryResult = corridas.stream()
						.filter(corrida -> corrida != null)
						.filter(corrida -> corrida.getUsuario().getId().equals(usuario.getId()))
						.filter(corrida -> !corrida.getStatus().equals(StatusCorridaEnum.FINALIZADO.getStatus()))
						.collect(Collectors.toList());
				
				if (queryResult != null && !queryResult.isEmpty()) {
					verifica = Boolean.TRUE;
				}
			}
			
		}
		return verifica;
	}
	
	@SuppressWarnings("all")
	public static List<Corrida> recuperarCorridasDisponiveis(Long idCidade) {
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidade().get(idCidade) != null) {
			return new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidade().get(idCidade).values());
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("all")
	public static List<Corrida> recuperarCorridasAgendadas(Long idCidade) {
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidadeAgendamento().get(idCidade) != null) {
			return new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidadeAgendamento().get(idCidade).values());
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("all")
	public static Object recuperarCorridaAgendadaPorId(Long idCidade, Long idCorrida) {
		
		List<Corrida> corridas = null;
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidadeAgendamento().get(idCidade) != null) {
			corridas = new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidadeAgendamento().get(idCidade).values());
		}
		Corrida corridaId = null;
		Optional<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getId().equals(idCorrida))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			corridaId = queryResult.get();
		}
		return corridaId;
	}
	
	@SuppressWarnings("all")
	public static List<Corrida> recuperarCorridasFinalizadas(Long idCidade) {
		
		Date dataCorrente = new Date();
		List<Corrida> corridas = null;
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade) != null) {
			corridas = new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade).values());
		}
		 
		List<Corrida> queryResult = null;
		if (corridas != null) {
			queryResult = corridas.stream()
					.filter(corrida -> corrida != null)
					.filter(corrida -> corrida.getStatus().equals(StatusCorridaEnum.FINALIZADO.getStatus()))
					.filter(corrida -> corrida.getDataFinalizacao() != null && corrida.getDataFinalizacao().getTime() 
							< recuperaData5Minutos(dataCorrente).getTime())
					.collect(Collectors.toList());
		}
		return queryResult;
	}
	
	private static Date recuperaData5Minutos(Date dataCorrente) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -5);
		return c.getTime();
	}
	
	public static void adicionarCorridaAndamento(Corrida corrida) {
		
		if (corrida != null && corrida.getUsuario() != null
				&& corrida.getUsuario().getVeiculo() != null) {
			corrida.getUsuario().setVeiculo(null);
		}
		if (corrida != null && corrida.getUsuario() != null
				&& corrida.getUsuario().getVeiculosAuxiliar() != null) {
			corrida.getUsuario().setVeiculosAuxiliar(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getMotorista().getImagemCarteira() != null 
				&& !corrida.getMotorista().getImagemCarteira().isEmpty()) {
			corrida.getMotorista().setImagemCarteira(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getMotorista().getImagemCarteiraTaxista() != null 
				&& !corrida.getMotorista().getImagemCarteiraTaxista().isEmpty()) {
			corrida.getMotorista().setImagemCarteiraTaxista(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getMotorista().getVeiculoCorrente() != null
				&& corrida.getMotorista().getVeiculoCorrente().getImagemCrlv() != null 
				&& !corrida.getMotorista().getVeiculoCorrente().getImagemCrlv().isEmpty()) {
			corrida.getMotorista().getVeiculoCorrente().setImagemCrlv(null);
		}
		if (corrida != null && corrida.getMotorista() != null
				&& corrida.getVeiculo().getMotorista() != null
				&& corrida.getVeiculo().getMotorista().getVeiculoCorrente() != null
				&& corrida.getVeiculo().getMotorista().getVeiculoCorrente() != null
				&& corrida.getVeiculo().getMotorista().getVeiculoCorrente().getImagemCrlv() != null 
				&& !corrida.getVeiculo().getMotorista().getVeiculoCorrente().getImagemCrlv().isEmpty()) {
			corrida.getVeiculo().getMotorista().getVeiculoCorrente().setImagemCrlv(null);
		}
		if (corrida != null && corrida.getVeiculo() != null
				&& corrida.getVeiculo().getMotorista() != null
				&& corrida.getVeiculo().getMotorista().getImagemCarteira() != null
				&& !corrida.getVeiculo().getMotorista().getImagemCarteira().isEmpty()) {
			corrida.getVeiculo().getMotorista().setImagemCarteira(null);
		}
		if (corrida != null && corrida.getVeiculo() != null
				&& corrida.getVeiculo().getMotorista() != null
				&& corrida.getVeiculo().getMotorista().getImagemCarteiraTaxista() != null
				&& !corrida.getVeiculo().getMotorista().getImagemCarteiraTaxista().isEmpty()) {
			corrida.getVeiculo().getMotorista().setImagemCarteiraTaxista(null);
		}
		if (corrida != null && corrida.getVeiculo() != null
				&& corrida.getVeiculo().getImagemCrlv() != null 
				&& !corrida.getVeiculo().getImagemCrlv().isEmpty()) {
			corrida.getVeiculo().setImagemCrlv(null);
		}
		MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(
				corrida.getCidade().getId()).put(corrida.getId(), corrida);
	}
	
	public static void removerCorridaAndamento(Corrida corrida) {
		MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(
				corrida.getCidade().getId()).remove(corrida.getId());
	}
	
	@SuppressWarnings("all")
	public static Corrida recuperarCorridaPorId(Long idCidade, Long idCorrida) {
		
		List<Corrida> corridas = null;
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade) != null) {
			corridas = new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade).values());
		}
		Corrida corridaId = null;
		Optional<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getId().equals(idCorrida))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			corridaId = queryResult.get();
		}
		return corridaId;
	}
	
	@SuppressWarnings("all")
	public static Corrida recuperarCorridaPendenteUsuario(Long idUsuario, Long idCidade) {
		
		List<Corrida> corridas = null;
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade) != null) {
			corridas = new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade).values());
		}
		Corrida corridaPendente = null;
		Optional<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getUsuario().getId().equals(idUsuario))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			corridaPendente = queryResult.get();
		}
		return corridaPendente;
	}
	
	@SuppressWarnings("all")
	public static Corrida recuperarCorridaPendenteMotorista(Long idMotorista, Long idCidade) {
		
		List<Corrida> corridas = null;
		if (MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade) != null) {
			corridas = new ArrayList(MapaCorridaUtil.getInstance().getMapaCorridaCidadeAndamento().get(idCidade).values());
		}
		Corrida corridaPendente = null;
		Optional<Corrida> queryResult = corridas.stream()
				.filter(corrida -> corrida != null)
				.filter(corrida -> corrida.getMotorista() != null)
				.filter(corrida -> corrida.getMotorista().getId().equals(idMotorista))
				.findFirst();
		
		if (queryResult != null && queryResult.isPresent()) {
			corridaPendente = queryResult.get();
		}
		return corridaPendente;
	}
	
	public static void adicionarCorridaAgendamento(Corrida corrida) {
		MapaCorridaUtil.getInstance().getMapaCorridaCidadeAgendamento().get(
				corrida.getCidade().getId()).put(corrida.getId(), corrida);
	}

	public static void removerCorridaAgendamento(Corrida corrida) {
		MapaCorridaUtil.getInstance().getMapaCorridaCidadeAgendamento().get(
				corrida.getCidade().getId()).remove(corrida.getId());
	}

	/* Métodos Get/Set */
	public Map<Long, Map<Long, Corrida>> getMapaCorridaCidade() {
		return mapaCorridaCidade;
	}
	public void setMapaCorridaCidade(Map<Long, Map<Long, Corrida>> mapaCorridaCidade) {
		this.mapaCorridaCidade = mapaCorridaCidade;
	}
	public Map<Long, Map<Long, Corrida>> getMapaCorridaCidadeAndamento() {
		return mapaCorridaCidadeAndamento;
	}
	public void setMapaCorridaCidadeAndamento(Map<Long, Map<Long, Corrida>> mapaCorridaCidadeAndamento) {
		this.mapaCorridaCidadeAndamento = mapaCorridaCidadeAndamento;
	}
	public Map<Long, Map<Long, Corrida>> getMapaCorridaCidadeAgendamento() {
		return mapaCorridaCidadeAgendamento;
	}
	public void setMapaCorridaCidadeAgendamento(Map<Long, Map<Long, Corrida>> mapaCorridaCidadeAgendamento) {
		this.mapaCorridaCidadeAgendamento = mapaCorridaCidadeAgendamento;
	}
	public static MapaCorridaUtil getInstance() {
		return instance;
	}

}