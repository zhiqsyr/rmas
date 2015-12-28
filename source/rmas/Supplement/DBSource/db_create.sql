/** added by dongbz 2014-11-27 start */
create database rmas default charset utf8 COLLATE utf8_general_ci;

create table sm_user(
	user_id int(10) not null AUTO_INCREMENT comment'ID',
	user_no varchar(30) not null comment'员工工号',
	user_name varchar(50) not null comment'员工姓名',
	password varchar(32) not null comment'员工密码',
	phone varchar(30) comment'联系电话',
	email varchar(30) comment'邮箱',
	address varchar(100) comment'家庭住址',
	education varchar(30) comment'学历',
	dept_name varchar(30) comment'部门',
	post varchar(30) comment'职位',
	career_level varchar(30) comment'职业等级',
	create_user INT,
	create_time DATETIME,
	last_modifier INT,
	last_modify_time DATETIME,
	validity varchar(10),
	PRIMARY key (user_id)
);

-- 角色
CREATE TABLE sm_role
(
	role_id		INT AUTO_INCREMENT PRIMARY KEY,
	role_name	VARCHAR(50),	-- 角色名称
	role_desc	VARCHAR(200),	-- 角色描述
	create_user INT,
	create_time DATETIME,
	last_modifier		INT,
	last_modify_time	DATETIME,
	validity	VARCHAR(10) DEFAULT 'VALID'	-- VALID/INVALID
);


-- 菜单
CREATE TABLE sm_menu
(
	menu_id		INT PRIMARY KEY,
	menu_name	VARCHAR(50),	-- 菜单名称
	menu_url	VARCHAR(200),	-- 菜单路径
	menu_desc	VARCHAR(200),	-- 菜单描述
	menu_order	INT,			-- 排序
	leaf	 	VARCHAR(5),		-- 是否叶菜单：YES/NO
	parent_id	INT,
	validity	VARCHAR(10)	DEFAULT 'VALID'	-- VALID/INVALID
);

CREATE TABLE sm_user_role
(
	user_role_id INT AUTO_INCREMENT PRIMARY KEY,
	user_id INT,
	role_id INT,
	create_user INT,
	create_time DATETIME
);	   

CREATE TABLE sm_role_menu
(
	role_menu_id INT AUTO_INCREMENT PRIMARY KEY,
	role_id INT,
	menu_id INT,
	create_user INT,
	create_time DATETIME
);	

-- 字典类型
CREATE TABLE sm_dict_type
(
	type_id 		INT PRIMARY KEY,
	type_key		VARCHAR(50) UNIQUE,	-- 字典类型英文表示
	type_name		VARCHAR(50),	-- 字典类型名称
	modifiable		VARCHAR(5),		-- YES/NO
	show_which		VARCHAR(20),
	validity		VARCHAR(10) DEFAULT 'VALID'	-- VALID/INVALID
);

-- 字典类型编码
CREATE TABLE sm_dict_code
(	
	code_id		INT AUTO_INCREMENT PRIMARY KEY,
	code_key	VARCHAR(50),	-- 字典类型编码英文表示
	code_name	VARCHAR(50),	-- 字典类型编码名称
	code_desc	VARCHAR(100),	-- 描述
	code_order	INT,	-- 排序
	type_id		INT,
	create_user 		INT,
	create_time 		DATETIME,
	last_modifier		INT,
	last_modify_time	DATETIME,
	validity			VARCHAR(10)	DEFAULT 'VALID'	-- VALID/INVALID
);

-- 客户
create table sm_customer(
	customer_id int(10) AUTO_INCREMENT comment'ID',
	short_name varchar(30) unique not null comment'客户公司简称',
	full_name varchar(50) unique not null comment'客户公司名',
	contactor_name varchar(30) comment'联系人',
	mobilephone varchar(30) comment'联系电话',
	email varchar(30) comment'邮箱',
	fixphone varchar(30) comment'固定电话',
	create_user INT,
	create_time DATETIME,
	last_modifier INT,
	last_modify_time DATETIME,
	validity varchar(10),
	PRIMARY key (customer_id)
);

