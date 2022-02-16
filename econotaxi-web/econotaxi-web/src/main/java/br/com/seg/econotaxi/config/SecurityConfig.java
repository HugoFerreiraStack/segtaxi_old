package br.com.seg.econotaxi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import br.com.seg.econotaxi.config.autenticacao.CustomAuthenticationProvider;

/**
 * Classe responsável pela configuração do Spring Security.
 *
 * Criado em 25 de mai de 2017
 * @author Bruno Rocha
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// Constantes
    private static final String LOGIN_URL = "/public/login.jsf";

    // Atributos
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    /**
     * Método de configuração do Spring Security.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/pages/**").authenticated()
                .and()
                .formLogin()
                    .loginProcessingUrl("/security_check")
                    .loginPage(LOGIN_URL)
                    .failureUrl(LOGIN_URL)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(Boolean.TRUE)
                    .logoutSuccessUrl(LOGIN_URL)
                .and()
                .sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(Boolean.TRUE);
    }

    /**
     * Método de configuração do provedor de autenticação do Spring Security.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
    
}