
# -----------------------------------------------------------------------
# TRANSACTION_MAPPER
# -----------------------------------------------------------------------
drop table if exists TRANSACTION_MAPPER;

CREATE TABLE TRANSACTION_MAPPER
(
ID BIGINT NOT NULL COMMENT 'OID',
BEFORE_TRANSACTION_ID BIGINT NOT NULL COMMENT '元予定トランザクションId',
AFTER_TRANSACTION_ID BIGINT COMMENT '先トランザクションId',
    PRIMARY KEY(ID),
    UNIQUE (BEFORE_TRANSACTION_ID, AFTER_TRANSACTION_ID));
ALTER TABLE TRANSACTION_MAPPER COMMENT 'トランザクション変遷マッパー';

# -----------------------------------------------------------------------
# ENTRY
# -----------------------------------------------------------------------
drop table if exists ENTRY;

CREATE TABLE ENTRY
(
ID BIGINT NOT NULL COMMENT 'OID',
QUANTITY_UNIT_ID BIGINT COMMENT '移動量 数量尺度ID',
QUANTITY_A_VALUE BIGINT NOT NULL COMMENT '移動量 量 量(整数)',
QUANTITY_A_SCALE INTEGER NOT NULL COMMENT '移動量 量 小数点位置。正の数なら左へ、負の数なら右へ移動させる',
ACCOUNT_ID BIGINT NOT NULL COMMENT '勘定ID',
TRANSACTION_ID BIGINT NOT NULL COMMENT 'トランザクションID',
    PRIMARY KEY(ID));
ALTER TABLE ENTRY COMMENT 'エントリー';

# -----------------------------------------------------------------------
# TRANSACTION
# -----------------------------------------------------------------------
drop table if exists TRANSACTION;

CREATE TABLE TRANSACTION
(
ID BIGINT NOT NULL COMMENT 'OID',
ACTUAL CHAR (1) NOT NULL COMMENT '実績の取引の場合はY。予定はN',
REGISTER_DATE TIMESTAMP NOT NULL COMMENT '入力日',
PROCESS_DATE TIMESTAMP NOT NULL COMMENT '取引日',
    PRIMARY KEY(ID));
ALTER TABLE TRANSACTION COMMENT 'トランザクション';

# -----------------------------------------------------------------------
# ACCOUNT
# -----------------------------------------------------------------------
drop table if exists ACCOUNT;

CREATE TABLE ACCOUNT
(
ID BIGINT NOT NULL COMMENT 'OID',
PLACE_ID BIGINT NOT NULL COMMENT '場所ID',
ITEM_ID BIGINT NOT NULL COMMENT '品目ID',
OWNER_ID BIGINT NOT NULL COMMENT '所有者ID',
REGISTER_DATE TIMESTAMP NOT NULL COMMENT '勘定作成日',
    PRIMARY KEY(ID),
    UNIQUE (PLACE_ID, ITEM_ID, OWNER_ID));
ALTER TABLE ACCOUNT COMMENT '勘定';

# -----------------------------------------------------------------------
# ITEM
# -----------------------------------------------------------------------
drop table if exists ITEM;

CREATE TABLE ITEM
(
ID BIGINT NOT NULL COMMENT 'OID',
NAME VARCHAR (255) NOT NULL COMMENT '品目名',
PARENT_ID BIGINT COMMENT '親品目ID',
    PRIMARY KEY(ID));
ALTER TABLE ITEM COMMENT '品目';

# -----------------------------------------------------------------------
# PLACE_CODE
# -----------------------------------------------------------------------
drop table if exists PLACE_CODE;

