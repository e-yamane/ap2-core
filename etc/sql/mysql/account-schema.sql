
# -----------------------------------------------------------------------
# TRANSACTION_MAPPER
# -----------------------------------------------------------------------
drop table if exists TRANSACTION_MAPPER;

CREATE TABLE TRANSACTION_MAPPER
(
ID BIGINT NOT NULL COMMENT 'OID',
BEFORE_TRANSACTION_ID BIGINT NOT NULL COMMENT '���\��g�����U�N�V����Id',
AFTER_TRANSACTION_ID BIGINT COMMENT '��g�����U�N�V����Id',
    PRIMARY KEY(ID),
    UNIQUE (BEFORE_TRANSACTION_ID, AFTER_TRANSACTION_ID));
ALTER TABLE TRANSACTION_MAPPER COMMENT '�g�����U�N�V�����ϑJ�}�b�p�[';

# -----------------------------------------------------------------------
# ENTRY
# -----------------------------------------------------------------------
drop table if exists ENTRY;

CREATE TABLE ENTRY
(
ID BIGINT NOT NULL COMMENT 'OID',
QUANTITY_UNIT_ID BIGINT COMMENT '�ړ��� ���ʎړxID',
QUANTITY_A_VALUE BIGINT NOT NULL COMMENT '�ړ��� �� ��(����)',
QUANTITY_A_SCALE INTEGER NOT NULL COMMENT '�ړ��� �� �����_�ʒu�B���̐��Ȃ獶�ցA���̐��Ȃ�E�ֈړ�������',
ACCOUNT_ID BIGINT NOT NULL COMMENT '����ID',
TRANSACTION_ID BIGINT NOT NULL COMMENT '�g�����U�N�V����ID',
    PRIMARY KEY(ID));
ALTER TABLE ENTRY COMMENT '�G���g���[';

# -----------------------------------------------------------------------
# TRANSACTION
# -----------------------------------------------------------------------
drop table if exists TRANSACTION;

CREATE TABLE TRANSACTION
(
ID BIGINT NOT NULL COMMENT 'OID',
ACTUAL CHAR (1) NOT NULL COMMENT '���т̎���̏ꍇ��Y�B�\���N',
REGISTER_DATE TIMESTAMP NOT NULL COMMENT '���͓�',
PROCESS_DATE TIMESTAMP NOT NULL COMMENT '�����',
    PRIMARY KEY(ID));
ALTER TABLE TRANSACTION COMMENT '�g�����U�N�V����';

# -----------------------------------------------------------------------
# ACCOUNT
# -----------------------------------------------------------------------
drop table if exists ACCOUNT;

CREATE TABLE ACCOUNT
(
ID BIGINT NOT NULL COMMENT 'OID',
PLACE_ID BIGINT NOT NULL COMMENT '�ꏊID',
ITEM_ID BIGINT NOT NULL COMMENT '�i��ID',
OWNER_ID BIGINT NOT NULL COMMENT '���L��ID',
REGISTER_DATE TIMESTAMP NOT NULL COMMENT '����쐬��',
    PRIMARY KEY(ID),
    UNIQUE (PLACE_ID, ITEM_ID, OWNER_ID));
ALTER TABLE ACCOUNT COMMENT '����';

# -----------------------------------------------------------------------
# ITEM
# -----------------------------------------------------------------------
drop table if exists ITEM;

CREATE TABLE ITEM
(
ID BIGINT NOT NULL COMMENT 'OID',
NAME VARCHAR (255) NOT NULL COMMENT '�i�ږ�',
PARENT_ID BIGINT COMMENT '�e�i��ID',
    PRIMARY KEY(ID));
ALTER TABLE ITEM COMMENT '�i��';

# -----------------------------------------------------------------------
# PLACE_CODE
# -----------------------------------------------------------------------
drop table if exists PLACE_CODE;

