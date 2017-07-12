-- password is johnsmith
INSERT INTO pride_users VALUES (11111,'john.smith@dummy.ebi.com', '$2a$10$Zf1AEQW1Blw7e4Dt.y3Bne5flAXs.R69AbBdCqcv0h8Cv7Y6Ycatq','Mr', 'john', 'smith', 'EBI', TO_DATE('2010-04-29', 'YYYY-MM-DD'), TO_DATE('2010-04-30', 'YYYY-MM-DD'), 'UK');

-- password is alicewonderland
INSERT INTO pride_users VALUES (11112,'alice.wonderland@dummy.ebi.com', '$2a$10$uWjpexapABY/9bzr8OlFuO78vixvN..bsdHVscgIl/AxPEm18GVEy', 'Miss', 'alice', 'wonderland', 'wonderland', TO_DATE('2010-04-29', 'YYYY-MM-DD'), TO_DATE('2010-04-30', 'YYYY-MM-DD'), 'USA');

INSERT INTO authorities VALUES (1111, 11111, 'SUBMITTER');
INSERT INTO authorities VALUES (2111, 11112, 'REVIEWER');
INSERT INTO authorities VALUES (3111, 11112, 'SUBMITTER');

INSERT INTO project (project_pk, submitter_fk, accession, doi, title, num_assays, is_public, submission_type, submission_date, update_date, project_description, data_proc_protocol_descr, sample_proc_protocol_descr, other_omics_link, keywords, reanalysis, publication_date, changed)
VALUES (11111, 11111, 'PXD00001', 'doi.doi', 'Project title', 3, 1, 'COMPLETE', TO_DATE('2010-05-22', 'YYYY-MM-DD'), TO_DATE('2010-07-15', 'YYYY-MM-DD'), 'Project description', 'Data processing protocol', 'Sample processing protocol', 'Other.omics.link', 'some,keywords', 'reanalysis', TO_DATE('2010-10-21', 'YYYY-MM-DD'), 0);

INSERT INTO assay (assay_pk, project_fk, accession, title, short_label, protein_count, peptide_count, unique_peptide_count, identified_spectrum_count, total_spectrum_count, ms2_annotation, chromatogram, experimental_factor)
VALUES (44444, 11111, '000001', 'Experiment title', 'Short label', 1, 1, 1, 1, 1, 1, 1, 'Experimental factor');

INSERT INTO assay (assay_pk, project_fk, accession, title, short_label, protein_count, peptide_count, unique_peptide_count, identified_spectrum_count, total_spectrum_count, ms2_annotation, chromatogram, experimental_factor)
VALUES (444440, 11111, '000002', 'Experiment 2 title', 'Short label 2', 1, 1, 1, 1, 1, 0, 0, 'Experimental 2 factor');

INSERT INTO contact (contact_pk, assay_fk, title, first_name, last_name, affiliation, email)
VALUES (33333, 44444, 'Mr', 'John', 'Smith', 'EBI', 'john.smith@dummy.ebi.com');

INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (66666, 'Project Sample Param Label', 'Project Sample', 'Project Sample Name');

INSERT INTO project_cvparam (param_pk, project_fk, cv_param_fk, param_type, value)
VALUES (77777, 11111, 66666, 'SAMPLE', 'Project Sample Value');

INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (1212121212, 'Exp Type CV Param Label', 'Exp Type CV Param Accession', 'Exp Type CV Param Name');

INSERT INTO project_cvparam (param_pk, project_fk, cv_param_fk, value, param_type)
VALUES (1111111111, 11111, 1212121212, 'Experiment Type Value', 'EXPERIMENT_TYPE');

INSERT INTO project_cvparam (param_pk, project_fk, cv_param_fk, value, param_type)
VALUES (15151515, 11111, 1212121212, 'Quantification Method Value', 'QUANTIFICATION_METHOD');

INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (1313131313, 'Group Project CV Param Label', 'Group Project CV Param Accession', 'Group Project CV Param Name');

INSERT INTO project_cvparam (param_pk, project_fk, cv_param_fk, value, param_type)
VALUES (1414141414, 11111, 1313131313, 'Group Project Value', 'PROJECT');

INSERT INTO cv_param VALUES(50005, 'MOD', 'MOD:00091', 'L-arginine amide');
INSERT INTO cv_param VALUES(50006, 'MOD', 'MOD:00227', 'omega-N-phospho-L-arginine');

