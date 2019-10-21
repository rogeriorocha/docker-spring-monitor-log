package br.gov.mg.bdmg.fsservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.mg.bdmg.fsservice.model.ArquivoDado;

public interface ArquivoDadoRepository extends JpaRepository<ArquivoDado, Long> {
	
	
	List<ArquivoDado> findTop10ByAtivoAndDataExpurgoLessThanEqualOrderByDataInclAsc(String ativo, Date dataExpurgo);
}