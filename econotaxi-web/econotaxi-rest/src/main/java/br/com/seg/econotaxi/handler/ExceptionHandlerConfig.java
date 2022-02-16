package br.com.seg.econotaxi.handler;

import br.com.seg.econotaxi.exception.NegocioException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerConfig extends ResponseEntityExceptionHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerConfig.class);

    @ExceptionHandler({InvalidGrantException.class})
    public ErroVo oathException(HttpServletRequest request, Exception e) {
    	LOG.error(e.getMessage(), e);
        return new ErroVo(1, e.getMessage());
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<Object> negocioException(WebRequest request, Exception e) {
    	LOG.error(e.getMessage(), e);
        return handleExceptionInternal(e, new ErroVo(2, e.getMessage()),
                new HttpHeaders(), HttpStatus.PRECONDITION_FAILED, request);
    }

    private class ErroVo {

        private Integer codigo;
        private String mensagem;

        ErroVo(Integer codigo, String mensagem) {
            this.codigo = codigo;
            this.mensagem = mensagem;
        }

        public Integer getCodigo() {
            return codigo;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

}
