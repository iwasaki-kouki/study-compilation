DROP TABLE IF EXISTS admin CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS livers CASCADE;
DROP TABLE IF EXISTS favorites CASCADE;


CREATE TABLE IF NOT EXISTS admin(
	user_id SERIAL NOT NULL,
	authority VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (user_id)
);



CREATE TABLE IF NOT EXISTS users(
	user_id SERIAL NOT NULL,
	authority VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS livers(
	id SERIAL NOT NULL,
	name VARCHAR(255) NOT NULL,
	ch_name VARCHAR(255) NOT NULL,
	twitter_url VARCHAR(255) NOT NULL,
	youtube_url VARCHAR(255) NOT NULL,
	thumbnail  VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS favorites(
	id SERIAL NOT NULL,
	  user_id INT NOT NULL,
	  livers_id INT NOT NULL,
	  PRIMARY KEY (id)
);

ALTER TABLE favorite ADD CONSTRAINT FK_favorite_users FOREIGN KEY (user_id) REFERENCES users;
ALTER TABLE favorite ADD CONSTRAINT FK_favorite_topic FOREIGN KEY (livers_id) REFERENCES livers;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO wawt;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO wawt;