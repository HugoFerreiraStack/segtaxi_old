/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Alerta;
import br.com.seg.econotaxi.vo.AlertaFiltroVO;

/**
 * @author bruno
 *
 */
public interface AlertaService {

	void alterarAlerta(Alerta alertaSelecionado);

	List<Alerta> recuperarAlertasNaoVistos();

	List<Alerta> recuperarAlertasNaoSolucionados();

	Long countPorFiltro(AlertaFiltroVO filtro);

	List<Alerta> pesquisarPorFiltro(AlertaFiltroVO filtro, int first, int pageSize);

	Alerta consultarAlertaPorId(Long ids);

}
