CREATE TABLE IF NOT EXISTS logger (
	id INT AUTO_INCREMENT PRIMARY KEY,
	message_text VARCHAR(500) NOT NULL,
	level VARCHAR(50) NOT NULL,
	created_at date NOT  NULL
);