-- 产品
create table sm_product(
	product_id INT AUTO_INCREMENT PRIMARY KEY,
	pn varchar(100) comment'产品编号',
	brand varchar(50) not null comment'品牌',
	price double(10,2) comment'价格',
	product_type int comment'产品类型',
	model_no varchar(30) comment'型号',
	pcb_type varchar(30) comment'PCB类型',
	keep_days int comment'保修天数',
	create_user INT,
	create_time DATETIME,
	last_modifier INT,
	last_modify_time DATETIME,
	validity varchar(10)
);

-- 主板
create table sm_bom(
	bom_id int(20) not null AUTO_INCREMENT comment'ID',
	cno varchar(100) comment'企业编码',
	bom_desc varchar(200) comment'描述',
	sno varchar(30) comment'序号',
	item_cate varchar(30) comment'项目类别',
	category varchar(30) comment'类别',
	material_no varchar(50) comment'物料编号',
	material_name varchar(300) comment'物料名称',
	material_charact varchar(300) comment'物料特性',
	manufacturer varchar(100) comment'厂商',
	manufacturer_model varchar(100) comment'厂商型号',
	dosage varchar(30) comment'用量',
	ino varchar(1000) comment'位号',
	product_id 	INT,
	creater 		INT,
	create_time	DATETIME,
	PRIMARY key (bom_id)
);

create table t_order(
	order_id INT AUTO_INCREMENT PRIMARY KEY,	
	rma varchar(30) comment'订单编号',
	custrma varchar(50) comment'客户订单号',
	customer_id INT not null comment'客户公司',
	express_com varchar(100) comment'快递公司',
	express_no VARCHAR(50),
	boxes int(10) comment'箱数',
	remarks varchar(1000) comment'备注',
	tat_level INT comment'TAT等级',
	close_time datetime comment'客户要求出货时间',
	total_qty varchar(30) comment'总数',
	total_finished varchar(30) comment'录入数',
	total_remain varchar(30) comment'未录入数',
	keyin_status varchar(30) comment'订单录入状态',
	receive_status varchar(20),	-- 收货状态：待收货/已收货
	receiver INT comment'收货人',
	receive_time datetime comment'收货时间',
	last_modifier INT,
	last_modify_time DATETIME,
	validity varchar(10)
);

CREATE TABLE t_sn (
	sn_id 		INT AUTO_INCREMENT PRIMARY KEY,
	sn 			VARCHAR(100),
	sn_index 	INT,	-- 订单SN序号
	keep_status 		VARCHAR(20),	-- 保修状态
	twice_back_times 	INT,		-- 二反次数
	cid_type			INT,		-- CID类型
	customer_fault_desc	INT,	-- 客户故障描述
	outlet				INT,
	fail_code	VARCHAR(100),
	case_id		VARCHAR(100),
	remark		VARCHAR(1000),		
	mac_imei1	VARCHAR(100),
	mac_imei2	VARCHAR(100),
	product_id 	INT,
	order_id 	INT,
	  
	hard_level		INT,			-- 难度指数
	status			VARCHAR(30),	-- 状态
	final_result	VARCHAR(30),	-- 结果
	
	flasher		INT,
	flash_time	DATETIME,
	
	assigner	INT,			-- 分配人员
	assign_time	DATETIME,		-- 分配时间
	
	material_used	VARCHAR(10),	-- 用料情况：YES/NO
	
	repairer		INT,		-- 维修人员
	repair_code		INT,		-- 维修代码
	repair_remark	VARCHAR(500),
	repaired_time	DATETIME,	-- 维修结束时间
	
	qcer		INT,		-- QC人员
	qc_remark 	VARCHAR(500),
	qc_time		DATETIME,	-- QC时间
	
	keyouter	INT,		-- KEYOUT人员
	keyout_time	DATETIME,	-- KEYOUT时间
	
	do_id		INT,		-- 出货单ID
	doer		INT,		-- DO人员
	do_time		DATETIME,	-- DO时间
	
	stoper		INT,			-- 终止人员
	stop_time 	DATETIME,		-- 终止时间
	stop_reason	VARCHAR(500),	-- 终止原因
	
	create_user 		INT,
	create_time 		DATETIME,
	last_modifier 		INT,
	last_modify_time 	DATETIME,
	validity 			VARCHAR(10)
);