-- insert project modification
INSERT INTO project_ptm (project_ptm_pk, project_fk, cv_param_fk)
VALUES (111, 11111, 50005);
INSERT INTO project_ptm (project_ptm_pk, project_fk, cv_param_fk)
VALUES (222, 11111, 50006);


INSERT INTO assay_ptm (assay_ptm_pk, assay_fk, cv_param_fk)
VALUES (1010101010, 44444, 50005);

INSERT INTO assay_cvparam (param_pk, assay_fk, cv_param_fk, value, param_type)
VALUES (1515151515, 44444, 1212121212, 'Quantification Method Value', 'QUANTIFICATION_METHOD');

INSERT INTO assay_cvparam (param_pk, assay_fk, cv_param_fk, value, param_type)
VALUES (1717171717, 44444, 1212121212, 'Sample Value', 'SAMPLE');

INSERT INTO assay_cvparam (param_pk, assay_fk, cv_param_fk, value, param_type)
VALUES (1818181818, 44444, 1212121212, 'Assay Group Param Value', 'ASSAY');

INSERT INTO reference (reference_pk, project_fk, pubmed_id, reference_line, doi)
VALUES (88888, 11111, 12345678, 'I am a reference line', 'references.doi');

INSERT INTO project_tag (project_tag_pk, project_fk, tag)
  VALUES (89898, 11111, 'PRIDE');

INSERT INTO lab_head (lab_head_pk, project_fk, title, first_name, last_name, affiliation, email)
  VALUES (91919, 11111, 'Mr', 'John', 'Smith', 'EBI', 'john.smith@dummy.ebi.com');

INSERT INTO project_files (file_pk, project_fk, assay_fk, file_type, file_size, file_name, file_path, file_source)
VALUES (99999, 11111, 44444, 'PEAK', 1024, 'file.name', '/path/to/file/file.name', 'SUBMITTED');

INSERT INTO project_users(user_fk, project_fk)
VALUES (11111, 11111);

INSERT INTO project_users(user_fk, project_fk)
VALUES (11112, 11111);

INSERT INTO software (software_pk, assay_fk, name,order_index, version, customization)
VALUES (1111,44444,'Mascot',1,'1.2.3','customizations');

INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (12, 'Software CV Param Label', 'Software CV Param Accession', 'Software CV Param Name');

INSERT INTO software_cvparam (param_pk, software_fk, cv_param_fk, value )
VALUES (1111, 1111, 12, 'software value');

INSERT INTO software_userparam (param_pk, software_fk, param_name, value )
VALUES (2222, 1111, 'software param name', 'software value');

INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (13, 'MS', 'MS:1000079', 'FT_ICR');

INSERT INTO instrument (instrument_pk, assay_fk, cv_param_fk, value)
VALUES (1111, 44444, 13, 'icr');

INSERT INTO instrument_component (instrument_component_pk, instrument_fk, order_index, instrument_component_type)
VALUES (1111, 1111, 1, 'SOURCE');
INSERT INTO instrument_component (instrument_component_pk, instrument_fk, order_index, instrument_component_type)
VALUES (1112, 1111, 2, 'ANALYZER');
INSERT INTO instrument_component (instrument_component_pk, instrument_fk, order_index, instrument_component_type)
VALUES (1113, 1111, 3, 'DETECTOR');


INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (14, 'MS', 'source', 'ESI');
INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (15, 'MS', 'analyzer1', 'TOF');
INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (151, 'MS', 'analyzer2', 'LTQ');
INSERT INTO cv_param (cv_param_pk, cv_label, accession, name)
VALUES (16, 'MS', 'detector', 'plate');

-- source
INSERT INTO instrument_component_cvparam (param_pk, instrument_component_fk, cv_param_fk, value)
VALUES (1111,1111,14,null);
-- analyser 1
INSERT INTO instrument_component_cvparam (param_pk, instrument_component_fk, cv_param_fk, value)
VALUES (1112,1112,15,null);
--analyser 2
INSERT INTO instrument_component_cvparam (param_pk, instrument_component_fk, cv_param_fk, value)
VALUES (1113,1112,151,null);
-- detector
INSERT INTO instrument_component_cvparam (param_pk, instrument_component_fk, cv_param_fk, value)
VALUES (1114,1113,16,null);

INSERT INTO project_cvparam (param_pk, project_fk, cv_param_fk, value, param_type)
VALUES (1513551515, 11111, 13, 'instrument_name', 'INSTRUMENT');

