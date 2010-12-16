-- PostgreSQL�̈ڍsSQL 
alter table entry add column AMOUNT_UNIT_ID int8;
alter table entry add column AMOUNT_Q_VALUE int8;
alter table entry add column AMOUNT_Q_SCALE integer;
update entry set amount_q_value = quantity, amount_q_scale = 0;
alter table entry alter column AMOUNT_Q_VALUE set not null; 
alter table entry alter column AMOUNT_Q_SCALE set not null; 
alter table entry drop column quantity;


-- Oracle�̈ڍsSQL
alter table entry add (
  AMOUNT_UNIT_ID NUMBER (20, 0),
  AMOUNT_Q_VALUE NUMBER (20, 0),
  AMOUNT_Q_SCALE NUMBER (10, 0)
);
update entry set amount_q_value = quantity, amount_q_scale = 0;
alter table entry modify (
  AMOUNT_Q_VALUE NUMBER (20, 0) not null,
  AMOUNT_Q_SCALE NUMBER (10, 0) not null
);
alter table entry drop column quantity;
