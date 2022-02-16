/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Feriado;

/**
 * @author bruno
 *
 */
public interface FeriadoRepository extends JpaRepository<Feriado, Long>, FeriadoRepositoryCustom {

}