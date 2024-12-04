DROP TABLE if exists MEMBER CASCADE;
DROP TABLE if exists DUST CASCADE;
DROP TABLE if exists BOARD CASCADE;
DROP TABLE if exists REGION CASCADE;
DROP TABLE if exists BOOKMARK CASCADE;

CREATE TABLE `MEMBER` (
                          `memberId`	BIGINT	    PRIMARY KEY AUTO_INCREMENT,
                          `loginId`	    VARCHAR(50)	NOT NULL,
                          `password`	VARCHAR(12)	NOT NULL,
                          `name`	    VARCHAR(10)	NOT NULL,
                          `is_deleted`  BOOLEAN     NOT NULL DEFAULT FALSE
  );

  CREATE TABLE `DUST` (
                        `stationId`	    BIGINT	        PRIMARY KEY,
                        `sidoId`	    BIGINT	        NOT NULL,
                        `stationName`	VARCHAR(100)	NOT NULL,
                        `dataTime`	    DATETIME	    NULL,
                        `pm10Value`	    INT     	    NULL,
                        `pm25Value`	    INT	            NULL,
                        `no2Value`	    DOUBLE	        NULL
);

CREATE TABLE `BOARD` (
                         `boardId`	    BIGINT	        PRIMARY KEY AUTO_INCREMENT,
                         `sidoId`	    BIGINT	        NOT NULL,
                         `memberId`	    BIGINT	        NOT NULL,
                         `title`	        VARCHAR(200)	NOT NULL,
                         `content`	    VARCHAR(2000)	NULL,
                         `created_date`	TIMESTAMP	    NOT NULL default CURRENT_TIMESTAMP
);

CREATE TABLE `REGION` (
                          `sidoId`	BIGINT	    PRIMARY KEY,
                          `sidoName`	VARCHAR(10)	NOT NULL
);

CREATE TABLE `BOOKMARK` (
                            `bookmarkId`	BIGINT	PRIMARY KEY AUTO_INCREMENT,
                            `stationId`	    BIGINT	NOT NULL,
                            `memberId`	    BIGINT	NOT NULL
);

ALTER TABLE BOOKMARK ADD CONSTRAINT FK_BOOKMARK_DUST FOREIGN KEY (stationId) references DUST (stationId);
ALTER TABLE BOOKMARK ADD CONSTRAINT FK_BOOKMARK_MEMBER FOREIGN KEY (memberId) references MEMBER (memberId);
ALTER TABLE DUST ADD CONSTRAINT FK_DUST_REGION FOREIGN KEY (sidoId) references REGION (sidoId);
ALTER TABLE BOARD ADD CONSTRAINT FK_BOARD_REGION FOREIGN KEY (sidoId) references REGION (sidoId);
ALTER TABLE BOARD ADD CONSTRAINT FK_BOARD_MEMBER FOREIGN KEY (memberId) references MEMBER (memberId);

-- REGION 테이블 데이터 초기화
INSERT INTO REGION (sidoId, sidoName) values (1, '서울');
INSERT INTO REGION (sidoId, sidoName) values (2, '부산');
INSERT INTO REGION (sidoId, sidoName) values (3, '대구');
INSERT INTO REGION (sidoId, sidoName) values (4, '인천');
INSERT INTO REGION (sidoId, sidoName) values (5, '광주');
INSERT INTO REGION (sidoId, sidoName) values (6, '대전');
INSERT INTO REGION (sidoId, sidoName) values (7, '울산');
INSERT INTO REGION (sidoId, sidoName) values (8, '경기');
INSERT INTO REGION (sidoId, sidoName) values (9, '강원');
INSERT INTO REGION (sidoId, sidoName) values (10, '충북');
INSERT INTO REGION (sidoId, sidoName) values (11, '충남');
INSERT INTO REGION (sidoId, sidoName) values (12, '전북');
INSERT INTO REGION (sidoId, sidoName) values (13, '전남');
INSERT INTO REGION (sidoId, sidoName) values (14, '경북');
INSERT INTO REGION (sidoId, sidoName) values (15, '경남');
INSERT INTO REGION (sidoId, sidoName) values (16, '제주');
INSERT INTO REGION (sidoId, sidoName) values (17, '세종');