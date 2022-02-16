/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public interface MotoristaLigueService {

	void salvarMotorista(MotoristaLigue motorista);
	
	MotoristaLigue recuperarMotoristaPorChave(Long chave);
	
	Integer recuperarQtdMotoristaPorStatus(Integer status);

	List<MotoristaLigue> recuperarMotoristaPorStatus(Integer status);

	Long pesquisarCountMotoristaPorFiltro(MotoristaLigue filtro);

	List<MotoristaLigue> pesquisarMotoristaPorFiltro(MotoristaLigue filtro, int first, int pageSize);

	MotoristaLigue recuperarMotoristaPorUsuario(Usuario usuario);

	List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade);

	void excluir(MotoristaLigue motorista);

	List<MotoristaLigue> recuperarMotoristasPorCidade(Cidade cidadeSelecionada);
	
}