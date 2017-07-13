CREATE SEQUENCE assayParamSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE assayPtmSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE assaySequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE authoritySequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE contactSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE instrCompParamSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE instrCompSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE instrumentSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE paramSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE projectFileSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE projectParamSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE projectPtmSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE projectSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE referenceSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE projectTagSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE labHeadSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE softwareParamSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE softwareSequence START WITH 1 INCREMENT BY 100;

CREATE SEQUENCE prideUserSequence START WITH 1 INCREMENT BY 100;


-- Generated by Oracle SQL Developer Data Modeler 3.1.4.710
--   at:        2013-08-23 12:23:35 BST
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g



CREATE TABLE assay
(
  assay_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  accession VARCHAR2 (128)  NOT NULL ,
  title VARCHAR2 (500)  NOT NULL ,
  short_label VARCHAR2 (500) ,
  protein_count NUMBER (15)  NOT NULL ,
  peptide_count NUMBER (15)  NOT NULL ,
  unique_peptide_count NUMBER (15)  NOT NULL ,
  identified_spectrum_count NUMBER (15)  NOT NULL ,
  total_spectrum_count NUMBER (15)  NOT NULL ,
  ms2_annotation NUMBER (1)  NOT NULL ,
  chromatogram NUMBER (1)  NOT NULL ,
  experimental_factor VARCHAR2 (500)
)
;


CREATE INDEX assay_project_IDX ON assay
(
  project_fk ASC
)
;

ALTER TABLE assay
ADD CONSTRAINT assay_pk PRIMARY KEY ( assay_pk ) ;


ALTER TABLE assay
ADD CONSTRAINT unique_assay_ac UNIQUE ( accession ) ;



CREATE TABLE assay_cvparam
(
  param_pk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  param_type VARCHAR2 (64)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX assay_cvparam_assay_IDX ON assay_cvparam
(
  assay_fk ASC
)
;
CREATE INDEX assay_cvparam_cvparam_IDX ON assay_cvparam
(
  cv_param_fk ASC
)
;
CREATE INDEX assay_cvparam_type_IDX ON assay_cvparam
(
  param_type ASC
)
;

ALTER TABLE assay_cvparam
ADD CONSTRAINT param_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE assay_ptm
(
  assay_ptm_pk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  value VARCHAR2 (255)
)
;


CREATE INDEX assay_ptm_assay_IDX ON assay_ptm
(
  assay_fk ASC
)
;
CREATE INDEX assay_ptm_cvparam_IDX ON assay_ptm
(
  cv_param_fk ASC
)
;

ALTER TABLE assay_ptm
ADD CONSTRAINT assay_ptm_PK PRIMARY KEY ( assay_ptm_pk ) ;



CREATE TABLE assay_userparam
(
  param_pk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10)  NOT NULL ,
  param_type VARCHAR2 (64)  NOT NULL ,
  param_name VARCHAR2 (2000)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX assay_userparam_assay_IDX ON assay_userparam
(
  assay_fk ASC
)
;
CREATE INDEX assay_userparam_type_IDX ON assay_userparam
(
  param_type ASC
)
;

ALTER TABLE assay_userparam
ADD CONSTRAINT assay_userparam_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE authorities
(
  authority_pk NUMBER (10)  NOT NULL ,
  user_fk NUMBER (10)  NOT NULL ,
  authority VARCHAR2 (50)  NOT NULL
)
;



ALTER TABLE authorities
ADD CONSTRAINT authorities_fk PRIMARY KEY ( authority_pk ) ;



CREATE TABLE contact
(
  contact_pk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10)  NOT NULL ,
  title VARCHAR2 (16)  NOT NULL ,
  first_name VARCHAR2 (255)  NOT NULL ,
  last_name VARCHAR2 (255)  NOT NULL ,
  affiliation VARCHAR2 (2000)  NOT NULL ,
  email VARCHAR2 (500)  NOT NULL
)
;


CREATE INDEX contact_assay_fk_IDX ON contact
(
  assay_fk ASC
)
;

ALTER TABLE contact
ADD CONSTRAINT contact_pk PRIMARY KEY ( contact_pk ) ;



CREATE TABLE cv_param
(
  cv_param_pk NUMBER (10)  NOT NULL ,
  cv_label VARCHAR2 (32)  NOT NULL ,
  accession VARCHAR2 (255)  NOT NULL ,
  name VARCHAR2 (2000)  NOT NULL
)
;



ALTER TABLE cv_param
ADD CONSTRAINT cv_param_PK PRIMARY KEY ( cv_param_pk ) ;


ALTER TABLE cv_param
ADD CONSTRAINT unique_param_ac UNIQUE ( cv_label , accession ) ;



