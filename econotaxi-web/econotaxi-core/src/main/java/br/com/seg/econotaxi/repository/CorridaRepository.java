/**
 * 
 */
package br.com.seg.econotaxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.seg.econotaxi.model.Corrida;
import br.com.seg.econotaxi.vo.MinhaCorridaVo;

/**
 * @author bruno
 *
 */
public interface CorridaRepository extends JpaRepository<Corrida, Long>, CorridaRepositoryCustom {

	Corrida findById(@Param("id") Long id);

	@Query(value =
			"select new br.com.seg.econotaxi.vo.MinhaCorridaVo(c.id, concat(c.origem, ' - ', c.origemEndereco), "+ 
					"concat(c.destino, ' - ', c.destinoEndereco), " +
					"m.id, m.idUsuario, v.id, v.marca, v.modelo, v.cor, v.placa, v.tipo, " +
					"c.dataSolicitacao, c.dataInicio, c.dataFinalizacao, c.classificacaoCorrida, m, c.valorFinal, c.status)" +
			"From Corrida c " +
					"inner join c.motorista m " +
					"inner join c.veiculo v " +
					"inner join c.usuario u " +
			"where u.id = :idUsuario and c.tipo = :tipo " + 
			"order by c.dataSolicitacao desc ")
	List<MinhaCorridaVo> findCorridasByUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") Integer tipo);

}