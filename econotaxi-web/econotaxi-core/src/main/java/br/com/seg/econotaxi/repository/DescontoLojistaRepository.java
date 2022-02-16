/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.DescontoLojista;

/**
 * @author bruno
 *
 */
public interface DescontoLojistaRepository 
	extends JpaRepository<DescontoLojista, Long>, DescontoLojistaRepositoryCustom {

}