package br.gov.mg.bdmg.fsservice.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@EnableConfigurationProperties
public class AppProperties {
	
	private Storage storage = new Storage();

	public Storage getStorage() {
		return storage;
	}

	public static class Storage {

		private String location;

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
	}

}