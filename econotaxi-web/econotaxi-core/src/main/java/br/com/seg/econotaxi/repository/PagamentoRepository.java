/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Pagamento;

/**
 * @author bruno
 *
 */
public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, PagamentoRepositoryCustom {

	Pagamento findById(@Param("id") Long id);

	@Query(value = "select p.tokenMaxipago from Pagamento p " +
			"where p.id = :id")
	String findToken(@Param("id") Long id);

}