package config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@Getter
@Slf4j
public enum ApplicationConfiguration {

	INSTANCE;

	private String dbUrl;
	private String dbUser;
	private String dbPassword;
	private int initPoolSize = 12;
	private int maxPoolSize = 30;
	private int poolIncreaseStep = 2;

	ApplicationConfiguration() {
		initProperties();
	}

	private void initProperties() {
		try (InputStream inputStream = new FileInputStream("./src/main/resources/application.properties")) {
			Properties properties = new Properties();
			properties.load(inputStream);
			dbUrl = properties.getProperty("dbUrl");
			dbUser = properties.getProperty("dbUser");
			dbPassword = properties.getProperty("dbPassword");
			if (Objects.nonNull(properties.getProperty("initPoolSize"))) {
				initPoolSize = Integer.parseInt(properties.getProperty("initPoolSize"));
			}
			if (Objects.nonNull(properties.getProperty("maxPoolSize"))) {
				initPoolSize = Integer.parseInt(properties.getProperty("maxPoolSize"));
			}
			if (Objects.nonNull(properties.getProperty("poolIncreaseStep"))) {
				initPoolSize = Integer.parseInt(properties.getProperty("poolIncreaseStep"));
			}
		} catch (IOException e) {
			log.error("Properties has not been loaded", e);
			throw new Error("Properties has not been loaded", e);
		}
	}

	@Override
	public String toString() {
		return "ApplicationConfiguration{" +
				"dbUrl='" + dbUrl + '\'' +
				", dbUser='" + dbUser + '\'' +
				", dbPassword='" + dbPassword + '\'' +
				", initPoolSize=" + initPoolSize +
				'}';
	}
}
