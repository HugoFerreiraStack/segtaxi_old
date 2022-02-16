package br.com.seg.econotaxi.repository;

import br.com.seg.econotaxi.model.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SistemaRepository extends JpaRepository<Sistema, Long> {

    Sistema findByChave(String chave);

}
