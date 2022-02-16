package br.com.seg.econotaxi.resource;

import br.com.seg.econotaxi.model.LocalFavorito;
import br.com.seg.econotaxi.service.LocalFavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/local-favorito")
public class LocalFavoritoResource {

    @Autowired
    private LocalFavoritoService localFavoritoService;

    @Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA"})
    @RequestMapping(value = "/{idUsuario}", method = RequestMethod.GET)
    public List<LocalFavorito> recuperarPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        return localFavoritoService.recuperarPorUsuario(idUsuario);
    }

}
