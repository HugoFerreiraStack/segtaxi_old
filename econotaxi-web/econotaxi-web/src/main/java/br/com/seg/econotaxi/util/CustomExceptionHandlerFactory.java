package br.com.seg.econotaxi.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Fábrica de manipulação de exceções do sistema.
 *
 * Criado em 25 de mai de 2017
 * @author Welson Carvalho
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    // Atributos
    private ExceptionHandlerFactory parent;

    /**
     * Instancia um novo custom exception handler factory.
     *
     * @param parent parent
     */
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    /**
     * Obtém exception handler.
     *
     * @return exception handler
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(parent.getExceptionHandler());
    }

}