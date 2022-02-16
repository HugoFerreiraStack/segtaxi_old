/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Lojista;
import br.com.seg.econotaxi.model.Usuario;

/**
 * @author bruno
 *
 */
public interface LojistaService {

	Lojista salvar(Lojista lojista);

	Lojista consultarLojistaPorChave(Long idLojista);

	Long pesquisarCountLojistaPorFiltro(Lojista filtro);

	List<Lojista> pesquisarLojistaPorFiltro(Lojista filtro, int first, int pageSize);

	Integer recuperarQtdLojistaPorStatus(Integer status);

	List<Lojista> recuperarLojistaPorStatus(Integer status);

	Lojista recuperarLojistaPorUsuario(Usuario usuario);

}
