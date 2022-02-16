package br.com.seg.econotaxi.util;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.apache.log4j.Logger;

import br.com.seg.econotaxi.exception.NegocioException;

import java.util.Iterator;
import java.util.Map;

/**
 * Classe para manipulação de exceções no sistema.
 *
 * Criado em 25 de mai de 2017
 * @author Welson Carvalho
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	// Constantes
	Logger log = Logger.getLogger(CustomExceptionHandler.class);
	
    // Atributos
    private ExceptionHandler wrapped;
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
    final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

    /**
     * Construtor da classe.
     * 
     * @param exception a exceção a ser manipulada
     */
    public CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
     */
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    /* (non-Javadoc)
     * @see javax.faces.context.ExceptionHandlerWrapper#handle()
     */
    @Override
    public void handle() throws FacesException {
    	
        final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();
        while(iterator.hasNext()) {
        	
            ExceptionQueuedEvent event = iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable exception = context.getException();

            try {
            	log.error(exception.getMessage(), exception);
                requestMap.put("exceptionMessage", exception.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessageError(exception), ""));
                if (exception instanceof javax.faces.application.ViewExpiredException) {
                	navigationHandler.handleNavigation(facesContext, null, "/index.html");
                } else {
                	navigationHandler.handleNavigation(facesContext, null, "/exception.jsf");
                }
                facesContext.renderResponse();
            } finally {
                iterator.remove();
            }
        }
        getWrapped().handle();
    }

    /**
     * Método responsável por recuperar a mensagem de exceção.
     * 
     * @param exception o objeto de exceção
     * @return a mensagem de exceção
     */
    private String getMessageError(Throwable exception) {
        Throwable cause = getNegocioException(exception);
        return cause.getMessage();
    }

    /**
     * Recupera a exceção de negócio
     * 
     * @param exception o objeto de exceção
     * @return a exceção de negócio
     */
    private Throwable getNegocioException(Throwable exception) {
    	
        Throwable cause = exception;
        Integer tentativas = 0;
        Boolean encontrado = Boolean.FALSE;
        while(tentativas < 4 && !encontrado &&
                cause != null) {
            cause = cause.getCause();
            encontrado = cause instanceof NegocioException;
            tentativas += 1;
        }
        return (encontrado) ? cause : exception;
    }

}