/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.Date;
import java.util.List;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Feriado;

/**
 * @author bruno
 *
 */
public interface FeriadoRepositoryCustom {

	Long pesquisarCountFeriadoPorFiltro(Feriado filtro);

	List<Feriado> pesquisarFeriadoPorFiltro(Feriado filtro, int first, int pageSize);

	boolean verificaExistenciaFeriado(Feriado feriado);
	
	Boolean verificaDiaFeriado(Date dataFinalizacao, Cidade cidade);
	
}