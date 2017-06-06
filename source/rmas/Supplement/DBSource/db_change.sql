/** added by dongbz at 2014-12-23 start */
UPDATE sm_dict_type SET show_which = 'CODE_NAME' WHERE type_id = 100;
UPDATE sm_dict_type SET show_which = 'CODE_NAME' WHERE type_id = 200;
UPDATE sm_dict_type SET show_which = 'CODE_NAME' WHERE type_id = 400;
UPDATE sm_dict_type SET show_which = 'CODE_NAME' WHERE type_id = 500;
UPDATE sm_dict_type SET show_which = 'CODE_NAME' WHERE type_id = 600;

UPDATE sm_menu SET menu_order = 30 WHERE menu_id = 220;
UPDATE sm_menu SET menu_order = 20 WHERE menu_id = 230;

-- ALTER TABLE t_sn ADD sn_index INT;	-- 订单SN序号
/** added by dongbz at 2014-12-23 end */

/* 以上已经执行 */

/* added by dongbz at 2015-01-19 start */
delete from sm_menu where menu_id = 220;

INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (220, 'MIDH', '/zul/produce/midh_query.zul', NULL, 20, 'YES', 200, 'VALID');
-- INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
-- VALUES (235, 'L1KEYIN', '/zul/produce/l1keyin_query.zul', NULL, 35, 'YES', 200, 'VALID');
-- INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
-- VALUES (280, '综合查询', '/zul/produce/produce_query.zul', NULL, 80, 'YES', 200, 'VALID');

update t_sn_produce t set t.produce_type = 'L1KYEIN' where t.produce_type = 'ASSIGN';
/* added by dongbz at 2015-01-19 end */

/* added by dongbz at 2015-02-26 start */
UPDATE sm_menu SET validity = 'INVALID' WHERE menu_id IN (320, 330);

ALTER TABLE sm_customer ADD COLUMN addr VARCHAR(200);

INSERT INTO EXCEL_REPORT_CONFIG (ID, REPORT_NAME, TABLE_NAME, START_ROW, SERIAL_NUM_COL, TEMPLATE_VERSION, TEMPLATE_PATH, REPORT_TYPE, SHEET_NAME, END_ROW, START_COL, END_COL)
VALUES ('18F436DB-FB60-4E02-8C51-30B630944D73', 'BOM LIST', 'sm_bom', 2, NULL, NULL, NULL, 'ZZHXX', 'BOM LIST', -1, 1, 12);


INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '企业编码', 'cno', NULL, '1', NULL, 2, NULL, NULL, '1', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '描述', 'bom_desc', NULL, '1', NULL, 3, NULL, NULL, '2', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '序号', 'sno', NULL, '1', NULL, 4, NULL, NULL, '3', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '项目类别', 'item_cate', NULL, '1', NULL, 5, NULL, NULL, '4', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '类别', 'category', NULL, '1', NULL, 6, NULL, NULL, '5', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '物料编号', 'material_no', NULL, '1', NULL, 7, NULL, NULL, '6', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '物料名称', 'material_name', NULL, '1', NULL, 8, NULL, NULL, '7', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '物料特性', 'material_charact', NULL, '1', NULL, 9, NULL, NULL, '8', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '厂商型号', 'manufacturer_model', NULL, '1', NULL, 10, NULL, NULL, '9', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '厂商', 'manufacturer', NULL, '1', NULL, 11, NULL, NULL, '10', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '用量', 'dosage', NULL, '1', NULL, 12, NULL, NULL, '11', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', '位号', 'ino', NULL, '1', NULL, 13, NULL, NULL, '12', NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', NULL, 'creater', NULL, '1', NULL, 22, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', NULL, 'create_time', NULL, '1', NULL, 23, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO EXCEL_COLUMN_CONFIG (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES (UUID(), '18F436DB-FB60-4E02-8C51-30B630944D73', NULL, 'product_id', NULL, '1', NULL, 24, NULL, NULL, NULL, NULL, NULL, NULL);

