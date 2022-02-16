package br.com.seg.econotaxi.oauth.details;

import br.com.seg.econotaxi.enums.TipoUsuarioEnum;
import br.com.seg.econotaxi.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {

    private Usuario usuario;
    private List<AuthGrantedAuthority> authorities;

    public AuthUserDetails(Usuario usuario) {
        this.usuario = usuario;
        TipoUsuarioEnum tipoUsuario = TipoUsuarioEnum.valueOfCodigo(this.usuario.getTipo());

        this.authorities = new ArrayList<>();
        this.authorities.add(new AuthGrantedAuthority(tipoUsuario.getRole()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.usuario.getHashSenha();
    }

    @Override
    public String getUsername() {
        return this.usuario.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