-- sn生产流程
CREATE TABLE t_sn_produce (
	produce_id INT AUTO_INCREMENT PRIMARY KEY,
	sn_id 		INT,
	producer 	INT, 
	produce_type	VARCHAR(30),	-- 生产类型
	result 			VARCHAR(30),	-- 结果
	result_remark	VARCHAR(500),	-- 结果备注
	repair_code	INT,	-- 维修代码
	start_time 	DATETIME,
	end_time 	DATETIME
);	

-- 维修用料
CREATE TABLE t_sn_repair_material (
	rm_id	INT AUTO_INCREMENT PRIMARY KEY,
	number			INT,	-- 数量
	bom_id			INT,
	sn_id	 		INT,
	create_user 		INT,
	create_time 		DATETIME,
	last_modifier 		INT,
	last_modify_time 	DATETIME,
	validity 			VARCHAR(10)
);

-- 出货
CREATE TABLE t_order_do (
	do_id 		INT AUTO_INCREMENT PRIMARY KEY,
	do_rma 		VARCHAR(30),	-- 出货单号
	order_id	INT,
	customer_id INT,
	excel_path 	VARCHAR(500),	-- 出货单Excel文件保存路径
	word_path 	VARCHAR(500),	-- 出货单Word文件保存路径
	dor			INT,
	do_time		DATETIME
);
/** added by dongbz 2014-11-27 end */

/* added by dongbz at 2015-02-28 start */

CREATE TABLE EXCEL_COLUMN_CONFIG
	(
	ID                      VARCHAR (46) NOT NULL,
	REPORT_ID               VARCHAR (46) NOT NULL,
	ALIAS_NAME              VARCHAR (100),
	COLUMN_NAME             VARCHAR (46),
	PROCESSOR_AND_VALIDATOR VARCHAR (500),
	NEED_SAVE               CHAR (1),
	NEED_SHOW               CHAR (1),
	ORDINAL                 INT,
	DATA_TYPE               VARCHAR (32),
	COLUMN_CONVERTER        VARCHAR (100),
	DATA_POSITION           VARCHAR (10),
	script_validator        VARCHAR (10),
	value_mapper            VARCHAR (255),
	serial                  VARCHAR (10),
	PRIMARY KEY (ID)
	);

CREATE TABLE EXCEL_FILE_SAVE
	(
	ID             VARCHAR (46) NOT NULL,
	USER_ID        VARCHAR (20) NOT NULL,
	FILE_NAME      VARCHAR (200) NOT NULL,
	FILE_SAVE_PATH VARCHAR (200),
	UPLOAD_TIME    DATETIME,
	REPORT_TYPE    VARCHAR (20),
	IS_SUCCESS     CHAR (1) DEFAULT '0',
	PRIMARY KEY (ID)
	);


CREATE TABLE EXCEL_REPORT_CONFIG
	(
	ID               VARCHAR (46) NOT NULL,
	REPORT_NAME      VARCHAR (100) NOT NULL,
	TABLE_NAME       VARCHAR (46) NOT NULL,
	START_ROW        INT,
	SERIAL_NUM_COL   VARCHAR (10),
	TEMPLATE_VERSION VARCHAR (32),
	TEMPLATE_PATH    VARCHAR (200),
	REPORT_TYPE      VARCHAR (32),
	SHEET_NAME       VARCHAR (46),
	END_ROW          INT,
	START_COL        INT,
	END_COL          INT,
	PRIMARY KEY (ID)
	);
/* added by dongbz at 2015-02-28 end */	