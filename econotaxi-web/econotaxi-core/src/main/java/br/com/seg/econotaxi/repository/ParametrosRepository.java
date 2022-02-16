package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.Parametro;

public interface ParametrosRepository extends JpaRepository<Parametro, Long>, ParametrosRepositoryCustom {

}