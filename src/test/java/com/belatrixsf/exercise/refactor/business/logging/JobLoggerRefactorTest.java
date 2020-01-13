package com.belatrixsf.exercise.refactor.business.logging;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import com.belatrixsf.exercise.refactor.constants.Constans;
import com.belatrixsf.exercise.refactor.exception.LoggerException;
import com.belatrixsf.exercise.refactor.persistence.repository.LoggerH2Repository;
import com.belatrixsf.exercise.refactor.util.LoggerProperties;

public class JobLoggerRefactorTest {
	
	private JobLoggerRefactor jobLoggerRefactor;
	private Properties properties;
	private LoggerH2Repository loggerH2Repository;
	private DataSource dataSource;
	
	@Before
	public void setup() throws Exception {
		dataSource = com.belatrixsf.exercise.refactor.persistence.datasource.DataSource.getDataSource();
		
		ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("sql-scripts/test-data.sql"));
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		loggerH2Repository = new LoggerH2Repository(jdbcTemplate);
		
		properties = LoggerProperties.getProperties();
		
		properties.setProperty(Constans.LOGGER_TO_CONSOLE, "true");
		properties.setProperty(Constans.LOGGER_TO_FILE, "true");
		properties.setProperty(Constans.LOGGER_TO_DATABASE, "true");
		
		jobLoggerRefactor = new JobLoggerRefactor(loggerH2Repository, properties);
		
	}

	@Test
	public void logMessage() throws Exception {		
		
		jobLoggerRefactor.logMessage("Info Testing message", Level.INFO);
		jobLoggerRefactor.logMessage("Warning Testing message", Level.WARNING);
		jobLoggerRefactor.logMessage("Severe Testing message", Level.SEVERE);
		
		assertEquals(3, loggerH2Repository.count());
	}
	
	@Test(expected = LoggerException.class)
	public void when_message_is_null_then_return_exception() throws Exception {
		jobLoggerRefactor.logMessage(null, Level.INFO);
	}
	
	@Test(expected = LoggerException.class)
	public void when_message_is_empty_then_return_exception() throws Exception {
		jobLoggerRefactor.logMessage("", Level.INFO);
	}
	
	@Test(expected = LoggerException.class)
	public void when_level_is_null_then_return_exception() throws Exception {
		jobLoggerRefactor.logMessage("Info message", null);
	}
	
	@Test(expected = LoggerException.class)
	public void when_log_database_is_false_then_return_invalid_configuration() throws Exception {
		properties.setProperty(Constans.LOGGER_TO_DATABASE, "false");
		jobLoggerRefactor.logMessage("Info Testing message", Level.INFO);
		assertThat(loggerH2Repository.count(), is(0));
	}
	
	@Test
	public void testLogInfo() throws Exception {
		properties.setProperty(Constans.LOGGER_TO_DATABASE, "true");
		jobLoggerRefactor.logMessage("Info Testing message", Level.INFO);
		assertThat(loggerH2Repository.count(), is(1));
	}
	
	@Test
	public void testLogWarning() throws Exception {
		properties.setProperty(Constans.LOGGER_TO_DATABASE, "true");
		jobLoggerRefactor.logMessage("Warning Testing message", Level.WARNING);
		assertThat(loggerH2Repository.count(), is(1));
	}
	
	@Test
	public void testLogSevere() throws Exception {
		properties.setProperty(Constans.LOGGER_TO_DATABASE, "true");
		jobLoggerRefactor.logMessage("Severe Testing message", Level.SEVERE);
		assertThat(loggerH2Repository.count(), is(1));
	}
	
	@After
	public void tearDown() throws Exception {
		final Statement statement = dataSource.getConnection().createStatement();
		statement.execute("drop all objects delete files");
	}

}
