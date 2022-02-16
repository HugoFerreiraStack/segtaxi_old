/**
 * 
 */
package br.com.seg.econotaxi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.seg.econotaxi.model.Funcionalidade;
import br.com.seg.econotaxi.repository.FuncionalidadeRepository;
import br.com.seg.econotaxi.service.FuncionalidadeService;

/**
 * @author bruno
 *
 */
@Service("funcionalidadeService")
public class FuncionalidadeServiceImpl implements FuncionalidadeService {

	@Autowired
	private FuncionalidadeRepository funcionalidadeRepository;
	
	@Override
	public void salvarFuncionalidade(Funcionalidade funcionalidade) {
		funcionalidadeRepository.save(funcionalidade);
	}

	@Override
	public Funcionalidade recuperarFuncionalidadePorChave(Long chave) {
		return funcionalidadeRepository.findById(chave);
	}

	@Override
	public List<Funcionalidade> recuperarTodasFuncionalidades() {
		return funcionalidadeRepository.findAllByOrderByNomeAsc();
	}

	@Override
	public List<Long> recuperarFuncionalidadesUsuario(Long id) {
		return funcionalidadeRepository.findByIdUsuario(id);
	}

}
