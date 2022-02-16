package br.com.seg.econotaxi.config.autenticacao;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Classe responsável por realizar a autenticação utilizando o tipo de autenticação definido nas configurações
 * do Spring Security.
 *
 * Criado em 25 de jun de 2017
 * @author Bruno rocha
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider, Serializable {

    // Constantes
	private static final long serialVersionUID = 902556431470430435L;

	/**
	 * Método  responsável por realizar a autenticação utilizando o tipo de autenticação definido nas configurações
	 * do Spring Security.
	 */
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

	/**
	 * Método responsável por verificar o suporte de determinada classe.
	 */
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

}