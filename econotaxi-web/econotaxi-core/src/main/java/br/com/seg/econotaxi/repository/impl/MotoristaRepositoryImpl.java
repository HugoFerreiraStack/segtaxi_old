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
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.MotoristaRepositoryCustom;
import br.com.seg.econotaxi.vo.CarrosVO;

/**
 * @author bruno
 *
 */
public class MotoristaRepositoryImpl extends RepositoryCustomImpl<Motorista, Long> 
	implements MotoristaRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#recuperarQtdMotoristaPorStatus(java.lang.Integer)
	 */
	@Override
	public Integer recuperarQtdMotoristaPorStatus(Integer status) {

		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(m) from Motorista m where m.status = :status ");
		params.put("status", status);
		return countByParameters(hql.toString(), params).intValue();
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#recuperarMotoristaPorStatus(java.lang.Integer)
	 */
	@Override
	public List<Motorista> recuperarMotoristaPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Motorista(m, u) from Motorista m, Usuario u ");
		hql.append("where u.id = m.idUsuario and m.status = :status ");
		params.put("status", status);
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#pesquisarMotoristaPorFiltro(br.com.seg.econotaxi.model.Motorista, int, int)
	 */
	@Override
	public List<Motorista> pesquisarMotoristaPorFiltro(Motorista filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Motorista(m, u) from Motorista m, Usuario u where u.id = m.idUsuario ");
		definirFiltro(filtro, hql, params);
		hql.append("order by u.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.MotoristaRepositoryCustom#pesquisarCountMotoristaPorFiltro(br.com.seg.econotaxi.model.Motorista)
	 */
	@Override
	public Long pesquisarCountMotoristaPorFiltro(Motorista filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(m) from Motorista m, Usuario u where u.id = m.idUsuario ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	private void definirFiltro(Motorista filtro, StringBuilder hql, Map<String, Object> params) {
		
		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(u.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getId() != null) {
			hql.append(" and m.id = :idMotorista ");
			params.put("idMotorista", filtro.getId());
		}
		
		if (filtro.getCidade() != null && filtro.getCidade().getId() != null) {
			hql.append(" and m.cidade.id = :idCidade ");
			params.put("idCidade", filtro.getCidade().getId());
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
			hql.append(" and upper(u.sexo) = :sexo ");
			params.put("sexo", filtro.getSexo().toUpperCase());
		}
		
		if (filtro.getStatus() != null) {
			hql.append(" and m.status = :status ");
			params.put("status", filtro.getStatus());
		}
		
		if (filtro.getVersaoApp() != null && !filtro.getVersaoApp().isEmpty()) {
			hql.append(" and u.versaoApp = :versaoApp ");
			params.put("versaoApp", filtro.getVersaoApp());
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
	public Motorista recuperarMotoristaPorUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select m from Motorista m where m.idUsuario = :idUsuario ");
		params.put("idUsuario", usuario.getId());
		return findOne(hql.toString(), params);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CarrosVO> recuperarMotoristasCidadeOnline(Cidade cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.vo.CarrosVO(m, u, v) ");
		hql.append("from Motorista m, Usuario u, Veiculo v ");
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
	public List<Motorista> recuperarMotoristasPorCidade(Cidade cidadeSelecionada) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Motorista(m, u) from Motorista m, Usuario u ");
		hql.append("where m.idUsuario = u.id and m.cidade.id = :idCidade ");
		hql.append("and m.tipoTeleTaxi is not null and m.tipoTeleTaxi != 0 ");
		params.put("idCidade", cidadeSelecionada.getId());
		hql.append("order by u.nome asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}