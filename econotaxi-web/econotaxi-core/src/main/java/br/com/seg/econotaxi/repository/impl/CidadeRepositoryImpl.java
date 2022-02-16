/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.repository.CidadeRepositoryCustom;

/**
 * @author bruno
 *
 */
public class CidadeRepositoryImpl extends RepositoryCustomImpl<Cidade, Long> 
	implements CidadeRepositoryCustom {

	@PersistenceContext(name = "default")
	private EntityManager entityManager;
	
	@Override
	public Long pesquisarCountCidadePorFiltro(Cidade filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Cidade c where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.CidadeRepositoryCustom#pesquisarCidadePorFiltro(br.com.seg.econotaxi.model.Cidade, int, int)
	 */
	@Override
	public List<Cidade> pesquisarCidadePorFiltro(Cidade filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Cidade c where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by c.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Cidade filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(c.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getSiglaUf() != null && !StringUtils.isEmpty(filtro.getSiglaUf())) {
			hql.append(" and upper(c.siglaUf) = :siglaUf ");
			params.put("siglaUf", filtro.getSiglaUf().toUpperCase());
		}
		
		if (filtro.getSiglaPais() != null && !StringUtils.isEmpty(filtro.getSiglaPais())) {
			hql.append(" and upper(c.siglaPais) = :siglaPais ");
			params.put("siglaPais", filtro.getSiglaPais().toUpperCase());
		}
		
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.CidadeRepositoryCustom#verificaExistenciaCidade(br.com.seg.econotaxi.model.Cidade)
	 */
	@Override
	public boolean verificaExistenciaCidade(Cidade cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(c) from Cidade c where upper(c.nome) = :nome ");
		params.put("nome", cidade.getNome().toUpperCase());
		hql.append("and c.siglaUf = :siglaUf ");
		params.put("siglaUf", cidade.getSiglaUf().toUpperCase());
		if (cidade.getId() != null) {
			hql.append("and c.id != :idCidade ");
			params.put("idCidade", cidade.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public List<Cidade> findAllByOrderByNomeAsc() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select c from Cidade c ");
		hql.append("order by c.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Cidade recuperarCidadeUsuarioApp(String latitude, String longitude) {
	
		Cidade cidade = null;
		List<Cidade> cidades = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		sql.append("c.id, c.nome, c.sigla_uf, c.sigla_pais, c.latitude, c.longitude, c.raio_km, ");
		sql.append("c.vl_minimo_corrida, c.vl_hora_parada, c.vl_bandeirada, c.vl_km_bandeira_1, c.vl_km_bandeira_2, ");
		sql.append("c.vl_adic_executivo, c.vl_porcent_adic_executivo, c.vl_adic_bike, ");
		sql.append("c.vl_porcent_adic_bike, c.vl_minimo_entrega, c.hr_inicio_bandeira_2, c.hr_fim_bandeira_2, c.vl_km_entrega, c.kg_maximo_entrega, ");
		sql.append("c.ind_possui_rastreador, c.ind_aceita_pgto_dinheiro, c.ind_aceita_pgto_debito, c.vl_desconto_lojista, ");
		sql.append("c.vl_adic_cadeirinha, c.vl_porcent_adic_cadeirinha, c.vl_porcent_desc_dinheiro, c.vl_porcent_desc_niver, ");
		sql.append("c.vl_porcent_desc_momento, c.dt_inicio_porc_desc_momento, c.hr_inicio_porc_desc_momento, c.dt_fim_porc_desc_momento, c.hr_fim_porc_desc_momento, ");
		sql.append("ACOS( SIN( RADIANS( `latitude` ) ) * SIN( RADIANS( :latitude ) ) + COS( RADIANS( `latitude` ) )  ");
		sql.append("* COS( RADIANS( :latitude )) * COS( RADIANS( `longitude` ) - RADIANS( :longitude )) ) * 6380 AS `distance`, c.kmForaCidade, c.valorKmForaCidade ");
		sql.append("c.ind_aceita_pgt_credito, c.ind_possui_taximetro, c.ind_possui_carro_particular, c.ind_possui_taxi, c.ind_possui_mototaxi, ");
		sql.append("c.ind_possui_entrega_moto, c.ind_possui_entrega_carro, c.ind_possui_entrega_caminhao, c.ind_possui_guincho, c.ind_possui_teletaxi, ");
		sql.append("c.vl_hora_parada_particular, c.vl_bandeirada_particular, c.vl_km_bandeira_1_particular, c.vl_km_bandeira_2_particular, "); 
		sql.append("c.vl_hora_parada_moto_taxi, c.vl_bandeirada_moto_taxi, c.vl_km_bandeira_1_moto_taxi, c.vl_km_bandeira_2_moto_taxi, "); 
		sql.append("c.vl_adicional_exec_particular, c.vl_adicional_exec_porc_particular, c.vl_km_fora_particular, c.vl_km_fora_moto_taxi, ");
		sql.append("c.vl_porcent_desc_niver_part, ");
		sql.append("c.vl_porcent_desc_momento_part, c.dt_inicio_porc_desc_momento_part, ");
		sql.append("c.hr_inicio_porc_desc_momento_part, c.dt_fim_porc_desc_momento_part, c.hr_fim_porc_desc_momento_part, ");
		sql.append("c.vl_porcent_desc_niver_moto, ");
		sql.append("c.vl_porcent_desc_momento_moto, c.dt_inicio_porc_desc_momento_moto, ");
		sql.append("c.hr_inicio_porc_desc_momento_moto, c.dt_fim_porc_desc_momento_moto, c.hr_fim_porc_desc_momento_moto, c.vl_minimo_corrida_band_2 ");
		sql.append("FROM taxi.`cidade` c where c.latitude is not null and c.longitude is not null and c.raio_km is not null ");
		/*WHERE
		ACOS( SIN( RADIANS( `latitude` ) ) * SIN( RADIANS( -15.828057200000002 ) ) + COS( RADIANS( `latitude` ) )
		* COS( RADIANS( -15.828057200000002 )) * COS( RADIANS( `longitude` ) - RADIANS( -47.95489329999998 )) ) * 6380 < 12325*/
		sql.append("ORDER BY `distance` ");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		
		query.setParameter("latitude", latitude);
		query.setParameter("longitude", longitude);
		
		query.setFirstResult(0);
		query.setMaxResults(10);
		
		List<Object[]> resultado = query.getResultList();
		
		cidades = montarResultado(resultado);
		
		if (cidades != null && !cidades.isEmpty()) {
			for (Cidade city : cidades) {
				if (Float.valueOf(city.getRaioKm()) > city.getDistancia().floatValue()) {
					cidade = city;
					break;
				}
			}
		}
		return cidade;
	}
	
	private List<Cidade> montarResultado(List<Object[]> resultado) {
		List<Cidade> dados = new ArrayList<Cidade>();
		
		Cidade dado = null;
		
		for(Object[] r : resultado) {
			
			try {
				dado = new Cidade(
						getLong(r[0]), 
						getString(r[1]), 
						getString(r[2]), 
						getString(r[3]), 
						getString(r[4]), 
						getString(r[5]), 
						getString(r[6]), 
						getBigDecimal(r[7]), 
						getBigDecimal(r[8]), 
						getBigDecimal(r[9]), 
						getBigDecimal(r[10]), 
						getBigDecimal(r[11]), 
						getBigDecimal(r[12]), 
						getBigDecimal(r[13]), 
						getBigDecimal(r[14]), 
						getBigDecimal(r[15]), 
						getBigDecimal(r[16]), 
						getString(r[17]),
						getString(r[18]),
						getBigDecimal(r[19]),
						getInteger(r[20]),
						getInteger(r[21]),
						getInteger(r[22]),
						getInteger(r[23]),
						getBigDecimal(r[24]),
						getBigDecimal(r[25]),
						getBigDecimal(r[26]),
						getBigDecimal(r[27]), //
						getBigDecimal(r[28]),
						getBigDecimal(r[29]),
						getString(r[30]),
						getString(r[31]),
						getString(r[32]),
						getString(r[33]), //
						getBigDecimal(r[34]),
						getInteger(r[35]),
						getBigDecimal(r[36]),
						getInteger(r[37]),
						getInteger(r[38]),
						getInteger(r[39]),
						getInteger(r[40]),
						getInteger(r[41]),
						getInteger(r[42]),
						getInteger(r[43]),
						getInteger(r[44]),
						getInteger(r[45]),
						getInteger(r[46]),
						getBigDecimal(r[47]),
						getBigDecimal(r[48]),
						getBigDecimal(r[49]),
						getBigDecimal(r[50]),
						getBigDecimal(r[51]),
						getBigDecimal(r[52]),
						getBigDecimal(r[53]),
						getBigDecimal(r[54]),
						getBigDecimal(r[55]),
						getBigDecimal(r[56]),
						getBigDecimal(r[57]),
						getBigDecimal(r[58]),
						getBigDecimal(r[59]),
						getBigDecimal(r[60]),
						getBigDecimal(r[61]),
						getString(r[62]),
						getString(r[63]),
						getString(r[64]),
						getString(r[65]),
						getBigDecimal(r[66]),
						getBigDecimal(r[67]),
						getBigDecimal(r[68]),
						getString(r[69]),
						getString(r[70]),
						getString(r[71]),
						getString(r[72]),
						getBigDecimal(r[73]));
				
				dados.add(dado);
			} catch (RuntimeException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			
		}
		
		return dados;
	}
	
	private String getString(Object valor) {
		return (valor != null) ? String.valueOf(valor) : null;
	}
	
	private Integer getInteger(Object valor) {
		return (valor != null) ? Integer.valueOf(valor.toString()) : null;
	}
	
	private Long getLong(Object valor) {
		return (valor != null) ? Long.valueOf(valor.toString()) : null;
	}
	
	private BigDecimal getBigDecimal(Object valor) {
		
		return (valor != null) ? new BigDecimal(valor.toString()) : null;
	}

}