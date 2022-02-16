/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.HashMap;
import java.util.Map;

import br.com.seg.econotaxi.model.Veiculo;

/**
 * @author bruno
 *
 */
public class MapaMotoristaBloqueadoUtil {
	
	private static MapaMotoristaBloqueadoUtil instance = new MapaMotoristaBloqueadoUtil();
	private Map<Long, Veiculo> mapaVeiculo = new HashMap<Long, Veiculo>();
	
	static {
		instance.setMapaVeiculo(new HashMap<Long, Veiculo>());
	}
	
	public static void adicionarVeiculo(Veiculo veiculo) {
		
		if (veiculo != null && veiculo.getId() != null) {
			MapaMotoristaBloqueadoUtil.getInstance().getMapaVeiculo().put(veiculo.getId(), veiculo);
		}
		
	}
	
	public static void removerVeiculo(Veiculo veiculo) {
		
		if (veiculo != null && veiculo.getId() != null 
				&& MapaMotoristaBloqueadoUtil.getInstance().getMapaVeiculo().get(veiculo.getId()) != null) {
			MapaMotoristaBloqueadoUtil.getInstance().getMapaVeiculo().remove(veiculo.getId());
		}
		
	}
	
	public static Veiculo recuperarVeiculo(Long idVeiculo) {
		
		Veiculo veiculo = null;
		if (MapaMotoristaBloqueadoUtil.getInstance().getMapaVeiculo().get(idVeiculo) != null) {
			veiculo = MapaMotoristaBloqueadoUtil.getInstance().getMapaVeiculo().get(idVeiculo);
		}
		return veiculo;
	}
	
	public Map<Long, Veiculo> getMapaVeiculo() {
		return mapaVeiculo;
	}
	public void setMapaVeiculo(Map<Long, Veiculo> mapaVeiculo) {
		this.mapaVeiculo = mapaVeiculo;
	}
	public static MapaMotoristaBloqueadoUtil getInstance() {
		return instance;
	}
	
}