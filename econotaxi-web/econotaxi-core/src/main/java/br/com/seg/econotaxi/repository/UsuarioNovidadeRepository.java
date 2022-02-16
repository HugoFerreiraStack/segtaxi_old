package br.com.seg.econotaxi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.seg.econotaxi.model.UsuarioNovidade;

public interface UsuarioNovidadeRepository extends JpaRepository<UsuarioNovidade, Long>, UsuarioNovidadeRepositoryCustom {

}