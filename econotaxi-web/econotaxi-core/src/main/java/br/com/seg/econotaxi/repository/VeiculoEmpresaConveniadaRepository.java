/**
 * 
 */
package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.VeiculoEmpresaConveniada;
import br.com.seg.econotaxi.model.VeiculoEmpresaConveniadaPk;

/**
 * @author bruno
 *
 */
public interface VeiculoEmpresaConveniadaRepository extends JpaRepository<VeiculoEmpresaConveniada, VeiculoEmpresaConveniadaPk> {

	VeiculoEmpresaConveniada findById(@Param("id") VeiculoEmpresaConveniadaPk id);

}