/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Auxiliar;

/**
 * @author bruno
 *
 */
public interface AuxiliarRepository extends JpaRepository<Auxiliar, Long>, AuxiliarRepositoryCustom {

}