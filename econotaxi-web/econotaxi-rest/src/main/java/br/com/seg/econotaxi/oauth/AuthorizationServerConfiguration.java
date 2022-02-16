package br.com.seg.econotaxi.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import br.com.seg.econotaxi.oauth.service.AuthClientDetailsService;
import br.com.seg.econotaxi.oauth.service.AuthUserDetailsService;
import br.com.seg.econotaxi.oauth.token.CustomTokenEnhancer;
import br.com.seg.econotaxi.util.SpringContextUtil;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

//    private TokenStore tokenStore = new InMemoryTokenStore();
	
//	@Autowired
//	private DataSource dataSource;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Autowired
    private AuthClientDetailsService authClientDetailsService;
    
    @Bean
    public TokenStore tokenStore() {
//    	return new JdbcTokenStore(this.dataSource);
    	return new InMemoryTokenStore();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	
    	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    	tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));

        endpoints.tokenStore(SpringContextUtil.getBean(TokenStore.class))
        		.tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(this.authUserDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.OPTIONS);


    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients
//                .inMemory()
//                .withClient("mobile")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .scopes("bar", "read", "write")
//                .refreshTokenValiditySeconds(20000)
//                .accessTokenValiditySeconds(20000)
//                .secret("123")
//                .resourceIds("econotaxi_restservice");

        clients
                .withClientDetails(this.authClientDetailsService);
//                .jdbc((DataSource) SpringContextUtil.getBean("dataSource"))
//                .clients(this.authClientDetailsService);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenStore(SpringContextUtil.getBean(TokenStore.class));
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        
        return defaultTokenServices;
    }

    public TokenEnhancer tokenEnhancer() {
    	return new CustomTokenEnhancer();
    }
}
