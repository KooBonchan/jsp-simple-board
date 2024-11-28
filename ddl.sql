DROP VIEW board_view;
DROP TABLE board;
DROP SEQUENCE seq_board_idx;
CREATE TABLE board(
  idx integer PRIMARY KEY,
  -- content
  nickname varchar2(10) NOT NULL,
  password char(64) NOT NULL, -- sha256
  title varchar2(200) NOT NULL,
  content varchar2(2000) NOT NULL,
  org_img_path varchar2(100),
  real_img_path char(50),
  -- metadata
  postdate date DEFAULT sysdate NOT NULL,
  pageview integer DEFAULT 0 NOT NULL,
  download integer DEFAULT 0 NOT NULL
);
CREATE VIEW board_view
AS
SELECT idx, nickname, title, postdate, pageview, download,
row_number() OVER (ORDER BY idx DESC) AS row_num
FROM board;
CREATE SEQUENCE seq_board_idx;

INSERT INTO board (idx, nickname, password, title, CONTENT) VALUES (seq_board_idx.nextval, 'berthoud', 'c123!@', 'Slapocalypse', 'with Giacomo Turra');
INSERT INTO board (idx, nickname, password, title, CONTENT) VALUES (seq_board_idx.nextval, 'wooten', 'v123!@', 'Stomping Ground', 'in Bela Fleck and the Flecktones');
INSERT INTO board (idx, nickname, password, title, CONTENT) VALUES (seq_board_idx.nextval, 'narucho', 'y123!@', 'Galactic Funk', 'bass intro + solo');
INSERT INTO board (idx, nickname, password, title, CONTENT) VALUES (seq_board_idx.nextval, 'smitsuru', 'sutoh123!@', 'Sunnyside Cruise', 'of 35th anniversary version, T-square super big band.');
COMMIT;



SELECT * FROM board;