CREATE TABLE PLACE_CODE
(
ID BIGINT NOT NULL COMMENT 'OID',
PLACE_ID BIGINT NOT NULL COMMENT '場所ID',
CI_CODE_SYSTEM_ID BIGINT NOT NULL COMMENT '場所コード情報 コード体系ID',
CI_CODE VARCHAR (1024) NOT NULL COMMENT '場所コード情報 コード',
CI_REVISION BIGINT NOT NULL COMMENT '場所コード情報 リビジョン',
CI_VALID_DATE TIMESTAMP COMMENT '場所コード情報 有効開始日時',
CI_INVALID_DATE TIMESTAMP COMMENT '場所コード情報 無効開始日時',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 登録日時',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 最終更新日',
    PRIMARY KEY(ID));
ALTER TABLE PLACE_CODE COMMENT '場所コード';

# -----------------------------------------------------------------------
# PLACE
# -----------------------------------------------------------------------
drop table if exists PLACE;

CREATE TABLE PLACE
(
ID BIGINT NOT NULL COMMENT 'OID',
PLACE_CODE VARCHAR (255) NOT NULL COMMENT '内部管理用場所コード',
NAME VARCHAR (255) NOT NULL COMMENT '場所名',
VIRTUAL CHAR (1) default 'N' NOT NULL COMMENT '論理的な場所',
PARENT_ID BIGINT COMMENT '親場所ID',
OWNER_ID BIGINT NOT NULL COMMENT '所有者OID',
REVISION BIGINT NOT NULL COMMENT 'リビジョン',
STATUS_CODE VARCHAR (2) NOT NULL COMMENT 'ステータスコード',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 登録日時',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 最終更新日',
    PRIMARY KEY(ID),
    UNIQUE (PLACE_CODE));
ALTER TABLE PLACE COMMENT '場所';

# -----------------------------------------------------------------------
# PARTY_CODE
# -----------------------------------------------------------------------
drop table if exists PARTY_CODE;

CREATE TABLE PARTY_CODE
(
ID BIGINT NOT NULL COMMENT 'OID',
PARTY_ID BIGINT NOT NULL COMMENT 'パーティID',
CI_CODE_SYSTEM_ID BIGINT NOT NULL COMMENT 'パーティコード情報 コード体系ID',
CI_CODE VARCHAR (1024) NOT NULL COMMENT 'パーティコード情報 コード',
CI_REVISION BIGINT NOT NULL COMMENT 'パーティコード情報 リビジョン',
CI_VALID_DATE TIMESTAMP COMMENT 'パーティコード情報 有効開始日時',
CI_INVALID_DATE TIMESTAMP COMMENT 'パーティコード情報 無効開始日時',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 登録日時',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 最終更新日',
    PRIMARY KEY(ID));
ALTER TABLE PARTY_CODE COMMENT 'パーティコード';

# -----------------------------------------------------------------------
# PARTY
# -----------------------------------------------------------------------
drop table if exists PARTY;

CREATE TABLE PARTY
(
ID BIGINT NOT NULL COMMENT 'OID',
PARTY_CODE VARCHAR (255) NOT NULL COMMENT '内部管理用パーティコード',
NAME VARCHAR (255) NOT NULL COMMENT 'パーティ名',
REVISION BIGINT NOT NULL COMMENT 'リビジョン',
STATUS_CODE VARCHAR (2) NOT NULL COMMENT 'ステータスコード',
TS_REGISTERER_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 登録日時',
TS_LAST_MODIFIED_DATE TIMESTAMP NOT NULL COMMENT '更新日時情報 最終更新日',
    PRIMARY KEY(ID),
    UNIQUE (PARTY_CODE));
ALTER TABLE PARTY COMMENT 'パーティ';

# -----------------------------------------------------------------------
# CODE_SYSTEM
# -----------------------------------------------------------------------
drop table if exists CODE_SYSTEM;

CREATE TABLE CODE_SYSTEM
(
ID BIGINT NOT NULL COMMENT 'OID',
NAME VARCHAR (255) NOT NULL COMMENT 'コード体系名',
    PRIMARY KEY(ID),
    UNIQUE (NAME));
ALTER TABLE CODE_SYSTEM COMMENT 'コード体系';
