
-----------------------------------------------------------------------------
-- TRANSACTION_MAPPER
-----------------------------------------------------------------------------
DROP TABLE TRANSACTION_MAPPER;


CREATE TABLE TRANSACTION_MAPPER
(
    ID int8 NOT NULL,
      -- REFERENCES TRANSACTION (ID)
    BEFORE_TRANSACTION_ID int8 NOT NULL,
      -- REFERENCES TRANSACTION (ID)
    AFTER_TRANSACTION_ID int8,
    PRIMARY KEY (ID),
    CONSTRAINT unq_TRANSACTION_MAPPER_1 UNIQUE (BEFORE_TRANSACTION_ID, AFTER_TRANSACTION_ID)
);

COMMENT ON TABLE TRANSACTION_MAPPER IS 'トランザクション変遷マッパー';
COMMENT ON COLUMN TRANSACTION_MAPPER.ID IS 'OID';
COMMENT ON COLUMN TRANSACTION_MAPPER.BEFORE_TRANSACTION_ID IS '元予定トランザクションId';
COMMENT ON COLUMN TRANSACTION_MAPPER.AFTER_TRANSACTION_ID IS '先トランザクションId';


-----------------------------------------------------------------------------
-- ENTRY
-----------------------------------------------------------------------------
DROP TABLE ENTRY;


