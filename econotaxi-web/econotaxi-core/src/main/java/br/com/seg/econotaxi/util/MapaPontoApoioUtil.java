/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Localidade;
import br.com.seg.econotaxi.service.LocalidadeService;

/**
 * @author bruno
 *
 */
public class MapaPontoApoioUtil {
	
	private static MapaPontoApoioUtil instance = new MapaPontoApoioUtil();
	private Map<Long, Map<Long, Localidade>> mapaPontoApoio = new HashMap<Long, Map<Long, Localidade>>();
	
	static {
		
		LocalidadeService localidadeService = (LocalidadeService) 
				SpringContextUtil.getBean("localidadeService");
		Collection<Cidade> cidades = MapaCidadeUtil.getInstance().getMapaCidade().values();
		instance.setMapaPontoApoio(new HashMap<Long, Map<Long, Localidade>>());
		for (Cidade cidade : cidades) {
			List<Localidade> pontosApoio = localidadeService.recuperarTodasLocalidadesPorTipo(
					new Integer(1), cidade.getId());
			if (pontosApoio != null && !pontosApoio.isEmpty()) {
				Map<Long, Localidade> mapaPA = new HashMap<Long, Localidade>();
				for (Localidade localidade : pontosApoio) {
					mapaPA.put(localidade.getId(), localidade);
				}
				instance.getMapaPontoApoio().put(cidade.getId(), mapaPA);
			} else {
				instance.getMapaPontoApoio().put(cidade.getId(), new HashMap<Long, Localidade>());
			}
		}
	}
	
	public static List<Localidade> recuperarLocalidadesPorCidade(Cidade cidade) {
		
		List<Localidade> pas = new ArrayList<Localidade>();
		if (MapaPontoApoioUtil.getInstance().getMapaPontoApoio().get(cidade.getId()) != null
				&& MapaPontoApoioUtil.getInstance().getMapaPontoApoio().get(cidade.getId()).values() != null
				&& !MapaPontoApoioUtil.getInstance().getMapaPontoApoio().get(cidade.getId()).values().isEmpty()) {
			pas.addAll(MapaPontoApoioUtil.getInstance().getMapaPontoApoio().get(cidade.getId()).values());
		}
		if (pas == null || pas.size() == 0) {
			pas = null;
		}
		return pas;
	}
	
	public static void adicionarLocalidade(Localidade local, Cidade cidade) {
		
		if (local != null && local.getId() != null) {
			
			Map<Long, Localidade> mapa = MapaPontoApoioUtil.getInstance().getMapaPontoApoio().get(cidade.getId());
			if (mapa != null && mapa.get(local.getId()) != null) {
				mapa.remove(local.getId());
				mapa.put(local.getId(), local);
			} else {
				mapa.put(local.getId(), local);
			}
		}
	}
	
	public static void removerLocalidade(Localidade local, Cidade cidade) {

		if (local != null && local.getId() != null) {
			
			Map<Long, Localidade> mapa = MapaPontoApoioUtil.getInstance().getMapaPontoApoio().get(cidade.getId());
			if (mapa != null && mapa.get(local.getId()) != null) {
				mapa.remove(local.getId());
			}
		}
		
	}
	
	public Map<Long, Map<Long, Localidade>> getMapaPontoApoio() {
		return mapaPontoApoio;
	}

	public void setMapaPontoApoio(Map<Long, Map<Long, Localidade>> mapaPontoApoio) {
		this.mapaPontoApoio = mapaPontoApoio;
	}

	public static MapaPontoApoioUtil getInstance() {
		return instance;
	}
	
}