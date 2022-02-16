package br.com.seg.econotaxi.resource;

import java.security.Principal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.util.SpringContextUtil;

@RestController
@RequestMapping("rest/token")
public class TokenResource {

	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
	@RequestMapping(value = "/oauth/token", method = RequestMethod.DELETE)
	public void logout(HttpServletRequest request, Principal principal) {
		try {
			
			String authorization = request.getHeader("Authorization");
			
			if (authorization != null && authorization.contains("Bearer")) {
				TokenStore tokenStore = SpringContextUtil.getBean(TokenStore.class);
				
				OAuth2Authentication authentication = (OAuth2Authentication) principal;
				
				DefaultTokenServices defaultTokenServices = SpringContextUtil.getBean(DefaultTokenServices.class);
				OAuth2AccessToken token = defaultTokenServices.getAccessToken(authentication);
				
				defaultTokenServices.revokeToken(token.getValue());
				
				Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientIdAndUserName(
						authentication.getOAuth2Request().getClientId(), principal.getName());
				
				for (OAuth2AccessToken t : tokens) {
					System.out.println(t.isExpired());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