CREATE TABLE PLACE_CODE
(
ID BIGINT NOT NULL COMMENT 'OID',
PLACE_ID BIGINT NOT NULL COMMENT '�ꏊID',
CI_CODE_SYSTEM_ID BIGINT NOT NULL COMMENT '�ꏊ�R�[�h��� �R�[�h�̌nID',
CI_CODE VARCHAR (1024) NOT NULL COMMENT '�ꏊ�R�[�h��� �R�[�h',
CI_REVISION BIGINT NOT NULL COMMENT '�ꏊ�R�[�h��� ���r�W����',
CI_VALID_DATE TIMESTAMP COMMENT '�ꏊ�R�[�h��� �L���J�n����',
CI_INVALID_DATE TIMESTAMP COMMENT '�ꏊ�R�[�h��� �����J�n����',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �o�^����',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �ŏI�X�V��',
    PRIMARY KEY(ID));
ALTER TABLE PLACE_CODE COMMENT '�ꏊ�R�[�h';

# -----------------------------------------------------------------------
# PLACE
# -----------------------------------------------------------------------
drop table if exists PLACE;

CREATE TABLE PLACE
(
ID BIGINT NOT NULL COMMENT 'OID',
PLACE_CODE VARCHAR (255) NOT NULL COMMENT '�����Ǘ��p�ꏊ�R�[�h',
NAME VARCHAR (255) NOT NULL COMMENT '�ꏊ��',
VIRTUAL CHAR (1) default 'N' NOT NULL COMMENT '�_���I�ȏꏊ',
PARENT_ID BIGINT COMMENT '�e�ꏊID',
OWNER_ID BIGINT NOT NULL COMMENT '���L��OID',
REVISION BIGINT NOT NULL COMMENT '���r�W����',
STATUS_CODE VARCHAR (2) NOT NULL COMMENT '�X�e�[�^�X�R�[�h',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �o�^����',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �ŏI�X�V��',
    PRIMARY KEY(ID),
    UNIQUE (PLACE_CODE));
ALTER TABLE PLACE COMMENT '�ꏊ';

# -----------------------------------------------------------------------
# PARTY_CODE
# -----------------------------------------------------------------------
drop table if exists PARTY_CODE;

CREATE TABLE PARTY_CODE
(
ID BIGINT NOT NULL COMMENT 'OID',
PARTY_ID BIGINT NOT NULL COMMENT '�p�[�e�BID',
CI_CODE_SYSTEM_ID BIGINT NOT NULL COMMENT '�p�[�e�B�R�[�h��� �R�[�h�̌nID',
CI_CODE VARCHAR (1024) NOT NULL COMMENT '�p�[�e�B�R�[�h��� �R�[�h',
CI_REVISION BIGINT NOT NULL COMMENT '�p�[�e�B�R�[�h��� ���r�W����',
CI_VALID_DATE TIMESTAMP COMMENT '�p�[�e�B�R�[�h��� �L���J�n����',
CI_INVALID_DATE TIMESTAMP COMMENT '�p�[�e�B�R�[�h��� �����J�n����',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �o�^����',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �ŏI�X�V��',
    PRIMARY KEY(ID));
ALTER TABLE PARTY_CODE COMMENT '�p�[�e�B�R�[�h';

# -----------------------------------------------------------------------
# PARTY
# -----------------------------------------------------------------------
drop table if exists PARTY;

CREATE TABLE PARTY
(
ID BIGINT NOT NULL COMMENT 'OID',
PARTY_CODE VARCHAR (255) NOT NULL COMMENT '�����Ǘ��p�p�[�e�B�R�[�h',
NAME VARCHAR (255) NOT NULL COMMENT '�p�[�e�B��',
REVISION BIGINT NOT NULL COMMENT '���r�W����',
STATUS_CODE VARCHAR (2) NOT NULL COMMENT '�X�e�[�^�X�R�[�h',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �o�^����',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '�X�V������� �ŏI�X�V��',
    PRIMARY KEY(ID),
    UNIQUE (PARTY_CODE));
ALTER TABLE PARTY COMMENT '�p�[�e�B';

# -----------------------------------------------------------------------
# CODE_SYSTEM
# -----------------------------------------------------------------------
drop table if exists CODE_SYSTEM;

CREATE TABLE CODE_SYSTEM
(
ID BIGINT NOT NULL COMMENT 'OID',
NAME VARCHAR (255) NOT NULL COMMENT '�R�[�h�̌n��',
    PRIMARY KEY(ID),
    UNIQUE (NAME));
ALTER TABLE CODE_SYSTEM COMMENT '�R�[�h�̌n';
