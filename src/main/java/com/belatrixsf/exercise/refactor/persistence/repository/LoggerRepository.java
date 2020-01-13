package com.belatrixsf.exercise.refactor.persistence.repository;

import com.belatrixsf.exercise.refactor.persistence.model.LoggerEntity;

public interface LoggerRepository {
	
	public void saveLogger(LoggerEntity loggerEntity);
	public int count();

}
