/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Localidade;

/**
 * @author bruno
 *
 */
public interface LocalidadeRepository extends JpaRepository<Localidade, Long>, LocalidadeRepositoryCustom {

}