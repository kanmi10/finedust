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