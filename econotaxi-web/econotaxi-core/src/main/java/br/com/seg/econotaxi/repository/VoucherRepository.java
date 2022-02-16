/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Voucher;

/**
 * @author bruno
 *
 */
public interface VoucherRepository extends JpaRepository<Voucher, Long>, 
	VoucherRepositoryCustom {

}