/**
 * 
 */
package br.com.seg.econotaxi.listener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.springframework.web.jsf.FacesContextUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Bruno rocha
 *
 */
public class EventListener implements Serializable {

	// Constantes
	private static final long serialVersionUID = -7659102920476770583L;
	
	/**
	 * Método responsável por obter alguma managed bean.
	 * 
	 * @param beanName o nome do componente
	 * @param t a classe
	 * @return o objeto managed bean instanciado
	 */
	protected <T> T recuperarBean(String beanName, Class<T> t) {
	    FacesContext context = FacesContext.getCurrentInstance();
	    T bean = (T) FacesContextUtils.getWebApplicationContext(context).getBean(beanName, t);
	    return bean;
	}
	
	/**
	 * TODO javadoc
	 * 
	 * @param entidade
	 * @return
	 */
	protected byte[] converterObjectParaByte(Object entidade) {
		
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream ous;
			ous = new ObjectOutputStream(bao);
			ous.writeObject(entidade);
			return bao.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * TODO javadoc
	 * 
	 * @param entidade
	 * @return
	 */
	protected String converterObjectParaJson(Object entidade) {
		
		String json = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(entidade);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}