-- ALTER TABLE sm_bom modify COLUMN ino VARCHAR(1000);

UPDATE sm_menu SET menu_url = '/zul/statis/order_do_query.zul', validity = 'VALID' WHERE menu_id = 320;
/* added by dongbz at 2015-02-26 end */

/* added by dongbz at 2015-03-22 start */
UPDATE sm_menu SET menu_name = '收货编辑' WHERE menu_id = 120;

update sm_menu set menu_name = 'L1keyin Midh' where menu_id = 220;
update sm_menu set menu_name = 'L1keyin Other' where menu_id = 235;

ALTER TABLE t_sn ADD mac_imei1_n VARCHAR(100);
ALTER TABLE t_sn ADD mac_imei2_n VARCHAR(100);
/* added by dongbz at 2015-03-22 end */

/* added by dongbz at 2015-05-21 start */
UPDATE sm_menu SET menu_name = 'RMA Management' WHERE menu_id = 100;	-- 订单管理
UPDATE sm_menu SET menu_name = 'Create RMA' WHERE menu_id = 110;	-- 创建订单
UPDATE sm_menu SET menu_name = 'Receive' WHERE menu_id = 120;	-- 编辑订单

UPDATE sm_menu SET menu_name = 'Produce Management' WHERE menu_id = 200;	-- 生产管理
UPDATE sm_menu SET menu_name = 'Comprehensive Search' WHERE menu_id = 280;	-- 综合查询

UPDATE sm_menu SET menu_name = 'Report Forms Management' WHERE menu_id = 300;	-- '报表管理'
UPDATE sm_menu SET menu_name = 'RMA Track' WHERE menu_id = 310;	-- '订单跟踪'
UPDATE sm_menu SET menu_name = 'DO Forms' WHERE menu_id = 320;	-- '出货单'
UPDATE sm_menu SET menu_name = 'Receive Forms' WHERE menu_id = 330;	-- '收货单'
UPDATE sm_menu SET menu_name = 'Employee Benefit' WHERE menu_id = 340;	-- '员工效益统计'

UPDATE sm_menu SET menu_name = 'System Management' WHERE menu_id = 400;	-- 系统管理
UPDATE sm_menu SET menu_name = 'Product Information' WHERE menu_id = 410;	-- 产品信息
UPDATE sm_menu SET menu_name = 'Customer Information' WHERE menu_id = 420;	-- 客户资料
UPDATE sm_menu SET menu_name = 'Config Management' WHERE menu_id = 430;	-- 配置管理
UPDATE sm_menu SET menu_name = 'User Management' WHERE menu_id = 440;	-- 用户管理
UPDATE sm_menu SET menu_name = 'Role Management' WHERE menu_id = 450;	-- 角色管理
/* added by dongbz at 2015-05-21 end */

/* added by dongbz at 2015-05-22 start */
ALTER TABLE t_sn ADD flash_result VARCHAR(30);
ALTER TABLE t_sn ADD repair_result VARCHAR(30);
ALTER TABLE t_sn ADD qc_result VARCHAR(30);
/* added by dongbz at 2015-05-22 end */

-- 以上已经执行 --

/* added by dongbz at 2015-06-29 start */
ALTER TABLE t_order ADD received_pdf_path VARCHAR(200);

UPDATE sm_menu SET menu_name = 'Received Forms', menu_url = '/zul/statis/order_received_query.zul', validity = 'VALID' WHERE menu_id = 330;
/* added by dongbz at 2015-06-29 end */

/* added by dongbz at 2015-07-02 start */
UPDATE excel_column_config SET COLUMN_NAME = 'manufacturer' WHERE ID = '36f2bbc7-d095-11e4-9836-00e066bfc51b';
UPDATE excel_column_config SET COLUMN_NAME = 'manufacturer_model' WHERE ID = '36f4d3a7-d095-11e4-9836-00e066bfc51b';

