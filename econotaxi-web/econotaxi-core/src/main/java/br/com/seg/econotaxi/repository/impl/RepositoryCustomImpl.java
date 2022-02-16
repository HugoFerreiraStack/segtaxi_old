package br.com.seg.econotaxi.repository.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Classe utilitária que contém métodos genéricos para consultas em banco de dados.
 *
 * Criado em 24 de mai de 2017
 * @author Welson Carvalho
 */
public abstract class RepositoryCustomImpl<T, I> {
	
	@PersistenceContext(unitName = "default")
	private EntityManager entityManager;
	
	/**
	 * Métodos responsáveis por realizar a consulta que contará a quantidade de registros que servirá de base para
	 * a paginação. 
	 * 
	 * @param hql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @return a quantidade de registros da consulta
	 */
	protected Long countByParameters(String hql, Map<String, Object> params) {
		
        Query query = getEntityManager().createQuery(hql);
        setParametersOnQuery(query, params);
        return (Long) query.getSingleResult();
    }
	
	/**
	 * Métodos responsáveis por realizar a consulta que contará a quantidade de registros que servirá de base para
	 * a paginação. 
	 * 
	 * @param sql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @return a quantidade de registros da consulta
	 */
	protected Long countByParametersNative(String sql, Map<String, Object> params) {
		
		Query query = getEntityManager().createNativeQuery(sql);
		setParametersOnQuery(query, params);
		return Long.valueOf(query.getSingleResult().toString());
	}
	
	/**
	 * Métodos responsáveis por realizar a consulta que somará valores. 
	 * 
	 * @param hql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @return a quantidade de registros da consulta
	 */
	protected BigDecimal sumByParameters(String hql, Map<String, Object> params) {
		
        Query query = getEntityManager().createQuery(hql);
        setParametersOnQuery(query, params);
        return (BigDecimal) query.getSingleResult();
    }
	
	/**
	 * Método responsável por recuperar registros do banco de dados realizando paginação utilizando consulta em HQL.
	 * 
	 * @param hql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @param first a página inicial
	 * @param max a quantidade de registros a paginar
	 * @return lista de objetos recuperados que atendem aos critérios da consulta
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByParametersPaginator(String hql, Map<String, Object> params, int first, int max) {
		
        Query query = getEntityManager().createQuery(hql);
        setParametersOnQuery(query, params);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }
	
	/**
	 * Método responsável por recuperar registros do banco de dados realizando paginação utilizando consulta em HQL.
	 * 
	 * @param hql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @param first a página inicial
	 * @param max a quantidade de registros a paginar
	 * @param returnClass classe de retorno
	 * @return lista de objetos recuperados que atendem aos critérios da consulta
	 */
	@SuppressWarnings("unchecked")
	protected <E extends Object> List<E> findByParametersPaginator(String hql, Map<String, Object> params, int first, int max, Class<E> returnClass) {
		
        Query query = getEntityManager().createQuery(hql);
        setParametersOnQuery(query, params);
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.getResultList();
    }
	
	/**
	 * Método responsável por recuperar registros do banco de dados sem realizar paginação utilizando consulta em HQL.
	 * 
	 * @param hql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @return lista de objetos recuperados que atendem aos critérios da consulta
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByParametersWithoutPaginator(String hql, Map<String, Object> params) {
		
        Query query = getEntityManager().createQuery(hql);
        setParametersOnQuery(query, params);
        return query.getResultList();
    }
	
	/**
	 * Método responsável por recuperar registros do banco de dados sem realizar paginação utilizando consulta em HQL.
	 * 
	 * @param hql a consulta a ser realizada
	 * @param params os critérios da consulta
	 * @param returnClass classe de retorno
	 * @return lista de objetos recuperados que atendem aos critérios da consulta
	 */
	@SuppressWarnings("unchecked")
	protected <E extends Object> List<E> findByParametersWithoutPaginator(String hql, Map<String, Object> params, Class<E> returnClass) {
		
        Query query = getEntityManager().createQuery(hql, returnClass);
        setParametersOnQuery(query, params);
        return query.getResultList();
    }
	
	/**
	 * Método responsável por definir os parâmetros da query
	 * 
	 * @param query a query a ser realizada
	 * @param params os parâmetros a serem definidos na query
	 */
	protected void setParametersOnQuery(Query query, Map<String, Object> params) {
		
        if(params != null) {
            params.entrySet().forEach(stringObjectEntry -> {
                query.setParameter(stringObjectEntry.getKey(), stringObjectEntry.getValue());
            });
        }
    }
	
	/**
	 * Método responsável por recuperar apenas um registro de acordo com os critérios da consulta.
	 * 
	 * @param hql a consulta
	 * @param params os parâmetros da consulta
	 * @return o registro
	 */
	@SuppressWarnings("unchecked")
	protected T findOne(String hql, Map<String, Object> params) {
		
		Query query = getEntityManager().createQuery(hql);
        setParametersOnQuery(query, params);
        return (T) query.getSingleResult();
	}
	
	/**
	 * Método responsável por recuperar apenas um registro de acordo com os critérios da consulta.
	 * 
	 * @param hql a consulta
	 * @param params os parâmetros da consulta
	 * @return 
	 * @return o registro
	 */
	@SuppressWarnings("unchecked")
	protected <E extends Object> E findOne(String hql, Map<String, Object> params, Class<E> returnClass) {
		
		Query query = getEntityManager().createQuery(hql, returnClass);
        setParametersOnQuery(query, params);
        return (E) query.getSingleResult();
	}
	
	// Métodos get/set
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}