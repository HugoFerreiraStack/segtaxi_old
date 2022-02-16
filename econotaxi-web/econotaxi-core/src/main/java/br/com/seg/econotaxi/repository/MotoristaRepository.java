/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Motorista;

/**
 * @author bruno
 *
 */
public interface MotoristaRepository extends JpaRepository<Motorista, Long>, MotoristaRepositoryCustom {

	Motorista findById(@Param("id") Long id);

}