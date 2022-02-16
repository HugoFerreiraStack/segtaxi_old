/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Familia;

/**
 * @author bruno
 *
 */
public interface FamiliaRepository extends JpaRepository<Familia, Long>, FamiliaRepositoryCustom {

}