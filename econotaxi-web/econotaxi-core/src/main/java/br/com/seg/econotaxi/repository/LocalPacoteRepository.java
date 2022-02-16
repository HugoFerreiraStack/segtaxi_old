/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.LocalPacote;

/**
 * @author bruno
 *
 */
public interface LocalPacoteRepository extends JpaRepository<LocalPacote, Long>, LocalPacoteRepositoryCustom {

	LocalPacote findById(@Param("id") Long id);

}