/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Alerta;
import br.com.seg.econotaxi.service.AlertaService;
import br.com.seg.econotaxi.vo.AlertaFiltroVO;

/**
 * @author bruno
 *
 */
@Service("alertaService")
public class AlertaServiceImpl implements AlertaService {

	@Override
	public void alterarAlerta(Alerta alertaSelecionado) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Alerta> recuperarAlertasNaoVistos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alerta> recuperarAlertasNaoSolucionados() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countPorFiltro(AlertaFiltroVO filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alerta> pesquisarPorFiltro(AlertaFiltroVO filtro, int first, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alerta consultarAlertaPorId(Long ids) {
		// TODO Auto-generated method stub
		return null;
	}

}