package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.UsuarioNotificacao;

public interface UsuarioNotificacaoRepository extends JpaRepository<UsuarioNotificacao, Long>, UsuarioNotificacaoRepositoryCustom {

}