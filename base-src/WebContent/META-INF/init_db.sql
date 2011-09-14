/*==============================================================*/
/* Table: DICT_CATALOG                                          */
/*==============================================================*/
drop table if exists DICT_CATALOG;

create table DICT_CATALOG
(
   CATALOG_ID           char(32) not null comment '目录ID，定长5位数字构成(UUID)',
   ENTRY_TYPE           int not null default 1 comment '条目类型（0：目录/1：明细）',
   ENTRY_NAME           varchar(64) not null comment '条目名称',
   ENTRY_SHUTNAME       varchar(32) comment '条目缩写',
   ENTRY_SIRE           char(32) comment '上级条目（针对级联条目设置）',
   ENTRY_ORDER          int not null default 0 comment '字典明细排序标志',
   ENTRY_DESC           varchar(64) comment '条目描述',
   ENABLED              int(1) not null default 1 comment '启用标志 0：停用/1：启用 def=1',
   primary key (CATALOG_ID)
);

alter table DICT_CATALOG comment '字典目录表';


/*==============================================================*/
/* Table: AUTHORITY_INFO                                        */
/*==============================================================*/

drop table if exists AUTHORITY_INFO;

create table AUTHORITY_INFO
(
   AUTHOR_ID            char(32) not null comment '权限记录PK，UUID',
   AUTHOR_SIGN          varchar(32) not null comment '权限标识（英文标识）',
   ENABLED              int(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
   AUTHOR_DESC          varchar(64) comment '权限描述，用于页面显示',
   AUTHOR_NAME          varchar(32) not null comment '权限名称，中文名称',
   primary key (AUTHOR_ID)
);

alter table AUTHORITY_INFO comment '系统权限信息表';

/*==============================================================*/
/* Table: SCOURCE_INFO                                          */
/*==============================================================*/
drop table if exists SCOURCE_INFO;

create table SCOURCE_INFO
(
   SRC_ID               char(32) not null comment '资源记录主键 PK，UUID',
   AUTHOR_ID            char(32) comment '权限记录PK，对应该资源的访问权限',
   SRC_NAME             varchar(128) not null comment '资源名称，用于菜单显示',
   SRC_SORT             char(32) not null comment '资源分组（ 一级菜单显示）',
   SRC_TYPE             int(1) not null comment '资源类型（0:主菜单/1：子菜单）',
   SRC_URL              varchar(256) comment '资源访问路径',
   SRC_DESC             varchar(256) comment '资源描述',
   SRC_ORDER            int(2) not null default 0 comment '资源排序，对应菜单位置',
   ENABLED              int(1) not null default 1 comment '标识该资源是否启用 0：停用/1：启用；默认为 启用 1',
   STYLECSS             varchar(64) comment '自定义样式/支持CSS',
   primary key (SRC_ID)
);

/*==============================================================*/
/* Table: ROLE_INFO                                             */
/*==============================================================*/
drop table if exists ROLE_INFO;

create table ROLE_INFO
(
   ROLE_ID              char(32) not null comment '角色记录PK，UUID',
   ROLE_NAME            varchar(32) not null comment '角色名称，中文描述',
   ROLE_SIGN            varchar(32) not null comment '权限标识（英文标识）',
   ROLE_DESC            varchar(64) comment '权限描述，用于页面显示',
   ENABLED              int(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
   primary key (ROLE_ID)
);

alter table ROLE_INFO comment '系统角色信息表';

/*==============================================================*/
/* Table: ROLE_MAP_AUTHOR                                       */
/*==============================================================*/
drop table if exists ROLE_MAP_AUTHOR;

create table ROLE_MAP_AUTHOR
(
   MAPPING_ID           char(32) not null comment '映射主键，无实际意义',
   AUTHOR_ID            char(32) not null comment '权限记录PK，UUID',
   ROLE_ID              char(32) not null comment '角色记录PK，UUID',
   primary key (MAPPING_ID)
);

alter table ROLE_MAP_AUTHOR comment '角色权限映射表';

/*==============================================================*/
/* Table: DEPARTMENT_INFO                                       */
/*==============================================================*/
drop table if exists DEPARTMENT_INFO;

create table DEPARTMENT_INFO
(
   DEPT_ID              char(32) not null comment '部门ID（UUID）',
   NODE_DEEP            int(1) not null comment '节点深度，树节点深度（从0开始计数）',
   FATHER_ID            char(32) comment '上级节点ID，当为null时 为 跟节点',
   DEPT_NAME            varchar(64) not null comment '部门名称',
   DEPT_ORDER           int(2) not null default 0 comment '部门排序',
   ENABLED              numeric(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
   LEAFNODE             int(1) not null default 0 comment '是否叶子节点 0：是/1：否',
   NODE_XPATH           varchar(256) not null comment '节点路径,采用 - 分隔',
   primary key (DEPT_ID)
);

alter table DEPARTMENT_INFO comment '组织结构表';

/*==============================================================*/
/* Table: USER_BASIC                                            */
/*==============================================================*/
drop table if exists USER_BASIC;

create table USER_BASIC
(
   USER_ID              char(32) not null comment '该ID作用范围为整个系统 MD5(身份证)',
   DEPT_ID              char(32) comment '部门ID（UUID）',
   USER_LOGINID         varchar(64) not null comment '用户登录帐号',
   USER_SEXED           int(1) comment '用户性别: 0：女/1：男',
   USER_PASSWORD        char(32) not null comment '用户登录密码 MD5 加密',
   USER_NAME            varchar(32) not null comment '用户姓名',
   USER_IDENTITY        varchar(18) comment '用户证件号码(身份证)',
   ENABLED              int(1) not null default 1 comment '启用标志 0：停用/1：启用 def=启用',
   EXTENSION_NO         varchar(12) comment '分机号码',
   WORKING_PLACE        varchar(256) comment '办公地点',
   IP_ADDRESS           varchar(15) comment '分配给员工的IP地址',
   CMPY_EMAIL           varchar(64) comment '单位邮箱',
   WORK_EMAIL           varchar(64) comment '工作邮箱',
   USER_MOBILENO        varchar(18),
   POLITICS_STATUS      char(32) comment '政治面貌，来自数据字典',
   MARITAL_STATUS       int(1) default 0 comment '婚姻情况：0 未婚 / 1 已婚  def=0',
   BIRTHDAY             date comment '生日日期 （yyyy-MM-dd）',
   EMPLOYEE_NO          varchar(32) comment '工卡号码，单位给每个员工的的卡号',
   QUALIFICATIONS       char(32) comment '职称等级（见数据字典）',
   COMPANY_NAME         varchar(64) comment '公司名称',
   POST_TYPE            char(32) comment '公司部门：见数据字典 金融 软开 其他 等',
   EDUCATION_BG         char(32) comment '本人学历（来自数据字典）',
   SPECIALTY_KNOW       varchar(64) comment '所学专业',
   GRADUATE_SCHOOL      varchar(128) comment '毕业学校',
   DEGREE               char(32) comment '学位',
   WORKDATE        date comment '参加工作时间',
   TEAM_STATS           int(1) comment '当前团队状态(1:在场、0:离场)',
   primary key (USER_ID)
);

alter table USER_BASIC comment '用户基本信息，该表记录用户信息为私人信息';

/*==============================================================*/
/* Table: USER_ADDITION                                         */
/*==============================================================*/
drop table if exists USER_ADDITION;
create table USER_ADDITION
(
   USER_ID              char(32) not null comment '该ID作用范围为整个系统 MD5(身份证)',
   JOIN_WORK            text comment '参与过的项目',
   JOIN_TRAIN           text comment '参加过的培训',
   SPECIALITY           text comment '个人特长',
   SKILL_SPECIALITY     text comment '技术专长和业务专长',
   MANAGE_EMPIRICAL     text comment '从事过的技术及管理工作',
   CERTIFICATE          text comment '获得专业证书',
   primary key (USER_ID)
);

alter table USER_ADDITION comment '用户系统信息附加表,用户记录用户附加信息';

/*==============================================================*/
/* Table: USER_MAP_ROLE                                         */
/*==============================================================*/
drop table if exists USER_MAP_ROLE;
create table USER_MAP_ROLE
(
   MAPPING_ID           char(32) not null comment '映射主键',
   USER_ID              char(32) comment '该ID作用范围为整个系统 MD5(身份证)',
   ROLE_ID              char(32) comment '角色记录PK，UUID',
   primary key (MAPPING_ID)
);

alter table USER_MAP_ROLE comment '人员角色映射表';

/*==============================================================*/
/* Table: ACCESS_DATA_POWER                                     */
/*==============================================================*/
drop table if exists ACCESS_DATA_POWER;
create table ACCESS_DATA_POWER
(
   MAPPING_ID           char(32) not null comment '逻辑主键 无实际意义',
   USER_ID              char(32) comment '该ID作用范围为整个系统 MD5(身份证)',
   DEPT_ID              char(32) comment '部门ID（UUID）',
   primary key (MAPPING_ID)
);

alter table ACCESS_DATA_POWER comment '个人数据集权限';

/*==============================================================*/
/* Table: TEAM_TRANSFER_LOGS                                    */
/*==============================================================*/
drop table if exists TEAM_TRANSFER_LOGS;
create table TEAM_TRANSFER_LOGS
(
   TRANSFER_ID          char(32) not null comment '逻辑主键、无实际意义 MD5（UUID）',
   USER_ID              char(32) not null comment '该ID作用范围为整个系统',
   DEPT_ID              char(32) not null comment '部门ID（UUID）',
   JOIN_DATE            date not null comment '进场时间',
   LEAVE_DATE           date default NULL comment '退场时间',
   primary key (TRANSFER_ID)
);

alter table TEAM_TRANSFER_LOGS comment '人员近离场记录表（记录人员在项目团队的进场、离场记录流水）';

/*==============================================================*/
/* Table: PROJECT_INFO                                          */
/*==============================================================*/
drop table if exists PROJECT_INFO;

create table PROJECT_INFO
(
   PROJECT_ID           char(32) not null comment '项目记录主键，MD5(项目编号)',
   DEPT_ID              char(32) not null comment '项目所属部门',
   PROJECT_NO           varchar(64) not null comment '项目编号',
   PROJECT_NAME         varchar(128) comment '项目名称',
   PROJECT_AGENT		varchar(128) comment '经办人',
   PROJECT_OPER 		varchar(128) comment '业务负责人',
   PROJECT_STATS        char(32) not null comment '见数据字典：项目状态',
   WEEK_BEGIN           int(1) default 7 comment '周报开始星期 1--周一，2--周二  ...',
   HOURS_RULE			int(1) not null default 8 comment '一天工作日度量  默认是8小时为一个工作日',
   NEED_REPORT          int(1) not null default 1 comment '是否需要填写周报 0:不需要/1：需要',
   FILL_DATE            date not null comment '录入时间',
   WORK_DAYS			int default 0 comment '项目计划工作量',
   PROJECT_DES          varchar(256) comment '项目描述',
   primary key (PROJECT_ID)
);

alter table PROJECT_INFO comment '项目信息表';

/*==============================================================*/
/* Table: PROJECT_STATS                                         */
/*==============================================================*/
drop table if exists PROJECT_STATS;
create table PROJECT_STATS
(
   STATS_ID             char(32) not null comment '记录ID。无实际意义 MD5(UUID)',
   PROJECT_ID           char(32) not null comment '项目记录主键，MD5(项目编号)',
   MILESTONE_NAME       char(32) not null comment '里程碑名称（MILESTONE_NAME）见数据字典',
   PLAN_START           date comment '计划开始日期',
   PLAN_END             date comment '计划结束日期',
   REALITY_START        date comment '实际开始日期',
   REALITY_END          date comment '实际结束日期',
   MILESTONE_STATS      char(32) comment '里程碑状态（见数据字典）',
   PLAN_VERSION         varchar(32) comment '项目计划版本号',
   primary key (STATS_ID)
);

alter table PROJECT_STATS comment '项目状态跟踪表，记录项目启动、暂定、完成时间点';

drop table if exists PRO_TRANSFER_LOGS;

/*==============================================================*/
/* Table: PRO_TRANSFER_LOGS                                     */
/*==============================================================*/
create table PRO_TRANSFER_LOGS
(
   STATS_ID             char(32) not null comment '记录ID。无实际意义 MD5(UUID)',
   PROJECT_ID           char(32) not null comment '项目记录主键，MD5(项目编号)',
   USER_ID              char(32) comment '该ID作用范围为整个系统 MD5(身份证)',
   ROLE_TYPE            char(32) comment '角色记录PK，UUID',
   JOININ_DATE          date not null comment '加入时间',
   LEAVE_DATE           date comment '人员离开该项目时间',
   CURRT_STATS          int(1) not null default 1 comment '当前状态（0：无效/1：有效）',
   primary key (STATS_ID)
);

alter table PRO_TRANSFER_LOGS comment '项目人员异动表';

/*==============================================================*/
/* Table: EDAILY_REPORT                                         */
/*==============================================================*/
drop table if exists EDAILY_REPORT;
create table EDAILY_REPORT
(
   DREPORT_ID           char(32) not null comment '记录ID，无实际意义 MD5(UUID)',
   PROJECT_ID           char(32) not null comment '项目记录主键，MD5(项目编号)',
   USER_ID              char(32) not null comment '日报属主',
   DEPT_ID              char(32) not null comment '部门ID 与 人员ID 为组合主键  冗余',
   REPORT_TYPE			TINYINT UNSIGNED NOT NULL DEFAULT 0 comment '0/1 网银/主机',
   REPORT_DATE          date not null comment '日报日期',
   WORK_HOURS           int not null default 0 comment '工时数 （浮点 格式为 0）',
   WORK_TYPE            char(32) not null comment '工作类别:见数据字典',
   BEG_TYPE             char(32) comment '假期类别:见数据字典',
   WORK_ACTIVITY        char(32) comment '见数据字典',
   WORK_SUB_ACTIVITY    char(32) comment '见数据字典：项目子活动',
   WORK_STATS           char(32) comment '(网银)工作状态:见数据字典',
   WORK_SCHDE           float default 0 comment '主机工作进度',
   RECORDOR_ID          char(32) not null comment '填写人ID',
   RECORD_DATE          date not null comment '填写日期',
   DELAY_AFFECT			text comment '延迟影响原因',
   DELAY_SOLVE			text comment '延迟影响解决方案',
   WORK_CONTENT         text comment '工作内容',
   RESULTS_SHOW         text comment '成果物',
   REP_COMMENT          text comment '备注',
   APPEND_FFIELD        varchar(32) comment '附加字段1',
   APPEND_SFIELD        varchar(64) comment '附加字段2',
   APPEND_TFIELD        varchar(128) comment '附加字段3',
   APPEND_RFIELD        varchar(256) comment '附加字段4',
   primary key (DREPORT_ID)
);

alter table EDAILY_REPORT comment '员工日报明细，记录员工日报详细信息';

/*==============================================================*/
/* Table: EWEEKLY_REPORT                                        */
/*==============================================================*/
drop table if exists EWEEKLY_REPORT;
create table EWEEKLY_REPORT
(
   WREPORT_ID           char(32) not null comment '周报ID 无实际意义 MD5（UUID）',
   USER_ID              char(32) not null comment '周报属主 FK',
   START_DATE           date not null comment '起始日期',
   END_DATE             date not null comment '截至日期',
   DEPT_ID              char(32) comment '部门ID（UUID）',
   REPORT_NO            varchar(64) not null,
   RECORD_DATE          date not null comment '填写日期',
   UPDATE_DATE          timestamp comment '周报更改日期',
   RECORDOR_ID          char(32) not null comment '填写人ID',
   WORK_SUMMARY         text not null comment '工作总结',
   NWEEK_PLAN           text not null comment '下周工作计划',
   UNSOLVE_PROBLEM      text comment '问题与帮助',
   APPEND_FFIELD        varchar(32) comment '附加字段1',
   APPEND_SFIELD        varchar(64) comment '附加字段2',
   APPEND_TFIELD        varchar(128) comment '附加字段3',
   APPEND_RFIELD        varchar(256) comment '附加字段4',
   primary key (WREPORT_ID)
);

alter table EWEEKLY_REPORT comment '个人周报';

/*==============================================================*/
/* Table: PWEEKLY_REPORT                                        */
/*==============================================================*/
drop table if exists PWEEKLY_REPORT;
create table PWEEKLY_REPORT
(
   REPORT_ID            char(32) not null comment '周报ID :MD5（UUID）',
   PROJECT_ID           char(32) comment '项目记录主键，MD5(项目编号)',
   REPORT_TYPE          int(1) not null default 0 comment '周报类型  0/1 测试/开发 def=0',
   PSMER_ID             char(32) not null comment '项目当时PSM',
   USER_ID              char(32) not null comment '周报填写人（报表字段）',
   START_DATE           date not null comment '起始日期',
   END_DATE             date not null comment '截至日期',
   RECORD_DATE          date not null comment '填写日期',
   REPORT_NO            varchar(64) not null,
   EFFICIENCY_EXECUTE   char(32) comment '执行效率（见数据字典）',
   HUMAN_RESOURCE       char(32) comment '项目人力资源（见数据字典）',
   REQUIREMENT_ALTER    char(32) comment '需求变更频率（见数据字典）',
   CLIENT_RELATION      char(32) comment '客户关系（见数据字典）',
   PROJECT_DESC         text comment '项目描述',
   LEADER_APPRAISE      text comment '项目评价',
   RESULTS_SHOW         text comment '成果物',
   CWEEK_PLAN           text comment '本周计划',
   COMPLETE_PLAN        text comment '计划完成情况',
   NWEEK_PLAN           text comment '下周工作计划',
   APPEND_FFIELD        varchar(32) comment '附加字段1',
   APPEND_SFIELD        varchar(64) comment '附加字段2',
   APPEND_TFIELD        varchar(128) comment '附加字段3',
   APPEND_RFIELD        varchar(256) comment '附加字段4',
   primary key (REPORT_ID)
);

alter table PWEEKLY_REPORT comment '项目周报';

/*==============================================================*/
/* Table: PWEEKLY_MEMBERPLAN                                    */
/*==============================================================*/
drop table if exists PWEEKLY_MEMBERPLAN;

create table PWEEKLY_MEMBERPLAN
(
   DATA_ID              char(32) not null comment '周报ID :MD5（UUID）',
   REPORT_ID            char(32) not null comment '周报ID :MD5（UUID）',
   PROJECT_ID           char(32) not null comment '项目ID :MD5（UUID）',
   BRANCH_ID            char(32) comment '所在部门ID',
   USER_ID              char(32) not null comment '该ID作用范围为整个系统',
   WEEKLY_SUMMARY       text comment '本周总结',
   WEEKLY_PLAN          text comment '下周工作计划计划',
   APPEND_FFIELD        varchar(32) comment '附加字段1',
   APPEND_SFIELD        varchar(64) comment '附加字段2',
   APPEND_TFIELD        varchar(128) comment '附加字段3',
   APPEND_RFIELD        varchar(256) comment '附加字段4',
   primary key (DATA_ID)
);

alter table PWEEKLY_MEMBERPLAN comment '项目成员本周总结以及下周计划描述';

/*==============================================================*/
/* Table: PWEEKLY_PROBLEMS                                      */
/*==============================================================*/
drop table if exists PWEEKLY_PROBLEMS;
create table PWEEKLY_PROBLEMS
(
   PROBLEM_ID           char(32) not null comment '问题ID :MD5（UUID）',
   REPORT_ID            char(32) not null comment '周报ID :MD5（UUID）',
   DISCOVER_DATE        date not null comment '提出日期',
   SOLVE_DATE           date comment '计划解决日期',
   USER_NAME            varchar(32) not null comment '提出人（描述）',
   RESPOSIBLER          varchar(32) comment '该问题的负责人',
   PROBLEM_DESC         text not null,
   RESOLVE_WAY          text comment '解决措施',
   PROBLEM_STATS        char(32) not null comment '问题状态（见数据字典）',
   APPEND_FFIELD        varchar(32) comment '附加字段1',
   APPEND_SFIELD        varchar(64) comment '附加字段2',
   APPEND_TFIELD        varchar(128) comment '附加字段3',
   APPEND_RFIELD        varchar(256) comment '附加字段4',
   primary key (PROBLEM_ID)
);

alter table PWEEKLY_PROBLEMS comment '项目周报问题描述';

/*==============================================================*/
/* Table: TODO_LIST                                             */
/*==============================================================*/
drop table if exists TODO_LIST;

create table TODO_LIST
(
   TODO_ID              char(32) not null comment '逻辑ID 无实际意义',
   USER_ID              char(32) not null comment '记录人ID',
   ENTYR_DATE           date not null comment '记录日期',
   DEAD_DATE            date not null comment '截至日期',
   TODO_DES             varchar(256) not null comment '代办表述',
   STATUS               int(1) not null comment '完成情况 0/1 进行中/完成',
   primary key (TODO_ID)
);

alter table TODO_LIST comment '代办任务表';

/*==============================================================*/
/* Table: HOLIDAY_RULE                                          */
/*==============================================================*/
drop table if exists HOLIDAY_RULE;

create table HOLIDAY_RULE
(
   HOLIDAY_ID           char(32) not null comment '逻辑主键-UUID',
   USER_ID              char(32) not null comment '填写人ID',
   HOLIDAY_NAME         varchar(64) not null comment '规则名称',
   START_DATE           date not null comment '生效日期',
   END_DATE             date not null comment '规则作废日期',
   ENTRY_DATE           date not null comment '规则录入日',
   ENABLED              int(1) not null default 1 comment '是否激活 0/1 为激活/激活',
   DAY_TYPE             int(1) not null default 0 comment '类型 0/1 假期/上班',
   ADDITION_INFO        varchar(128) comment '附加说明',
   primary key (HOLIDAY_ID)
);

alter table HOLIDAY_RULE comment '节假日规则';

/*==============================================================*/
/* Table: CHECKING_RULE                                         */
/*==============================================================*/
drop table if exists CHECKING_RULE;

create table CHECKING_RULE
(
   RULE_ID              char(32) not null comment '逻辑主键-UUID',
   DEPT_ID              char(32) not null comment '部门ID（UUID），规则适用的部门',
   USER_ID              char(32) not null comment '填写人ID',
   RULE_NAME            varchar(64) not null comment '规则名称',
   START_DATE           date not null comment '生效日期',
   END_DATE             date not null comment '规则作废日期',
   BEGIN_WEEK           int(1) not null comment '开始周（1--7/周日---周六）',
   END_WEEK             int(1) not null comment '开始周（1--7/周日---周六）',
   BEGINWK_TIME         char(5) not null comment '上班时间 格式(mm:dd) 24 小时制',
   ENDWK_TIME           char(5) not null comment '下班时间 格式 mm:dd  24 小时制',
   ENTRYING_DATE        date not null comment '规则录入日 默认为当天',
   ENABLED              int(1) not null default 1 comment '是否激活 0/1 为激活/激活',
   primary key (RULE_ID)
);

alter table CHECKING_RULE comment '考勤规则定义表';

/*==============================================================*/
/* Table: CHECK_DETAIL                                          */
/*==============================================================*/
drop table if exists CHECK_DETAIL;

create table CHECK_DETAIL
(
   DETAIL_ID            char(32) not null comment '逻辑ID 无实际意义',
   USER_ID              char(32) not null comment '签到人员ID',
   RULE_ID              char(32) comment '采用规则ID CHECK_RULE',
   DEPT_ID              char(32) not null comment '部门ID（UUID）',
   DAY_STATS            int(1) not null comment '当日状态  0/1/2 正常/假期/休假',
   BEGIN_STATS          int(1) not null default 0 comment '签到情况:0/1/2/3  未签到/正常/迟到/不考核',
   END_STATS            int(1) not null default 0 comment '签到情况:0/1/2/3  未签退/正常/早退/不考核',
   CHECK_DATE           date not null comment '签到日期',
   BEGIN_CHECK          char(5) comment '签到时间 HH:mm',
   END_CHECK            char(5) comment '签退时间 HH:mm',
   COMMENTS             varchar(128) comment '附加说明',
   primary key (DETAIL_ID)
);

alter table CHECK_DETAIL comment '考勤明细表';

/*==============================================================*/
/* Table: JOB_DETAIL                                            */
/*==============================================================*/
drop table if exists JOB_DETAIL;

create table JOB_DETAIL
(
   DETAIL_ID           	char(32) not null comment '逻辑ID 无实际意义',
   JOB_ID               char(32) not null comment '运行任务ID',
   LAST_DATE            date not null comment '最后一次运行时间',
   SPLIT_DAY			int(1) not null default 1 comment '间隔时间  天为单位',
   CHAIN_NAME			VARCHAR(128) not null  comment '执行任务的责任链名称',
   primary key (DETAIL_ID)
);

alter table JOB_DETAIL comment '定时任务运行记录表';
