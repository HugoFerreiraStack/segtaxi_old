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
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Veiculo;
import br.com.seg.econotaxi.repository.VeiculoRepositoryCustom;

/**
 * @author bruno
 *
 */
public class VeiculoRepositoryImpl extends RepositoryCustomImpl<Veiculo, Long> 
		implements VeiculoRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.VeiculoRepositoryCustom#pesquisarCountPerfilPorFiltro(br.com.seg.econotaxi.model.Veiculo)
	 */
	@Override
	public Long pesquisarCountPerfilPorFiltro(Veiculo filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from Veiculo v, Usuario u where v.motorista.idUsuario = u.id ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.VeiculoRepositoryCustom#pesquisarPerfilPorFiltro(br.com.seg.econotaxi.model.Veiculo, int, int)
	 */
	@Override
	public List<Veiculo> pesquisarPerfilPorFiltro(Veiculo filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Veiculo(v, u) from Veiculo v, Usuario u where v.motorista.idUsuario = u.id ");
		definirFiltro(filtro, hql, params);
		hql.append("order by u.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Veiculo filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getDescricao() != null && !StringUtils.isEmpty(filtro.getDescricao())) {
			hql.append(" and upper(v.descricao) like :descricao ");
			params.put("descricao", "%" + filtro.getDescricao().toUpperCase() + "%");
		}
		
		if (filtro.getMotorista() != null && filtro.getMotorista().getNome() != null 
				&& !StringUtils.isEmpty(filtro.getMotorista().getNome())) {
			hql.append(" and upper(u.nome) like :nomeMotorista ");
			params.put("nomeMotorista", "%" + filtro.getMotorista().getNome().toUpperCase() + "%");
		}
		
		if (filtro.getNomeMotoristaLogado() != null && !StringUtils.isEmpty(filtro.getNomeMotoristaLogado())) {
			hql.append(" and upper(u.nome) like :nomeMotorista ");
			params.put("nomeMotorista", "%" + filtro.getNomeMotoristaLogado().toUpperCase() + "%");
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
		
		if (filtro.getIndicadorPossuiEmpresaAssociada() != null) {
			if (filtro.getIndicadorPossuiEmpresaAssociada().equals(1)) {
				hql.append("and exists (select 1 from VeiculoEmpresaConveniada vec where vec.id.idVeiculo = v.id and vec.id.idEmpresaConveniada = :idEmpresaConveniada2) ");
				params.put("idEmpresaConveniada2", filtro.getIdEmpresaConveniada());
			} else {
				hql.append("and not exists (select 1 from VeiculoEmpresaConveniada vec where vec.id.idVeiculo = v.id and vec.id.idEmpresaConveniada = :idEmpresaConveniada2) ");
				params.put("idEmpresaConveniada2", filtro.getIdEmpresaConveniada());
			}
		}
		
	}

	@Override
	public Integer recuperarQtdVeiculoPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from Veiculo v where v.status = :status ");
		params.put("status", status);
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public List<Veiculo> recuperarVeiculoPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Veiculo(v, u) from Veiculo v, Usuario u ");
		hql.append("where v.motorista.idUsuario = u.id and v.status = :status ");
		params.put("status", status);
		hql.append("order by v.marca asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Veiculo recuperarVeiculoPorRenavan(String renavan) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from Veiculo v where v.renavan = :renavan ");
		params.put("renavan", renavan);
		return findOne(hql.toString(), params);
	}

	@Override
	public Veiculo recuperarVeiculoPorMotorista(Motorista motorista) {
		
		Veiculo veiculo = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from Veiculo v where v.motorista.id = :idMotorista ");
		params.put("idMotorista", motorista.getId());
		try {
			veiculo = findOne(hql.toString(), params);
		} catch (Exception e) {	}
		return veiculo;
	}
	
	@Override
	public List<Veiculo> recuperarVeiculosAuxiliar(Motorista motoristaAuxiliar) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select v from Veiculo v ");
		hql.append("where v.status = :status ");
		params.put("status", StatusVeiculoEnum.ATIVO.getStatus());
		hql.append("and v.motorista.idUsuario in (select a.idUsuario from Auxiliar a ");
		hql.append("where a.cpf = :cpfAuxiliar) ");
		hql.append("and v.motorista.status = :statusMotorista ");
		params.put("statusMotorista", StatusMotoristaEnum.ATIVO.getStatus());
		params.put("cpfAuxiliar", motoristaAuxiliar.getCpf());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<Veiculo> recuperarVeiculosPorCidade(Cidade cidadeSelecionada) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Veiculo(v, u) from Veiculo v, Usuario u ");
		hql.append("where v.motorista.cidade.id = :idCidade ");
		params.put("idCidade", cidadeSelecionada.getId());
		hql.append("and v.motorista.indicadorAceitaVoucher = 1 and v.motorista.indicadorPermissionario = 1 and v.motorista.idUsuario = u.id ");
		hql.append("and v.motorista.status = :statusMotorista ");
		hql.append("and v.status = :statusMotorista ");
		params.put("statusMotorista", StatusMotoristaEnum.ATIVO.getStatus());
		hql.append("order by v.placa asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Long pesquisarCountVeiculoEmpresaPorFiltro(Veiculo filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(v) from Veiculo v, Usuario u where v.motorista.idUsuario = u.id ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	@Override
	public List<Veiculo> pesquisarVeiculoEmpresaPorFiltro(Veiculo filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Veiculo(v, u, ");
		hql.append("(select 1 from VeiculoEmpresaConveniada vec where vec.id.idVeiculo = v.id and vec.id.idEmpresaConveniada = :idEmpresaConveniada)) ");
		hql.append("from Veiculo v, Usuario u where v.motorista.idUsuario = u.id ");
		params.put("idEmpresaConveniada", filtro.getIdEmpresaConveniada());
		definirFiltro(filtro, hql, params);
		hql.append("order by u.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	@Override
	public List<Veiculo> recuperarVeiculosEmpresa(Long idEmpresaConveniada, Long idCidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Veiculo(v, u) ");
		hql.append("from Veiculo v, Usuario u where v.motorista.idUsuario = u.id ");
		hql.append("and exists (select 1 from VeiculoEmpresaConveniada vec where vec.id.idVeiculo = v.id and vec.id.idEmpresaConveniada = :idEmpresaConveniada) ");
		params.put("idEmpresaConveniada", idEmpresaConveniada);
		hql.append("and v.motorista.cidade.id = :idCidade ");
		params.put("idCidade", idCidade);
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}