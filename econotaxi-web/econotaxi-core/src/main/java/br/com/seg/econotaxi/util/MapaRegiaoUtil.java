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
public class MapaRegiaoUtil {
	
	private static MapaRegiaoUtil instance = new MapaRegiaoUtil();
	private Map<Long, Map<Long, Localidade>> mapaRegiao = new HashMap<Long, Map<Long, Localidade>>();
	private Map<Long, Map<Long, Localidade>> mapaBairro = new HashMap<Long, Map<Long, Localidade>>();
	
	static {
		
		LocalidadeService localidadeService = (LocalidadeService) 
				SpringContextUtil.getBean("localidadeService");
		Collection<Cidade> cidades = MapaCidadeUtil.getInstance().getMapaCidade().values();
		instance.setMapaBairro(new HashMap<Long, Map<Long, Localidade>>());
		instance.setMapaRegiao(new HashMap<Long, Map<Long, Localidade>>());
		
		for (Cidade cidade : cidades) {
			List<Localidade> bairros = localidadeService.recuperarTodasLocalidadesPorTipo(
					new Integer(2), cidade.getId());
			
			List<Localidade> regioes = localidadeService.recuperarTodasLocalidadesPorTipo(
					new Integer(3), cidade.getId());
			
			if (bairros != null && !bairros.isEmpty()) {
				Map<Long, Localidade> mapaBairro = new HashMap<Long, Localidade>();
				for (Localidade localidade : bairros) {
					mapaBairro.put(localidade.getId(), localidade);
				}
				instance.getMapaBairro().put(cidade.getId(), mapaBairro);
			}
			
			if (regioes != null && !regioes.isEmpty()) {
				Map<Long, Localidade> mapaRegiao = new HashMap<Long, Localidade>();
				for (Localidade localidade : regioes) {
					mapaRegiao.put(localidade.getId(), localidade);
				}
				instance.getMapaRegiao().put(cidade.getId(), mapaRegiao);
			}
		}
	}
	
	public static List<Localidade> recuperarLocalidadesPorCidade(Cidade cidade, Long tipo) {
		
		List<Localidade> pas = new ArrayList<Localidade>();
		if (tipo.equals(2l)) {
			if (MapaRegiaoUtil.getInstance().getMapaBairro().get(cidade.getId()) != null
					&& MapaRegiaoUtil.getInstance().getMapaBairro().get(cidade.getId()).values() != null
					&& !MapaRegiaoUtil.getInstance().getMapaBairro().get(cidade.getId()).values().isEmpty()) {
				pas.addAll(MapaRegiaoUtil.getInstance().getMapaBairro().get(cidade.getId()).values());
			}
			if (pas == null || pas.size() == 0) {
				pas = null;
			}
			
		} else if (tipo.equals(3l)) {
			if (MapaRegiaoUtil.getInstance().getMapaRegiao().get(cidade.getId()) != null
					&& MapaRegiaoUtil.getInstance().getMapaRegiao().get(cidade.getId()).values() != null
					&& !MapaRegiaoUtil.getInstance().getMapaRegiao().get(cidade.getId()).values().isEmpty()) {
				pas.addAll(MapaRegiaoUtil.getInstance().getMapaRegiao().get(cidade.getId()).values());
			}
			if (pas == null || pas.size() == 0) {
				pas = null;
			}
			
		}
		return pas;
	}
	
	public static void adicionarLocalidade(Localidade local, Cidade cidade) {
		
		if (local != null && local.getId() != null) {
			
			if (local.getTipo().equals(2)) {
				Map<Long, Localidade> mapa = MapaRegiaoUtil.getInstance().getMapaBairro().get(cidade.getId());
				if (mapa != null && mapa.get(local.getId()) != null) {
					mapa.remove(local.getId());
					mapa.put(local.getId(), local);
				} else {
					mapa.put(local.getId(), local);
				}
				
			} else if (local.getTipo().equals(3)) {
				Map<Long, Localidade> mapa = MapaRegiaoUtil.getInstance().getMapaRegiao().get(cidade.getId());
				if (mapa != null && mapa.get(local.getId()) != null) {
					mapa.remove(local.getId());
					mapa.put(local.getId(), local);
				} else {
					mapa.put(local.getId(), local);
				}
				
			}
			
		}
	}
	
	public static void removerLocalidade(Localidade local, Cidade cidade) {

		if (local != null && local.getId() != null) {
			
			if (local.getTipo().equals(2)) {
				Map<Long, Localidade> mapa = MapaRegiaoUtil.getInstance().getMapaBairro().get(
						cidade.getId());
				if (mapa != null && mapa.get(local.getId()) != null) {
					mapa.remove(local.getId());
				}
				
			} else if (local.getTipo().equals(3)) {
				
				Map<Long, Localidade> mapa = MapaRegiaoUtil.getInstance().getMapaRegiao().get(
						cidade.getId());
				if (mapa != null && mapa.get(local.getId()) != null) {
					mapa.remove(local.getId());
				}
			}
		}
		
	}
	
	public Map<Long, Map<Long, Localidade>> getMapaRegiao() {
		return mapaRegiao;
	}

	public void setMapaRegiao(Map<Long, Map<Long, Localidade>> mapaRegiao) {
		this.mapaRegiao = mapaRegiao;
	}

	public Map<Long, Map<Long, Localidade>> getMapaBairro() {
		return mapaBairro;
	}

	public void setMapaBairro(Map<Long, Map<Long, Localidade>> mapaBairro) {
		this.mapaBairro = mapaBairro;
	}

	public static MapaRegiaoUtil getInstance() {
		return instance;
	}
	
}