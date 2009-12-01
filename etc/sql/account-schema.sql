
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

COMMENT ON TABLE TRANSACTION_MAPPER IS '�g�����U�N�V�����ϑJ�}�b�p�[';
COMMENT ON COLUMN TRANSACTION_MAPPER.ID IS 'OID';
COMMENT ON COLUMN TRANSACTION_MAPPER.BEFORE_TRANSACTION_ID IS '���\��g�����U�N�V����Id';
COMMENT ON COLUMN TRANSACTION_MAPPER.AFTER_TRANSACTION_ID IS '��g�����U�N�V����Id';


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

COMMENT ON TABLE ENTRY IS '�G���g���[';
COMMENT ON COLUMN ENTRY.ID IS 'OID';
COMMENT ON COLUMN ENTRY.QUANTITY IS '�ړ���';
COMMENT ON COLUMN ENTRY.ACCOUNT_ID IS '����ID';
COMMENT ON COLUMN ENTRY.TRANSACTION_ID IS '�g�����U�N�V����ID';


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

COMMENT ON TABLE TRANSACTION IS '�g�����U�N�V����';
COMMENT ON COLUMN TRANSACTION.ID IS 'OID';
COMMENT ON COLUMN TRANSACTION.ACTUAL IS '���т̎���̏ꍇ��Y�B�\���N';
COMMENT ON COLUMN TRANSACTION.REGISTER_DATE IS '���͓�';
COMMENT ON COLUMN TRANSACTION.PROCESS_DATE IS '�����';


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
    REGISTER_DATE timestamp NOT NULL,
      -- REFERENCES OWNER (ID)
    OWNER_ID int8 NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_ACCOUNT_1 UNIQUE (PLACE_ID, ITEM_ID, OWNER_ID)
);

COMMENT ON TABLE ACCOUNT IS '����';
COMMENT ON COLUMN ACCOUNT.ID IS 'OID';
COMMENT ON COLUMN ACCOUNT.PLACE_ID IS '�ꏊID';
COMMENT ON COLUMN ACCOUNT.ITEM_ID IS '�i��ID';
COMMENT ON COLUMN ACCOUNT.REGISTER_DATE IS '����쐬��';
COMMENT ON COLUMN ACCOUNT.OWNER_ID IS '���L��ID';


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

COMMENT ON TABLE PLACE IS '�ꏊ';
COMMENT ON COLUMN PLACE.ID IS 'OID';
COMMENT ON COLUMN PLACE.NAME IS '�ꏊ��';
COMMENT ON COLUMN PLACE.VIRTUAL IS '�_���I�ȏꏊ';
COMMENT ON COLUMN PLACE.PARENT_ID IS '�e�ꏊID';
COMMENT ON COLUMN PLACE.OWNER_ID IS '���L��OID';


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

COMMENT ON TABLE ITEM IS '�i��';
COMMENT ON COLUMN ITEM.ID IS 'OID';
COMMENT ON COLUMN ITEM.NAME IS '�i�ږ�';
COMMENT ON COLUMN ITEM.PARENT_ID IS '�e�i��ID';


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

COMMENT ON TABLE OWNER IS '���L��';
COMMENT ON COLUMN OWNER.ID IS 'OID';
COMMENT ON COLUMN OWNER.NAME IS '���L�Җ�';

