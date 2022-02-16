/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import br.com.seg.econotaxi.model.Auditoria;

public interface AuditoriaRepositoryCustom {

	/**
	 * Método responsável por recuperar registros de auditoria por filtro
	 * 
	 * @param filtro os critérios da consulta
	 * @param first a página corrente
	 * @param pageSize a quantidade de registros por página
	 * @return auditorias que atendem aos critérios da consulta
	 */
	List<Auditoria> pesquisarPorFiltro(Auditoria filtro, int first, int pageSize);

	/**
	 * Método responsável por recuperar a quantidade de auditorias de acordo com os critérios da consulta
	 * 
	 * @param filtro os critérios da consulta
	 * @return a quantidade de auditorias de acordo com os critérios da consulta
	 */
	Long countPorFiltro(Auditoria filtro);
	
}