/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.enums.StatusMotoristaEnum;
import br.com.seg.econotaxi.enums.StatusVeiculoEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.MotoristaLigue;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.MotoristaLigueRepositoryCustom;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public class MotoristaLigueRepositoryImpl extends RepositoryCustomImpl<MotoristaLigue, Long> 
	implements MotoristaLigueRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#recuperarQtdMotoristaPorStatus(java.lang.Integer)
	 */
	@Override
	public Integer recuperarQtdMotoristaPorStatus(Integer status) {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(m) from MotoristaLigue m where m.status = :status ");
		params.put("status", status);
		return countByParameters(hql.toString(), params).intValue();
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#recuperarMotoristaPorStatus(java.lang.Integer)
	 */
	@Override
	public List<MotoristaLigue> recuperarMotoristaPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select m from MotoristaLigue m ");
		hql.append("where m.status = :status ");
		params.put("status", status);
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#pesquisarMotoristaPorFiltro(br.com.seg.econotaxi.model.Motorista, int, int)
	 */
	@Override
	public List<MotoristaLigue> pesquisarMotoristaPorFiltro(MotoristaLigue filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select m from MotoristaLigue m where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by m.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#pesquisarCountMotoristaPorFiltro(br.com.seg.econotaxi.model.Motorista)
	 */
	@Override
	public Long pesquisarCountMotoristaPorFiltro(MotoristaLigue filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(m) from MotoristaLigue m where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	private void definirFiltro(MotoristaLigue filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(m.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getId() != null) {
			hql.append(" and m.id = :idMotorista ");
			params.put("idMotorista", filtro.getId());
		}
		
		if (filtro.getCpf() != null && !StringUtils.isEmpty(filtro.getCpf())) {
			hql.append(" and m.cpf = :cpf ");
			params.put("cpf", filtro.getCpf());
		}
		
		if (filtro.getRg() != null && !StringUtils.isEmpty(filtro.getRg())) {
			hql.append(" and m.rg = :rg ");
			params.put("rg", filtro.getRg());
		}
		
		if (filtro.getCelular() != null && !StringUtils.isEmpty(filtro.getCelular())) {
			hql.append(" and m.celular = :celular ");
			params.put("celular", filtro.getCelular());
		}
		
		if (filtro.getCidadeMotorista() != null && !StringUtils.isEmpty(filtro.getCidadeMotorista())) {
			hql.append(" and upper(m.cidadeMotorista) like :cidadeMotorista ");
			params.put("cidadeMotorista", "%" + filtro.getCidadeMotorista().toUpperCase() + "%");
		}
		
		if (filtro.getSiglaUf() != null && !StringUtils.isEmpty(filtro.getSiglaUf())) {
			hql.append(" and upper(m.siglaUf) = :siglaUf ");
			params.put("siglaUf", filtro.getSiglaUf().toUpperCase());
		}
		
		if (filtro.getIndicadorOnline() != null) {
			hql.append(" and m.indicadorOnline = :indicadorOnline ");
			params.put("indicadorOnline", filtro.getIndicadorOnline());
		}
		
		if (filtro.getSexo() != null 
				&& !StringUtils.isEmpty(filtro.getSexo())) {
			hql.append(" and upper(m.sexo) = :sexo ");
			params.put("sexo", filtro.getSexo().toUpperCase());
		}
		
		if (filtro.getStatus() != null) {
			hql.append(" and m.status = :status ");
			params.put("status", filtro.getStatus());
		}
		
		if (filtro.getTipoTeleTaxi() != null && filtro.getTipoTeleTaxi().equals(1)) {
			hql.append(" and (m.tipoTeleTaxi = 1 or m.tipoTeleTaxi = 3) ");
		} else if (filtro.getTipoTeleTaxi() != null && filtro.getTipoTeleTaxi().equals(2)) {
			hql.append(" and (m.tipoTeleTaxi = 2 or m.tipoTeleTaxi = 3) ");
		} else if (filtro.getTipoTeleTaxi() != null && filtro.getTipoTeleTaxi().equals(3)) {
			hql.append(" and (m.tipoTeleTaxi = 3 or m.tipoTeleTaxi = 1 or m.tipoTeleTaxi = 2) ");
		}
		
		if (filtro.getTipoMotorista() != null && filtro.getTipoMotorista().equals(1)) {
			hql.append(" and m.indicadorPermissionario = 1 ");
		} else if (filtro.getTipoMotorista() != null && filtro.getTipoMotorista().equals(2)) {
			hql.append(" and m.indicadorAuxiliar = 1 ");
		}
		
	}

	@Override
	public MotoristaLigue recuperarMotoristaPorUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select m from MotoristaLigue m where m.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		return findOne(hql.toString(), params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.vo.CarrosVO(m, u, v) ");
		hql.append("from MotoristaLigue m, Usuario u, Veiculo v ");
		hql.append("where u.id = m.idUsuario ");
		hql.append("and v.motorista.id = m.id ");
		hql.append("and m.status = :status ");
		params.put("status", StatusMotoristaEnum.ATIVO.getStatus());
		hql.append("and v.status = :statusVeiculo ");
		params.put("statusVeiculo", StatusVeiculoEnum.ATIVO.getStatus());
		hql.append("and DATE_FORMAT(u.dataUltimaPosicao, '%Y%m%d%H%i%S') >= :dataUltima ");
		params.put("dataUltima", recuperarData10Minutos());
		hql.append("and m.cidade.id = :idCidade ");
		params.put("idCidade", cidade.getId());
		Query query = getEntityManager().createQuery(hql.toString());
        setParametersOnQuery(query, params);
        return query.getResultList();
	}

	private String recuperarData10Minutos() {
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -10);
		return new SimpleDateFormat("YYYYMMddHHmmss").format(c.getTime());
	}

	@Override
	public List<MotoristaLigue> recuperarMotoristasPorCidade(Cidade cidadeSelecionada) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select m from MotoristaLigue m ");
		hql.append("where m.cidade.id = :idCidade ");
		hql.append("and m.tipoTeleTaxi is not null and m.tipoTeleTaxi != 0 ");
		params.put("idCidade", cidadeSelecionada.getId());
		hql.append("order by m.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}