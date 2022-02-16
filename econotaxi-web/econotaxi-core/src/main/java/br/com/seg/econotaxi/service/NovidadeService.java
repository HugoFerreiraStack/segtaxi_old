/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface NovidadeService {

	void salvarNovidade(Novidade novidade);
	
	Novidade recuperaNovidadePorChave(Long chave);
	
	List<Novidade> recuperarUltimasNovidades(Usuario usuario);

	Long pesquisarCountNovidadePorFiltro(Novidade filtro);

	List<Novidade> pesquisarNovidadePorFiltro(Novidade filtro, int first, int pageSize);

	List<Novidade> recuperarNovidades2Dias();

	List<Novidade> recuperarNovidadesNaoVistas(Usuario usuario);

	void excluir(Novidade novidade);
	
}