package com.belatrixsf.exercise.refactor.business.logging;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.belatrixsf.exercise.refactor.constants.Constans;
import com.belatrixsf.exercise.refactor.exception.LoggerException;
import com.belatrixsf.exercise.refactor.persistence.model.LoggerEntity;
import com.belatrixsf.exercise.refactor.persistence.repository.LoggerRepository;

public class JobLoggerRefactor {
	
	private Properties properties;
	private LoggerRepository loggerRepository;
	
	private static final Logger LOGGER = Logger.getLogger(JobLoggerRefactor.class.getName());

	
	public JobLoggerRefactor(LoggerRepository loggerRepository, Properties properties) {
		this.loggerRepository = loggerRepository;
		this.properties = properties;
	}
    
    private void validateMessage(String messageText, Level level) throws Exception {
    	if(Objects.isNull(messageText) || messageText.trim().isEmpty())
    		throw new LoggerException("Error, message must be specified");

        if(Objects.isNull(level))
            throw new LoggerException("Error, level must be specified");
    }
    
    public void logMessage(String messageText, Level level) throws Exception {
    	validateMessage(messageText, level);    	
    	
    	Boolean logToFile = Boolean.parseBoolean(properties.getProperty(Constans.LOGGER_TO_FILE));
    	Boolean logToConsole = Boolean.parseBoolean(properties.getProperty(Constans.LOGGER_TO_CONSOLE));
    	Boolean logToDatabase = Boolean.parseBoolean(properties.getProperty(Constans.LOGGER_TO_DATABASE));
    	
    	validateConfiguration(logToFile, logToConsole, logToDatabase);
    	
    	messageText = getFormatMessage(messageText, level);
    	
    	if(logToDatabase) {
    		logDataBase(messageText, level);
    	}
    	
    	if(logToFile) {
    		logFile(messageText, level);
    	}
    	
    	if(logToConsole) {
    		ConsoleHandler consoleHanlder = new ConsoleHandler();
    		LOGGER.log(level, messageText);
    		LOGGER.addHandler(consoleHanlder);
    	}
    }
    
    private void validateConfiguration(Boolean logToFile,Boolean logToConsole,Boolean logToDatabase)throws LoggerException {
        if(!(logToDatabase && logToConsole && logToFile)){
            throw new LoggerException("Invalid Configuration");
        }
    }
    
    private void logFile(String messageText, Level level) throws Exception {
    	
    	String nameFile = properties.getProperty(Constans.LOGGER_FILE_PATH);
    	String parentPath = JobLoggerRefactor.class.getClassLoader().getResource("").getPath() + nameFile;
    	
    	File logFile = new File(parentPath);	
    	
    	if(!logFile.exists()){
    		logFile.createNewFile();
        }
    	
    	FileHandler fileHandler = new FileHandler("loggerTxt.log");
    	fileHandler.setFormatter(new SimpleFormatter());
    	
    	LOGGER.log(level, messageText);
    	LOGGER.addHandler(fileHandler);
    	       
    }
    
    private void logDataBase(String messageText, Level level) throws Exception {    	
    	loggerRepository.saveLogger(new LoggerEntity(messageText, level.intValue()));
    }
    
    private String getFormatMessage(String messageText, Level level) {
    	return level.getName() + " " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + " - " + messageText;
    }

}
