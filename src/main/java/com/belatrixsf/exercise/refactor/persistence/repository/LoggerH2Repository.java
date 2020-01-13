package com.belatrixsf.exercise.refactor.persistence.repository;

import java.time.LocalDateTime;

import org.springframework.jdbc.core.JdbcTemplate;

import com.belatrixsf.exercise.refactor.persistence.model.LoggerEntity;

public class LoggerH2Repository implements LoggerRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public LoggerH2Repository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void saveLogger(LoggerEntity loggerEntity) {
		jdbcTemplate.update("insert into logger(message_text, level, created_at) values (?,?,?)", 
				loggerEntity.getMessage(), loggerEntity.getLevel(), LocalDateTime.now());
	}
	
	public int count(){
		return jdbcTemplate.queryForObject("select count(*) from logger", Integer.class);
	}

}
