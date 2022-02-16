package br.com.seg.econotaxi.oauth.token;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.exception.NegocioException;
import br.com.seg.econotaxi.service.UsuarioService;
import br.com.seg.econotaxi.util.SpringContextUtil;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		if (!authentication.getOAuth2Request().isRefresh()) {
			
			TipoUsuarioEnum tipoUsuario = recuperarTipoUsuario(String.valueOf(authentication.getPrincipal()));
			
			if (authentication.getOAuth2Request().getClientId().equals("mobile_pas_front")
					&& !TipoUsuarioEnum.CLIENTE.equals(tipoUsuario)) {
				
				throw new NegocioException("Incorrect role: " + authentication.getPrincipal());
			}
		}
		
		return accessToken;
	}

	private TipoUsuarioEnum recuperarTipoUsuario(String login) {
		UsuarioService service = SpringContextUtil.getBean(UsuarioService.class);
		Integer tipoUsuario = service.recuperarTipoPorLogin(login);
		return TipoUsuarioEnum.valueOfCodigo(tipoUsuario);
	}
}
