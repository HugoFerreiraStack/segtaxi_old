package br.com.seg.econotaxi.oauth.service;

import br.com.seg.econotaxi.model.Sistema;
import br.com.seg.econotaxi.oauth.details.AuthClientDetails;
import br.com.seg.econotaxi.service.SistemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

@Component
public class AuthClientDetailsService implements ClientDetailsService {

    @Autowired
    private SistemaService sistemaService;

    @Override
    public ClientDetails loadClientByClientId(String chave) throws ClientRegistrationException {

        Sistema sistema = sistemaService.recuperarPorChave(chave);

        if (sistema == null) {
            throw new ClientRegistrationException("Cliente não encontrado");
        }

        return new AuthClientDetails(sistema);
    }
}
