
-----------------------------------------------------------------------------
-- TRANSACTION_MAPPER
-----------------------------------------------------------------------------
DROP TABLE TRANSACTION_MAPPER CASCADE;


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
DROP TABLE ENTRY CASCADE;


CREATE TABLE ENTRY
(
    ID int8 NOT NULL,
          -- REFERENCES UNIT (ID)
    QUANTITY_UNIT_ID int8,
        QUANTITY_A_VALUE int8 NOT NULL,
    QUANTITY_A_SCALE integer NOT NULL,
      -- REFERENCES ACCOUNT (ID)
    ACCOUNT_ID int8 NOT NULL,
      -- REFERENCES TRANSACTION (ID)
    TRANSACTION_ID int8 NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE ENTRY IS '�G���g���[';
COMMENT ON COLUMN ENTRY.ID IS 'OID';
COMMENT ON COLUMN ENTRY.QUANTITY_UNIT_ID IS '�ړ��� ���ʎړxID';
COMMENT ON COLUMN ENTRY.QUANTITY_A_VALUE IS '�ړ��� �� ��(����)';
COMMENT ON COLUMN ENTRY.QUANTITY_A_SCALE IS '�ړ��� �� �����_�ʒu�B���̐��Ȃ獶�ցA���̐��Ȃ�E�ֈړ�������';


COMMENT ON COLUMN ENTRY.ACCOUNT_ID IS '����ID';
COMMENT ON COLUMN ENTRY.TRANSACTION_ID IS '�g�����U�N�V����ID';


-----------------------------------------------------------------------------
-- TRANSACTION
-----------------------------------------------------------------------------
DROP TABLE TRANSACTION CASCADE;


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
DROP TABLE ACCOUNT CASCADE;


CREATE TABLE ACCOUNT
(
    ID int8 NOT NULL,
      -- REFERENCES PLACE (ID)
    PLACE_ID int8 NOT NULL,
      -- REFERENCES ITEM (ID)
    ITEM_ID int8 NOT NULL,
      -- REFERENCES PARTY (ID)
    OWNER_ID int8 NOT NULL,
    REGISTER_DATE timestamp NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_ACCOUNT_1 UNIQUE (PLACE_ID, ITEM_ID, OWNER_ID)
);

COMMENT ON TABLE ACCOUNT IS '����';
COMMENT ON COLUMN ACCOUNT.ID IS 'OID';
COMMENT ON COLUMN ACCOUNT.PLACE_ID IS '�ꏊID';
COMMENT ON COLUMN ACCOUNT.ITEM_ID IS '�i��ID';
COMMENT ON COLUMN ACCOUNT.OWNER_ID IS '���L��ID';
COMMENT ON COLUMN ACCOUNT.REGISTER_DATE IS '����쐬��';


-----------------------------------------------------------------------------
-- ITEM
-----------------------------------------------------------------------------
DROP TABLE ITEM CASCADE;


CREATE TABLE ITEM
(
    ID int8 NOT NULL,
    NAME varchar (255) NOT NULL,
      -- REFERENCES ITEM (ID)
    PARENT_ID int8,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE ITEM IS '�i��';
COMMENT ON COLUMN ITEM.ID IS 'OID';
COMMENT ON COLUMN ITEM.NAME IS '�i�ږ�';
COMMENT ON COLUMN ITEM.PARENT_ID IS '�e�i��ID';


-----------------------------------------------------------------------------
-- PLACE_CODE
-----------------------------------------------------------------------------
DROP TABLE PLACE_CODE CASCADE;


CREATE TABLE PLACE_CODE
(
    ID int8 NOT NULL,
      -- REFERENCES PLACE (ID)
    PLACE_ID int8 NOT NULL,
          -- REFERENCES CODE_SYSTEM (ID)
    CI_CODE_SYSTEM_ID int8 NOT NULL,
    CI_CODE varchar (1024) NOT NULL,
    CI_REVISION int8 NOT NULL,
    CI_VALID_DATE timestamp,
    CI_INVALID_DATE timestamp,
        TS_REGISTERER_DATE timestamp NOT NULL,
    TS_LAST_MODIFIED_DATE timestamp NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE PLACE_CODE IS '�ꏊ�R�[�h';
COMMENT ON COLUMN PLACE_CODE.ID IS 'OID';
COMMENT ON COLUMN PLACE_CODE.PLACE_ID IS '�ꏊID';
COMMENT ON COLUMN PLACE_CODE.CI_CODE_SYSTEM_ID IS '�ꏊ�R�[�h��� �R�[�h�̌nID';
COMMENT ON COLUMN PLACE_CODE.CI_CODE IS '�ꏊ�R�[�h��� �R�[�h';
COMMENT ON COLUMN PLACE_CODE.CI_REVISION IS '�ꏊ�R�[�h��� ���r�W����';
COMMENT ON COLUMN PLACE_CODE.CI_VALID_DATE IS '�ꏊ�R�[�h��� �L���J�n����';
COMMENT ON COLUMN PLACE_CODE.CI_INVALID_DATE IS '�ꏊ�R�[�h��� �����J�n����';

COMMENT ON COLUMN PLACE_CODE.TS_REGISTERER_DATE IS '�X�V������� �o�^����';
COMMENT ON COLUMN PLACE_CODE.TS_LAST_MODIFIED_DATE IS '�X�V������� �ŏI�X�V��';



-----------------------------------------------------------------------------
-- PLACE
-----------------------------------------------------------------------------
DROP TABLE PLACE CASCADE;


CREATE TABLE PLACE
(
    ID int8 NOT NULL,
    PLACE_CODE varchar (255) NOT NULL,
    NAME varchar (255) NOT NULL,
    VIRTUAL char default 'N' NOT NULL,
      -- REFERENCES PLACE (ID)
    PARENT_ID int8,
      -- REFERENCES PARTY (ID)
    OWNER_ID int8 NOT NULL,
    REVISION int8 NOT NULL,
    STATUS_CODE varchar (2) NOT NULL,
        TS_REGISTERER_DATE timestamp NOT NULL,
    TS_LAST_MODIFIED_DATE timestamp NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_PLACE_1 UNIQUE (PLACE_CODE)
);

COMMENT ON TABLE PLACE IS '�ꏊ';
COMMENT ON COLUMN PLACE.ID IS 'OID';
COMMENT ON COLUMN PLACE.PLACE_CODE IS '�����Ǘ��p�ꏊ�R�[�h';
COMMENT ON COLUMN PLACE.NAME IS '�ꏊ��';
COMMENT ON COLUMN PLACE.VIRTUAL IS '�_���I�ȏꏊ';
COMMENT ON COLUMN PLACE.PARENT_ID IS '�e�ꏊID';
COMMENT ON COLUMN PLACE.OWNER_ID IS '���L��OID';
COMMENT ON COLUMN PLACE.REVISION IS '���r�W����';
COMMENT ON COLUMN PLACE.STATUS_CODE IS '�X�e�[�^�X�R�[�h';
COMMENT ON COLUMN PLACE.TS_REGISTERER_DATE IS '�X�V������� �o�^����';
COMMENT ON COLUMN PLACE.TS_LAST_MODIFIED_DATE IS '�X�V������� �ŏI�X�V��';



-----------------------------------------------------------------------------
-- PARTY_CODE
-----------------------------------------------------------------------------
DROP TABLE PARTY_CODE CASCADE;


CREATE TABLE PARTY_CODE
(
    ID int8 NOT NULL,
      -- REFERENCES PARTY (ID)
    PARTY_ID int8 NOT NULL,
          -- REFERENCES CODE_SYSTEM (ID)
    CI_CODE_SYSTEM_ID int8 NOT NULL,
    CI_CODE varchar (1024) NOT NULL,
    CI_REVISION int8 NOT NULL,
    CI_VALID_DATE timestamp,
    CI_INVALID_DATE timestamp,
        TS_REGISTERER_DATE timestamp NOT NULL,
    TS_LAST_MODIFIED_DATE timestamp NOT NULL,
    PRIMARY KEY (ID)
);

COMMENT ON TABLE PARTY_CODE IS '�p�[�e�B�R�[�h';
COMMENT ON COLUMN PARTY_CODE.ID IS 'OID';
COMMENT ON COLUMN PARTY_CODE.PARTY_ID IS '�p�[�e�BID';
COMMENT ON COLUMN PARTY_CODE.CI_CODE_SYSTEM_ID IS '�p�[�e�B�R�[�h��� �R�[�h�̌nID';
COMMENT ON COLUMN PARTY_CODE.CI_CODE IS '�p�[�e�B�R�[�h��� �R�[�h';
COMMENT ON COLUMN PARTY_CODE.CI_REVISION IS '�p�[�e�B�R�[�h��� ���r�W����';
COMMENT ON COLUMN PARTY_CODE.CI_VALID_DATE IS '�p�[�e�B�R�[�h��� �L���J�n����';
COMMENT ON COLUMN PARTY_CODE.CI_INVALID_DATE IS '�p�[�e�B�R�[�h��� �����J�n����';

COMMENT ON COLUMN PARTY_CODE.TS_REGISTERER_DATE IS '�X�V������� �o�^����';
COMMENT ON COLUMN PARTY_CODE.TS_LAST_MODIFIED_DATE IS '�X�V������� �ŏI�X�V��';



-----------------------------------------------------------------------------
-- PARTY
-----------------------------------------------------------------------------
DROP TABLE PARTY CASCADE;


CREATE TABLE PARTY
(
    ID int8 NOT NULL,
    PARTY_CODE varchar (255) NOT NULL,
    NAME varchar (255) NOT NULL,
    REVISION int8 NOT NULL,
    STATUS_CODE varchar (2) NOT NULL,
        TS_REGISTERER_DATE timestamp NOT NULL,
    TS_LAST_MODIFIED_DATE timestamp NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_PARTY_1 UNIQUE (PARTY_CODE)
);

COMMENT ON TABLE PARTY IS '�p�[�e�B';
COMMENT ON COLUMN PARTY.ID IS 'OID';
COMMENT ON COLUMN PARTY.PARTY_CODE IS '�����Ǘ��p�p�[�e�B�R�[�h';
COMMENT ON COLUMN PARTY.NAME IS '�p�[�e�B��';
COMMENT ON COLUMN PARTY.REVISION IS '���r�W����';
COMMENT ON COLUMN PARTY.STATUS_CODE IS '�X�e�[�^�X�R�[�h';
COMMENT ON COLUMN PARTY.TS_REGISTERER_DATE IS '�X�V������� �o�^����';
COMMENT ON COLUMN PARTY.TS_LAST_MODIFIED_DATE IS '�X�V������� �ŏI�X�V��';



-----------------------------------------------------------------------------
-- CODE_SYSTEM
-----------------------------------------------------------------------------
DROP TABLE CODE_SYSTEM CASCADE;


CREATE TABLE CODE_SYSTEM
(
    ID int8 NOT NULL,
    NAME varchar (255) NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT unq_CODE_SYSTEM_1 UNIQUE (NAME)
);

COMMENT ON TABLE CODE_SYSTEM IS '�R�[�h�̌n';
COMMENT ON COLUMN CODE_SYSTEM.ID IS 'OID';
COMMENT ON COLUMN CODE_SYSTEM.NAME IS '�R�[�h�̌n��';

