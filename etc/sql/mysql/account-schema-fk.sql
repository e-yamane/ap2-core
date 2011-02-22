# -----------------------------------------------------------------------
# TRANSACTION_MAPPER
# -----------------------------------------------------------------------
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

# -----------------------------------------------------------------------
# ENTRY
# -----------------------------------------------------------------------
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
ALTER TABLE ENTRY
    ADD CONSTRAINT fk_ENTRY__3 FOREIGN KEY (
      QUANTITY_UNIT_ID
    )
    REFERENCES UNIT (ID)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT 
;



# -----------------------------------------------------------------------
# TRANSACTION
# -----------------------------------------------------------------------

# -----------------------------------------------------------------------
# ACCOUNT
# -----------------------------------------------------------------------
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
    REFERENCES PARTY (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

# -----------------------------------------------------------------------
# ITEM
# -----------------------------------------------------------------------
ALTER TABLE ITEM
    ADD CONSTRAINT fk_ITEM__1 FOREIGN KEY (
      PARENT_ID
    )
    REFERENCES ITEM (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;

# -----------------------------------------------------------------------
# PLACE_CODE
# -----------------------------------------------------------------------
ALTER TABLE PLACE_CODE
    ADD CONSTRAINT fk_PLACE_CODE__1 FOREIGN KEY (
      PLACE_ID
    )
    REFERENCES PLACE (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE PLACE_CODE
    ADD CONSTRAINT fk_PLACE_CODE__2 FOREIGN KEY (
      CI_CODE_SYSTEM_ID
    )
    REFERENCES CODE_SYSTEM (ID)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT 
;



# -----------------------------------------------------------------------
# PLACE
# -----------------------------------------------------------------------
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
    REFERENCES PARTY (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;


# -----------------------------------------------------------------------
# PARTY_CODE
# -----------------------------------------------------------------------
ALTER TABLE PARTY_CODE
    ADD CONSTRAINT fk_PARTY_CODE__1 FOREIGN KEY (
      PARTY_ID
    )
    REFERENCES PARTY (ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE 
;
ALTER TABLE PARTY_CODE
    ADD CONSTRAINT fk_PARTY_CODE__2 FOREIGN KEY (
      CI_CODE_SYSTEM_ID
    )
    REFERENCES CODE_SYSTEM (ID)
    ON UPDATE RESTRICT
    ON DELETE RESTRICT 
;



# -----------------------------------------------------------------------
# PARTY
# -----------------------------------------------------------------------


# -----------------------------------------------------------------------
# CODE_SYSTEM
# -----------------------------------------------------------------------

