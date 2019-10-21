package br.gov.mg.bdmg.fsservice.storage;


import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	long store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(long id);

	Resource loadAsResource(long id);

	void deleteAll();

}