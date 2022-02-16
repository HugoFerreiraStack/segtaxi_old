/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.ClienteTeleTaxi;

/**
 * @author bruno
 *
 */
public interface ClienteTeleTaxiRepository extends JpaRepository<ClienteTeleTaxi, Long>, 
	ClienteTeleTaxiRepositoryCustom {

}