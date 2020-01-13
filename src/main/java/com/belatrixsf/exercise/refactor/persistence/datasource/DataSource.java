package com.belatrixsf.exercise.refactor.persistence.datasource;

import java.util.Properties;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.belatrixsf.exercise.refactor.constants.Constans;
import com.belatrixsf.exercise.refactor.util.LoggerProperties;

public class DataSource {
	
	public static javax.sql.DataSource getDataSource() {
		Properties properties = LoggerProperties.getProperties();		
		
		return new DriverManagerDataSource(
				properties.getProperty(Constans.DATABASE_URL),
				properties.getProperty(Constans.DATABASE_USER),
				properties.getProperty(Constans.DATABASE_PASSWORD));
	}

}
