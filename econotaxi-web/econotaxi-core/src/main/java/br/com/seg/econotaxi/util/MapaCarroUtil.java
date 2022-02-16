/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public class MapaCarroUtil {
	
	private static MapaCarroUtil instance = new MapaCarroUtil();
	private Map<Long, Map<Long, CarrosVO>> mapaCarro = new HashMap<Long, Map<Long, CarrosVO>>();
	
	static {
		
		Collection<Cidade> cidades = MapaCidadeUtil.getInstance().getMapaCidade().values();
		instance.setMapaCarro(new HashMap<Long, Map<Long, CarrosVO>>());
		for (Cidade cidade : cidades) {
			instance.getMapaCarro().put(cidade.getId(), new HashMap<Long, CarrosVO>());
		}
	}
	
	public static List<CarrosVO> recuperarCarrosPorCidade(Cidade cidade) {
		
		List<CarrosVO> carros = new ArrayList<CarrosVO>();
		if (MapaCarroUtil.getInstance().getMapaCarro().get(cidade.getId()) != null
				&& MapaCarroUtil.getInstance().getMapaCarro().get(cidade.getId()).values() != null
				&& !MapaCarroUtil.getInstance().getMapaCarro().get(cidade.getId()).values().isEmpty()) {
			carros.addAll(MapaCarroUtil.getInstance().getMapaCarro().get(cidade.getId()).values());
		}
		if (carros == null || carros.size() == 0) {
			carros = null;
		}
		return carros;
	}
	
	public static CarrosVO recuperarCarroMotorista(Cidade cidade, Long idMotorista) {
		
		CarrosVO carro = null;
		List<CarrosVO> carros = recuperarCarrosPorCidade(cidade);
		if (carros != null && !carros.isEmpty()) {
			List<CarrosVO> cs = carros.stream()
					.filter(car -> car != null)
					.filter(car -> car.getIdMotorista().equals(idMotorista))
					.collect(Collectors.toList());
			if (cs != null && !cs.isEmpty()) {
				carro = cs.get(0);
			}
		}
		return carro;
	}
	
	public static CarrosVO recuperarCarroPorIdVeiculo(Cidade cidade, Veiculo veiculo) {

		CarrosVO carro = null;
		List<CarrosVO> carros = recuperarCarrosPorCidade(cidade);
		if (carros != null && !carros.isEmpty()) {
			List<CarrosVO> cs = carros.stream()
					.filter(car -> car != null)
					.filter(car -> car.getIdVeiculo().equals(veiculo.getId()))
					.collect(Collectors.toList());
			if (cs != null && !cs.isEmpty()) {
				carro = cs.get(0);
			}
		}
		return carro;
	}
	
	public static List<CarrosVO> recuperarCarrosLocalidade(Cidade cidade, Long idLocalidade) {
		
		List<CarrosVO> carros = recuperarCarrosPorCidade(cidade);
		if (carros != null && !carros.isEmpty()) {
			List<CarrosVO> cs = carros.stream()
					.filter(car -> car != null)
					.filter(car -> car.getIdPontoApoio() != null 
						&& car.getIdPontoApoio().equals(idLocalidade))
					.collect(Collectors.toList());
			
			if (cs != null && !cs.isEmpty()) {
				Collections.sort(cs, new Comparator<CarrosVO>() {
					@Override
					public int compare(CarrosVO o1, CarrosVO o2) {
						return o1.getDataPontoApoio().compareTo(o2.getDataPontoApoio());
					}
				});
			}
			
			carros = cs;
		}
		return carros;
	}
	
	public synchronized static void adicionarCarro(CarrosVO carro, Cidade cidade) {
		
		if (carro != null && carro.getIdMotorista() != null) {
			
			Map<Long, CarrosVO> mapa = MapaCarroUtil.getInstance().getMapaCarro().get(cidade.getId());
			if (mapa != null && mapa.get(carro.getIdMotorista()) != null) {
				mapa.remove(carro.getIdMotorista());
				mapa.put(carro.getIdMotorista(), carro);
			} else {
				mapa.put(carro.getIdMotorista(), carro);
			}
		}
	}
	
	public static void removerCarro(CarrosVO carro, Cidade cidade) {

		if (carro != null && carro.getIdMotorista() != null) {
			
			Map<Long, CarrosVO> mapa = MapaCarroUtil.getInstance().getMapaCarro().get(cidade.getId());
			if (mapa != null && mapa.get(carro.getIdMotorista()) != null) {
				mapa.remove(carro.getIdMotorista());
			}
		}
		
	}
	
	public Map<Long, Map<Long, CarrosVO>> getMapaCarro() {
		return mapaCarro;
	}

	public void setMapaCarro(Map<Long, Map<Long, CarrosVO>> mapaCarro) {
		this.mapaCarro = mapaCarro;
	}

	public static MapaCarroUtil getInstance() {
		return instance;
	}

}