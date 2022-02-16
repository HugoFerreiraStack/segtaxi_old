package br.com.seg.econotaxi.oauth.service;

import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.oauth.details.AuthUserDetails;
import br.com.seg.econotaxi.util.HashUtil;
import br.com.seg.econotaxi.util.SpringContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private AuthUserDetailsService authUserDetailsService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            UserDetails userDetails = authUserDetailsService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));

            if (userDetails != null) {
            	
                AuthUserDetails authUserDetails = (AuthUserDetails) userDetails;
                
                checkUserLogin(String.valueOf(authentication.getPrincipal()), authUserDetails.getUsuario().getTipo());

                String hashSenha = HashUtil.stringHexa(HashUtil.gerarHash(
                		String.valueOf(authentication.getCredentials()).concat(
                				authUserDetails.getUsuario().getLogin())
                        			.concat(new SimpleDateFormat("ddMMYYYYHHmmss").format(
                        					authUserDetails.getUsuario().getDataCadastro())), "SHA-256"));
                
                String hashSenhaNovo = HashUtil.stringHexa(HashUtil.gerarHash(
                		String.valueOf(authentication.getCredentials()).concat(
                				authUserDetails.getUsuario().getLogin())
                        			.concat(new SimpleDateFormat("ddMMyyyy").format(
                        					authUserDetails.getUsuario().getDataCadastro())), "SHA-256"));

                if (hashSenha.equals(authUserDetails.getUsuario().getHashSenha()) ||
                		hashSenhaNovo.equals(authUserDetails.getUsuario().getHashSenha())) {
                    return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), hashSenha, authUserDetails.getAuthorities());
                } else {

                    throw new BadCredentialsException("Bad credentials");
                }

            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        } catch (UsernameNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new BadCredentialsException("Bad credentials");
        }

    }
    
    private void checkUserLogin(String username, Integer tipoUsuario) {
    	
    	if (TipoUsuarioEnum.MOTORISTA.getCodigo().equals(tipoUsuario)) {
    	
	    	TokenStore tokenStore = SpringContextUtil.getBean(TokenStore.class);
	    	
	    	Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(getClientId(), username);
	    	if (tokens != null && !tokens.isEmpty()) {
	    		
	    		Boolean existeTokenAtivo = Boolean.FALSE;
	    		
	    		Iterator<OAuth2AccessToken> iter = tokens.iterator();
	    		while (iter.hasNext() && !existeTokenAtivo) {
	    			
	    			OAuth2AccessToken token = iter.next();
	    			existeTokenAtivo = !token.isExpired();
	    		}
	    		
	    		if (existeTokenAtivo) {
	    			throw new NegocioException("User just logged: " + username);
	    		}
	    	}
    	}
    }
    
    private String getClientId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String authorizationHeaderValue = request.getHeader("Authorization");
        String base64AuthorizationHeader = Optional.ofNullable(authorizationHeaderValue)
                .map(headerValue->headerValue.substring("Basic ".length())).orElse("");

        if(base64AuthorizationHeader != null && !base64AuthorizationHeader.isEmpty()){
            String decodedAuthorizationHeader = new String(Base64.getDecoder().decode(base64AuthorizationHeader), Charset.forName("UTF-8"));
            return decodedAuthorizationHeader.split(":")[0];
        }

        return "";
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
