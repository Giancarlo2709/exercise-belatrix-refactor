package com.belatrixsf.exercise.refactor.util;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import com.belatrixsf.exercise.refactor.persistence.datasource.DataSource;

public class LoggerProperties {

	private static volatile LoggerProperties instance = null;
	private static volatile Properties properties = null;

	private LoggerProperties() {
	}

	public static LoggerProperties getInstance() {
		if (Objects.isNull(instance)) {
			synchronized (LoggerProperties.class) {
				if (Objects.isNull(instance)) {
					instance = new LoggerProperties();
				}
			}
		}

		return instance;
	}

	public static Properties getProperties() {
		if (Objects.isNull(properties)) {
			synchronized (LoggerProperties.class) {
				if (Objects.isNull(properties)) {
					try {
						InputStream inputStream = DataSource.class.getClassLoader()
							.getResourceAsStream("logger.properties");
						properties = new Properties();
						properties.load(inputStream);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return properties;
	}

}
