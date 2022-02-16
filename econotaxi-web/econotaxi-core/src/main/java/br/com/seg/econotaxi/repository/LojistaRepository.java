/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Lojista;

/**
 * @author bruno
 *
 */
public interface LojistaRepository extends JpaRepository<Lojista, Long>, LojistaRepositoryCustom {

}