UPDATE excel_report_config SET SERIAL_NUM_COL = 7 WHERE ID = '18F436DB-FB60-4E02-8C51-30B630944D73';
/* added by dongbz at 2015-07-02 end */

-- 以上已经执行 --

/* added by dongbz at 2015-07-08 start */
ALTER TABLE sm_customer ADD ship_via VARCHAR(200);
ALTER TABLE sm_customer ADD attn VARCHAR(200);
/* added by dongbz at 2015-07-08 end */

-- 以上已经执行 --

/* added by dongbz at 2015-08-10 start */
UPDATE t_sn SET keep_status = 'NEW' 
WHERE keep_status = 'NO';

UPDATE t_sn SET keep_status = 'WITHIN'
WHERE keep_status = 'YES';
/* added by dongbz at 2015-08-10 end */

-- 以上已经执行 --

/* added by dongbz at 2015-08-29 start */
ALTER TABLE sm_bom ADD validity VARCHAR(10);
/* added by dongbz at 2015-08-29 end */

-- 以上已经执行 --

INSERT INTO rmas.excel_column_config (ID, REPORT_ID, ALIAS_NAME, COLUMN_NAME, PROCESSOR_AND_VALIDATOR, NEED_SAVE, NEED_SHOW, ORDINAL, DATA_TYPE, COLUMN_CONVERTER, DATA_POSITION, script_validator, value_mapper, serial)
VALUES ('1c01f433-f3cb-11e4-8z56-1078d2c191d1', '18F436DB-FB60-4E02-8C51-30B630944D73', NULL, 'validity', NULL, '1', NULL, 25, NULL, NULL, NULL, NULL, NULL, NULL);

DELETE FROM sm_bom WHERE validity IS NULL;
ALTER TABLE sm_bom modify ino VARCHAR(1000);

-- 以上已经执行 --

ALTER TABLE t_sn ADD oqc_result VARCHAR(10);
ALTER TABLE t_order ADD do_status VARCHAR(10);

UPDATE t_order SET do_status = 'ONLINE' 
WHERE EXISTS (SELECT NULL FROM t_sn s WHERE s.order_id = t_order.order_id AND s.status != 'DONE')
OR t_order.total_qty != (SELECT count(*) FROM t_sn s WHERE s.order_id = t_order.order_id);

UPDATE t_order SET do_status = 'DONE' 
WHERE NOT EXISTS (SELECT NULL FROM t_sn s WHERE s.order_id = t_order.order_id AND s.status != 'DONE')
AND t_order.total_qty = (SELECT count(*) FROM t_sn s WHERE s.order_id = t_order.order_id);

UPDATE t_order SET do_status = 'ONLINE' WHERE do_status IS NULL;

INSERT INTO rmas.sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (265, 'OQC', '/zul/produce/oqc_query.zul', NULL, 65, 'YES', 200, 'VALID');

UPDATE rmas.sm_menu
SET menu_id = 270
	, menu_name = 'DO'
	, menu_url = '/zul/produce/do_edit.zul'
	, menu_desc = NULL
	, menu_order = 70
	, leaf = 'YES'
	, parent_id = 200
	, validity = 'VALID'
WHERE menu_id = 270;

-- 以上已经执行 --

insert into rmas.sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
values (350, 'Employee Return Statis', '/zul/statis/employee_return_statis.zul', NULL, 50, 'YES', 300, 'VALID');

create index index_order_id on t_sn(order_id);
create index index_sn on t_sn(sn);
create index index_producer on t_sn_produce(producer);
create index index_sn_bom_id on t_sn_repair_material(sn_id, bom_id);

-- 以上已经执行 --

alter table t_sn add oqcer int after qc_result;
alter table t_sn add oqc_time datetime;

-- 以上已经执行 --