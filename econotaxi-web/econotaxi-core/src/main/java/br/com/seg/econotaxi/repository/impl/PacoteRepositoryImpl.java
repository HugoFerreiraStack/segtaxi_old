/**
 * 
 */
package br.com.seg.econotaxi.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import br.com.seg.econotaxi.model.Cidade;
import br.com.seg.econotaxi.model.Pacote;
import br.com.seg.econotaxi.repository.PacoteRepositoryCustom;

/**
 * @author bruno
 *
 */
public class PacoteRepositoryImpl extends RepositoryCustomImpl<Pacote, Long> 
	implements PacoteRepositoryCustom {

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.PacoteRepositoryCustom#pesquisarCountPacotePorFiltro(br.com.seg.econotaxi.model.Pacote)
	 */
	@Override
	public Long pesquisarCountPacotePorFiltro(Pacote filtro) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(p) from Pacote p where 1=1 ");
		definirFiltro(filtro, hql, params);
		return countByParameters(hql.toString(), params);
	}

	/* (non-Javadoc)
	 * @see br.com.seg.econotaxi.repository.PacoteRepositoryCustom#pesquisarPacotePorFiltro(br.com.seg.econotaxi.model.Pacote, int, int)
	 */
	@Override
	public List<Pacote> pesquisarPacotePorFiltro(Pacote filtro, int first, int pageSize) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Pacote p where 1=1 ");
		definirFiltro(filtro, hql, params);
		hql.append("order by p.titulo asc ");
		return findByParametersPaginator(hql.toString(), params, first, pageSize);
	}

	private void definirFiltro(Pacote filtro, StringBuilder hql, Map<String, Object> params) {

		if (filtro.getTitulo() != null && !StringUtils.isEmpty(filtro.getTitulo())) {
			hql.append(" and upper(p.titulo) like :titulo ");
			params.put("titulo", "%" + filtro.getTitulo().toUpperCase() + "%");
		}
		
		if (filtro.getCidade() != null && !StringUtils.isEmpty(filtro.getCidade().getNome())) {
			hql.append(" and upper(p.cidade.nome) like :nomeCidade ");
			params.put("nomeCidade", "%" + filtro.getCidade().getNome().toUpperCase() + "%");
		}
		
	}

	@Override
	public List<Pacote> findAllByOrderByNomeAsc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verificaExistenciaPacote(Pacote pacote) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select count(p) from Pacote p where upper(p.titulo) = :nome ");
		params.put("nome", pacote.getTitulo().toUpperCase());
		if (pacote.getId() != null) {
			hql.append("and p.id != :idPacote ");
			params.put("idPacote", pacote.getId());
		}
		return countByParameters(hql.toString(), params) > 0;
	}

	@Override
	public List<Pacote> recuperarPacotesComLocaisPorCidade(Cidade cidade) {
		
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		hql.append("select p from Pacote p ");
		hql.append("where p.cidade.id = :idCidade ");
		params.put("idCidade", cidade.getId());
		hql.append("and exists (select 1 from LocalPacote l where l.idPacote = p.id) ");
		hql.append("order by p.titulo asc ");
		return findByParametersWithoutPaginator(hql.toString(), params);
	}

}