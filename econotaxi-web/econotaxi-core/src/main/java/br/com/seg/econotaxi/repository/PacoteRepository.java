/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Pacote;

/**
 * @author bruno
 *
 */
public interface PacoteRepository extends JpaRepository<Pacote, Long>, Serializable, PacoteRepositoryCustom {

	Pacote findById(@Param("id") Long id);

}