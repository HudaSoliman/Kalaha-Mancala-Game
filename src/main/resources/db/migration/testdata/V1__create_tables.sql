CREATE SEQUENCE IF NOT EXISTS seq_game                    MINVALUE 100 NOCACHE;
CREATE SEQUENCE IF NOT EXISTS seq_pit          			  MINVALUE 100 CACHE 10;

CREATE TABLE IF NOT EXISTS game  (
  id             BIGINT                  NOT NULL PRIMARY KEY,
  turn           SMALLINT                NOT NULL,
  winner         SMALLINT                NULL
);

CREATE TABLE IF NOT EXISTS pit(
  id             BIGINT                			  NOT NULL PRIMARY KEY,
  owner          SMALLINT              			  NOT NULL,
  stones_count   INTEGER UNSIGNED                 NOT NULL,
  index			 INTEGER UNSIGNED		   	      NOT NULL,
  game_id		 BIGINT						      NOT NULL,
  is_home        SMALLINT              			  NOT NULL,
  CONSTRAINT     `fk_pt_gm_id_gm`             FOREIGN KEY (game_id)             REFERENCES game(id) ON DELETE CASCADE

);