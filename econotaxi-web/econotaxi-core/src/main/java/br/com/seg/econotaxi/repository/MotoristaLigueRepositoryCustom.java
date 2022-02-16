/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public interface MotoristaLigueRepositoryCustom {

	Integer recuperarQtdMotoristaPorStatus(Integer status);

	List<MotoristaLigue> recuperarMotoristaPorStatus(Integer status);

	List<MotoristaLigue> pesquisarMotoristaPorFiltro(MotoristaLigue filtro, int first, int pageSize);
	
	Long pesquisarCountMotoristaPorFiltro(MotoristaLigue filtro);
	
	MotoristaLigue recuperarMotoristaPorUsuario(Usuario usuario);
	
	List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade);
	
	List<MotoristaLigue> recuperarMotoristasPorCidade(Cidade cidadeSelecionada);
	
}