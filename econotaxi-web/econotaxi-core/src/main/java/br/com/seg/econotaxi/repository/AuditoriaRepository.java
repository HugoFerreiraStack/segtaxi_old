package br.com.seg.econotaxi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Auditoria;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>, Serializable, AuditoriaRepositoryCustom {

}