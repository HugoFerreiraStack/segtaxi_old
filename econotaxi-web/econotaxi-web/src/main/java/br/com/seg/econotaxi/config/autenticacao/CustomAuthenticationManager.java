package br.com.seg.econotaxi.config.autenticacao;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Classe responsável por realizar a autenticação utilizando o tipo de autenticação definido nas configurações
 * do Spring Security.
 *
 * Criado em 25 de mai de 2017
 * @author Bruno Rocha
 */
@Component
public class CustomAuthenticationManager implements AuthenticationManager, Serializable {

    // Constantes
	private static final long serialVersionUID = -1449297855699323784L;

	/**
	 * Método  responsável por realizar a autenticação utilizando o tipo de autenticação definido nas configurações
	 * do Spring Security.
	 */
	@Override
    public Authentication authenticate(Authentication usuario) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken(usuario.getPrincipal(),
                usuario.getCredentials(), usuario.getAuthorities());
    }

}