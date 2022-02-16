package br.com.seg.econotaxi.oauth.details;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import br.com.seg.econotaxi.model.Sistema;

public class AuthClientDetails implements ClientDetails {

	private static final long serialVersionUID = 5479345035080974404L;
	
	private Sistema sistema;
    private Set<String> recursosId;
    private Set<String> escopos;
    private Set<String> acessos;

    public AuthClientDetails(Sistema sistema) {
        this.sistema = sistema;

        // Recursos ID's
        String[] recursos = this.sistema.getIdRecurso().split(",");
        this.recursosId = Arrays.stream(recursos).map(
                recurso -> recurso.trim()).collect(Collectors.toSet());

        // Escopos
        String[] escopos = this.sistema.getEscopos().split(",");
        this.escopos = Arrays.stream(escopos).map(
                escopo -> escopo.trim()).collect(Collectors.toSet());

        // Tipos Acessos
        String[] acessos = this.sistema.getTiposAcessos().split(",");
        this.acessos = Arrays.stream(acessos).map(
                acesso -> acesso.trim()).collect(Collectors.toSet());
    }

    @Override
    public String getClientId() {
        return this.sistema.getChave();
    }

    @Override
    public Set<String> getResourceIds() {
        return this.recursosId;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.sistema.getCredencial();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        return this.escopos;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.acessos;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 300;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 86400;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
