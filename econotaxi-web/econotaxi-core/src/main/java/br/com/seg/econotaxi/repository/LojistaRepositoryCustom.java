/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface LojistaRepositoryCustom {

	List<Lojista> pesquisarLojistaPorFiltro(Lojista filtro, int first, int pageSize);
	
	Long pesquisarCountLojistaPorFiltro(Lojista filtro);
	
	Integer recuperarQtdLojistaPorStatus(Integer status);
	
	List<Lojista> recuperarLojistaPorStatus(Integer status);
	
	Lojista recuperarLojistaPorUsuario(Usuario usuario);
	
}