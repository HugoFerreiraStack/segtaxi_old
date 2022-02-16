/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface NovidadeRepositoryCustom {

	List<Novidade> recuperarUltimasNovidades(Usuario usuario);
	
	Long pesquisarCountNovidadePorFiltro(Novidade filtro);

	List<Novidade> pesquisarNovidadePorFiltro(Novidade filtro, int first, int pageSize);
	
	List<Novidade> recuperarNovidades2Dias();
	
	List<Novidade> recuperarNovidadesNaoVistas(Usuario usuario);
	
}