package br.com.seg.econotaxi.repository;

import br.com.seg.econotaxi.model.PagamentoEndereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagamentoEnderecoRepository extends JpaRepository<PagamentoEndereco, Long> {

    List<PagamentoEndereco> findByIdUsuario(Long idUsuario);

}
