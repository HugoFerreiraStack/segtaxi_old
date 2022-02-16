/**
 * 
 */
package br.com.seg.econotaxi.service;

import java.util.Date;
import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Feriado;

/**
 * @author bruno
 *
 */
public interface FeriadoService {

	Long pesquisarCountFeriadoPorFiltro(Feriado filtro);
	
	List<Feriado> pesquisarFeriadoPorFiltro(Feriado filtro, int first, int pageSize);

	void salvarFeriado(Feriado feriado);

	boolean verificaExistenciaFeriado(Feriado feriado);

	void excluir(Feriado feriado);

	Feriado recuperarFeriadoPorChave(Long id);

	Boolean verificaDiaFeriado(Date dataFinalizacao, Cidade cidade);

}