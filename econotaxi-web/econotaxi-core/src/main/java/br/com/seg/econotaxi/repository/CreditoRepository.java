package br.com.seg.econotaxi.repository;

import br.com.seg.econotaxi.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditoRepository extends JpaRepository<Credito, Long> {

    Credito findByIdUsuario(Long idUsuario);

}
