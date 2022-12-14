
DROP TABLE IF EXISTS DAUO_EXAM;

CREATE TABLE DAUO_EXAM
(
    REG_DTM      VARCHAR(14) PRIMARY KEY,
    JOIN_CNT     INT             NOT NULL,
    RESIGN_CNT   INT             NOT NULL,
    PAY_AMT      DECIMAL(18,0)  NOT NULL,
    USED_AMT     DECIMAL(18,0)  NOT NULL,
    SALES_AMT    DECIMAL(18,0)  NOT NULL
);

DROP TABLE IF EXISTS DAOU_USER;

CREATE TABLE  DAOU_USER (
  USER_ID int NOT NULL AUTO_INCREMENT,
  PASSWORD varchar(100) NOT NULL,
  USERNAME varchar(100) NOT NULL,
  ROLE varchar(45) DEFAULT NULL,
  PRIMARY KEY(USER_ID)
);