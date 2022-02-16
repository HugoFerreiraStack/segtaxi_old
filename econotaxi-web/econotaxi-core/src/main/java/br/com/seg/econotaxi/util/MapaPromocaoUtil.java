/**
 * 
 */
package br.com.seg.econotaxi.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.seg.econotaxi.enums.PromocaoEnum;
import br.com.seg.econotaxi.model.Promocao;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.PromocaoService;

/**
 * @author bruno
 *
 */
public class MapaPromocaoUtil {
	
	private static MapaPromocaoUtil instance = new MapaPromocaoUtil();
	private Map<Long, Promocao> mapaPromocao = new HashMap<Long, Promocao>();
	
	static {
		
		PromocaoService promocaoService = (PromocaoService) SpringContextUtil.getBean("promocaoService");
		List<Promocao> promocoes = promocaoService.recuperarUltimasPromocoes();
		
		instance.setMapaPromocao(new HashMap<Long, Promocao>());
		for (Promocao promocao : promocoes) {
			instance.getMapaPromocao().put(promocao.getId(), promocao);
		}
	}
	
	public static void adicionarPromocao(Promocao promocao) {
		
		if (promocao != null && promocao.getId() != null) {
			MapaPromocaoUtil.getInstance().getMapaPromocao().remove(promocao.getId());
			MapaPromocaoUtil.getInstance().getMapaPromocao().put(promocao.getId(), promocao);
		}
		
	}
	
	public static void removerPromocao(Promocao promocao) {
		
		if (promocao != null && promocao.getId() != null) {
			MapaPromocaoUtil.getInstance().getMapaPromocao().remove(promocao.getId());
		}
		
	}
	
	public static List<Promocao> recuperarPromocoes() {
		
		List<Promocao> promocoes = null;
		if (MapaPromocaoUtil.getInstance().getMapaPromocao() != null 
				&& !MapaPromocaoUtil.getInstance().getMapaPromocao().isEmpty()) {
			promocoes = new ArrayList<Promocao>(MapaPromocaoUtil.getInstance()
					.getMapaPromocao().values());
		}
		return promocoes;
	}
	
	public static BigDecimal definirPorcentagemDesconto(Usuario usuario) {
		
		List<Promocao> promocoes = recuperarPromocoes();
		BigDecimal porcentagem = new BigDecimal(0);
		if (promocoes != null && !promocoes.isEmpty()) {
			
			for (Promocao promocao : promocoes) {
				if (promocao.getTipo().equals(PromocaoEnum.PRIMEIRA_CORRIDA.getCodigo())
						&& (usuario.getQtdCorridasRealizadas() == null || usuario.getQtdCorridasRealizadas() == 0)) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.SEGUNDA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 1) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.TERCEIRA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 2) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.QUARTA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 3) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.QUINTA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 4) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.SEXTA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 5) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.SETIMA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 6) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.OITAVA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 7) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.NOVA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 8) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.DECIMA_CORRIDA.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 9) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.APOS_10_CORRIDAS.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && usuario.getQtdCorridasRealizadas() == 10) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.CADA_5_CORRIDAS.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && (usuario.getQtdCorridasRealizadas() + 1) % 5 == 2) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.CADA_10_CORRIDAS.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && (usuario.getQtdCorridasRealizadas() + 1) % 10 == 2) {
					porcentagem = promocao.getPorcentagem();
					break;
				} else if (promocao.getTipo().equals(PromocaoEnum.CADA_15_CORRIDAS.getCodigo())
						&& usuario.getQtdCorridasRealizadas() != null && (usuario.getQtdCorridasRealizadas() + 1) % 15 == 2) {
					porcentagem = promocao.getPorcentagem();
					break;
				}
			}
		}
		return porcentagem;
	}
	
	public Map<Long, Promocao> getMapaPromocao() {
		return mapaPromocao;
	}

	public void setMapaPromocao(Map<Long, Promocao> mapaPromocao) {
		this.mapaPromocao = mapaPromocao;
	}

	public static MapaPromocaoUtil getInstance() {
		return instance;
	}
	
}