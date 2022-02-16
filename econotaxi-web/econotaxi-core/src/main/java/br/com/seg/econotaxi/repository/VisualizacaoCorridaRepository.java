/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.VisualizacaoCorrida;

/**
 * @author bruno
 *
 */
public interface VisualizacaoCorridaRepository extends JpaRepository<VisualizacaoCorrida, Long>, 
	VisualizacaoCorridaRepositoryCustom {


}