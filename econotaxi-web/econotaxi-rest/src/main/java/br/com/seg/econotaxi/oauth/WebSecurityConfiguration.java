package br.com.seg.econotaxi.oauth;

import br.com.seg.econotaxi.oauth.service.AuthUserDetailsService;
import br.com.seg.econotaxi.oauth.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
        auth.userDetailsService(authUserDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {

        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(customAuthenticationProvider);

        return new ProviderManager(providers);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "*/**")
                .antMatchers(HttpMethod.GET, "/public/**")
                .antMatchers(HttpMethod.GET, "/javax.faces.resource/**")
                .antMatchers(HttpMethod.OPTIONS, "/pages/**")
                .antMatchers(HttpMethod.GET, "/pages/**")
                .antMatchers(HttpMethod.TRACE, "/pages/**")
                .antMatchers(HttpMethod.PUT, "/pages/**")
                .antMatchers(HttpMethod.HEAD, "/pages/**")
                .antMatchers(HttpMethod.DELETE, "/pages/**")
                .antMatchers(HttpMethod.PATCH, "/pages/**")
                .antMatchers(HttpMethod.POST, "/pages/**")

                // Nova conta de cliente
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/nova-conta/**")
                .antMatchers(HttpMethod.POST, "/rest/usuario/nova-conta/**")
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/versaoApp")
                .antMatchers(HttpMethod.POST, "/rest/usuario/versaoApp")
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/deslogarMotorista")
                .antMatchers(HttpMethod.POST, "/rest/usuario/deslogarMotorista")
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/passageiros")
                .antMatchers(HttpMethod.POST, "/rest/usuario/passageiros")
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/versaoApp/")
                .antMatchers(HttpMethod.POST, "/rest/usuario/versaoApp/")
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/deslogarMotorista/")
                .antMatchers(HttpMethod.POST, "/rest/usuario/deslogarMotorista/")
                .antMatchers(HttpMethod.OPTIONS, "/rest/usuario/passageiros/")
                .antMatchers(HttpMethod.POST, "/rest/usuario/passageiros/")
                .antMatchers(HttpMethod.GET, "/gs-app-websocket/**");
    }

}
