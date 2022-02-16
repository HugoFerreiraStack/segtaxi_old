package br.com.seg.econotaxi.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener necessário para utilização do Servidor de Aplicação JBOSS. Obriga o JSF a não inicializar objetos com valor zero,
 * deixando null os objetos que estão de fato vazios.
 *
 * Criado em 25 de mai de 2017
 * @author Bruno rocha
 */
@WebListener
public class CoerceToZeroListener implements ServletContextListener {

	/**
	 * Método que obriga o JSF a não inicializar objetos com valor zero, deixando null os objetos 
	 * que estão de fato vazios.
	 */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
    }

    /**
     * Método para finalização do listener.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // Not implemented.
    }
    
}