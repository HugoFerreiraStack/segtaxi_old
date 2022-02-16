/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.Map;

import br.com.seg.econotaxi.model.Voucher;
import br.com.seg.econotaxi.repository.VoucherRepositoryCustom;

/**
 * @author bruno
 *
 */
public class VoucherRepositoryImpl extends RepositoryCustomImpl<Voucher, Long> 
	implements VoucherRepositoryCustom {

	@Override
	public boolean verificaExistenciaVoucher(String voucher) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from Voucher v where upper(v.voucher) = :voucher ");
		params.put("voucher", voucher.toUpperCase());
		return countByParameters(hql.toString(), params) > 0;
	}

	
	
}