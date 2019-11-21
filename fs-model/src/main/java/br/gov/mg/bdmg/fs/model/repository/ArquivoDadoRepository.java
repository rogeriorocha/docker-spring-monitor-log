package br.gov.mg.bdmg.fs.model.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.mg.bdmg.fs.model.ArquivoDado;


public interface ArquivoDadoRepository extends JpaRepository<ArquivoDado, Long> {
	
	List<ArquivoDado> findTop10ByAtivoAndDataExpurgoLessThanEqualOrderByDataInclAsc(String ativo, Date dataExpurgo);
}