CREATE TABLE instrument
(
  instrument_pk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX instrument_cvparam_IDX ON instrument
(
  cv_param_fk ASC
)
;
CREATE INDEX instrument_assay_IDX ON instrument
(
  assay_fk ASC
)
;

ALTER TABLE instrument
ADD CONSTRAINT instrument_PK PRIMARY KEY ( instrument_pk ) ;



CREATE TABLE instrument_component
(
  instrument_component_pk NUMBER (10)  NOT NULL ,
  instrument_fk NUMBER (10)  NOT NULL ,
  order_index NUMBER (10)  NOT NULL ,
  instrument_component_type VARCHAR2 (64)  NOT NULL
)
;


CREATE INDEX instr_comp_instrument_IDX ON instrument_component
(
  instrument_fk ASC
)
;
CREATE INDEX instr_comp_type_IDX ON instrument_component
(
  instrument_component_type ASC
)
;

ALTER TABLE instrument_component
ADD CONSTRAINT instrument_component_PK PRIMARY KEY ( instrument_component_pk ) ;



CREATE TABLE instrument_component_cvparam
(
  param_pk NUMBER (10)  NOT NULL ,
  instrument_component_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX ic_cvparam_param_fk_IDX ON instrument_component_cvparam
(
  cv_param_fk ASC
)
;
CREATE INDEX ic_cvparam_instr_fk_IDX ON instrument_component_cvparam
(
  instrument_component_fk ASC
)
;

ALTER TABLE instrument_component_cvparam
ADD CONSTRAINT ic_cvparam_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE instrument_component_userparam
(
  param_pk NUMBER (10)  NOT NULL ,
  instrument_component_fk NUMBER (10)  NOT NULL ,
  param_name VARCHAR2 (2000)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX instr_comp_uparam_IDX ON instrument_component_userparam
(
  instrument_component_fk ASC
)
;

ALTER TABLE instrument_component_userparam
ADD CONSTRAINT instr_comp_uparam_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE lab_head
(
  lab_head_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  email VARCHAR2 (500)  NOT NULL ,
  title VARCHAR2 (16)  NOT NULL ,
  first_name VARCHAR2 (255)  NOT NULL ,
  last_name VARCHAR2 (255)  NOT NULL ,
  affiliation VARCHAR2 (500)  NOT NULL
)
;

CREATE INDEX lab_head_project_fk_IDX ON lab_head
(
  project_fk ASC
)
;

ALTER TABLE lab_head
ADD CONSTRAINT lab_head_PK PRIMARY KEY ( lab_head_pk ) ;



CREATE TABLE project
(
  project_pk NUMBER (10)  NOT NULL ,
  submitter_fk NUMBER (10)  NOT NULL ,
  accession VARCHAR2 (128)  NOT NULL ,
  doi VARCHAR2 (128) ,
  title VARCHAR2 (500)  NOT NULL ,
  num_assays NUMBER (10)  NOT NULL ,
  is_public NUMBER (1)  NOT NULL ,
  submission_type VARCHAR2 (64)  NOT NULL ,
  submission_date DATE  NOT NULL ,
  update_date DATE  NOT NULL ,
  project_description CLOB ,
  sample_proc_protocol_descr CLOB ,
  data_proc_protocol_descr CLOB ,
  other_omics_link VARCHAR2 (500) ,
  keywords VARCHAR2 (500) ,
  reanalysis VARCHAR2 (255) ,
  publication_date DATE ,
  changed NUMBER (1)  NOT NULL
)
;


CREATE INDEX project_submitter_IDX ON project
(
  submitter_fk ASC
)
;

ALTER TABLE project
ADD CONSTRAINT project_pk PRIMARY KEY ( project_pk ) ;


ALTER TABLE project
ADD CONSTRAINT unique_project_ac UNIQUE ( accession ) ;



CREATE TABLE project_cvparam
(
  param_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  param_type VARCHAR2 (64)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX project_param_project_IDX ON project_cvparam
(
  project_fk ASC
)
;
CREATE INDEX project_param_cvparam_IDX ON project_cvparam
(
  cv_param_fk ASC
)
;
CREATE INDEX project_param_type_IDX ON project_cvparam
(
  param_type ASC
)
;

ALTER TABLE project_cvparam
ADD CONSTRAINT project_param_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE project_files
(
  file_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10) ,
  file_type VARCHAR2 (128)  NOT NULL ,
  file_size NUMBER (15)  NOT NULL ,
  file_name VARCHAR2 (500)  NOT NULL ,
  file_path VARCHAR2 (500) ,
  file_source VARCHAR2 (128)  NOT NULL
)
;


CREATE INDEX project_files_project_IDX ON project_files
(
  project_fk ASC
)
;
CREATE INDEX project_files_assay_IDX ON project_files
(
  assay_fk ASC
)
;

ALTER TABLE project_files
ADD CONSTRAINT project_file_PK PRIMARY KEY ( file_pk ) ;



CREATE TABLE project_ptm
(
  project_ptm_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  value VARCHAR2 (255)
)
;


CREATE INDEX project_ptm_cvparam_IDX ON project_ptm
(
  cv_param_fk ASC
)
;
CREATE INDEX project_ptm_project_IDX ON project_ptm
(
  project_fk ASC
)
;

ALTER TABLE project_ptm
ADD CONSTRAINT project_ptm_PK PRIMARY KEY ( project_ptm_pk ) ;



CREATE TABLE project_tag
(
  project_tag_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  tag VARCHAR2 (500)  NOT NULL
)
;



ALTER TABLE project_tag
ADD CONSTRAINT project_tag_pk PRIMARY KEY ( project_tag_pk ) ;



CREATE TABLE project_userparam
(
  param_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  param_type VARCHAR2 (64)  NOT NULL ,
  param_name VARCHAR2 (2000)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX project_userparam_project_IDX ON project_userparam
(
  project_fk ASC
)
;
CREATE INDEX project_userparam_type_IDX ON project_userparam
(
  param_type ASC
)
;

ALTER TABLE project_userparam
ADD CONSTRAINT project_userparam_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE project_users
(
  project_fk NUMBER (10)  NOT NULL ,
  user_fk NUMBER (10)  NOT NULL
)
;



ALTER TABLE project_users
ADD CONSTRAINT project_users_pk PRIMARY KEY ( project_fk, user_fk ) ;



CREATE TABLE reference
(
  reference_pk NUMBER (10)  NOT NULL ,
  project_fk NUMBER (10)  NOT NULL ,
  pubmed_id NUMBER (15) ,
  doi VARCHAR2 (128) ,
  reference_line VARCHAR2 (2000)
)
;


CREATE INDEX reference_project_IDX ON reference
(
  project_fk ASC
)
;

ALTER TABLE reference
ADD CONSTRAINT reference_pk PRIMARY KEY ( reference_pk ) ;



CREATE TABLE software
(
  software_pk NUMBER (10)  NOT NULL ,
  assay_fk NUMBER (10)  NOT NULL ,
  name VARCHAR2 (255)  NOT NULL ,
  order_index NUMBER (10) ,
  version VARCHAR2 (255) ,
  customization VARCHAR2 (255)
)
;


CREATE INDEX software__assay_IDX ON software
(
  assay_fk ASC
)
;

ALTER TABLE software
ADD CONSTRAINT software_PK PRIMARY KEY ( software_pk ) ;



CREATE TABLE software_cvparam
(
  param_pk NUMBER (10)  NOT NULL ,
  software_fk NUMBER (10)  NOT NULL ,
  cv_param_fk NUMBER (10)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX sw_cvparam_software_IDX ON software_cvparam
(
  software_fk ASC
)
;
CREATE INDEX sw_cvparam_cvparam_IDX ON software_cvparam
(
  cv_param_fk ASC
)
;

ALTER TABLE software_cvparam
ADD CONSTRAINT sw_cvparam_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE software_userparam
(
  param_pk NUMBER (10)  NOT NULL ,
  software_fk NUMBER (10)  NOT NULL ,
  param_name VARCHAR2 (2000)  NOT NULL ,
  value CLOB
)
;


CREATE INDEX sw_uparam_software_IDX ON software_userparam
(
  software_fk ASC
)
;

ALTER TABLE software_userparam
ADD CONSTRAINT sw_uparam_PK PRIMARY KEY ( param_pk ) ;



CREATE TABLE pride_users
(
  user_pk NUMBER (10)  NOT NULL ,
  email VARCHAR2 (500)  NOT NULL ,
  password VARCHAR2 (255)  NOT NULL ,
  title VARCHAR2 (16)  NOT NULL ,
  first_name VARCHAR2 (255)  NOT NULL ,
  last_name VARCHAR2 (255)  NOT NULL ,
  affiliation VARCHAR2 (500)  NOT NULL ,
  creation_date DATE  NOT NULL ,
  update_date DATE  NOT NULL ,
  country VARCHAR2 (100),
  orcid VARCHAR2 (50),
)
;



ALTER TABLE pride_users
ADD CONSTRAINT user_pk PRIMARY KEY ( user_pk ) ;


ALTER TABLE pride_users
ADD CONSTRAINT unique_email UNIQUE ( email ) ;




ALTER TABLE assay_cvparam
ADD CONSTRAINT assay_cvparam_assay_FK FOREIGN KEY
  (
    assay_fk
  )
REFERENCES assay
  (
    assay_pk
  )
ON DELETE CASCADE
;


ALTER TABLE assay_cvparam
ADD CONSTRAINT assay_prm_cv_prm_fk FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE assay
ADD CONSTRAINT assay_proj_fk FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE assay_ptm
ADD CONSTRAINT assay_ptm_assay_FK FOREIGN KEY
  (
    assay_fk
  )
REFERENCES assay
  (
    assay_pk
  )
ON DELETE CASCADE
;


ALTER TABLE assay_ptm
ADD CONSTRAINT assay_ptm_cv_prm_fk FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE assay_userparam
ADD CONSTRAINT assay_userparam_assay_FK FOREIGN KEY
  (
    assay_fk
  )
REFERENCES assay
  (
    assay_pk
  )
ON DELETE CASCADE
;


ALTER TABLE authorities
ADD CONSTRAINT auth_user_FK FOREIGN KEY
  (
    user_fk
  )
REFERENCES pride_users
  (
    user_pk
  )
ON DELETE CASCADE
;


ALTER TABLE contact
ADD CONSTRAINT contact_assay_FK FOREIGN KEY
  (
    assay_fk
  )
REFERENCES assay
  (
    assay_pk
  )
ON DELETE CASCADE
;


ALTER TABLE instrument
ADD CONSTRAINT cv_param_fk FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE instrument_component_cvparam
ADD CONSTRAINT ic_cvparam_cv_param_FK FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE instrument_component_cvparam
ADD CONSTRAINT ic_cvparam_instr_FK FOREIGN KEY
  (
    instrument_component_fk
  )
REFERENCES instrument_component
  (
    instrument_component_pk
  )
ON DELETE CASCADE
;


ALTER TABLE instrument_component
ADD CONSTRAINT instr_comp_instrument_FK FOREIGN KEY
  (
    instrument_fk
  )
REFERENCES instrument
  (
    instrument_pk
  )
ON DELETE CASCADE
;


ALTER TABLE instrument_component_userparam
ADD CONSTRAINT instr_comp_uparam_instr_FK FOREIGN KEY
  (
    instrument_component_fk
  )
REFERENCES instrument_component
  (
    instrument_component_pk
  )
ON DELETE CASCADE
;


ALTER TABLE instrument
ADD CONSTRAINT instrument_assay_fk FOREIGN KEY
  (
    assay_fk
  )
REFERENCES assay
  (
    assay_pk
  )
ON DELETE CASCADE
;

ALTER TABLE lab_head
ADD CONSTRAINT lab_head_project_FK FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project_files
ADD CONSTRAINT proj_file_prj_fk FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project_ptm
ADD CONSTRAINT proj_ptm_cv_prm_fk FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE project_ptm
ADD CONSTRAINT proj_ptm_proj_fk FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project
ADD CONSTRAINT project_USERS_FK FOREIGN KEY
  (
    submitter_fk
  )
REFERENCES pride_users
  (
    user_pk
  )
;


ALTER TABLE project_cvparam
ADD CONSTRAINT project_cvparam_project_FK FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project_cvparam
ADD CONSTRAINT project_param_cv_param_FK FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE project_tag
ADD CONSTRAINT project_tag_project_FK FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project_userparam
ADD CONSTRAINT project_userparam_project_FK FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project_users
ADD CONSTRAINT project_users_project_FK FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE project_users
ADD CONSTRAINT project_users_users_FK FOREIGN KEY
  (
    user_fk
  )
REFERENCES pride_users
  (
    user_pk
  )
;


ALTER TABLE reference
ADD CONSTRAINT reference_project_FK FOREIGN KEY
  (
    project_fk
  )
REFERENCES project
  (
    project_pk
  )
ON DELETE CASCADE
;


ALTER TABLE software_cvparam
ADD CONSTRAINT soft_prm_cv_prm_fk FOREIGN KEY
  (
    cv_param_fk
  )
REFERENCES cv_param
  (
    cv_param_pk
  )
;


ALTER TABLE software
ADD CONSTRAINT software_assay_FK FOREIGN KEY
  (
    assay_fk
  )
REFERENCES assay
  (
    assay_pk
  )
ON DELETE CASCADE
;


ALTER TABLE software_cvparam
ADD CONSTRAINT software_param_software_FK FOREIGN KEY
  (
    software_fk
  )
REFERENCES software
  (
    software_pk
  )
ON DELETE CASCADE
;


ALTER TABLE software_userparam
ADD CONSTRAINT sw_uparam_software_FK FOREIGN KEY
  (
    software_fk
  )
REFERENCES software
  (
    software_pk
  )
ON DELETE CASCADE
;



-- Oracle SQL Developer Data Modeler Summary Report:
--
-- CREATE TABLE                            24
-- CREATE INDEX                            31
-- ALTER TABLE                             58
-- CREATE VIEW                              0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE STRUCTURED TYPE                   0
-- CREATE COLLECTION TYPE                   0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
