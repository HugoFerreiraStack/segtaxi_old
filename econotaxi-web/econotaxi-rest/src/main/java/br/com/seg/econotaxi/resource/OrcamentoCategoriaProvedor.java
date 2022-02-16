package br.com.seg.econotaxi.resource;

import org.springframework.web.bind.annotation.RestController;

import br.com.seg.econotaxi.ws.ui.model.request.OrcamentoCategoriaRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/*
 * @author Obede Doreto && Iago Ferreira
 * ref: SEGTAXI IMPL 001-COOPERATIVE
 * 
 * This class is a controller that implements
 * "orçamento de categorias para o provedor"
 * 
 * This should return available categories for closer city.
 * 
 * 
 * */


@RestController
@RequestMapping("rest/orcamento-categoria-provedor")
public class OrcamentoCategoriaProvedor {
	
	@SuppressWarnings("unused")
	@Secured({"ROLE_CLIENTE", "ROLE_MOTORISTA", "ROLE_LOJISTA", "ROLE_ADMINISTRATIVO"})
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<OrcamentoCategoriaRequest> getTest(@RequestBody OrcamentoCategoriaRequest orcamentoRequest){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
//		OAuth2Authentication = authentication.getPrincipal()
//		BeanUtils.copyProperties(orcamentoRequest, authentication)
		
//		 Step 1 - Get current logger user by auth.
//		 Step 2 - Find user data.
//		 Step 3 - Get request payload.
//		 Step 4 - Apply DTO validations.
//		 Step 5 - Transform in DTO
//		 Step 6 - Find cities by user and coordinates.
//		 Step 7 - Process and return data.
		
		return ResponseEntity.ok(orcamentoRequest);
	}
	
}
