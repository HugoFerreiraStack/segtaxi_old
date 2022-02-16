/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.List;

import br.com.seg.econotaxi.model.Funcionalidade;

/**
 * @author bruno
 *
 */
public interface FuncionalidadeService {

	void salvarFuncionalidade(Funcionalidade funcionalidade);
	
	Funcionalidade recuperarFuncionalidadePorChave(Long chave);
	
	List<Funcionalidade> recuperarTodasFuncionalidades();

	List<Long> recuperarFuncionalidadesUsuario(Long id);
	
}