/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.CidadeService;

/**
 * @author bruno
 *
 */
public class MapaCidadeUtil {
	
	private static MapaCidadeUtil instance = new MapaCidadeUtil();
	private Map<Long, Cidade> mapaCidade = new HashMap<Long, Cidade>();
	
	static {
		
		CidadeService cidadeService = (CidadeService) SpringContextUtil.getBean("cidadeService");
		List<Cidade> cidades = cidadeService.recuperarTodasCidades();
		
		instance.setMapaCidade(new HashMap<Long, Cidade>());
		for (Cidade cidade : cidades) {
			instance.getMapaCidade().put(cidade.getId(), cidade);
		}
	}
	
	public static Cidade recuperarCidadeMaisProxima(Usuario usuario) {
		
		Cidade cidade = null;
		Double distancia = null;
		for (Cidade cidadeMapa : MapaCidadeUtil.getInstance().getMapaCidade().values()) {
			if (cidadeMapa.getLatitude() != null && !cidadeMapa.getLatitude().isEmpty()
					&& cidadeMapa.getLongitude() != null && !cidadeMapa.getLongitude().isEmpty()
					&& usuario.getLatitudeCorrente() != null && !usuario.getLatitudeCorrente().isEmpty()
					&& usuario.getLongitudeCorrente() != null && !usuario.getLongitudeCorrente().isEmpty()) {
				double distanciaCalc = DistanciaUtil.distance(Double.valueOf(usuario.getLatitudeCorrente()), 
						Double.valueOf(cidadeMapa.getLatitude()), Double.valueOf(usuario.getLongitudeCorrente()), 
						Double.valueOf(cidadeMapa.getLongitude()), 0.0, 0.0);
				if (distancia == null) {
					distancia = distanciaCalc;
					cidade = cidadeMapa;
					//System.out.println("Distância: " + distancia);
					//System.out.println("Cidade: " + cidade.getNome());
				} else if (distanciaCalc < distancia) {
					distancia = distanciaCalc;
					cidade = cidadeMapa;
					//System.out.println("Distância: " + distancia);
					//System.out.println("Cidade: " + cidade.getNome());
				}
			}
		}
		if (cidade == null || distancia > (Double.valueOf(cidade.getRaioKm()) * 1000)) {
			cidade = null;
		}
		return cidade;
	}
	
	public static void adicionarCidade(Cidade cidade) {
		
		if (cidade != null && cidade.getId() != null) {
			
			if (MapaCidadeUtil.getInstance().getMapaCidade().get(cidade.getId()) != null) {
				MapaCidadeUtil.getInstance().getMapaCidade().remove(cidade.getId());
				MapaCidadeUtil.getInstance().getMapaCidade().put(cidade.getId(), cidade);
			} else {
				MapaCidadeUtil.getInstance().getMapaCidade().put(cidade.getId(), cidade);
			}
			
		}
		
	}
	
	public Map<Long, Cidade> getMapaCidade() {
		return mapaCidade;
	}

	public void setMapaCidade(Map<Long, Cidade> mapaCidade) {
		this.mapaCidade = mapaCidade;
	}

	public static MapaCidadeUtil getInstance() {
		return instance;
	}
	
}