CREATE TABLE ENTRY
(
    ID int8 NOT NULL,
    QUANTITY int8 NOT NULL,
      -- REFERENCES ACCOUNT (ID)
    ACCOUNT_ID int8 NOT NULL,
      -- REFERENCES TRANSACTION (ID)
    TRANSACTION_ID int8 NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE ENTRY IS 'エントリー';
COMMENT ON COLUMN ENTRY.ID IS 'OID';
COMMENT ON COLUMN ENTRY.QUANTITY IS '移動量';
COMMENT ON COLUMN ENTRY.ACCOUNT_ID IS '勘定ID';
COMMENT ON COLUMN ENTRY.TRANSACTION_ID IS 'トランザクションID';


-----------------------------------------------------------------------------
-- TRANSACTION
-----------------------------------------------------------------------------
DROP TABLE TRANSACTION;


CREATE TABLE TRANSACTION
(
    ID int8 NOT NULL,
    ACTUAL char NOT NULL,
    REGISTER_DATE timestamp NOT NULL,
    PROCESS_DATE timestamp NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE TRANSACTION IS 'トランザクション';
COMMENT ON COLUMN TRANSACTION.ID IS 'OID';
COMMENT ON COLUMN TRANSACTION.ACTUAL IS '実績の取引の場合はY。予定はN';
COMMENT ON COLUMN TRANSACTION.REGISTER_DATE IS '入力日';
COMMENT ON COLUMN TRANSACTION.PROCESS_DATE IS '取引日';


-----------------------------------------------------------------------------
-- ACCOUNT
-----------------------------------------------------------------------------
DROP TABLE ACCOUNT;


CREATE TABLE ACCOUNT
(
    ID int8 NOT NULL,
      -- REFERENCES PLACE (ID)
    PLACE_ID int8 NOT NULL,
      -- REFERENCES ITEM (ID)
    ITEM_ID int8 NOT NULL,
      -- REFERENCES OWNER (ID)
    OWNER_ID int8 NOT NULL,
    REGISTER_DATE timestamp NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_ACCOUNT_1 UNIQUE (PLACE_ID, ITEM_ID, OWNER_ID)
);

COMMENT ON TABLE ACCOUNT IS '勘定';
COMMENT ON COLUMN ACCOUNT.ID IS 'OID';
COMMENT ON COLUMN ACCOUNT.PLACE_ID IS '場所ID';
COMMENT ON COLUMN ACCOUNT.ITEM_ID IS '品目ID';
COMMENT ON COLUMN ACCOUNT.OWNER_ID IS '所有者ID';
COMMENT ON COLUMN ACCOUNT.REGISTER_DATE IS '勘定作成日';


-----------------------------------------------------------------------------
-- PLACE
-----------------------------------------------------------------------------
DROP TABLE PLACE;


CREATE TABLE PLACE
(
    ID int8 NOT NULL,
    NAME varchar (256) NOT NULL,
    VIRTUAL char default 'N' NOT NULL,
      -- REFERENCES PLACE (ID)
    PARENT_ID int8,
      -- REFERENCES OWNER (ID)
    OWNER_ID int8 NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE PLACE IS '場所';
COMMENT ON COLUMN PLACE.ID IS 'OID';
COMMENT ON COLUMN PLACE.NAME IS '場所名';
COMMENT ON COLUMN PLACE.VIRTUAL IS '論理的な場所';
COMMENT ON COLUMN PLACE.PARENT_ID IS '親場所ID';
COMMENT ON COLUMN PLACE.OWNER_ID IS '所有者OID';


-----------------------------------------------------------------------------
-- ITEM
-----------------------------------------------------------------------------
DROP TABLE ITEM;


CREATE TABLE ITEM
(
    ID int8 NOT NULL,
    NAME varchar (256) NOT NULL,
      -- REFERENCES ITEM (ID)
    PARENT_ID int8,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE ITEM IS '品目';
COMMENT ON COLUMN ITEM.ID IS 'OID';
COMMENT ON COLUMN ITEM.NAME IS '品目名';
COMMENT ON COLUMN ITEM.PARENT_ID IS '親品目ID';


-----------------------------------------------------------------------------
-- OWNER_CODE
-----------------------------------------------------------------------------
DROP TABLE OWNER_CODE;


CREATE TABLE OWNER_CODE
(
    ID int8 NOT NULL,
      -- REFERENCES OWNER (ID)
    OWNER_ID int8 NOT NULL,
          -- REFERENCES CODE_SYSTEM (ID)
    CI_CODE_SYSTEM_ID int8 NOT NULL,
    CI_CODE varchar (1024) NOT NULL,
    CI_REVISION integer NOT NULL,
    CI_REGISTERER_DATE timestamp NOT NULL,
    CI_VALID_DATE timestamp,
    CI_INVALID_DATE timestamp,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE OWNER_CODE IS '所有者コード';
COMMENT ON COLUMN OWNER_CODE.ID IS 'OID';
COMMENT ON COLUMN OWNER_CODE.OWNER_ID IS '所有者ID';
COMMENT ON COLUMN OWNER_CODE.CI_CODE_SYSTEM_ID IS '所有者コード情報 コード体系ID';
COMMENT ON COLUMN OWNER_CODE.CI_CODE IS '所有者コード情報 コード';
COMMENT ON COLUMN OWNER_CODE.CI_REVISION IS '所有者コード情報 リビジョン';
COMMENT ON COLUMN OWNER_CODE.CI_REGISTERER_DATE IS '所有者コード情報 登録日時';
COMMENT ON COLUMN OWNER_CODE.CI_VALID_DATE IS '所有者コード情報 有効開始日時';
COMMENT ON COLUMN OWNER_CODE.CI_INVALID_DATE IS '所有者コード情報 無効開始日時';



-----------------------------------------------------------------------------
-- OWNER
-----------------------------------------------------------------------------
DROP TABLE OWNER;


CREATE TABLE OWNER
(
    ID int8 NOT NULL,
    NAME varchar (256) NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE OWNER IS '所有者';
COMMENT ON COLUMN OWNER.ID IS 'OID';
COMMENT ON COLUMN OWNER.NAME IS '所有者名';


-----------------------------------------------------------------------------
-- CODE_SYSTEM
-----------------------------------------------------------------------------
DROP TABLE CODE_SYSTEM;


CREATE TABLE CODE_SYSTEM
(
    ID int8 NOT NULL,
    NAME varchar (256) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_CODE_SYSTEM_1 UNIQUE (NAME)
);

COMMENT ON TABLE CODE_SYSTEM IS 'コード体系';
COMMENT ON COLUMN CODE_SYSTEM.ID IS 'OID';
COMMENT ON COLUMN CODE_SYSTEM.NAME IS 'コード体系名';

