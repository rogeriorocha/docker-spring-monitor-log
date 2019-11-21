package br.gov.mg.bdmg.fs.util;


import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;




public class ResourceUtil extends FileUtil {

	private static final String FOLDER_NAME_PROPERTIES_LABEL = "/META-INF/";

	private static final Logger LOGGER = Logger.getLogger(ResourceUtil.class.getName());

	public static Properties lerArquivoProperties(String nomeArquivo) {
		Properties properties = new Properties();
		try {
			properties.load(ResourceUtil.class.getResourceAsStream(FOLDER_NAME_PROPERTIES_LABEL.concat(nomeArquivo)));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return properties;
	}

}
