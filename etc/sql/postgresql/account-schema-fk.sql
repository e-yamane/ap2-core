
----------------------------------------------------------------------
-- CODE_SYSTEM                                                      
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

ALTER TABLE OWNER_CODE
    ADD CONSTRAINT fk_OWNER_CODE__1 FOREIGN KEY (
      OWNER_ID
    )
    REFERENCES OWNER (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE OWNER_CODE
    ADD CONSTRAINT fk_OWNER_CODE__2 FOREIGN KEY (
      CI_CODE_SYSTEM_ID
    )
    REFERENCES CODE_SYSTEM (ID)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT 
;

----------------------------------------------------------------------
-- OWNER_CODE                                                      
----------------------------------------------------------------------


----------------------------------------------------------------------
-- OWNER                                                      
----------------------------------------------------------------------

