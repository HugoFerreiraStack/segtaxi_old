/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Auditoria;

/**
 * @author bruno
 *
 */
public interface AuditoriaService {

	Long countPorFiltro(Auditoria filtro);

	List<Auditoria> pesquisarPorFiltro(Auditoria filtro, int first, int pageSize);

	void salvar(Auditoria a);

}
