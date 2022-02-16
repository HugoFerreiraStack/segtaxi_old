package br.com.seg.econotaxi.resource;

import br.com.seg.econotaxi.model.PagamentoEndereco;
import br.com.seg.econotaxi.model.Usuario;
import br.com.seg.econotaxi.service.PagamentoEnderecoService;
import br.com.seg.econotaxi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("rest/pagamento-endereco")
public class PagamentoEnderecoResource {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PagamentoEnderecoService pagamentoEnderecoService;

    @RequestMapping(method = RequestMethod.GET)
    public List<PagamentoEndereco> recuperarEnderecos(Principal principal) {
        Usuario usuario = usuarioService.recuperarPorLogin(principal.getName());
        return pagamentoEnderecoService.recuperarEnderecosDoUsuario(usuario.getId());
    }

}
