/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoEnum;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.VeiculoLigue;
import br.com.seg.econotaxi.repository.VeiculoLigueRepositoryCustom;

/**
 * @author bruno
 *
 */
public class VeiculoLigueRepositoryImpl extends RepositoryCustomImpl<VeiculoLigue, Long> 
		implements VeiculoLigueRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.VeiculoRepositoryCustom#pesquisarCountPerfilPorFiltro(br.com.seg.econotaxi.model.Veiculo)
	 */
	@Override
	public Long pesquisarCountPerfilPorFiltro(VeiculoLigue filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from VeiculoLigue v where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.VeiculoRepositoryCustom#pesquisarPerfilPorFiltro(br.com.seg.econotaxi.model.Veiculo, int, int)
	 */
	@Override
	public List<VeiculoLigue> pesquisarPerfilPorFiltro(VeiculoLigue filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from VeiculoLigue v where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by v.motorista.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(VeiculoLigue filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getDescricao() != null && !StringUtils.isEmpty(filtro.getDescricao())) {
			hql.append(" and upper(v.descricao) like :descricao ");
			params.put("descricao", "%" + filtro.getDescricao().toUpperCase() + "%");
		}
		
		if (filtro.getMotorista() != null && filtro.getMotorista().getNome() != null 
				&& !StringUtils.isEmpty(filtro.getMotorista().getNome())) {
			hql.append(" and upper(v.motorista.nome) like :nomeMotorista ");
			params.put("nomeMotorista", "%" + filtro.getMotorista().getNome().toUpperCase() + "%");
		}
		
		if (filtro.getIme() != null && !StringUtils.isEmpty(filtro.getIme())) {
			hql.append(" and upper(v.ime) = :ime ");
			params.put("ime", filtro.getIme().toUpperCase());
		}
		
		if (filtro.getMotorista() != null && filtro.getMotorista().getId() != null) {
			hql.append(" and v.motorista.id = :idMotorista ");
			params.put("idMotorista", filtro.getMotorista().getId());
		}
		
		if (filtro.getStatus() != null) {
			hql.append(" and v.status = :status ");
			params.put("status", filtro.getStatus());
		}
		
		if (filtro.getUnidade() != null && !filtro.getUnidade().isEmpty()) {
			hql.append(" and v.unidade = :unidade ");
			params.put("unidade", filtro.getUnidade());
		}
		
		if (filtro.getPlaca() != null && !filtro.getPlaca().isEmpty()) {
			hql.append(" and upper(v.placa) = :placa ");
			params.put("placa", filtro.getPlaca().toUpperCase());
		}
		
	}

	@Override
	public Integer recuperarQtdVeiculoPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from VeiculoLigue v where v.status = :status ");
		params.put("status", status);
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public List<VeiculoLigue> recuperarVeiculoPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from VeiculoLigue v ");
		hql.append("where v.status = :status ");
		params.put("status", status);
		hql.append("order by v.marca asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public VeiculoLigue recuperarVeiculoPorRenavan(String renavan) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from VeiculoLigue v where v.renavan = :renavan ");
		params.put("renavan", renavan);
		return findOne(hql.toString(), params);
	}

	@Override
	public VeiculoLigue recuperarVeiculoPorMotorista(MotoristaLigue motorista) {
		
		VeiculoLigue veiculo = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from VeiculoLigue v where v.motorista.id = :idMotorista ");
		params.put("idMotorista", motorista.getId());
		try {
			veiculo = findOne(hql.toString(), params);
		} catch (Exception e) {	}
		return veiculo;
	}
	
	@Override
	public List<VeiculoLigue> recuperarVeiculosAuxiliar(MotoristaLigue motoristaAuxiliar) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from VeiculoLigue v ");
		hql.append("where v.status = :status ");
		params.put("status", StatusVeiculoEnum.ATIVO.getStatus());
		hql.append("and v.motorista.idUsuario in (select a.idUsuario from Auxiliar a ");
		hql.append("where a.cpf = :cpfAuxiliar) ");
		hql.append("and v.motorista.status = :statusMotorista ");
		params.put("statusMotorista", StatusMotoristaEnum.ATIVO.getStatus());
		params.put("cpfAuxiliar", motoristaAuxiliar.getCpf());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}