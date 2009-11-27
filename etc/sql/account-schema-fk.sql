
----------------------------------------------------------------------
-- OWNER                                                      
----------------------------------------------------------------------

ALTER TABLE TRANSACTION_MAPPER
    ADD CONSTRAINT fk_TRANSACTION_MAPPER__1 FOREIGN KEY (
      BEFORE_TRANSACTION_ID
    )
    REFERENCES TRANSACTION (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE TRANSACTION_MAPPER
    ADD CONSTRAINT fk_TRANSACTION_MAPPER__2 FOREIGN KEY (
      AFTER_TRANSACTION_ID
    )
    REFERENCES TRANSACTION (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- TRANSACTION_MAPPER                                                      
----------------------------------------------------------------------

ALTER TABLE ENTRY
    ADD CONSTRAINT fk_ENTRY__1 FOREIGN KEY (
      ACCOUNT_ID
    )
    REFERENCES ACCOUNT (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE ENTRY
    ADD CONSTRAINT fk_ENTRY__2 FOREIGN KEY (
      TRANSACTION_ID
    )
    REFERENCES TRANSACTION (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- ENTRY                                                      
----------------------------------------------------------------------

ALTER TABLE PLAN_TRANSACTION
    ADD CONSTRAINT fk_PLAN_TRANSACTION__1 FOREIGN KEY (
      ID
    )
    REFERENCES TRANSACTION (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- PLAN_TRANSACTION                                                      
----------------------------------------------------------------------

ALTER TABLE ACTUAL_TRANSACTION
    ADD CONSTRAINT fk_ACTUAL_TRANSACTION__1 FOREIGN KEY (
      ID
    )
    REFERENCES TRANSACTION (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- ACTUAL_TRANSACTION                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- TRANSACTION                                                      
----------------------------------------------------------------------

ALTER TABLE ACCOUNT
    ADD CONSTRAINT fk_ACCOUNT__1 FOREIGN KEY (
      PLACE_ID
    )
    REFERENCES PLACE (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE ACCOUNT
    ADD CONSTRAINT fk_ACCOUNT__2 FOREIGN KEY (
      ITEM_ID
    )
    REFERENCES ITEM (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE ACCOUNT
    ADD CONSTRAINT fk_ACCOUNT__3 FOREIGN KEY (
      OWNER_ID
    )
    REFERENCES OWNER (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- ACCOUNT                                                      
----------------------------------------------------------------------

ALTER TABLE PLACE
    ADD CONSTRAINT fk_PLACE__1 FOREIGN KEY (
      PARENT_ID
    )
    REFERENCES PLACE (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE PLACE
    ADD CONSTRAINT fk_PLACE__2 FOREIGN KEY (
      OWNER_ID
    )
    REFERENCES OWNER (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- PLACE                                                      
----------------------------------------------------------------------

ALTER TABLE ITEM
    ADD CONSTRAINT fk_ITEM__1 FOREIGN KEY (
      PARENT_ID
    )
    REFERENCES ITEM (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

----------------------------------------------------------------------
-- ITEM                                                      
----------------------------------------------------------------------

