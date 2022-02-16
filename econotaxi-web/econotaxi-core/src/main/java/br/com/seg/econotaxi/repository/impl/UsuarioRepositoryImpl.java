/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.enums.StatusCorridaEnum;
import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Motorista;
import br.com.seg.econotaxi.model.Notificacao;
import br.com.seg.econotaxi.model.Novidade;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.repository.UsuarioRepositoryCustom;

/**
 * @author bruno
 *
 */
public class UsuarioRepositoryImpl extends RepositoryCustomImpl<Usuario, Long> 
		implements UsuarioRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.UsuarioRepositoryCustom#verificaExistenciaUsuario(br.com.seg.econotaxi.model.Usuario)
	 */
	@Override
	public boolean verificaExistenciaUsuario(Usuario usuario) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u) from Usuario u where upper(u.login) = :login ");
		params.put("login", usuario.getLogin().toUpperCase());
		if (usuario.getId() != null) {
			hql.append("and u.id != :idUsuario ");
			params.put("idUsuario", usuario.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.UsuarioRepositoryCustom#pesquisarCountUsuarioPorFiltro(br.com.seg.econotaxi.model.Usuario)
	 */
	@Override
	public Long pesquisarCountUsuarioPorFiltro(Usuario filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u) from Usuario u where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.UsuarioRepositoryCustom#pesquisarUsuarioPorFiltro(br.com.seg.econotaxi.model.Usuario, int, int)
	 */
	@Override
	public List<Usuario> pesquisarUsuarioPorFiltro(Usuario filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by u.nome asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Usuario filtro, StringBuilder hql, Map<String, Object> params) {

		if (filtro.getNome() != null && !StringUtils.isEmpty(filtro.getNome())) {
			hql.append(" and upper(u.nome) like :nome ");
			params.put("nome", "%" + filtro.getNome().toUpperCase() + "%");
		}
		
		if (filtro.getLogin() != null && !StringUtils.isEmpty(filtro.getLogin())) {
			hql.append(" and upper(u.login) like :login ");
			params.put("login", "%" + filtro.getLogin().toUpperCase() + "%");
		}
		
		/*if (filtro.getIndicadorTeleTaxi() != null) {
			hql.append(" and u.indicadorTeleTaxi = 1 ");
		} else {
			hql.append(" and (u.indicadorTeleTaxi is null or u.indicadorTeleTaxi != 1) ");
		}*/
		
		if (filtro.getTipo() != null) {
			hql.append(" and u.tipo = :tipo ");
			params.put("tipo", filtro.getTipo());
		}
		
		if (filtro.getIdEmpresaConveniada() != null) {
			hql.append(" and u.idEmpresaConveniada = :idEmpresaConveniada ");
			params.put("idEmpresaConveniada", filtro.getIdEmpresaConveniada());
		}
		
		if (filtro.getIdCentroCusto() != null) {
			hql.append(" and (u.idCentroCusto = :idCentroCusto or u.idCentroCusto is null) ");
			params.put("idCentroCusto", filtro.getIdCentroCusto());
		}
		
		if (filtro.getCnpj() != null) {
			hql.append(" and u.cnpj = :cnpj ");
			params.put("cnpj", filtro.getCnpj());
		}
		
		if (filtro.getPossuiCartao() != null && filtro.getPossuiCartao().equals(1)) {
			hql.append(" and exists (select 1 from Pagamento p where p.usuario.id = u.id) ");
		} else if (filtro.getPossuiCartao() != null && filtro.getPossuiCartao().equals(2)) {
			hql.append(" and not exists (select 1 from Pagamento p where p.usuario.id = u.id) ");
		}
		
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.UsuarioRepositoryCustom#recuperarQtdUsuarioAppPorStatus(java.lang.Integer)
	 */
	@Override
	public Integer recuperarQtdUsuarioAppPorStatus(Integer status) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u) from Usuario u where u.tipo = :tipo ");
		params.put("tipo", TipoUsuarioEnum.CLIENTE.getCodigo());
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public Integer recuperarQtdUsuarioAppComPagamento() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u) from Usuario u where u.tipo = :tipo ");
		params.put("tipo", TipoUsuarioEnum.CLIENTE.getCodigo());
		hql.append("and exists (select 1 from Pagamento p where p.usuario.id = u.id) ");
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public Usuario recuperarUsuarioPorHashTrocaSenha(String hashTrocaSenha) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u where u.hashTrocaSenha = :hashTrocaSenha ");
		params.put("hashTrocaSenha", hashTrocaSenha);
		hql.append("and u.tipo = :tipo ");
		params.put("tipo", TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
		return findOne(hql.toString(), params);
	}

	@Override
	public List<Usuario> recuperarUsuariosAdministrativo() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u ");
		hql.append("where u.tipo = :tipo ");
		params.put("tipo", TipoUsuarioEnum.ADMINISTRATIVO.getCodigo());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<Usuario> recuperarUsuariosSemNotificacao(Notificacao notificacao) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u ");
		if (notificacao.getPublico().equals(1)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.MOTORISTA.getCodigo());
		} else if (notificacao.getPublico().equals(2)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.LOJISTA.getCodigo());
		} else if (notificacao.getPublico().equals(3)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.CLIENTE.getCodigo());
		} else if (notificacao.getPublico().equals(4)) {
			hql.append("where u.tipo in (:tipos) ");
			List<Integer> tipos = new ArrayList<Integer>();
			tipos.add(TipoUsuarioEnum.CLIENTE.getCodigo());
			tipos.add(TipoUsuarioEnum.LOJISTA.getCodigo());
			tipos.add(TipoUsuarioEnum.MOTORISTA.getCodigo());
			params.put("tipos", tipos);
		} else if (notificacao.getPublico().equals(8)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.MOTORISTA.getCodigo());
			hql.append("and exists (select 1 from Motorista m ");
			hql.append("where m.idUsuario = u.id ");
			hql.append("and (m.tipoTeleTaxi = :tipoTele or m.tipoTeleTaxi = 3)) ");
			params.put("tipoTele", 2);
		} else if (notificacao.getPublico().equals(9)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.MOTORISTA.getCodigo());
			hql.append("and exists (select 1 from Motorista m ");
			hql.append("where m.idUsuario = u.id ");
			hql.append("and (m.tipoTeleTaxi = :tipoTele or m.tipoTeleTaxi = 3)) ");
			params.put("tipoTele", 1);
		}
		hql.append("and not exists (select 1 from UsuarioNotificacao un where un.id.idUsuario = u.id ");
		hql.append("and un.id.idNotificacao = :idNotificacao) ");
		params.put("idNotificacao", notificacao.getId());
		hql.append("order by u.dataUltimaPosicao desc ");
		return findByParametersPaginator(hql.toString(), params, 0, 100);
	}

	@Override
	public List<Usuario> recuperarUsuariosSemNovidade(Novidade novidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u ");
		hql.append("where u.tipo in (:tipos) ");
		List<Integer> tipos = new ArrayList<Integer>();
		tipos.add(TipoUsuarioEnum.CLIENTE.getCodigo());
		tipos.add(TipoUsuarioEnum.LOJISTA.getCodigo());
		tipos.add(TipoUsuarioEnum.MOTORISTA.getCodigo());
		params.put("tipos", tipos);
		hql.append("and not exists (select 1 from UsuarioNovidade un where un.id.idUsuario = u.id ");
		hql.append("and un.id.idNovidade = :idNovidade) ");
		params.put("idNovidade", novidade.getId());
		hql.append("order by u.dataUltimaPosicao desc ");
		return findByParametersPaginator(hql.toString(), params, 0, 100);
	}

	@Override
	public int quantidadeUsuariosSemNotificacao(Notificacao notificacao) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u.id) from Usuario u ");
		if (notificacao.getPublico().equals(1)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.MOTORISTA.getCodigo());
		} else if (notificacao.getPublico().equals(2)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.LOJISTA.getCodigo());
		} else if (notificacao.getPublico().equals(3)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.CLIENTE.getCodigo());
		} else if (notificacao.getPublico().equals(4)) {
			hql.append("where u.tipo in (:tipos) ");
			List<Integer> tipos = new ArrayList<Integer>();
			tipos.add(TipoUsuarioEnum.CLIENTE.getCodigo());
			tipos.add(TipoUsuarioEnum.LOJISTA.getCodigo());
			tipos.add(TipoUsuarioEnum.MOTORISTA.getCodigo());
			params.put("tipos", tipos);
		} else if (notificacao.getPublico().equals(8)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.MOTORISTA.getCodigo());
			hql.append("and exists (select 1 from Motorista m ");
			hql.append("where m.idUsuario = u.id ");
			hql.append("and (m.tipoTeleTaxi = :tipoTele or m.tipoTeleTaxi = 3)) ");
			params.put("tipoTele", 2);
		} else if (notificacao.getPublico().equals(9)) {
			hql.append("where u.tipo = :tipo ");
			params.put("tipo", TipoUsuarioEnum.MOTORISTA.getCodigo());
			hql.append("and exists (select 1 from Motorista m ");
			hql.append("where m.idUsuario = u.id ");
			hql.append("and (m.tipoTeleTaxi = :tipoTele or m.tipoTeleTaxi = 3)) ");
			params.put("tipoTele", 1);
		}
		hql.append("and not exists (select 1 from UsuarioNotificacao un where un.id.idUsuario = u.id ");
		hql.append("and un.id.idNotificacao = :idNotificacao) ");
		params.put("idNotificacao", notificacao.getId());
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public int quantidadeUsuariosSemNovidade(Novidade novidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u.id) from Usuario u ");
		hql.append("where u.tipo in (:tipos) ");
		List<Integer> tipos = new ArrayList<Integer>();
		tipos.add(TipoUsuarioEnum.CLIENTE.getCodigo());
		tipos.add(TipoUsuarioEnum.LOJISTA.getCodigo());
		tipos.add(TipoUsuarioEnum.MOTORISTA.getCodigo());
		params.put("tipos", tipos);
		hql.append("and not exists (select 1 from UsuarioNovidade un where un.id.idUsuario = u.id ");
		hql.append("and un.id.idNovidade = :idNovidade) ");
		params.put("idNovidade", novidade.getId());
		return countByParameters(hql.toString(), params).intValue();
	}

	@Override
	public List<Usuario> recuperarUsuariosPorTipoCidade(Integer codigo, Cidade cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u ");
		hql.append("where u.tipo = :tipo ");
		params.put("tipo", codigo);
		if (codigo.equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			hql.append("and exists (select 1 from Motorista m ");
			hql.append("where m.idUsuario = u.id and m.cidade.id = :idCidade) ");
			params.put("idCidade", cidade.getId());
		} else if (codigo.equals(TipoUsuarioEnum.LOJISTA.getCodigo())) {
			hql.append("and exists (select 1 from Lojista l ");
			hql.append("where l.idUsuario = u.id and l.idCidade = :idCidade) ");
			params.put("idCidade", cidade.getId());
		} else if (codigo.equals(TipoUsuarioEnum.CLIENTE.getCodigo())) {
			hql.append("and exists (select 1 from Corrida c ");
			hql.append("where c.usuario.id = u.id and c.cidade.id = :idCidade) ");
			params.put("idCidade", cidade.getId());
		}
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public Usuario consultarUsuarioPorCelular(String celular) {
		
		Usuario usuario = null;
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u ");
		hql.append("where replace(replace(replace(replace(replace(u.celular, '(', ''), ')', ''), '-', ''), ' ', ''), ' ','') = :celular ");
		params.put("celular", celular);
		try {
			List<Usuario> usuarios = findByParametersWithoutPaginator(hql.toString(), params);
			if (usuarios != null && !usuarios.isEmpty()) {
				usuario = usuarios.get(0);
			}
		} catch (Exception e) { e.printStackTrace(); }
		return usuario;
	}

	@Override
	public List<Usuario> recuperarPermissionariosUsuario(Motorista motorista) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u, Motorista m ");
		hql.append("where m.idUsuario = u.id ");
		hql.append("and m.indicadorPermissionario = 1 ");
		hql.append("and m.idUsuario in (select a.idUsuario from Auxiliar a where a.cpf = :cpf) ");
		params.put("cpf", motorista.getCpf());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public List<Usuario> recuperarUsuariosPorTipoCidadeRadio(Integer codigo, Cidade cidade, Integer tipoRadio) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select u from Usuario u ");
		hql.append("where u.tipo = :tipo ");
		params.put("tipo", codigo);
		if (codigo.equals(TipoUsuarioEnum.MOTORISTA.getCodigo())) {
			hql.append("and exists (select 1 from Motorista m ");
			hql.append("where m.idUsuario = u.id and m.cidade.id = :idCidade ");
			params.put("idCidade", cidade.getId());
			hql.append("and (m.tipoTeleTaxi = :tipoTele or m.tipoTeleTaxi = 3)) ");
			params.put("tipoTele", tipoRadio);
		}
		return findByParametersWithoutPaginator(hql.toString(), params);
	}
	
	@Override
	public List<Usuario> recuperarPassageiros() {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select new br.com.seg.econotaxi.model.Usuario(");
		hql.append("u.login, u.nome, u.dataCadastro, u.celular, u.dtNascimento, u.sexo, ");
		hql.append("(select count(c.id) from Corrida c where c.usuario.id = u.id and c.status = :statusCorrida), ");
		hql.append("(select max(c.dataSolicitacao) from Corrida c where c.usuario.id = u.id and c.status = :statusCorrida), ");
		hql.append("(select min(c.dataSolicitacao) from Corrida c where c.usuario.id = u.id and c.status = :statusCorrida) ");
		params.put("statusCorrida", StatusCorridaEnum.FINALIZADO.getStatus());
		hql.append(") from Usuario u ");
		hql.append("where u.tipo = :tipo ");
		params.put("tipo", TipoUsuarioEnum.CLIENTE.getCodigo());
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

	@Override
	public boolean verificaExistenciaUsuarioCelular(Usuario usuario) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(u) from Usuario u where (replace(u.celular,'()- ','') = :celular ");
		params.put("celular", usuario.getCelular().replaceAll("[^0-9]", ""));
		hql.append("or u.celular = :celular2) ");
		params.put("celular2", usuario.getCelular());
		if (usuario.getId() != null) {
			hql.append("and u.id != :idUsuario ");
			params.put("idUsuario", usuario.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

}