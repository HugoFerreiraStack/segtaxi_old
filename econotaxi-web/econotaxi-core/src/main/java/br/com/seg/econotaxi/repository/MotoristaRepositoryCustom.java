/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public interface MotoristaRepositoryCustom {

	Integer recuperarQtdMotoristaPorStatus(Integer status);

	List<Motorista> recuperarMotoristaPorStatus(Integer status);

	List<Motorista> pesquisarMotoristaPorFiltro(Motorista filtro, int first, int pageSize);
	
	Long pesquisarCountMotoristaPorFiltro(Motorista filtro);
	
	Motorista recuperarMotoristaPorUsuario(Usuario usuario);
	
	List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade);
	
	List<Motorista> recuperarMotoristasPorCidade(Cidade cidadeSelecionada);
	
}