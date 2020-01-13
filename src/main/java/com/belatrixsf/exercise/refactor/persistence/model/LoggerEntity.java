package com.belatrixsf.exercise.refactor.persistence.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logger")
public class LoggerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "message_text")
	private String message;

	private Integer level;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public LoggerEntity(Long id, String message, Integer level, LocalDateTime createdAt) {
		this.id = id;
		this.message = message;
		this.level = level;
		this.createdAt = createdAt;
	}

	public LoggerEntity(String message, Integer level) {
		this.message = message;
		this.level = level;
		this.createdAt = LocalDateTime.now();
	}

	public LoggerEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LoggerEntity that = (LoggerEntity) o;
		return id.equals(that.id) && message.equals(that.message) && level.equals(that.level)
				&& createdAt.equals(that.createdAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, message, level, createdAt);
	}

	@Override
	public String toString() {
		return "LoggerEntity{" + "id=" + id + ", message='" + message + '\'' + ", level=" + level + ", createdAt="
				+ createdAt + '}';
	}

}
