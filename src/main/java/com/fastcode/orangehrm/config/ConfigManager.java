package com.fastcode.orangehrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static ConfigManager instance;
	private Properties properties;

	private ConfigManager() {
		properties = new Properties();

		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input != null) {
				properties.load(input);
			} else {
				throw new RuntimeException("Config file not found");
			}
		} catch (IOException e) {
			throw new RuntimeException("Error reading config file", e);
		}
	}

	public static synchronized ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getBrowser() {
		return properties.getProperty("browser", "chrome");
	}

	public String getLoginUrl() {
		return properties.getProperty("baseUrl");
	}

	public String getUsername() {
		return properties.getProperty("username");
	}

	public String getPassword() {
		return properties.getProperty("password");
	}
}
