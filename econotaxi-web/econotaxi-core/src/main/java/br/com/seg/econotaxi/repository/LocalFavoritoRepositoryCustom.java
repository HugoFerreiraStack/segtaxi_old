/**
 * 
 */
package br.com.seg.econotaxi.repository;

import br.com.seg.econotaxi.model.LocalFavorito;

/**
 * @author bruno
 *
 */
public interface LocalFavoritoRepositoryCustom {

	boolean verificaExistenciaLocalFavorito(LocalFavorito localFavorito);
	
}