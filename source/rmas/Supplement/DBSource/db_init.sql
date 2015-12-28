/** added by dongbz 2014-11-27 start */
INSERT INTO sm_dict_type (type_id, type_key, type_name, modifiable, show_which, validity)
VALUES (100, 'MAINTAIN_CODE', '维修代码', 'YES', 'CODE_NAME', 'VALID');
INSERT INTO sm_dict_type (type_id, type_key, type_name, modifiable, show_which, validity)
VALUES (200, 'CUSTOMER_FAULT_DESC', '客户故障描述', 'YES', 'CODE_NAME', 'VALID');
INSERT INTO sm_dict_type (type_id, type_key, type_name, modifiable, show_which, validity)
VALUES (300, 'TAT_LEVEL', 'TAT等级', 'YES', 'CODE_KEY', 'VALID');
INSERT INTO sm_dict_type (type_id, type_key, type_name, modifiable, show_which, validity)
VALUES (400, 'CID_TYPE', 'CID类型', 'YES', 'CODE_NAME', 'VALID');
INSERT INTO sm_dict_type (type_id, type_key, type_name, modifiable, show_which, validity)
VALUES (500, 'OUTLET', 'OUTLET', 'YES', 'CODE_NAME', 'VALID');
INSERT INTO sm_dict_type (type_id, type_key, type_name, modifiable, show_which, validity)
VALUES (600, 'PRODUCT_TYPE', '产品类型', 'YES', 'CODE_NAME', 'VALID');

INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('N01', 'NTF', 'YES', 10, 100, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('N05', 'Test OK (Hot BGA or MB)', 'YES', 20, 100, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('R01', 'Clear CMOS', 'YES', 30, 100, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('R02', 'Re-solder', 'YES', 40, 100, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('R03', 'Update BIOS', 'YES', 50, 100, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('R05', 'HOT BGA', 'YES', 60, 100, NULL, now(), NULL, now(), 'VALID');

INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('TAT01', '3', NULL, 10, 300, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('TAT02', '6', NULL, 20, 300, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('TAT03', '9', NULL, 30, 300, NULL, now(), NULL, now(), 'VALID');

INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('HM', 'HM', 'YES', 10, 600, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('NM', 'NM', 'NO', 20, 600, NULL, now(), NULL, now(), 'VALID');
INSERT INTO sm_dict_code (code_key, code_name, code_desc, code_order, type_id, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES ('DM', 'DM', 'NO', 30, 600, NULL, now(), NULL, now(), 'VALID');
/** added by dongbz 2014-11-27 end */

/** added by dongbz 2014-12-08 start */
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (100, '订单管理', NULL, NULL, 10, 'NO', NULL, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (110, '创建订单', '/zul/order/order_rma_create.zul', NULL, 10, 'YES', 100, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (120, '编辑订单', '/zul/order/order_query.zul', NULL, 20, 'YES', 100, 'VALID');

INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (200, '生产管理', NULL, NULL, 20, 'NO', NULL, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (210, 'KEYIN', '/zul/produce/keyin_query.zul', NULL, 10, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (220, 'MIDH', '/zul/produce/midh_query.zul', NULL, 20, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (230, 'FLASH', '/zul/produce/flash_query.zul', NULL, 30, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (235, 'L1KEYIN', '/zul/produce/l1keyin_query.zul', NULL, 35, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (240, 'REPARING', '/zul/produce/repair_query.zul', NULL, 40, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (250, 'QC', '/zul/produce/qc_query.zul', NULL, 50, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (260, 'KEYOUT', '/zul/produce/keyout_query.zul', NULL, 60, 'YES', 200, 'INVALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (270, 'DO', '/zul/produce/do_query.zul', NULL, 70, 'YES', 200, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (280, '综合查询', '/zul/produce/produce_query.zul', NULL, 80, 'YES', 200, 'VALID');

INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (300, '报表管理', NULL, NULL, 30, 'NO', NULL, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (310, '订单跟踪', '', NULL, 10, 'YES', 300, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (320, '出货单', '', NULL, 20, 'YES', 300, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (330, '收货单', '', NULL, 30, 'YES', 300, 'VALID');

INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (400, '系统管理', NULL, NULL, 40, 'NO', NULL, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (410, '产品信息', '/zul/system/product_query.zul', NULL, 10, 'YES', 400, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (420, '客户资料', '/zul/system/customer_query.zul', NULL, 20, 'YES', 400, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (430, '配置管理', '/zul/system/config_query.zul', NULL, 30, 'YES', 400, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (440, '用户管理', '/zul/system/user_query.zul', NULL, 40, 'YES', 400, 'VALID');
INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (450, '角色管理', '/zul/system/role_query.zul', NULL, 50, 'YES', 400, 'VALID');

INSERT INTO sm_user (user_no, user_name, password, validity)
VALUES ('admin', '系统管理员', '5f4dcc3b5aa765d61d8327deb882cf99', 'VALID');

INSERT INTO sm_user_role (user_id, role_id, create_user, create_time)
VALUES (1, 1, NULL, now());

INSERT INTO sm_role (role_id, role_name, role_desc, create_user, create_time, last_modifier, last_modify_time, validity)
VALUES (1, '系统管理员', NULL, NULL, NULL, NULL, NULL, 'VALID');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 110, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 120, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 210, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 220, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 230, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 235, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 240, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 250, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 270, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 280, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 310, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 320, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 340, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 410, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 420, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 430, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 440, 1, '2015-03-03 13:01:38');

INSERT INTO sm_role_menu (role_id, menu_id, create_user, create_time)
VALUES (1, 450, 1, '2015-03-03 13:01:38');
/** added by dongbz 2014-12-08 end */

/* added by dongbz at 2015-01-28 start */
UPDATE sm_menu SET menu_url = '/zul/statis/order_track.zul' WHERE menu_id = 310;

INSERT INTO sm_menu (menu_id, menu_name, menu_url, menu_desc, menu_order, leaf, parent_id, validity)
VALUES (340, '员工效益统计', '/zul/statis/employee_produce_statis.zul', NULL, 40, 'YES', 300, 'VALID');
/* added by dongbz at 2015-01-28 end */