-- SQL script to update GEOREF with existing geometries taken from FIGIS_GIS.VMEMEASURES
-- Geometries are used to set the WKT_GEOM (geometry as WKT)
UPDATE VME.GEOREF
SET WKT_GEOM = (SELECT SDO_UTIL.TO_WKTGEOMETRY(b.THE_GEOM) 
                FROM FIGIS_GIS.VMEMEASURES b
                WHERE GEOGRAPHICFEATUREID = b.VME_AREA_TIME)
WHERE
  EXISTS(SELECT GEOGRAPHICFEATUREID FROM FIGIS_GIS.VMEMEASURES b
                WHERE GEOGRAPHICFEATUREID = b.VME_AREA_TIME)

-------------------------------------------------------------------------
-- BELOW STEPS ARE REQUIRED PRIOR TO THE CREATION OF THE VMEMEASURE VIEW
-- AND AFTER THE EXTENSION of VME GRAPH
--							    |
--								V
-------------------------------------------------------------------------				

-- missing privileges to FIGIS_GIS
-- (these privileges should be added if the Oracle JNDI used in Geoserver is configured on FIGIS_GIS
--	btw, checking the Oracle datastore in Geoserver, it seems that VME tables are yet accessible)
GRANT SELECT ON VME.VME TO FIGIS_GIS;
GRANT SELECT ON VME.GEOREF TO FIGIS_GIS;
GRANT SELECT ON VME.SPECIFIC_MEASURE TO FIGIS_GIS;
GRANT SELECT ON VME.VME_TYPE TO FIGIS_GIS;
GRANT SELECT ON VME.MULTILINGUAL_STRING TO FIGIS_GIS;
GRANT SELECT ON VME.MULTILINGUALSTRING_STRINGMAP TO FIGIS_GIS;
COMMIT;
	
-- CREATE DETERMINISTIC WRAPPER FUNCTION (with VME user)
-- (we need a deterministic function to be use in metadata record and spatial index on VME.GEOREF)
CREATE OR REPLACE FUNCTION WKT_TO_GEOMETRY (wkt IN CLOB)
RETURN SDO_GEOMETRY DETERMINISTIC IS
BEGIN
  IF wkt IS NOT NULL THEN
	RETURN SDO_GEOMETRY(wkt, 4326);
  ELSE
	RETURN NULL;
  END IF;
END;

-- check existence of the newly created function
SELECT * FROM ALL_PROCEDURES WHERE OBJECT_NAME = 'WKT_TO_GEOMETRY';

-- FIGIS_GIS will need to execute this function as well
GRANT EXECUTE ON WKT_TO_GEOMETRY TO FIGIS_GIS;

-- Need to create function-based GIS metadata record and spatial index on GEOREF
-- Required to make the view working on Geoserver WMS
-- VERY IMPORTANT NOTE: the name of the procedure WKT_TO_GEOMETRY must be preceded by the schema (VME)
--- (1) Metadata record
INSERT INTO MDSYS.USER_SDO_GEOM_METADATA
VALUES (
  'GEOREF',
  'VME.WKT_TO_GEOMETRY(WKT_GEOM)',
  MDSYS.SDO_DIM_ARRAY(
	MDSYS.SDO_DIM_ELEMENT('X',-180,180,0.005),
	MDSYS.SDO_DIM_ELEMENT('Y',-90,90,0.005)),
  4326
);
COMMIT;

-- (2) CREATE SPATIAL INDEX on GEOREF
DROP INDEX VME_GEOREF_SPATIAL_IDX;
CREATE INDEX VME_GEOREF_SPATIAL_IDX
ON VME.GEOREF (WKT_TO_GEOMETRY(WKT_GEOM))
INDEXTYPE IS MDSYS.SPATIAL_INDEX
PARAMETERS('TABLESPACE=VME_TAB01 SDO_RTR_PCTFREE=0')
NOPARALLEL;
COMMIT;