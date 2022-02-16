package br.com.seg.econotaxi.oauth.details;

import org.springframework.security.core.GrantedAuthority;

public class AuthGrantedAuthority implements GrantedAuthority {

    private String authority;

    public AuthGrantedAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
