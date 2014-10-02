select * from ALL_TAB_COLUMNS
where column_name = 'BEGINYEAR'
or column_name = 'ENDYEAR'

ALTER TABLE vme DROP COLUMN beginyear;
ALTER TABLE vme DROP COLUMN endyear;

ALTER TABLE SPECIFIC_MEASURE DROP COLUMN beginyear;
ALTER TABLE SPECIFIC_MEASURE DROP COLUMN endyear;

ALTER TABLE GENERAL_MEASURE DROP COLUMN beginyear;
ALTER TABLE GENERAL_MEASURE DROP COLUMN endyear;