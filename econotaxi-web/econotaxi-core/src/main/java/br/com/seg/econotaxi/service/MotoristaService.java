/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public interface MotoristaService {

	void salvarMotorista(Motorista motorista);
	
	Motorista recuperarMotoristaPorChave(Long chave);
	
	Integer recuperarQtdMotoristaPorStatus(Integer status);

	List<Motorista> recuperarMotoristaPorStatus(Integer status);

	Long pesquisarCountMotoristaPorFiltro(Motorista filtro);

	List<Motorista> pesquisarMotoristaPorFiltro(Motorista filtro, int first, int pageSize);

	Motorista recuperarMotoristaPorUsuario(Usuario usuario);

	List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade);

	void excluir(Motorista motorista);

	List<Motorista> recuperarMotoristasPorCidade(Cidade cidadeSelecionada);
	
}